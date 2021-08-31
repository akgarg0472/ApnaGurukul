package com.akgarg.apnagurukul.service;

import com.akgarg.apnagurukul.entity.SellBookAd;
import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.firebase.FirebaseManager;
import com.akgarg.apnagurukul.helper.BookSellHelper;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.model.BuyBookRequest;
import com.akgarg.apnagurukul.model.ResponseMessage;
import com.akgarg.apnagurukul.repository.SellBookAdRepository;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@SuppressWarnings("deprecation")
@Service
public class BookService {

    private final SellBookAdRepository sellBookAdRepository;
    private final UsersRepository usersRepository;
    private final FirebaseManager firebaseManager;
    private final StandardPasswordEncoder standardPasswordEncoder;

    @Autowired
    public BookService(SellBookAdRepository sellBookAdRepository,
                       UsersRepository usersRepository,
                       FirebaseManager firebaseManager,
                       StandardPasswordEncoder standardPasswordEncoder) {
        this.sellBookAdRepository = sellBookAdRepository;
        this.usersRepository = usersRepository;
        this.firebaseManager = firebaseManager;
        this.standardPasswordEncoder = standardPasswordEncoder;
    }

    public ResponseMessage processBuyBook(String id, BuyBookRequest buyBookRequest) {
        Optional<SellBookAd> byId = this.sellBookAdRepository.findById(Integer.parseInt(id));
        SellBookAd sellBookAd = byId.orElse(null);

        if (sellBookAd == null) {
            return new ResponseMessage("Something wrong happen, please try again later", "high");
        }

        if (sellBookAd.getSellerEmail().equals(buyBookRequest.getEmail())) {
            return new ResponseMessage("Sorry, You can't purchase your own book.", "high");
        }

        EmailSender.sendEmail(sellBookAd.getSellerEmail(), "Update on your book sell Id#" + id,
                EmailMessages.sellBookQueryMessage(sellBookAd.getSellerName(),
                        sellBookAd.getBookTitle(),
                        sellBookAd.getId(),
                        buyBookRequest));

        return new ResponseMessage("", "none");
    }


    public ResponseMessage processBookSell(Principal principal, SellBookAd sellBookAd, String image) {
        ResponseMessage responseMessage = BookSellHelper.checkForErrors(principal, image, sellBookAd);
        if (responseMessage != null) {
            return responseMessage;
        }

        Users loggedInUser = this.usersRepository.getUserByUsername(principal.getName());
        if (loggedInUser == null) {
            System.out.println("user not found serious issue");
            return new ResponseMessage("You are not authenticated to perform this action", "high");
        }

        String bookFileName = loggedInUser.getName() + sellBookAd.getId() + sellBookAd.getBookTitle() + sellBookAd.getBookAuthor() + sellBookAd.getBookStream();
        String bookPhotoUrl = BookSellHelper.uploadBookImage(image, bookFileName, this.standardPasswordEncoder, this.firebaseManager);

        if (bookPhotoUrl.equals("error")) {
            System.out.println("Error uploading book photo");
            return new ResponseMessage("Error uploading book photo, please try again later", "high");
        }

        sellBookAd.setBookPhoto(bookPhotoUrl);
        sellBookAd.setSellerEmail(loggedInUser.getUsername());
        sellBookAd.setSellerName(loggedInUser.getName());
        sellBookAd.setSellerCity(loggedInUser.getCity());
        sellBookAd.setSellerState(loggedInUser.getState());
        sellBookAd.setSellerCountry(loggedInUser.getCountry());
        sellBookAd.setSellerPincode(loggedInUser.getPincode());
        this.sellBookAdRepository.save(sellBookAd);

        EmailSender.sendEmail(loggedInUser.getUsername(), "Book successfully listed for sale",
                EmailMessages.bookSellListSuccessMessage(
                        loggedInUser.getName(),
                        sellBookAd.getId(),
                        sellBookAd.getBookTitle(),
                        sellBookAd.getPublishingDate(),
                        sellBookAd.isDonateBook(),
                        sellBookAd.getSellingPrice()));

        return new ResponseMessage("Done", "none");
    }
}

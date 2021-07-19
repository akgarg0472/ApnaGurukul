package com.akgarg.apnagurukul.controllers.book;

import com.akgarg.apnagurukul.entity.SellBookAd;
import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.firebase.FirebaseManager;
import com.akgarg.apnagurukul.helper.BookSellHelper;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.model.ResponseMessage;
import com.akgarg.apnagurukul.repository.SellBookAdRepository;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

@SuppressWarnings({"deprecation", "FieldCanBeLocal", "unused"})
@Controller
public class SellBookController {

    private final SellBookAdRepository sellBookAdRepository;
    private final UsersRepository usersRepository;
    private final FirebaseManager firebaseManager;
    private final StandardPasswordEncoder standardPasswordEncoder;


    @Autowired
    public SellBookController(SellBookAdRepository sellBookAdRepository,
                              UsersRepository usersRepository,
                              FirebaseManager firebaseManager,
                              StandardPasswordEncoder standardPasswordEncoder) {
        this.sellBookAdRepository = sellBookAdRepository;
        this.usersRepository = usersRepository;
        this.firebaseManager = firebaseManager;
        this.standardPasswordEncoder = standardPasswordEncoder;
    }


    @RequestMapping(value = "/process-sell-book", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage processSellBook(Principal principal,
                                           @ModelAttribute SellBookAd sellBookAd,
                                           @RequestParam("image") String image) {
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
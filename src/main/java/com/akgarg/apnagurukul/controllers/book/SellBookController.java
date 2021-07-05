package com.akgarg.apnagurukul.controllers.book;

import com.akgarg.apnagurukul.entity.SellBookAd;
import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.firebase.FirebaseManager;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.model.ErrorMessage;
import com.akgarg.apnagurukul.repository.SellBookAdRepository;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

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


    private static String decode(String url) throws UnsupportedEncodingException {
        String prevURL = "";
        String decodeURL = url;

        while (!prevURL.equals(decodeURL)) {
            prevURL = decodeURL;
            decodeURL = URLDecoder.decode(decodeURL, "UTF-8");
        }
        return decodeURL;
    }


    @RequestMapping(value = "/process-sell-book", method = RequestMethod.POST)
    @ResponseBody
    public ErrorMessage processSellBook(Principal principal, @ModelAttribute SellBookAd sellBookAd, @RequestParam("image") String image) {
        if (principal == null) {
            return new ErrorMessage("You must login to perform this operation", "high");
        }

        if (image == null || image.length() < 23) {
            return new ErrorMessage("Invalid book photo", "high");
        }

        String response = verifyBookInformation(sellBookAd);
        if (!response.equals("valid")) {
            return new ErrorMessage(response, "high");
        }

        Users loggedInUser = this.usersRepository.getUserByUsername(principal.getName());
        if (loggedInUser == null) {
            System.out.println("user not found serious issue");
            return new ErrorMessage("You are not authenticated to perform this action", "high");
        }

        String bookFileName = loggedInUser.getName() + sellBookAd.getId() + sellBookAd.getBookTitle() + sellBookAd.getBookAuthor() + sellBookAd.getBookStream();
        String bookPhotoUrl = uploadBookImage(image, bookFileName);
        if (bookPhotoUrl.equals("error")) {
            System.out.println("Error uploading book photo");
            return new ErrorMessage("Error uploading book photo, please try again later", "high");
        }

        sellBookAd.setBookPhoto(bookPhotoUrl);
        sellBookAd.setSellerName(loggedInUser.getName());
        sellBookAd.setSellerCity(loggedInUser.getCity());
        sellBookAd.setSellerState(loggedInUser.getState());
        sellBookAd.setSellerCountry(loggedInUser.getCountry());
        sellBookAd.setSellerPincode(loggedInUser.getPincode());

        this.sellBookAdRepository.save(sellBookAd);
        EmailSender.sendEmail(loggedInUser.getUsername(), "Book successfully published for sale", EmailMessages.bookSellListSuccessMessage(loggedInUser.getName(), sellBookAd.getId(), sellBookAd.getBookTitle(), sellBookAd.getPublishingDate(), sellBookAd.isDonateBoook(), sellBookAd.getSellingPrice()));

        return new ErrorMessage("Done", "none");
    }


    // uploads book photo to cloud storage and returns the url of uploaded image
    private String uploadBookImage(String image, String filename) {
        String imageData;
        try {
            imageData = decode(image).replace(" ", "+");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }
        byte[] base64ImageDataArray = Base64.decodeBase64(imageData);

        return this.firebaseManager.upload(base64ImageDataArray, this.standardPasswordEncoder.encode(filename) + ".png");
    }

    private String verifyBookInformation(SellBookAd book) {
        if (book.getBookTitle() == null || book.getBookTitle().trim().equals("")) {
            System.out.println("invalid book title");
            return "Invalid book title";
        }

        if (book.getBookClass() == null || book.getBookClass().trim().equals("null")) {
            System.out.println("invalid book class");
            return "Please select book class";
        }

        if (book.getBookClass().trim().equals("eleventh") || book.getBookClass().trim().equals("twelfth")) {
            if (book.getBookStream() == null || book.getBookStream().trim().equals("null")) {
                System.out.println("invalid book stream");
                return "Please select book stream";
            }
        }

        if (book.getBookClass().trim().equals("UG") || book.getBookClass().trim().equals("PG")) {
            if (book.getAdditionalBookInfo() == null || book.getAdditionalBookInfo().trim().equals("null")) {
                System.out.println("invalid book additional info");
                return "Please enter additional info of book";
            }
        }

        if (!book.isDonateBoook()) {
            if (book.getOriginalPrice() < book.getSellingPrice()) {
                System.out.println("invalid book price");
                return "Invalid book pricing";
            }
        }

        if (book.getBookQuality() == null || book.getBookQuality().trim().equals("null")) {
            System.out.println("invalid book quality");
            return "Please select book quality";
        }

        return "valid";
    }
}

package com.akgarg.apnagurukul.helper;

import com.akgarg.apnagurukul.entity.SellBookAd;
import com.akgarg.apnagurukul.firebase.FirebaseManager;
import com.akgarg.apnagurukul.model.ResponseMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

@SuppressWarnings("deprecation")
public class BookSellHelper {

    // method to check if content of book is valid and according to specifications
    public static String verifyBookInformation(SellBookAd book) {
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

        if (!book.isDonateBook()) {
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


    // method to upload book photo to cloud storage and returns the url of uploaded image
    public static String uploadBookImage(String image, String filename,
                                         StandardPasswordEncoder standardPasswordEncoder,
                                         FirebaseManager firebaseManager) {
        String imageData;
        try {
            imageData = decode(image).replace(" ", "+");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }
        byte[] base64ImageDataArray = Base64.decodeBase64(imageData);

        return firebaseManager.upload(base64ImageDataArray, standardPasswordEncoder.encode(filename) + ".png");
    }


    // method to decode the book photo data string stream
    private static String decode(String url) throws UnsupportedEncodingException {
        String prevURL = "";
        String decodeURL = url;

        while (!prevURL.equals(decodeURL)) {
            prevURL = decodeURL;
            decodeURL = URLDecoder.decode(decodeURL, "UTF-8");
        }
        return decodeURL;
    }


    // method to check if there is any unwanted error or non login user trying to sell book
    public static ResponseMessage checkForErrors(Principal principal,
                                                 String image,
                                                 SellBookAd sellBookAd) {
        if (principal == null) {
            return new ResponseMessage("You must login to perform this operation", "high");
        }

        if (image == null || image.length() < 23) {
            return new ResponseMessage("Invalid book photo", "high");
        }

        String response = BookSellHelper.verifyBookInformation(sellBookAd);
        if (!response.equals("valid")) {
            return new ResponseMessage(response, "high");
        }

        return null;
    }
}
package com.akgarg.apnagurukul.helper;

import com.akgarg.apnagurukul.model.BuyBookRequest;

public class EmailMessages {

    public static String forgotPasswordOTPMessage(String toEmail,
                                                  String name,
                                                  String otp) {
        return "Dear " + "<strong>" + name + "</strong>," +
                "<br><br>" +
                "OTP to reset password of your account " + maskEmail(toEmail) + " is " +
                "<strong>" + otp + "</strong>." +
                " Please use this otp to reset your password." +
                "<br><br>" +
                "Regards" +
                "<br>" +
                "SKAK (Team ApnaGurukul)" +
                "<br>" +
                "India" +
                "<br><br>" +
                "<strong><p style=\"color: #7C6198;\">This email is automatically generated by the ApnaGurukul system. Please do not reply to this email.</p></strong>";
    }


    public static String registrationOTPMessage(String email,
                                                String name,
                                                int otp) {
        return "Dear " + "<strong>" + name + "</strong>," +
                "<br><br>" +
                "OTP to complete registration process of your account " + email + " is " +
                "<strong>" + otp + "</strong>. This OTP is valid for 5 minutes only. Please use this otp to complete the registration process." +
                "<br><br>" +
                "Regards" +
                "<br>" +
                "SKAK (Team ApnaGurukul)" +
                "<br>" +
                "India" +
                "<br><br>" +
                "<strong><p style=\"color: #7C6198;\">This email is automatically generated by the ApnaGurukul system. Please do not reply to this email.</p></strong>";
    }


    public static String registrationSuccessMessage(String email,
                                                    String name) {
        return "Dear " + "<strong>" + name + "</strong>," +
                "<br><br>" +
                "Greetings from team ApnaGurukul!!" +
                "<br><br>" +
                "It is to inform you that your account with username " + email + " is " +
                "successfully registered and verified with us. We are delighted to have you with us here at ApnaGurukul." +
                "<br><br>" +
                "Have a happy and cheerful experience with us." +
                "<br><br>" +
                "Thanks" +
                "<br>" +
                "Regards" +
                "<br>" +
                "SKAK (Team ApnaGurukul)" +
                "<br>" +
                "India" +
                "<br><br>" +
                "<strong><p style=\"color: #7C6198;\">This email is automatically generated by the ApnaGurukul system. Please do not reply to this email.</p></strong>";
    }


    public static String passwordSuccessfullyChangedMessage(String email,
                                                            String name) {
        return "Dear " + "<strong>" + name + "</strong>," +
                "<br><br>" +
                "Password of your account " + maskEmail(email) + " is " +
                "successfully changed. Use your new credentials to login on our portal." +
                "<br><br>" +
                "If you have not perform this action, it means someone is trying to change your account password. " +
                "We recommend you to change your account password immediately" +
                "<br><br>" +
                "Regards" +
                "<br>" +
                "SKAK (Team ApnaGurukul)" +
                "<br>" +
                "India" +
                "<br><br>" +
                "<strong><p style=\"color: #7C6198;\">This email is automatically generated by the ApnaGurukul system. " +
                "Please do not reply to this email.</p></strong>";
    }


    public static String bookSellListSuccessMessage(String name,
                                                    int id,
                                                    String bookTitle,
                                                    String publishingDate,
                                                    boolean donateBoook,
                                                    int sellingPrice) {
        if (donateBoook) {
            return "Dear " + "<strong>" + name + "</strong>," +
                    "<br><br>" +
                    "Congratulations!" +
                    "<br>" +
                    "Your book is successfully listed for selling. You have chose to donate your book for free of cost. " +
                    "You will receive email notification if someone queries or wish to purchase your book." +
                    "<br><br>" +
                    "Details of your listing is: " +
                    "<br>" +
                    "<strong>Listing id: </strong>" + id +
                    "<br>" +
                    "<strong>Title: </strong>" + bookTitle +
                    "<br>" +
                    "<strong>Listing Date: </strong>" + publishingDate +
                    "<br>" +
                    "<strong>Selling Price: </strong>" + 0 + " rs" +
                    "<br><br>" +
                    "We here at ApnaGurukul wish you good luck for your bright future." +
                    "<br><br>" +
                    "Regards" +
                    "<br>" +
                    "SKAK (Team ApnaGurukul)" +
                    "<br>" +
                    "India" +
                    "<br><br>" +
                    "<strong><p style=\"color: #7C6198;\">This email is automatically generated by the ApnaGurukul system. " +
                    "Please do not reply to this email.</p></strong>";
        } else {
            return "Dear " + "<strong>" + name + "</strong>," +
                    "<br><br>" +
                    "Congratulations!" +
                    "<br>" +
                    "Your book is successfully listed for selling. You will receive email notification if someone " +
                    "queries or wish to purchase your book." +
                    "<br><br>" +
                    "Details of your listing is: " +
                    "<br>" +
                    "<strong>Listing id: </strong>" + id +
                    "<br>" +
                    "<strong>Title: </strong>" + bookTitle +
                    "<br>" +
                    "<strong>Listing Date: </strong>" + publishingDate +
                    "<br>" +
                    "<strong>Selling Price: </strong>" + sellingPrice + " rs" +
                    "<br><br>" +
                    "Regards" +
                    "<br>" +
                    "SKAK (Team ApnaGurukul)" +
                    "<br>" +
                    "India" +
                    "<br><br>" +
                    "<strong><p style=\"color: #7C6198;\">This email is automatically generated by the ApnaGurukul system. " +
                    "Please do not reply to this email.</p></strong>";
        }
    }


    public static String sellBookQueryMessage(String sellerName,
                                              String bookTitle,
                                              int id,
                                              BuyBookRequest buyBookRequest) {
        return "Dear " + "<strong>" + sellerName + "</strong>," +
                "<br><br>" +
                "Someone has queried for your book having title <strong>" + bookTitle +
                "</strong> with book advertisement id#<strong>" + id + "</strong>. " +
                "We request you to please have a look at this request." +
                "<br><br>" +
                "Details of requester are: " +
                "<br>" +
                "<strong>Name: </strong>" + buyBookRequest.getName() +
                "<br>" +
                "<strong>Email: </strong>" + buyBookRequest.getEmail() +
                "<br>" +
                "<strong>Phone: </strong>" + buyBookRequest.getPhone() +
                "<br>" +
                "<strong>City: </strong>" + buyBookRequest.getCity() +
                "<br>" +
                "<strong>State: </strong>" + buyBookRequest.getState() +
                "<br>" +
                "<strong>Country: </strong>" + buyBookRequest.getCountry() +
                "<br>" +
                "<strong>Pincode: </strong>" + buyBookRequest.getPincode() +
                "<br>" +
                "<strong>Message: </strong>" + (buyBookRequest.getMessage().equals("") ? "No message" : buyBookRequest.getMessage()) +
                "<br><br>" +
                "Regards" +
                "<br>" +
                "SKAK (Team ApnaGurukul)" +
                "<br>" +
                "India" +
                "<br><br>" +
                "<strong>(Beware of fraud people during the whole process of online book selling or purchasing of books.)</strong>" +
                "<br><br>" +
                "<strong><p style=\"color: #7C6198;\">This email is automatically generated by the ApnaGurukul system. " +
                "Please do not reply to this email.</p></strong>";
    }


    private static String maskEmail(String email) {
        final String mask = "*****";
        final int indexOfAt = email.indexOf("@");

        if (indexOfAt > 3) {
            final int maskLen = Math.min(Math.max(indexOfAt / 2, 2), 5);
            final int start = (indexOfAt - maskLen) / 2;
            return email.substring(0, start) + mask.substring(0, maskLen) + email.substring(start + maskLen);
        }

        return email;
    }

}
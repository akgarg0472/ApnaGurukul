package com.akgarg.apnagurukul.helper;

public class EmailMessages {

    public static String forgotPasswordOTPMessage(String toEmail,
                                                  String name,
                                                  String otp) {
        return "Dear " + "<strong>" + name + "</strong>" +
                "<br><br>" +
                "OTP to reset password of your account " + toEmail + " is " +
                "<strong>" + otp + "</strong>. Use this otp to reset your password." +
                "<br>" +
                "<br><br>" +
                "Thanks" +
                "<br><br>" +
                "Regards" +
                "<br>" +
                "Team ApnaGurukul" +
                "<br>" +
                "India";
    }


    public static String registrationOTPMessage(String email,
                                                String name,
                                                int otp) {
        return "Dear " + "<strong>" + name + "</strong>" +
                "<br><br>" +
                "Thank you for registering on ApnaGurukul." +
                "<br>" +
                "OTP to complete registration process of your account with email " + email + " is " +
                "<strong>" + otp + "</strong>. This OTP is valid for 5 minutes only. Use this otp to complete the registration process." +
                "<br><br>" +
                "Thanks" +
                "<br><br>" +
                "Regards" +
                "<br>" +
                "Team ApnaGurukul" +
                "<br>" +
                "India";
    }


    public static String registrationSuccessMessage(String email,
                                                    String name) {
        return "Dear " + "<strong>" + name + "</strong>" +
                "<br><br>" +
                "Thank you for registering with us on ApnaGurukul." +
                "<br>" +
                "We are pleased to inform you that your account with email id '" + email + "' is " +
                "successfully registered and verified with us." +
                "<br>" +
                "Have a happy and cheerful experience with us." +
                "<br><br>" +
                "Thanks" +
                "<br>" +
                "Regards" +
                "<br>" +
                "Team ApnaGurukul" +
                "<br>" +
                "India";
    }


    public static String passwordSuccessfullyChangedMessage(String email,
                                                            String name) {
        return "Dear " + "<strong>" + name + "</strong>" +
                "<br><br>" +
                "Password of your account " + email + " is " +
                "successfully changed. Use your new credentials to login on our portal." +
                "<br>" +
                "If you have not perform this action, then we recommend you to change your password immediately" +
                "<br><br>" +
                "Thanks" +
                "<br>" +
                "Regards" +
                "<br>" +
                "Team ApnaGurukul" +
                "<br>" +
                "India";
    }
}
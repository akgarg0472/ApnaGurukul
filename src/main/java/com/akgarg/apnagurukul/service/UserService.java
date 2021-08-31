package com.akgarg.apnagurukul.service;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.firebase.FirebaseManager;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.helper.MyConstants;
import com.akgarg.apnagurukul.model.UpdateProfileUser;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@SuppressWarnings("deprecation")
@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final FirebaseManager firebaseManager;
    private final StandardPasswordEncoder standardPasswordEncoder;

    @Autowired
    public UserService(UsersRepository usersRepository,
                       FirebaseManager firebaseManager,
                       StandardPasswordEncoder standardPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.firebaseManager = firebaseManager;
        this.standardPasswordEncoder = standardPasswordEncoder;
    }


    public Users getUser(String username) {
        return this.usersRepository.findById(username).orElse(null);
    }


    public boolean updateUser(String loggedInUsername, HttpServletRequest request, UpdateProfileUser user) {
        Users dbUser = this.usersRepository.findById(loggedInUsername).orElse(null);
        if (dbUser == null || user == null || !user.getEmail().equals(dbUser.getUsername())) {
            return false;
        }

        if (user.getName() != null && !user.getName().equals("")) {
            dbUser.setName(user.getName());
        }

        if (user.getPhone() != null && !user.getPhone().equals("")) {
            dbUser.setPhone(user.getPhone());
        }

        if (user.getAddress() != null && !user.getAddress().equals("")) {
            dbUser.setAddress(user.getAddress());
        }

        if (user.getCity() != null && !user.getCity().equals("")) {
            dbUser.setCity(user.getCity());
        }

        if (user.getState() != null && !user.getState().equals("")) {
            dbUser.setState(user.getState());
        }

        if (user.getCountry() != null && !user.getCountry().equals("")) {
            dbUser.setCountry(user.getCountry());
        }

        if (user.getPincode() != null && !user.getPincode().equals("")) {
            dbUser.setPincode(user.getPincode());
        }

        if (request.getParameter("picture") != null) {
            String oldProfilePicture = dbUser.getProfilePicture();
            String profilePictureName = loggedInUsername + "-profile_picture";
            String profileImageUrl = this.firebaseManager.upload(Base64.getDecoder().decode(request.getParameter("picture")),
                    this.standardPasswordEncoder.encode(profilePictureName));

            if (!profileImageUrl.equals("error")) {
                dbUser.setProfilePicture(profileImageUrl);
                if (!oldProfilePicture.equals(MyConstants.DEFAULT_PROFILE_PICTURE)) {
                    boolean delete = this.firebaseManager.delete(oldProfilePicture);
                    System.out.println(delete ? "Old Profile picture deleted successfully" : "Failed to delete old profile picture");
                }
            }
        }

        this.usersRepository.save(dbUser);
        new Thread(() -> EmailSender.sendEmail(loggedInUsername, "Profile updated successfully",
                EmailMessages.profileUpdatedMessage(loggedInUsername, dbUser.getName()))).start();

        return true;
    }
}
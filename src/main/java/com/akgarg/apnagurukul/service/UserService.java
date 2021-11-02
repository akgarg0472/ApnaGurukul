package com.akgarg.apnagurukul.service;

import com.akgarg.apnagurukul.entity.FindStudent;
import com.akgarg.apnagurukul.entity.FindTeacher;
import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.firebase.FirebaseManager;
import com.akgarg.apnagurukul.helper.DateAndTimeMethods;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.helper.MyConstants;
import com.akgarg.apnagurukul.model.Notification;
import com.akgarg.apnagurukul.model.RecentActivity;
import com.akgarg.apnagurukul.model.ResponseMessage;
import com.akgarg.apnagurukul.model.UpdateProfileUser;
import com.akgarg.apnagurukul.repository.FindStudentRepository;
import com.akgarg.apnagurukul.repository.FindTeacherRepository;
import com.akgarg.apnagurukul.repository.SellBookAdRepository;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

@SuppressWarnings("deprecation")
@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final SellBookAdRepository sellBookAdRepository;
    private final FirebaseManager firebaseManager;
    private final StandardPasswordEncoder standardPasswordEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FindTeacherRepository findTeacherRepository;
    private final FindStudentRepository findStudentRepository;


    @Autowired
    public UserService(UsersRepository usersRepository,
                       SellBookAdRepository sellBookAdRepository,
                       FirebaseManager firebaseManager,
                       StandardPasswordEncoder standardPasswordEncoder,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       FindTeacherRepository findTeacherRepository,
                       FindStudentRepository findStudentRepository) {
        this.usersRepository = usersRepository;
        this.sellBookAdRepository = sellBookAdRepository;
        this.firebaseManager = firebaseManager;
        this.standardPasswordEncoder = standardPasswordEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.findTeacherRepository = findTeacherRepository;
        this.findStudentRepository = findStudentRepository;
    }


    public Users getUser(String username) {
        return this.usersRepository.findById(username).orElse(null);
    }


    public boolean updateUser(String loggedInUsername,
                              HttpServletRequest request,
                              UpdateProfileUser user) {
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

        List<RecentActivity> activities = dbUser.getActivities();
        activities.add(new RecentActivity("Profile updated", DateAndTimeMethods.getCurrentDate(), DateAndTimeMethods.getCurrentTime()));
        dbUser.setActivities(activities);
        this.usersRepository.save(dbUser);
        new Thread(() -> EmailSender.sendEmail(loggedInUsername, "Profile updated successfully",
                EmailMessages.profileUpdatedMessage(loggedInUsername, dbUser.getName()))).start();

        return true;
    }


    public ResponseMessage updatePassword(String email,
                                          String oldPassword,
                                          String newPassword,
                                          String confirmNewPassword) {
        Users loggedInUser = this.usersRepository.findById(email).orElse(null);

        if (loggedInUser == null) {
            return new ResponseMessage("User not found. Try again.", "Severe");
        }

        if (oldPassword == null || newPassword == null || confirmNewPassword == null
                || oldPassword.equals("") || newPassword.equals("") || confirmNewPassword.equals("")) {
            return new ResponseMessage("One of the password is blank", "Severe");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            return new ResponseMessage("New password and confirm new passwords didn't match.", "High");
        }

        if (!this.bCryptPasswordEncoder.matches(oldPassword, loggedInUser.getPassword())) {
            return new ResponseMessage("Old password is invalid", "High");
        }

        loggedInUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
        List<RecentActivity> activities = loggedInUser.getActivities();
        activities.add(new RecentActivity("Account password changed",
                DateAndTimeMethods.getCurrentDate(), DateAndTimeMethods.getCurrentTime()));
        loggedInUser.setActivities(activities);
        this.usersRepository.save(loggedInUser);
        EmailSender.sendEmail(email, "Password changed successfully",
                EmailMessages.passwordSuccessfullyChangedMessage(email, loggedInUser.getName()));

        return new ResponseMessage("Password changed successfully", "none");
    }


    public void cleanRecentActivities(Users user) {
        List<RecentActivity> activities = user.getActivities();

        if (activities.size() > 15) {
            this.usersRepository.deleteUserActivities(user.getUsername());
            activities.remove(0);
            user.setActivities(activities);
            this.usersRepository.save(user);
        }
    }


    public void cleanRecentNotifications(Users user) {
        List<Notification> notifications = user.getNotifications();

        if (notifications.size() > 15) {
            this.usersRepository.deleteUserNotifications(user.getUsername());
            notifications.remove(0);
            user.setNotifications(notifications);
            this.usersRepository.save(user);
        }
    }


    public int getTotalBooksAdvertisements(String username) {
        return this.sellBookAdRepository.getAllBySellerEmail(username).size();
    }


    public boolean addTeacher(String loggedInUsername, FindTeacher teacher) {
        if (loggedInUsername != null && !loggedInUsername.equals("")) {
            Users currentlyLoggedInUser = this.getUser(loggedInUsername);
            teacher.setUserEmail(currentlyLoggedInUser.getUsername());
            FindTeacher savedTeacher = this.findTeacherRepository.save(teacher);

            if (savedTeacher.getId() != -1) {
                List<RecentActivity> activities = currentlyLoggedInUser.getActivities();
                activities.add(new RecentActivity("Added teacher id: " + savedTeacher.getId(),
                        DateAndTimeMethods.getCurrentDate(), DateAndTimeMethods.getCurrentTime()));
                currentlyLoggedInUser.setActivities(activities);
                this.usersRepository.save(currentlyLoggedInUser);

                return true;
            }
        }

        // something wrong happended, so return false to indicate that teacher is not saved and nothing is updated in the database
        return false;
    }


    public boolean addStudent(String loggedInUsername, FindStudent student) {
        if (loggedInUsername != null && student != null) {
            Users currentlyLoggedInUser = this.getUser(loggedInUsername);

            if (currentlyLoggedInUser != null) {
                student.setUserEmail(currentlyLoggedInUser.getUsername());
                FindStudent savedStudent = this.findStudentRepository.save(student);

                // debugging purpose only
                System.out.println(savedStudent);

                if (savedStudent.getId() != -1) {
                    List<RecentActivity> activities = currentlyLoggedInUser.getActivities();
                    activities.add(new RecentActivity("Added student id: " + savedStudent.getId(), DateAndTimeMethods.getCurrentDate(), DateAndTimeMethods.getCurrentTime()));
                    currentlyLoggedInUser.setActivities(activities);
                    this.usersRepository.save(currentlyLoggedInUser);

                    return true;
                }
            }
        }

        // something wrong happended, so return false to indicate that student is not saved and nothing is updated in the database
        return false;
    }
}
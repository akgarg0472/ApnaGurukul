package com.akgarg.apnagurukul.controllers.user;

import com.akgarg.apnagurukul.entity.FindStudent;
import com.akgarg.apnagurukul.entity.FindTeacher;
import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.model.*;
import com.akgarg.apnagurukul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpSession session,
                            Principal principal,
                            Model model) {
        if (session.getAttribute("buyBookLogin") != null) {
            session.removeAttribute("buyBookLogin");
            return "redirect:/buy-book";
        } else if (session.getAttribute("sellBookLogin") != null) {
            session.removeAttribute("sellBookLogin");
            return "redirect:/sell-book";
        }

        if (principal != null) {
            Users user = this.userService.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("totalAds", this.userService.getTotalBooksAdvertisements(user.getUsername()));
                model.addAttribute("sponsored", 23);
                model.addAttribute("", 23);
                model.addAttribute("user", user);
                return "user/dashboard";
            }
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String myProfile(Principal principal,
                            Model model) {
        if (principal != null) {
            Users user = this.userService.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("user", user);
                return "user/my-profile";
            }
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/update-profile", method = RequestMethod.GET)
    public String updateProfile(Principal principal,
                                Model model) {
        if (principal != null) {
            Users user = this.userService.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("user", user);
                return "user/update-profile";
            }
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public String myNotifications(Principal principal,
                                  Model model) {
        if (principal != null) {
            Users user = this.userService.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                this.userService.cleanRecentNotifications(user);
                model.addAttribute("name", user.getName());
                List<Notification> notifications = user.getNotifications();
                Collections.reverse(notifications);
                model.addAttribute("notifications", notifications);
                return "user/my-notifications";
            }
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/recent-activities", method = RequestMethod.GET)
    public String recentActivities(Principal principal,
                                   Model model) {
        if (principal != null) {
            Users user = this.userService.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                this.userService.cleanRecentActivities(user);
                model.addAttribute("name", user.getName());
                List<RecentActivity> activities = user.getActivities();
                Collections.reverse(activities);
                model.addAttribute("activities", activities.stream().limit(15).collect(Collectors.toList()));
                return "user/recent-activities";
            }
        }

        return "redirect:/login";
    }


    @RequestMapping(value = "/add-student", method = RequestMethod.GET)
    public String addStudent() {
        return "user/add-student";
    }


    @RequestMapping(value = "/add-student", method = RequestMethod.POST)
    public ResponseEntity<?> addStudent(Principal principal, @ModelAttribute FindStudent student) {
        if (principal != null) {
            boolean addStudent = this.userService.addStudent(principal.getName(), student);

            return addStudent ?
                    ResponseEntity.status(OK).body(
                            new AddTeacherResponse(CREATED.value(), "Student added successfully")
                    )
                    :
                    ResponseEntity.status(OK).body(
                            new AddTeacherResponse(BAD_REQUEST.value(), "Add student failed")
                    );
        }

        return ResponseEntity.status(UNAUTHORIZED)
                .body(new AddTeacherResponse(UNAUTHORIZED.value(), "Please login again!!"));
    }


    @RequestMapping(value = "/add-teacher", method = RequestMethod.GET)
    public String addTeacher() {
        return "user/add-teacher";
    }


    @RequestMapping(value = "add-teacher", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addTeacher(Principal principal, @ModelAttribute FindTeacher teacher) {
        if (principal != null) {
            boolean addTeacher = this.userService.addTeacher(principal.getName(), teacher);

            return addTeacher ?
                    ResponseEntity.status(OK).body(
                            new AddTeacherResponse(CREATED.value(), "Teacher added successfully")
                    )
                    :
                    ResponseEntity.status(OK).body(
                            new AddTeacherResponse(BAD_REQUEST.value(), "Add teacher failed")
                    );
        }

        return ResponseEntity.status(UNAUTHORIZED)
                .body(new AddTeacherResponse(UNAUTHORIZED.value(), "Please login again!!"));
    }


    @RequestMapping(value = "/change-password", method = RequestMethod.GET)
    public String changePassword() {
        return "user/change-password";
    }


    @RequestMapping(value = "/process-upwd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updatePassword(Principal principal,
                                          @RequestParam("oldPass") String oldPassword,
                                          @RequestParam("newPass") String newPassword,
                                          @RequestParam("cNewPass") String confirmNewPassword) {
        if (principal != null) {
            return this.userService.updatePassword(principal.getName(), oldPassword, newPassword, confirmNewPassword);
        }

        return new ResponseMessage("Something went wrong, Please try again", "High");
    }


    @RequestMapping(value = "/process-up", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateProfile(Principal principal,
                                 HttpServletRequest request,
                                 @ModelAttribute UpdateProfileUser user) {
        if (principal != null) {
            return this.userService.updateUser(principal.getName(), request, user);
        } else {
            return false;
        }
    }


    @SuppressWarnings("SpringMVCViewInspection")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String userLogout() {
        return "redirect:/logout";
    }
}

package com.laptopshop.laptop_shop.controller.admin;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.laptopshop.laptop_shop.domain.User;
import com.laptopshop.laptop_shop.service.UploadService;
import com.laptopshop.laptop_shop.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    // DI : denpendency injection (khong nen dung anotision autowired)
    private final UserService userService;

    private final UploadService uploadService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {

        model.addAttribute("eric", "Hello Eric");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getAdminUserPage(Model model) {
        // model.addAttribute("newUser", new User());
        model.addAttribute("users", this.userService.getAllUsers());
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable("id") Long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUserUpdatePage(Model model, @PathVariable("id") Long id) {
        // model.addAttribute("newUser", new User());
        User user = this.userService.getUserById(id);
        model.addAttribute("newUser", user);
        // model.addAttribute("id", id);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUserUpdatePage(Model model, @ModelAttribute("newUser") User user,
            @RequestParam("kieuanhdevFile") MultipartFile file) {
        User user1 = this.userService.getUserById(user.getId());
        if (user1 != null) {
            user1.setFullName(user.getFullName());
            // user1.setEmail(user.getEmail());
            user1.setPhone(user.getPhone());
            user1.setAddress(user.getAddress());
            user1.setRole(this.userService.getRoleByName(user.getRole().getName()));
            String avatar = this.uploadService.handelSaveUploadFile(file, "avatar");
            user1.setAvatar(avatar);
            this.userService.handleSaveUser(user1);
        }
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/create") // get
    public String getAdminUserCreate(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST) // post
    public String createAdminUserPage(Model model,
            @ModelAttribute("newUser") @Valid User user,
            BindingResult newUserBindingResult,
            @RequestParam("kieuanhdevFile") MultipartFile file) {

        // valodate
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
        }

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String avatar = this.uploadService.handelSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        // System.out.println("Create user" + user);
        // this.userService.handleSaveUser(user);

        user.setAvatar(avatar);
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("id", id);
        User user = new User();
        user.setId(id);
        model.addAttribute("newUser", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUserPage(Model model, @ModelAttribute("newUser") User newUser) {
        // TODO: process POST request

        // System.out.println("Delete user" + newUser);
        this.userService.deleteUserById(newUser.getId());
        return "redirect:/admin/user";
    }

}

package com.antonklintsevich.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.antonklintsevich.entity.User;
import com.antonklintsevich.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/addbook")
    public int addBook(@RequestParam("userId") String userId, @RequestParam("bookId") String bookId) {
        userService.addBooktoUser(Long.parseLong(userId), Long.parseLong(bookId));
        return 322;
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userService.getall();
    }

}

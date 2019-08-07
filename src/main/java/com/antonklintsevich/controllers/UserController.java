package com.antonklintsevich.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.common.UserDto;
import com.antonklintsevich.services.UserService;

@RestController

public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/users/addbook")
    public void addBook(@RequestParam("userId") String userId, @RequestParam("bookId") String bookId) {
        userService.addBookToUser(Long.parseLong(userId), Long.parseLong(bookId));

    }
    @PutMapping("/users/addrole")
    public void addRole(@RequestParam("userId") String userId, @RequestParam("roleId") String roleId) {
        userService.addRoleToUser(Long.parseLong(userId), Long.parseLong(roleId));

    }

    @GetMapping("/users")
    @ResponseBody
    public List<UserDto> getAllUsers() {

        return userService.getAllUserAsUserDTO();
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }

    @PostMapping("/users")
    public void create(@RequestBody UserDto dto) {
        userService.create(dto);

    }

    @GetMapping("/users/{userId}")
    @ResponseBody
    public UserDto getUserbyId(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/users/{userId}")
    public void update(@PathVariable("userId") Long userId, @RequestBody UserDto userDto) {
        userService.update(userId, userDto);
    }
    @GetMapping("/users/{userId}/roles")
    @ResponseBody
    public Set<BookDto> getAllUserRoles(@PathVariable("userId") Long userId) {
        return userService.getAllUserBooksAsBookDto(userId);
    }
    @GetMapping("/users/{userId}/books")
    @ResponseBody
    public Set<BookDto> getAllUserBooks(@PathVariable("userId") Long userId) {
        return userService.getAllUserBooksAsBookDto(userId);
    }
}

package com.antonklintsevich.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @RequestMapping("/users/addbook")
    public int addBook(@RequestParam("userId") String userId, @RequestParam("bookId") String bookId) {
        userService.addBooktoUser(Long.parseLong(userId), Long.parseLong(bookId));
        return 322;
    }
    
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        
        return userService.getAllUserAsUserDTO();
    }
  
    @RequestMapping(value="/users/{userId}",method=RequestMethod.DELETE)
    public void delete(@PathVariable("userId")Long userId) {
        userService.Delete(userId);
    }
    @PostMapping("/users")
    public void create(@RequestBody UserDto dto) {
        userService.Create(dto);
        
    }
    @RequestMapping(value="/users/{userId}",method=RequestMethod.GET)
    @ResponseBody
    public UserDto getUserbyId(@PathVariable("userId")Long userId) {
        return userService.getUserById(userId);
    }
    @RequestMapping(value="/users/{userId}",method=RequestMethod.PUT)
    public void update(@PathVariable("userId")Long userId,@RequestBody UserDto userDto) {
        userService.Update(userId, userDto);
    }
    @RequestMapping(value="/users/{userId}/books",method=RequestMethod.GET)
    @ResponseBody
    public Set<BookDto> getAllUserBooks(@PathVariable("userId")Long userId) {
        return userService.getAllUserBooksAsBookDto(userId);
    }
}

package com.antonklintsevich.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.antonklintsevich.common.OrderDto;
import com.antonklintsevich.services.OrderService;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PutMapping("/orders/addbook")
    public void addBook(@RequestParam("orderId") Long orderId, @RequestParam("bookId") Long bookId) {
        orderService.addBookToOrder(orderId, bookId);

    }

    @DeleteMapping("/orders/deletebook")
    public void deleteBook(@RequestParam("orderId") Long orderId, @RequestParam("bookId") Long bookId) {
        orderService.deleteBookFromOrder(orderId, bookId);

    }

    @GetMapping("/orders")
    @ResponseBody
    public List<OrderDto> getAllBooks() {

        return orderService.getAllOrdersAsOrderDTO();
    }

    @DeleteMapping("/orders/{orderId}")
    public void delete(@PathVariable("orderId") Long orderId) {
        orderService.delete(orderId);
    }

    @PostMapping("/orders")
    public void create(@RequestParam("userId") Long userId, @RequestParam("bookId") Long... bookId) {

        orderService.create(userId, bookId);

    }

    @GetMapping("/orders/{orderId}")
    @ResponseBody
    public OrderDto getOrderbyId(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/orders/{orderId}")
    public void update(@PathVariable("orderId") Long orderId, @RequestBody OrderDto orderDto) {
        orderService.update(orderId, orderDto);
    }
}

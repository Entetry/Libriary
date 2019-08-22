package com.antonklintsevich;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.common.FilterData;
import com.antonklintsevich.common.FilterType;
import com.antonklintsevich.common.GiftDto;
import com.antonklintsevich.common.OrderDto;
import com.antonklintsevich.common.SortData;
import com.antonklintsevich.common.UserDto;
import com.antonklintsevich.common.SearchParameters;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
//public static void main(String[] args)throws JsonProcessingException  {
//          GiftDto giftDto =new GiftDto();
//          giftDto.setRecipientName("entetry");
//          giftDto.setBookDtos(new HashSet<BookDto>());
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper.writeValueAsString(giftDto));
//        BCryptPasswordEncoder bp=new BCryptPasswordEncoder();
//        System.out.println(bp.encode("781227"));
//        UserDto userDto = new UserDto();
//
//        OrderDto orderDto=new OrderDto();
//        orderDto.setOrderdate(new Date());
//        orderDto.setUserDto(userDto);
//      ObjectMapper mapper = new ObjectMapper();
//    System.out.println(mapper.writeValueAsString(orderDto));
//        user.setDob(new Date());
//        user.setEmail("kek@gmail.com");
//        user.setFirstname("dima");
//        user.setLastname("pidor");
//        user.setUsername("Username");
//        user.setEnabled(true);
//      ObjectMapper mapper = new ObjectMapper();
//      System.out.println(mapper.writeValueAsString(user));
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper.writeValueAsString(user));
//        System.out.println(UUID.randomUUID().toString());
//        Book book=new Book();
//        book.setAuthor("Vova");
//        book.setBookname("Haduken");
//        book.setDateAdd(new Date());
//        book.setNumberOfPages(333);
//        System.out.println(mapper.writeValueAsString(book));
//        Order order=new Order();
//        order.setUser(user);
//        order.setBooks(user.getBooks());
//        System.out.println(mapper.writeValueAsString(order));
//        BookDto dto=new BookDto();
//        dto.setAuthor("author");
//        dto.setBookname("name");
//        dto.setDateAdd(new Date());
//        dto.setBookname("bookname");
//        dto.setPrice(new BigDecimal("0.05"));
//        ObjectMapper mapper = new ObjectMapper();
 //       System.out.println(mapper.writeValueAsString(dto));
//        SearchParameters searchPatameters=new SearchParameters();
//        List<FilterData> filterData=new ArrayList<>();
//        filterData.add(new FilterData("bookname","Bra",FilterType.LIKE));
//        filterData.add(new FilterData());
//        List<SortData> searchDatas=new ArrayList<>();
//        searchDatas.add(new SortData());
//        searchDatas.add(new SortData());
//        searchDatas.add(new SortData());
//        searchPatameters.setSearchData(searchDatas);
//        searchPatameters.setFilterData(filterData);
//        ObjectMapper mapper = new ObjectMapper();
//      System.out.println(mapper.writeValueAsString(searchPatameters));
 //}
}

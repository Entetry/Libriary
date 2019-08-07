package com.antonklintsevich.common;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.entity.User;

public class DtoConverter {
    public static BookDto constructBookDTO(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setSubgenres(book.getSubgenres());
        bookDto.setId(book.getId());
        bookDto.setName(book.getBookname());
        bookDto.setNumberOfPages(book.getNumberOfPages());
        bookDto.setDateAdd(book.getDateAdd());

        return bookDto;
    }
   public static Book constructBookFromDto(BookDto dto) {
       Book book=new Book();
       book.setAuthor(dto.getAuthor());
       book.setBookname(dto.getName());
       book.setSubgenres(dto.getSubgenres());
       book.setNumberOfPages(dto.getNumberOfPages());
       book.setDateAdd(dto.getDateAdd());
       book.setId(dto.getId());
 
       return book;
   }
   public static UserDto constructUserDto(User user) {
            UserDto dto=new UserDto();
            dto.setFirstname(user.getFirstname());
            dto.setLastname(user.getLastname());
            dto.setEmail(user.getEmail());
            dto.setDob(user.getDob());
            dto.setId(user.getId());
            dto.setPassword(user.getPassword());
            dto.setUsername(user.getUsername());
            return dto;
   }
   public static User constructUserFromDto(UserDto dto) {
       User user=new User();
       user.setDob(dto.getDob());
       user.setEmail(dto.getEmail());
       user.setFirstname(dto.getFirstname());
       user.setLastname(dto.getLastname());
       if (dto.getId() !=null) {
           user.setId(dto.getId());
       }
       user.setPassword(dto.getPassword());
       user.setUsername(dto.getUsername());
       return user;
       
   }
   public static OrderDto constructOrderDTO(Order order) {
       OrderDto orderDto = new OrderDto();
       orderDto.setBooks(order.getBooks());
       orderDto.setOrderdate(order.getOrderdate());
       orderDto.setPrice(order.getPrice());
       orderDto.setUser(order.getUser());
       orderDto.setId(order.getId());

       return orderDto;
   }
  public static Order constructOrderFromDto(OrderDto orderDto) {
      Order order=new Order();
      order.setUser(orderDto.getUser());
      order.setPrice(orderDto.getPrice());
      order.setUser(orderDto.getUser());
      if(orderDto.getId()!=null) {
          order.setId(orderDto.getId());
      }
      order.setBooks(orderDto.getBooks());

      return order;
  }
}

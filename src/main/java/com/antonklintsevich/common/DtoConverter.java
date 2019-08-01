package com.antonklintsevich.common;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.User;

public class DtoConverter {
    public static BookDto constructBookDTO(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setGenre(book.getGenre());
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setNumberOfPages(book.getNumberOfPages());

        return bookDto;
    }
   public static Book constructBookFromDto(BookDto dto) {
       Book book=new Book();
       book.setAuthor(dto.getAuthor());
       book.setName(dto.getName());
       book.setGenre(dto.getGenre());
       book.setNumberOfPages(dto.getNumberOfPages());

       if (dto.getId() != null) {
           book.setId(dto.getId());
       }
       
       return book;
   }
   public static UserDto constructUserDto(User user) {
            UserDto dto=new UserDto();
            dto.setFirstname(user.getFirstname());
            dto.setLastname(dto.getLastname());
            dto.setEmail(user.getEmail());
            dto.setDob(user.getDob());
            dto.setId(user.getId());
            return dto;
   }
   public static User constructUserFromDto(UserDto dto,String password) {
       User user=new User();
       user.setDob(dto.getDob());
       user.setEmail(dto.getEmail());
       user.setFirstname(dto.getFirstname());
       user.setLastname(dto.getLastname());
       if (dto.getId() !=null) {
           user.setId(dto.getId());
       }
       user.setPassword(password);
       return user;
       
   }
}

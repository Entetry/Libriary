package com.antonklintsevich.common;

import java.util.HashSet;
import java.util.Set;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Genre;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.entity.Role;
import com.antonklintsevich.entity.Subgenre;
import com.antonklintsevich.entity.User;

public class DtoConverter {
    public static BookDto constructBookDTO(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setSubgenres(constructSubgenreDtoSet(book.getSubgenres()));
        bookDto.setId(book.getId());
        bookDto.setBookname(book.getBookname());
        bookDto.setNumberOfPages(book.getNumberOfPages());
        bookDto.setDateAdd(book.getDateAdd());
        bookDto.setPrice(book.getPrice());

        return bookDto;
    }

    public static Set<BookDto> constructBookDtoSet(Set<Book> books) {
        Set<BookDto> bookDtos = new HashSet<BookDto>();
        for (Book book : books) {
            bookDtos.add(constructBookDTO(book));
        }
        return bookDtos;

    }

    public static Set<Book> constructBookSet(Set<BookDto> bookDtos) {
        Set<Book> books = new HashSet<Book>();
        for (BookDto bookDto : bookDtos) {
            books.add(constructBookFromDto(bookDto));
        }
        return books;

    }

//    public static Set<GenreDto> constructGenreDtoSet(Set<Genre> genres){
//        Set<GenreDto> genreDtos=new HashSet<>(); 
//        for(Genre genre:genres) {
//            genreDtos.add(constructGernreDto(genre));
//        }
//        return genreDtos;
//        
//    }
//    public static Set<Genre> constructGenreSet(Set<GenreDto> genreDtos){
//        Set<Genre> genres=new HashSet<>(); 
//        for(GenreDto genreDto:genreDtos) {
//            genres.add(constructGenreFromDto(genreDto));
//        }
//        return genres;
//        
//    }
    public static Genre constructGenreFromDto(GenreDto genreDto) {
        Genre genre = new Genre();
        genre.setGenrename(genreDto.getGenrename());
        if (genreDto.getId() != null) {
            genre.setId(genreDto.getId());
        }
        return genre;
    }

    public static GenreDto constructGernreDto(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setGenrename(genre.getGenrename());
        return genreDto;
    }

    public static Set<SubgenreDto> constructSubgenreDtoSet(Set<Subgenre> subgenres) {
        Set<SubgenreDto> subgenreDtos = new HashSet<>();
        for (Subgenre subgenre : subgenres) {
            subgenreDtos.add(constructGernreDto(subgenre));
        }
        return subgenreDtos;

    }

    public static Set<Subgenre> constructSubgenreSet(Set<SubgenreDto> subgenreDtos) {
        Set<Subgenre> subgenres = new HashSet<>();
        for (SubgenreDto subgenreDto : subgenreDtos) {
            subgenres.add(constructSubgenreFromDto(subgenreDto));
        }
        return subgenres;

    }

    public static Subgenre constructSubgenreFromDto(SubgenreDto subgenreDto) {
        Subgenre subgenre = new Subgenre();
        subgenre.setSubgenrename(subgenreDto.getSubgenrename());
        subgenre.setGenre(constructGenreFromDto(subgenreDto.getGenreDto()));
        if (subgenreDto.getId() != null) {
            subgenre.setId(subgenreDto.getId());
        }
        return subgenre;
    }

    public static SubgenreDto constructGernreDto(Subgenre subgenre) {
        SubgenreDto subgenreDto = new SubgenreDto();
        subgenreDto.setId(subgenre.getId());
        subgenreDto.setSubgenrename(subgenre.getSubgenrename());
        subgenreDto.setGenreDto(constructGernreDto(subgenre.getGenre()));
        return subgenreDto;
    }

    public static Role constructRoleFromDto(RoleDto roleDto) {
        Role role = new Role();
        if (roleDto.getId() != null)
            role.setId(roleDto.getId());
        role.setRolename(roleDto.getRolename());
        return role;
    }

    public static RoleDto constructRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setRolename(role.getRolename());
        return roleDto;

    }

    public static Set<RoleDto> constructRoleDtoSet(Set<Role> roles) {
        Set<RoleDto> roleDtos = new HashSet<>();
        for (Role role : roles) {
            roleDtos.add(constructRoleDto(role));
        }
        return roleDtos;

    }

    public static Set<Role> constructRoleSet(Set<RoleDto> roleDtos) {
        Set<Role> roles = new HashSet<>();
        for (RoleDto roleDto : roleDtos) {
            roles.add(constructRoleFromDto(roleDto));
        }
        return roles;

    }

    public static Book constructBookFromDto(BookDto dto) {

        Book book = new Book();
        book.setAuthor(dto.getAuthor());
        book.setBookname(dto.getBookname());
        book.setNumberOfPages(dto.getNumberOfPages());
        book.setDateAdd(dto.getDateAdd());
        book.setId(dto.getId());
        book.setPrice(dto.getPrice());

        return book;
    }

    public static UserDto constructUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setDob(user.getDob());
        dto.setId(user.getId());
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        dto.setBooks(constructBookDtoSet(user.getBooks()));
        dto.setRoles(constructRoleDtoSet(user.getRoles()));
        dto.setEnabled(user.isEnabled());
        return dto;
    }

    public static User constructUserFromDto(UserDto dto) {
        User user = new User();
        user.setDob(dto.getDob());
        user.setEmail(dto.getEmail());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        return user;

    }

    public static OrderDto constructOrderDTO(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setBookDtos(constructBookDtoSet(order.getBooks()));
        orderDto.setOrderdate(order.getOrderdate());
        orderDto.setPrice(order.getPrice());
        orderDto.setUserDto(constructUserDto(order.getUser()));
        orderDto.setId(order.getId());

        return orderDto;
    }

    public static Order constructOrderFromDto(OrderDto orderDto) {
        Order order = new Order();
        order.setUser(constructUserFromDto(orderDto.getUserDto()));
        order.setPrice(orderDto.getPrice());
        if (orderDto.getId() != null) {
            order.setId(orderDto.getId());
        }
        order.setBooks(constructBookSet(orderDto.getBookDtos()));

        return order;
    }
}

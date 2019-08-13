package com.antonklintsevich.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.antonklintsevich.common.BookDto;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.common.UserDto;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Role;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.exception.BookNotFoundException;
import com.antonklintsevich.exception.RoleNotFoundException;
import com.antonklintsevich.exception.UserNotFoundException;
import com.antonklintsevich.persistense.BookRepository;
import com.antonklintsevich.persistense.DbUnit;
import com.antonklintsevich.persistense.RoleRepository;
import com.antonklintsevich.persistense.UserRepository;

@Service
public class UserService {//implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RoleRepository roleRepository;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    public List<UserDto> getAllUserAsUserDTO() {
        List<UserDto> userDtos = new ArrayList<UserDto>();

        for (User user : getAllUsers()) {
            userDtos.add(DtoConverter.constructUserDto(user));
        }

        return userDtos;
    }

    public List<User> getAllUsers() {
        Session session = DbUnit.getSessionFactory().openSession();

        List<User> users = new ArrayList<>();
        try {
            users.addAll(userRepository.findAll(session));
        }

        finally {
            session.close();
        }
        return users;
    }

    public void addBookToUser(Long userId, Long bookId) {

        Session session = DbUnit.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        System.out.println("Adding a book...");
        User user = userRepository.findOne(userId, session).orElseThrow(UserNotFoundException::new);
        user.addBook(bookRepository.findOne(bookId, session).orElseThrow(BookNotFoundException::new));
        try {
            userRepository.update(user, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    public void addRoleToUser(Long userId, Long roleId) {

        Session session = DbUnit.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        System.out.println("Adding a role...");
        User user = userRepository.findOne(userId, session).orElseThrow(UserNotFoundException::new);
        user.addRole(roleRepository.findOne(roleId, session).orElseThrow(RoleNotFoundException::new));
        try {
            userRepository.update(user, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    public void delete(Long userId) {
        Session session = DbUnit.getSessionFactory().openSession();
        User user=userRepository.findOne(userId, session).orElseThrow(UserNotFoundException::new);
        Transaction transaction = session.beginTransaction();
        try {
            userRepository.delete(user, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void update(Long userId, UserDto userDto) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        User user = userRepository.findOne(userId, session).orElseThrow(UserNotFoundException::new);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setDob(userDto.getDob());
        user.setEmail(userDto.getEmail());
        try {
            userRepository.update(user, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void create(UserDto dto) {
        Session session = DbUnit.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        User user = DtoConverter.constructUserFromDto(dto);
        user.setPassword(dto.getPassword());
        try {
            userRepository.create(user, session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public UserDto getUserById(Long id) {
        Session session = DbUnit.getSessionFactory().openSession();

        UserDto userDto = null;
        
            userDto = DtoConverter.constructUserDto(userRepository.findOne(id, session).orElseThrow(UserNotFoundException::new));
        try {
            userDto.setRoles(DtoConverter.constructRoleDtoSet(getAllUserRoles(id)));
            userDto.setBooks(DtoConverter.constructBookDtoSet(getAllUserBooks(id)));
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
        return userDto;
    }

    private Set<Book> getAllUserBooks(Long id) {
        Session session = DbUnit.getSessionFactory().openSession();
        Set<Book> books;
            books = userRepository.getAllUserBooks(id, session);
            session.close();
       
        return books;
    }

    public Set<BookDto> getAllUserBooksAsBookDto(Long userId) {
        Set<BookDto> bookDtos = new HashSet<>();

        for (Book book : getAllUserBooks(userId)) {
            bookDtos.add(DtoConverter.constructBookDTO(book));
        }

        return bookDtos;
    }

    public Set<Role> getAllUserRoles(Long id) {
        Session session = DbUnit.getSessionFactory().openSession();

        Set<Role> roles;
        try {
            roles = userRepository.getAllUserRoles(id, session);
        } finally {
            session.close();
        }
        return roles;
    }
   /* @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if(user==null) {
           throw new UsernameNotFoundException("invalid username or password");  
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
    private  org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, 
            List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
                user.isEnabled(), true, true, true, authorities);
        }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRolename()))
                .collect(Collectors.toList());
    }*/

}

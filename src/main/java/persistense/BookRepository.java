package persistense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import common.BookDto;
import common.DtoConverter;
import entity.Book;

public class BookRepository {

    public List<BookDto> getAllBookAsBookDTO() {
        List<BookDto> bookDTOs = new ArrayList<BookDto>();

        for (Book book : BookRepository.getAllBooks()) {
            bookDTOs.add(DtoConverter.constructBookDTO(book));
        }

        return bookDTOs;
    }



    public static List<Book> getAllBooks() {
        Session session = DbUnit.getSession();
        List<Book> books = new ArrayList<Book>();
        try {

            Query query = session.createQuery("from Book");
            List<Book> fd = query.list();
            for (Book b : fd) {
                books.add(b);
            }
        } finally {
            session.close();
        }
        return books;
    }

    public void delete(Long id) {
        Session session = DbUnit.getSession();
        System.out.println("Deleting  record...");
        session.beginTransaction();
        try {
            String hql = "delete from Book where id = :id";
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void update(BookDto dto) {
        Session session = DbUnit.getSession();
        try {

            System.out.println("Updating author...");
            Book book = DtoConverter.constructBookFromDto(dto);
            book.setDateAdd(new Date());
            session.beginTransaction();
            session.saveOrUpdate(book);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void create(BookDto dto) {
        Session session = DbUnit.getSession();
        try {
            System.out.println("Creating record...");
            Book book = DtoConverter.constructBookFromDto(dto);
            book.setDateAdd(new Date());
            session.beginTransaction();
            session.save(book);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}

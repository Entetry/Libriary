package com.antonklintsevich.persistense;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Subgenre;
import com.antonklintsevich.exception.BookNotFoundException;

@Repository
public class BookRepository extends AbstractHibernateDao<Book> {
    public Set<Subgenre> getAllBookSubgenres(Long id, EntityManager entityManager) {
        Book book = findOne(id, entityManager).orElseThrow(BookNotFoundException::new);
        Hibernate.initialize(book.getSubgenres());
        return book.getSubgenres();
    }

    public Set<Book> findBooksByUsersRequest(String data, EntityManager entityManager) {
        Set<Book> books = new HashSet<>();
        books.addAll((List<Book>) (entityManager
                .createQuery("from Book b where b.bookname like '" + data + "%' or b.author like '" + data + "%'")
                .getResultList()));
        return books;
    }

    @SuppressWarnings("unchecked")
    public List<Book> getAllBooksSorted(EntityManager entityManager, String field, String sorttype) {
        
        return entityManager.createNativeQuery("SELECT * FROM book ORDER BY " + field +" " + sorttype, Book.class).getResultList();
//        query.setParameter("field", field);
//        query.setParameter("sorttype", sorttype);
    }
//    public Set<Book> findBooksByUsersRequest(String data, EntityManager entityManager) {
//        Set<Book> books = new HashSet<>();
//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//        entityManager.getTransaction().begin();
//        List<Book> book = entityManager.createQuery("select book from Book as book").getResultList();
//        for (Book b : books) {
//            fullTextEntityManager.index(b);
//        } 
//        entityManager.getTransaction().commit();
//        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Book.class)
//                .get();
//        org.apache.lucene.search.Query wildcardQuery = queryBuilder.keyword().wildcard().onField("bookname").andField( "author")
//                .matching("*"+data+"*").createQuery();
//        org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(wildcardQuery,
//                Book.class);
//        books.addAll(jpaQuery.getResultList());
//        return books;
//    }
}
package com.antonklintsevich.persistense;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.common.SearchData;
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

    public List<Book> findBooksByUsersRequest(String data, EntityManager entityManager) {
        return entityManager
                .createQuery("from Book b where b.bookname like '" + data + "%' or b.author like '" + data + "%'",
                        Book.class)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Book> getAllBooksSorted(EntityManager entityManager, List<SearchData> searchdata) {
        List<Book> books = null;

        if (searchdata.size() == 1) {
            books = entityManager.createNativeQuery(
                    "SELECT * FROM book ORDER BY " + searchdata.get(0).name + " " + searchdata.get(0).sortOrder,
                    Book.class).getResultList();
        }
        if (searchdata.size() == 2) {
            books = entityManager
                    .createNativeQuery(
                            "SELECT * FROM book ORDER BY " + searchdata.get(0).name + " " + searchdata.get(0).sortOrder
                                    + ", " + searchdata.get(1).name + " " + searchdata.get(1).sortOrder,
                            Book.class)
                    .getResultList();
        }
        if (searchdata.size() == 3) {
            books = entityManager
                    .createNativeQuery(
                            "SELECT * FROM book ORDER BY " + searchdata.get(0).name + " " + searchdata.get(0).sortOrder
                                    + ", " + searchdata.get(1).name + " " + searchdata.get(1).sortOrder + 
                                    ", " + searchdata.get(2).name + " " + searchdata.get(2).sortOrder,
                            Book.class)
                    .getResultList();
        }
        if (searchdata.size() == 4) {
            books = entityManager
                    .createNativeQuery(
                            "SELECT * FROM book ORDER BY " + searchdata.get(0).name + " " + searchdata.get(0).sortOrder
                                    + ", " + searchdata.get(1).name + " " + searchdata.get(1).sortOrder +
                                    ", " + searchdata.get(2).name + " " + searchdata.get(2).sortOrder + 
                                    ", " + searchdata.get(3).name + " " + searchdata.get(3).sortOrder,
                            Book.class)
                    .getResultList();
        }
        return books;

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
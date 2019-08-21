package com.antonklintsevich.persistense;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.common.FilterData;
import com.antonklintsevich.common.FilterType;
import com.antonklintsevich.common.SortData;
import com.antonklintsevich.common.SearchParameters;
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
    public List<Book> getAllBooksSorted(EntityManager entityManager, SearchParameters searchPatameters) {
        List<Book> books = null;
        books = entityManager.createNativeQuery(getQuery(searchPatameters), Book.class).getResultList();

        return books;
    }

    private String getQuery(SearchParameters searchPatameters) {
        StringBuilder sb = new StringBuilder("SELECT * FROM book");
        if (!searchPatameters.getFilterData().isEmpty()) {
            sb.append(" WHERE");
            for (FilterData data : searchPatameters.getFilterData()) {
                if(data.getFilterType()==FilterType.LIKE) {
                    sb.append(" " + data.getField() + " " + data.getFilterType().getFilterType()+" '" + data.getValue() + "%'"
                    + " AND");
                }
                else {
                sb.append(" " + data.getField() + " " + data.getFilterType().getFilterType() + " " + data.getValue()
                        + " AND");}
            }
            sb = sb.delete(sb.length() - 4, sb.length());
        }
        if (!searchPatameters.getSearchData().isEmpty()) {
            sb.append(" ORDER BY");
            for (SortData data : searchPatameters.getSearchData()) {
                sb.append(" " + data.getName() + " " + data.getSortOrder() + ",");
            }
            sb = sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
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
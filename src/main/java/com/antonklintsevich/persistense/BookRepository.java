package com.antonklintsevich.persistense;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Subgenre;

@Repository
public class BookRepository<T>
extends AbstractHibernateDao<T> implements IGenericDao<T> {
    public Set<Subgenre> getAllBookSubgenres(Long id,Session session){
        Book book= (Book) findOne(id, session);
        Hibernate.initialize(book.getSubgenres());
        return book.getSubgenres();
    }
    
}

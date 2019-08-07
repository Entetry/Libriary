package com.antonklintsevich.persistense;

import java.util.List;

import org.hibernate.Session;

import com.antonklintsevich.entity.AbstractEntity;

public interface IGenericDao<T extends AbstractEntity> {

    T findOne(final Long id, Session session);

    List<T> findAll(Session session);

    T create(final T entity, Session session);

    T update(final T entity, Session session);

    void delete(final T entity, Session session);

    void deleteById(final Long entityId, Session session);
}
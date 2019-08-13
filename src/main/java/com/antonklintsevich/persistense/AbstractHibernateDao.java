package com.antonklintsevich.persistense;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;

import com.antonklintsevich.entity.AbstractEntity;
import com.antonklintsevich.exception.MyResourceNotFoundException;

public abstract class AbstractHibernateDao<T extends AbstractEntity> implements IGenericDao<T> {

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    public AbstractHibernateDao() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<T> findOne(Long id, Session session) {
        return Optional.ofNullable((T) session.get(clazz, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll(Session session) {
        return session.createQuery("from " + clazz.getName()).list();
    }

    @Override
    public T create(T entity, Session session) {
        session.saveOrUpdate(entity);
        return entity;
    }

    @Override
    public T update(T entity, Session session) {
        session.merge(entity);
        return entity;
    }

    @Override
    public void delete(T entity, Session session) {
        session.delete(entity);
    }
}

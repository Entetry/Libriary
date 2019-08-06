package com.antonklintsevich.persistense;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractHibernateDao<T> {

    private Class<T> clazz;
  
//    public AbstractHibernateDao(Class<T> clazzToSet){
//        this.clazz=clazzToSet;
//    }
    public void setClazz(Class<T> clazzToSet){
       this.clazz = clazzToSet;
    }
  
    public T findOne(Long id,Session session){
      return (T) session.get(clazz, id);
    }
 
    public List<T> findAll(Session session) {
        return session.createQuery("from " + clazz.getName()).list();
    }
 
    public T create(T entity,Session session) {
        session.saveOrUpdate(entity);
        return entity;
    }
 
    public T update(T entity,Session session) {
        return (T) session.merge(entity);
    }
 
    public void delete(T entity,Session session) {
        session.delete(entity);
    }
 
    public void deleteById(Long entityId,Session session) {
        T entity = findOne(entityId,session);
        session.delete(entity);
    }
}
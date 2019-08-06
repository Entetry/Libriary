package com.antonklintsevich.persistense;

import org.springframework.stereotype.Repository;

@Repository
public class GenreRepository <T>
extends AbstractHibernateDao<T> implements IGenericDao<T>{

}

package com.antonklintsevich.persistense;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Subgenre;

@Repository
public class SubgenreRepository<T>
extends AbstractHibernateDao<T> implements IGenericDao<T> {
 
}

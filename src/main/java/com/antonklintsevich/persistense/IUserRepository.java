package com.antonklintsevich.persistense;

import javax.persistence.EntityManager;

import com.antonklintsevich.entity.User;

public interface IUserRepository {
    User findByUsername(String username,EntityManager entityManager);
}

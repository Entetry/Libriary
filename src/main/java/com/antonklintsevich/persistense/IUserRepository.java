package com.antonklintsevich.persistense;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.antonklintsevich.entity.User;

public interface IUserRepository {
    User findByUsername(String username,EntityManager entityManager);
}

package com.antonklintsevich.persistense;

import org.hibernate.Session;

import com.antonklintsevich.entity.User;

public interface IUserRepository {
    User findByUsername(String username,Session session);
}

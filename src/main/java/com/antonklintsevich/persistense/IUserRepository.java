package com.antonklintsevich.persistense;
import com.antonklintsevich.entity.User;

public interface IUserRepository  {
 
    User findByUsername(String username);
}
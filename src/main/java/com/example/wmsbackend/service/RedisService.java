package com.example.wmsbackend.service;

public interface RedisService {
    void storeToken(String userName);
    String getToken(String userName);
    void deleteToken(String userName);
}

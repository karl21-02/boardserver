package com.fastcampus.boardserver.service;

public interface UserServiceImpl {

    void register(UsreDTO userProfile);

    UserDTO login(String id, String password);

    boolean isDuplicateId(String id);

    UserDTO getUserInfo(String userId);

    void updatePassword(String id, String beforePassword, String afterPassword);

    void deleteId(String id, String password);
}

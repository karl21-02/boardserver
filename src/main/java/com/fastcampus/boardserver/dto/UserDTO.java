package com.fastcampus.boardserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDTO {
    private int id;
    private String userId;
    private String password;
    private String nickname;
    private boolean isAdmin;
    private Date createTime;
}
package com.fastcampus.boardserver.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Integer id;
    private String name;
    private Integer isAdmin;
    private String contents;
    private Date createTime;
    private Integer views;
    private Integer categoryId;
    private Integer userId;
    private Integer fileId;
    private Date updateTime;
}

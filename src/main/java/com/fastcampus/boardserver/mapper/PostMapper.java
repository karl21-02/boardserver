package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.dto.PostDTO;

import java.util.List;

public interface PostMapper {

    public void register(PostDTO postDTO);
    public List<PostDTO> selectMyProducts(int accountId);
    public void updatePosts(PostDTO PostDTO);
    public void deletePosts(int postId);
}

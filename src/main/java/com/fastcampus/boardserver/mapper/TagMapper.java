package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TagMapper {
    public int register(TagDTO tagDTO);
    public void updateTags(TagDTO tagDTO);
    public void deletePostTag(int tagId);
    public void createPostTag(@Param("tagId") Integer tagId, @Param("postId") Integer postId); // 태그와 게시글의 관계를 n:m으로 표시하기 위한 테이블에 정보를 저장하기 위한 테이블 -> 이게 있어야만 join문으로 가져올 수 있음
}
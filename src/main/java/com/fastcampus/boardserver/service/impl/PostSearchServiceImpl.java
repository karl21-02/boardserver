package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.exception.BoardServerException;
import com.fastcampus.boardserver.mapper.PostSearchMapper;
import com.fastcampus.boardserver.service.PostSearchService;
import com.fastcampus.boardserver.service.SlackService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    @Autowired
    private PostSearchMapper productSearchMapper;
    @Autowired
    private SlackService slackService;


    @Async
    @Cacheable(value = "getPosts", key = "'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = productSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("getPosts 실패");
            slackService.sendSlackMessage("selectPosts 실패" + e.getMessage(), "error");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostByTag(String tagName) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = productSearchMapper.getPostByTag(tagName);
        } catch (RuntimeException e) {
            log.error("getPostByTag 실패");
        }
        return postDTOList;
    }
}
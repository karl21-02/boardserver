package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.service.PostSearchService;
import com.fastcampus.boardserver.service.PostService;
import com.fastcampus.boardserver.service.impl.PostSearchServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {
    private final PostSearchServiceImpl postSearchServiceImple;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postSearchRequest) {
        try {
            List<PostDTO> postDTOList = postSearchServiceImple.getPosts(postSearchRequest);
            return new PostSearchResponse(postDTOList);
        } catch (Exception e) {
            log.error("Error occurred while searching posts", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Search failed", e);
        }
    }




    // -- reponse 객체 --

    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {
        private List<PostDTO> postDTOList;
    }
}

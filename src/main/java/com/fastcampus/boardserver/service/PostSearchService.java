package com.fastcampus.boardserver.service;


import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface PostSearchService {
    List<PostDTO> getProducts(@Param("postSearchRequest") PostSearchRequest postSearchRequest);
}
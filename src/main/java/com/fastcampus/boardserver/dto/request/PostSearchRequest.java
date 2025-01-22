package com.fastcampus.boardserver.dto.request;

import com.fastcampus.boardserver.dto.CategoryDTO;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRequest {

    private String name;
    private String contents;
    private Integer categoryId;
    private CategoryDTO.SortStatus sortStatus;
}

package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.CategoryDTO;

public interface CategoryService {

    /**
     * 카테고리 등록
     * @param accountId
     * @param categoryDTO
     */
    void register(String accountId, CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);
    void delete(int categoryId);
}

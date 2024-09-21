package com.moneycat.budget.service;

import com.moneycat.budget.controller.model.response.CategoryResponse;
import com.moneycat.budget.persistence.repository.CategoryRepository;
import com.moneycat.budget.persistence.repository.entity.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getCategories() {
        List<CategoryEntity> parentCategories = categoryRepository.findByParentIdOrderByIdAscNameAsc(0L);
        List<CategoryEntity> children = categoryRepository.findByParentIdNotOrderByIdAscNameAsc(0L);
        return parentCategories.stream()
                .map(parentCategory -> createCategoryResponse(parentCategory, children))
                .collect(Collectors.toList());
    }

    private CategoryResponse createCategoryResponse(CategoryEntity parentCategory, List<CategoryEntity> childCategories) {
        List<CategoryResponse> subCategories = childCategories.stream()
                .filter(childCategory -> childCategory.getParentId().equals(parentCategory.getId()))
                .map(childCategory -> new CategoryResponse(childCategory.getId(), childCategory.getName(), null))
                .collect(Collectors.toList());
        return new CategoryResponse(parentCategory.getId(), parentCategory.getName(), subCategories);
    }

}

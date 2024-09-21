package com.moneycat.budget.controller.api;

import com.moneycat.budget.controller.model.response.CategoryResponse;
import com.moneycat.budget.service.CategoryService;
import com.moneycat.core.wrapper.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/categories")
@Tag(name = "카테고리 API", description = "카테고리 관련 작업을 처리합니다.")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "카테고리 조회", description = "모든 카테고리를 조회합니다.")
    public ResultResponse<List<CategoryResponse>> getCategories() {
        return new ResultResponse<>(categoryService.getCategories());
    }
}

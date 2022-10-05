package com.example.fastlms.admin.dto;

import com.example.fastlms.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long id;

    private String categoryName;

    private int sortValue;

    private boolean usingYn;

    public static List<CategoryDto> of(List<Category> categories){
        if (categories == null){
            return null;
        }

        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category x : categories) {
            categoryDtoList.add(of(x));
        }
        return categoryDtoList;
    }

    public static CategoryDto of(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .sortValue(category.getSortValue())
                .usingYn(category.isUsingYn())
                .build();
    }

}

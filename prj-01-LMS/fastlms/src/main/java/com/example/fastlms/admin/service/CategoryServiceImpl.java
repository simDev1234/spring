package com.example.fastlms.admin.service;

import com.example.fastlms.admin.dto.CategoryDto;
import com.example.fastlms.admin.entity.Category;
import com.example.fastlms.admin.model.CategoryInput;
import com.example.fastlms.admin.repository.CategoryRepository;
import com.example.fastlms.course.dto.CourseDto;
import com.example.fastlms.course.mapper.CategoryMapper;
import com.example.fastlms.course.model.CourseParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }

    /**
     * 카테고리 조회
     */
    @Override
    public List<CategoryDto> list() {

        List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc());

        return CategoryDto.of(categories);

    }

    /**
     * 카테고리 등록
     * - 카테고리가 이미 있는 경우 실패 응답
     */
    @Override
    public boolean add(String categoryName) {

        if (categoryName == null) {
            return false;
        }

        categoryRepository.save(
                Category.builder()
                        .categoryName(categoryName)
                        .usingYn(true)
                        .sortValue(0)
                        .build()
        );

        return true;
    }

    @Override
    public boolean update(CategoryInput parameter) {

        Optional<Category> optionalCategory = categoryRepository.findById(parameter.getId());

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategoryName(parameter.getCategoryName());
            category.setSortValue(parameter.getSortValue());
            category.setUsingYn(parameter.isUsingYn());
            categoryRepository.save(category);
        }

        return true;
    }

    @Override
    public boolean del(long id) {

        categoryRepository.deleteById(id);

        return true;
    }

    @Override
    public List<CategoryDto> frontList(CourseParam param) {

        return categoryMapper.select(param);
    }



}

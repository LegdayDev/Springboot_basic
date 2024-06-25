package com.legday.backboard.service;

import com.legday.backboard.common.NotFoundException;
import com.legday.backboard.entity.Category;
import com.legday.backboard.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category saveCategory(String title) {
        return categoryRepository.save(Category.builder().title(title).createDate(LocalDateTime.now()).build());
    }

    public Category findCategory(String title) {
        Optional<Category> cate = this.categoryRepository.findByTitle(title);

        if (cate.isEmpty()) { //free나 qna 타이틀의 카테고리 데이터가 없으며
            cate = Optional.ofNullable(saveCategory(title)); // 테이블에 해당 카테고리를 생성
        }

        if (cate.isPresent())
            return cate.get(); //Category 리턴
        else
            throw new NotFoundException("Category not Found!"); //발생할 일이 없음
    }
}

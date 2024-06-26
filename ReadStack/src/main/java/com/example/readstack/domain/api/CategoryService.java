package com.example.readstack.domain.api;

import com.example.readstack.domain.category.Category;
import com.example.readstack.domain.category.CategoryDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryService {
    private final CategoryDao categoryDao = new CategoryDao();

    public List<CategoryName> findAllCategoryNames() {
        return categoryDao.findAll()
                .stream()
                .map(CategoryNameMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<CategoryFullInfo> findById(int categoryId) {
        return categoryDao.findById(categoryId)
                .map(CategoryFullInfoMapper::map);
    }

    private static class CategoryNameMapper {
        static CategoryName map(Category category) {
            Integer id = category.getId();
            String name = category.getName();

            return new CategoryName(id, name);
        }
    }

    private static class CategoryFullInfoMapper {
        static CategoryFullInfo map(Category category) {
            return new CategoryFullInfo(
                    category.getId(),
                    category.getName(),
                    category.getDescription()
            );
        }
    }

}

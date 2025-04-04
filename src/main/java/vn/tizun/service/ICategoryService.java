package vn.tizun.service;

import vn.tizun.controller.request.CategoryCreationRequest;
import vn.tizun.controller.request.CategoryUpdateRequest;
import vn.tizun.controller.response.CategoryPageResponse;
import vn.tizun.controller.response.CategoryResponse;

public interface ICategoryService {
    CategoryPageResponse findAll(String keyword, String sort, int page, int size);
    CategoryResponse findById(Long id);
    CategoryResponse findByCategoryName(String categoryName);
    long save(CategoryCreationRequest req);
    void update(CategoryUpdateRequest req);
    void delete(Long id);
}

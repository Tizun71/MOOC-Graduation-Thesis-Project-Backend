package vn.tizun.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tizun.controller.request.CategoryCreationRequest;
import vn.tizun.controller.request.CategoryUpdateRequest;
import vn.tizun.controller.response.CategoryPageResponse;
import vn.tizun.controller.response.CategoryResponse;
import vn.tizun.exception.ResourceNotFoundException;
import vn.tizun.model.CategoryEntity;
import vn.tizun.repository.ICategoryRepository;
import vn.tizun.service.ICategoryService;

@Service
@Slf4j(topic = "CATEGORY-SERVICE")
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public CategoryPageResponse findAll(String keyword, String sort, int page, int size) {
        return null;
    }

    @Override
    public CategoryResponse findById(Long id) {
        CategoryEntity categoryEntity = getCategoryEntity(id);
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .categoryName(categoryEntity.getCategoryName())
                .description(categoryEntity.getDescription())
                .build();
    }

    @Override
    public CategoryResponse findByCategoryName(String categoryName) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long save(CategoryCreationRequest req) {

        CategoryEntity category = new CategoryEntity();
        category.setCategoryName(req.getCategoryName());
        category.setDescription(req.getDescription());

        categoryRepository.save(category);

        log.info("Saved category: {}", category);

        return category.getId();
    }

    @Override
    public void update(CategoryUpdateRequest req) {

        CategoryEntity category = getCategoryEntity(req.getId());

        category.setCategoryName(req.getCategoryName());
        category.setDescription(req.getDescription());

        categoryRepository.save(category);

        log.info("Updated category: {}", category);
    }

    @Override
    public void delete(Long id) {

        CategoryEntity category = getCategoryEntity(id);

        categoryRepository.delete(category);

        log.info("Deleted Category: {}", category);
    }

    private CategoryEntity getCategoryEntity(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}

package vn.tizun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tizun.model.CategoryEntity;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
}

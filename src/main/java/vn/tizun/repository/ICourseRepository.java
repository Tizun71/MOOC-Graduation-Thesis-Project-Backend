package vn.tizun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.tizun.model.CourseEntity;
import vn.tizun.model.UserEntity;

@Repository
public interface ICourseRepository extends JpaRepository<CourseEntity, Long> {
    @Query(value = "select c from CourseEntity c where (lower(c.courseName) like :keyword " +
            "or c.courseLevel like :keyword " +
            "or lower(c.category.categoryName) like :keyword " +
            "or lower(c.user.firstName) like :keyword " +
            "or lower(c.user.lastName) like :keyword)")
    Page<CourseEntity> searchByKeyword(String keyword, Pageable pageable);
}

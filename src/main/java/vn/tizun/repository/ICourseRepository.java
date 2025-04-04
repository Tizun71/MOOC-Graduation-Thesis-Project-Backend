package vn.tizun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tizun.model.CourseEntity;

@Repository
public interface ICourseRepository extends JpaRepository<CourseEntity, Long> {
    
}

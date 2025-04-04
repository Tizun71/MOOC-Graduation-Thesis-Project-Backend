package vn.tizun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tizun.model.LessonEntity;

@Repository
public interface ILessonRepository extends JpaRepository<LessonEntity, Long> {
}

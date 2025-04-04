package vn.tizun.service;

import org.springframework.web.multipart.MultipartFile;
import vn.tizun.controller.request.CourseCreationRequest;
import vn.tizun.controller.request.CourseUpdateRequest;
import vn.tizun.controller.response.CoursePageResponse;
import vn.tizun.controller.response.CourseResponse;

public interface ICourseService {
    CoursePageResponse findAll(String keyword, String sort, int page, int size);
    CourseResponse findById(Long id);
    CourseResponse findByCourseName(String courseName);
    long save(CourseCreationRequest req);
    void update(CourseUpdateRequest req);
    void updateImage(Long id, MultipartFile file);
    void delete(Long id);
}

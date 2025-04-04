package vn.tizun.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.tizun.controller.request.CourseCreationRequest;
import vn.tizun.controller.request.CourseUpdateRequest;
import vn.tizun.controller.response.CoursePageResponse;
import vn.tizun.controller.response.CourseResponse;
import vn.tizun.exception.ResourceNotFoundException;
import vn.tizun.model.CourseEntity;
import vn.tizun.model.UserEntity;
import vn.tizun.repository.ICourseRepository;
import vn.tizun.service.ICourseService;
import vn.tizun.service.IS3Service;

@Service
@Slf4j(topic = "COURSE-SERVICE")
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    private final ICourseRepository courseRepository;
    private final IS3Service s3Service;

    String FOLDER_DIRECTORY = "courses/";

    @Override
    public CoursePageResponse findAll(String keyword, String sort, int page, int size) {
        return null;
    }

    @Override
    public CourseResponse findById(Long id) {
        return null;
    }

    @Override
    public CourseResponse findByCourseName(String courseName) {
        return null;
    }

    @Override
    public long save(CourseCreationRequest req) {

        CourseEntity course = new CourseEntity();
        course.setCourseName(req.getCourseName());
        course.setDescription(req.getDescription());
        course.setCourseLevel(req.getCourseLevel());
        course.setInstructorId(req.getInstructorId());
        course.setCategoryId(req.getCategoryId());

        courseRepository.save(course);
        log.info("Saved course: {}", course);

        return course.getId();
    }

    @Override
    public void update(CourseUpdateRequest req) {

        CourseEntity course = getCourseEntity(req.getId());

        course.setCourseName(req.getCourseName());
        course.setDescription(req.getDescription());
        course.setCourseLevel(req.getCourseLevel());

        courseRepository.save(course);

        log.info("Updated course: {}", course);
    }

    @Override
    public void updateImage(Long id, MultipartFile file) {
        String image_url = s3Service.uploadFileToS3(FOLDER_DIRECTORY + id, file);

        CourseEntity course = getCourseEntity(id);
        course.setImageURL(image_url);

        courseRepository.save(course);
        log.info("Updated course image: {}", course);

    }

    private CourseEntity getCourseEntity(Long id){
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    @Override
    public void delete(Long id) {

    }
}

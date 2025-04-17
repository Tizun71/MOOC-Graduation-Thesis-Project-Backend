package vn.tizun.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.tizun.common.CourseStatus;
import vn.tizun.controller.request.CourseCreationRequest;
import vn.tizun.controller.request.CourseUpdateRequest;
import vn.tizun.controller.response.CoursePageResponse;
import vn.tizun.controller.response.CourseResponse;
import vn.tizun.controller.response.CoursePageResponse;
import vn.tizun.controller.response.CourseResponse;
import vn.tizun.exception.ResourceNotFoundException;
import vn.tizun.model.CategoryEntity;
import vn.tizun.model.CourseEntity;
import vn.tizun.model.CourseEntity;
import vn.tizun.model.UserEntity;
import vn.tizun.repository.ICategoryRepository;
import vn.tizun.repository.ICourseRepository;
import vn.tizun.repository.IUserRepository;
import vn.tizun.service.ICourseService;
import vn.tizun.service.IS3Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j(topic = "COURSE-SERVICE")
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    private final ICategoryRepository categoryRepository;
    private final IUserRepository userRepository;
    private final ICourseRepository courseRepository;
    private final IS3Service s3Service;

    String FOLDER_DIRECTORY = "courses/";

    @Override
    public CoursePageResponse findAll(String keyword, String sort, int page, int size) {
        log.info("findAll start");

        // Sorting
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)"); // tencot:asc|desc
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    order = new Sort.Order(Sort.Direction.ASC, columnName);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, columnName);
                }
            }
        }

        // Xu ly truong hop FE muon bat dau voi page = 1
        int pageNo = 0;
        if (page > 0) {
            pageNo = page - 1;
        }

        // Paging
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(order));

        Page<CourseEntity> entityPage;

        if (StringUtils.hasLength(keyword)) {
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = courseRepository.searchByKeyword(keyword, pageable);
        } else {
            entityPage = courseRepository.findAll(pageable);
        }

        return getCoursePageResponse(page, size, entityPage);
    }

    @Override
    public CourseResponse findById(Long id) {

        CourseEntity course = getCourseEntity(id);
        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .instructorName(course.getUser().getFirstName() + ' ' + course.getUser().getLastName())
                .categoryName(course.getCategory().getCategoryName())
                .courseLevel(course.getCourseLevel())
                .build();
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
        course.setCourseStatus(CourseStatus.UNPUBLISHED);

        Optional<UserEntity> user = userRepository.findById(req.getInstructorId());
        Optional<CategoryEntity> category = categoryRepository.findById(req.getCategoryId());

        course.setUser(user.get());
        course.setCategory(category.get());

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

        Optional<CategoryEntity> category = categoryRepository.findById(req.getCategoryId());
        course.setCategory(category.get());

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
        CourseEntity course = getCourseEntity(id);
        course.setCourseStatus(CourseStatus.DELETED);
        courseRepository.save(course);
    }

    private static CoursePageResponse getCoursePageResponse(int page, int size, Page<CourseEntity> CourseEntities) {
        log.info("Convert Course Entity Page");

        List<CourseResponse> CourseList = CourseEntities.stream().map(entity -> CourseResponse.builder()
                .id(entity.getId())
                .courseName(entity.getCourseName())
                .description(entity.getDescription())
                .instructorName(entity.getUser().getFirstName() + ' ' + entity.getUser().getLastName())
                .categoryName(entity.getCategory().getCategoryName())
                .courseLevel(entity.getCourseLevel())
                .build()
        ).toList();

        CoursePageResponse response = new CoursePageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(CourseEntities.getTotalElements());
        response.setTotalPages(CourseEntities.getTotalPages());
        response.setCourses(CourseList);

        return response;
    }
}

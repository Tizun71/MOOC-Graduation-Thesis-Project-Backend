package vn.tizun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.tizun.controller.request.CourseCreationRequest;
import vn.tizun.controller.request.CourseUpdateRequest;
import vn.tizun.controller.request.UserPasswordRequest;
import vn.tizun.controller.response.CoursePageResponse;
import vn.tizun.service.ICourseService;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/course")
@Tag(name = "Course Controller")
@Slf4j(topic = "COURSE-CONTROLLER")
@RequiredArgsConstructor
@Validated
public class CourseController {
    private final ICourseService courseService;

    @Operation(summary = "Get course list", description = "API retrieve course from db")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        log.info("Get course list");

        CoursePageResponse courseList = courseService.findAll(keyword, sort, page, size);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("messaege", "course list");
        result.put("data", courseList);

        return result;
    }

    @Operation(summary = "Create Course", description = "API add new course to db")
    @PostMapping("/add")
    public ResponseEntity<Object> createCourse(@RequestBody @Valid CourseCreationRequest request){
        log.info("Creating new course");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "course created successfully");
        result.put("data", courseService.save(request));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Course", description = "API update course to db")
    @PutMapping("/upd")
    public Map<String, Object> updateUser(@RequestBody CourseUpdateRequest request){

        log.info("Updating course with id: {}", request.getId());

        courseService.update(request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "course updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Update Course Image", description = "API update course image to S3")
    @PatchMapping("/upd-image")
    public Map<String, Object> changeImage(Long id,MultipartFile file){

        courseService.updateImage(id, file);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "course image updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Delete Category", description = "API inactivate course from db")
    @DeleteMapping("/del/{categoryId}")
    public Map<String, Object> deleteUser(@PathVariable  @Min(value = 1, message = "courseId must be equals or greater than 1") Long categoryId){
        log.info("Deleting course: {}", categoryId);

        courseService.delete(categoryId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "course deleted successfully");
        result.put("data", "");

        return result;
    }
}

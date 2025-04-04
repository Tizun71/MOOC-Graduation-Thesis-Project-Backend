package vn.tizun.controller.request;

import lombok.Getter;
import vn.tizun.common.CourseLevel;

@Getter
public class CourseUpdateRequest {
    private Long id;
    private String courseName;
    private String description;
    private CourseLevel courseLevel;
    private long categoryId;
}

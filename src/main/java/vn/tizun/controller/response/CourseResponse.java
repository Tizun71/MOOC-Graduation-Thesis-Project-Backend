package vn.tizun.controller.response;

import vn.tizun.common.CourseLevel;

import java.io.Serializable;

public class CourseResponse implements Serializable {
    private Long id;
    private String courseName;
    private String description;
    private Long instructorCourseId;
    private CourseLevel courseLevel;
    private int courseLength;
}

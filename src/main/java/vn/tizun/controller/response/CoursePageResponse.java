package vn.tizun.controller.response;

import java.io.Serializable;
import java.util.List;

public class CoursePageResponse extends PageResponseAbstract implements Serializable {
    private List<CourseResponse> courses;
}

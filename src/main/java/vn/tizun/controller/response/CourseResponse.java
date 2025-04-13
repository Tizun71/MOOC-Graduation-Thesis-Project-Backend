package vn.tizun.controller.response;

import lombok.*;
import vn.tizun.common.CourseLevel;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse implements Serializable {
    private Long id;
    private String courseName;
    private String description;
    private String instructorName;
    private String categoryName;
    private CourseLevel courseLevel;
}

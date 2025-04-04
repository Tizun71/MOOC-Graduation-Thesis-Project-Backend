package vn.tizun.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonCreationRequest {
    private Long courseId;
    private String title;
    private String videoUrl;
    private String content;
    private int position;
}

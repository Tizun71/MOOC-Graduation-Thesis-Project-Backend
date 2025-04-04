package vn.tizun.controller.request;

import lombok.Getter;

@Getter
public class LessonUpdateRequest {
    private Long id;
    private Long courseId;
    private String title;
    private String videoUrl;
    private String content;
    private int position;
}

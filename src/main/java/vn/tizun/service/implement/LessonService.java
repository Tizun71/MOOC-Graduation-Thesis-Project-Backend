package vn.tizun.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.tizun.controller.request.LessonCreationRequest;
import vn.tizun.controller.request.LessonUpdateRequest;
import vn.tizun.controller.response.LessonPageResponse;
import vn.tizun.controller.response.LessonResponse;
import vn.tizun.exception.ResourceNotFoundException;
import vn.tizun.model.LessonEntity;
import vn.tizun.model.SectionEntity;
import vn.tizun.model.UserEntity;
import vn.tizun.repository.ILessonRepository;
import vn.tizun.repository.ISectionRepository;
import vn.tizun.service.ILessonService;
import vn.tizun.service.IS3Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonService implements ILessonService {

    private final ILessonRepository lessonRepository;
    private final ISectionRepository sectionRepository;
    private final IS3Service s3Service;

    @Override
    public LessonPageResponse findAll(String keyword, String sort, int page, int size) {
        return null;
    }

    @Override
    public LessonResponse findById(Long id) {
        return null;
    }

    @Override
    public LessonResponse findByLessonName(String LessonName) {
        return null;
    }

    @Override
    public long save(LessonCreationRequest req) {
        LessonEntity lesson = new LessonEntity();
        lesson.setTitle(req.getTitle());
        lesson.setContent(req.getContent());

        Optional<SectionEntity> section = sectionRepository.findById(req.getSectionId());
        lesson.setSection(section.get());

        lesson.setPosition(lessonRepository.findMaxPosition() + 1);

        lessonRepository.save(lesson);
        log.info("Saved lesson: {}", lesson);

        return lesson.getId();
    }

    @Override
    public void update(LessonUpdateRequest req) {

        LessonEntity lesson = getLessonEntity(req.getId());

        lesson.setTitle(req.getTitle());
        lesson.setContent(req.getContent());
        lesson.setPosition(req.getPosition());

        lessonRepository.save(lesson);
        log.info("Updated lesson:{}", lesson);

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void uploadVideoToS3(Long courseId, MultipartFile file) {
        String FOLDER_DIRECTORY = "courses/" + courseId;
        String video_url = s3Service.uploadFileToS3(FOLDER_DIRECTORY, file);

        LessonEntity lesson = getLessonEntity(courseId);
        lesson.setVideoUrl(video_url);
        lessonRepository.save(lesson);
    }

    private LessonEntity getLessonEntity(Long id){
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }

}

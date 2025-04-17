package vn.tizun.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tizun.common.SectionStatus;
import vn.tizun.controller.request.SectionCreationRequest;
import vn.tizun.controller.request.SectionUpdateRequest;
import vn.tizun.exception.ResourceNotFoundException;
import vn.tizun.model.CourseEntity;
import vn.tizun.model.SectionEntity;
import vn.tizun.repository.ICourseRepository;
import vn.tizun.repository.ISectionRepository;
import vn.tizun.service.ISectionService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SectionService implements ISectionService {

    private final ISectionRepository sectionRepository;
    private final ICourseRepository courseRepository;

    @Override
    public List<SectionEntity> list(Long courseId) {
        List<SectionEntity> sections = sectionRepository.findAllByStatusAndCourseId(courseId, SectionStatus.ACTIVE);
        return sections;
    }

    @Override
    public long save(SectionCreationRequest req) {

        SectionEntity section = new SectionEntity();
        section.setTitle(req.getTitle());
        section.setPosition(sectionRepository.findMaxPosition() + 1);
        section.setStatus(SectionStatus.ACTIVE);

        Optional<CourseEntity> course = courseRepository.findById(req.getCourseId());
        section.setCourse(course.get());

        sectionRepository.save(section);

        return section.getId();
    }

    @Override
    public void update(SectionUpdateRequest req) {
        SectionEntity section = getSectionEntity(req.getId());
        section.setTitle(req.getTitle());
        section.setPosition(req.getPosition());

        sectionRepository.save(section);
    }

    @Override
    public void delete(Long id) {
        SectionEntity section = getSectionEntity(id);
        section.setStatus(SectionStatus.DELETED);
        sectionRepository.save(section);
    }

    private SectionEntity getSectionEntity(Long id){
        return sectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Section not found"));
    }
}

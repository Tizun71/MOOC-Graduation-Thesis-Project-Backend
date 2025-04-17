package vn.tizun.service;


import vn.tizun.controller.request.SectionCreationRequest;
import vn.tizun.controller.request.SectionUpdateRequest;
import vn.tizun.model.SectionEntity;

import java.util.List;

public interface ISectionService {
    List<SectionEntity> list(Long courseId);
    long save(SectionCreationRequest req);
    void update(SectionUpdateRequest req);
    void delete(Long id);
}

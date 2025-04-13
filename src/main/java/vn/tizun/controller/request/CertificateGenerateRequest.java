package vn.tizun.controller.request;

import lombok.Getter;
import vn.tizun.common.CourseLevel;

import java.util.Date;

@Getter
public class CertificateGenerateRequest {
    private String familyName;
    private String firstName;
    private String courseName;
    private CourseLevel level;
    private Date date;
}

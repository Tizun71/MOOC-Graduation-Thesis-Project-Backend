package vn.tizun.service;

import vn.tizun.controller.request.UserCreationRequest;
import vn.tizun.service.dto.MailDataDto;

public interface IMailService {
    void sendMail(MailDataDto req, String templateName);
    void sendMail_UserCreation(UserCreationRequest request);
}

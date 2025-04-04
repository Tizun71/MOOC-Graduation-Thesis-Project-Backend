package vn.tizun.service;

import vn.tizun.controller.request.UserCreationRequest;
import vn.tizun.controller.request.UserPasswordRequest;
import vn.tizun.controller.request.UserUpdateRequest;
import vn.tizun.controller.response.UserPageResponse;
import vn.tizun.controller.response.UserResponse;

import java.util.List;

public interface IUserService {
    UserPageResponse findAll(String keyword, String sort, int page, int size);
    UserResponse findById(Long id);
    UserResponse findByUsername(String username);
    UserResponse findByEmail(String email);
    long save(UserCreationRequest req);
    void update(UserUpdateRequest req);
    void changePassword(UserPasswordRequest req);
    void delete(Long id);
}

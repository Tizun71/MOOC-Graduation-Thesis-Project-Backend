package vn.tizun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.tizun.controller.request.UserCreationRequest;
import vn.tizun.controller.request.UserPasswordRequest;
import vn.tizun.controller.request.UserUpdateRequest;
import vn.tizun.controller.response.UserResponse;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mockup/user")
@Tag(name = "Mockup User Controller")
public class MockupUserController {

    @Operation(summary = "Get user list", description = "API retrieve user from db")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size
                                      ){
        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(1l);
        userResponse1.setFirstName("Tizun");
        userResponse1.setLastName("Mai");
        userResponse1.setGender(null);
        userResponse1.setBirthday(new Date());
        userResponse1.setUsername("admin");
        userResponse1.setEmail("admin@gmail.com");
        userResponse1.setPhone("0778936045");
        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2l);
        userResponse2.setFirstName("Duc");
        userResponse2.setLastName("Phan");
        userResponse2.setGender(null);
        userResponse2.setBirthday(new Date());
        userResponse2.setUsername("user");
        userResponse2.setEmail("user@gmail.com");
        userResponse2.setPhone("012936045");
        List<UserResponse> userList = List.of(userResponse1, userResponse2);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", userList);
        return result;
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID")
    @GetMapping("/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable @Min(1) Long userId){
        UserResponse userDetail = new UserResponse();
        userDetail.setId(1l);
        userDetail.setFirstName("Tizun");
        userDetail.setLastName("Mai");
        userDetail.setGender(null);
        userDetail.setBirthday(new Date());
        userDetail.setUsername("admin");
        userDetail.setEmail("admin@gmail.com");
        userDetail.setPhone("0778936045");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user");
        result.put("data", userDetail);
        return result;
    }

    @Operation(summary = "Create User", description = "API add new user to db")
    @PostMapping("/add")
    public Map<String, Object> createUser(UserCreationRequest request){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "user created successfully");
        result.put("data", 3);

        return result;
    }

    @Operation(summary = "Update User", description = "API update user to db")
    @PutMapping("/upd")
    public Map<String, Object> updateUser(UserUpdateRequest request){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "user updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Change Password", description = "API change password for user to db")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(UserPasswordRequest request){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "password updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Delete User", description = "API inactivate user from db")
    @DeleteMapping("/del/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "user deleted successfully");
        result.put("data", "");

        return result;
    }
}


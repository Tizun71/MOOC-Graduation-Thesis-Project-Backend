package vn.tizun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tizun.controller.request.SignInRequest;
import vn.tizun.controller.response.TokenResponse;
import vn.tizun.service.IAuthenticationService;

@RestController
@RequestMapping("/auth")
@Slf4j(topic = "AUTHENTICATION-CONTROLLER")
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @Operation(summary = "Access Token", description = "Get access token and refresh token by username and password")
    @PostMapping("/access-token")
    public TokenResponse getAccessToken(@RequestBody SignInRequest request) {
        log.info("Access token request");

        return authenticationService.getAccessToken(request);
    }

//    @Operation(summary = "Refresh token", description = "Get new access token by refresh token")
//    @PostMapping("/refresh-token")
//    public TokenResponse getRefreshToken(@RequestBody String refreshToken) {
//        return "";
//    }
}

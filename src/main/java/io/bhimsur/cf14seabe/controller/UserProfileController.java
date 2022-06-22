package io.bhimsur.cf14seabe.controller;

import io.bhimsur.cf14seabe.dto.BaseResponse;
import io.bhimsur.cf14seabe.dto.UserLoginRequest;
import io.bhimsur.cf14seabe.dto.UserLoginResponse;
import io.bhimsur.cf14seabe.dto.UserRegistrationRequest;
import io.bhimsur.cf14seabe.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/registration")
    public BaseResponse userRegistration(@RequestBody UserRegistrationRequest request) {
        return userProfileService.userRegistration(request);
    }

    @PostMapping("/login")
    public UserLoginResponse userLogin(@RequestBody UserLoginRequest request, HttpServletResponse httpServletResponse) {
        return userProfileService.userLogin(request, httpServletResponse);
    }
}

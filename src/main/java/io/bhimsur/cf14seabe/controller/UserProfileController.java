package io.bhimsur.cf14seabe.controller;

import io.bhimsur.cf14seabe.dto.BaseResponse;
import io.bhimsur.cf14seabe.dto.UserRegistrationRequest;
import io.bhimsur.cf14seabe.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/registration")
    public BaseResponse userRegistration(UserRegistrationRequest request) {
        return userProfileService.userRegistration(request);
    }
}

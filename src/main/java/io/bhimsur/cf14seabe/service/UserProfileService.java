package io.bhimsur.cf14seabe.service;

import io.bhimsur.cf14seabe.dto.BaseResponse;
import io.bhimsur.cf14seabe.dto.GetUserProfileRequest;
import io.bhimsur.cf14seabe.dto.UserLoginRequest;
import io.bhimsur.cf14seabe.dto.UserRegistrationRequest;
import io.bhimsur.cf14seabe.entity.UserProfile;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface UserProfileService {
    UserProfile getUserProfile(GetUserProfileRequest request);
    BaseResponse userRegistration(UserRegistrationRequest request);

    boolean userIdValidation(String userId);

    BaseResponse userLogin(UserLoginRequest request, HttpServletResponse httpServletResponse);
}

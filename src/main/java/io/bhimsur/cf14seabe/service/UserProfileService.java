package io.bhimsur.cf14seabe.service;

import io.bhimsur.cf14seabe.dto.*;
import io.bhimsur.cf14seabe.entity.UserProfile;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface UserProfileService {
    UserProfile getUserProfile(Metadata metadata);

    BaseResponse userRegistration(UserRegistrationRequest request);

    boolean userIdValidation(String userId);

    UserLoginResponse userLogin(UserLoginRequest request, HttpServletResponse httpServletResponse);
}

package io.bhimsur.cf14seabe.service;

import io.bhimsur.cf14seabe.dto.GetUserProfileRequest;
import io.bhimsur.cf14seabe.entity.UserProfile;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {
    UserProfile getUserProfile(GetUserProfileRequest request);
}

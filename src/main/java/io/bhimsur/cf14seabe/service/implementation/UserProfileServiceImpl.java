package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.dto.GetUserProfileRequest;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.exception.DataNotFoundException;
import io.bhimsur.cf14seabe.repository.UserProfileRepository;
import io.bhimsur.cf14seabe.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * @param request GetUserProfileRequest
     * @return UserProfile
     */
    @Override
    public UserProfile getUserProfile(GetUserProfileRequest request) {
        log.info("start getUserProfile request : {}", request);
        var result = userProfileRepository.findById(request.getUserProfileId());
        return result.orElseThrow(() -> new DataNotFoundException("User not found"));
    }
}

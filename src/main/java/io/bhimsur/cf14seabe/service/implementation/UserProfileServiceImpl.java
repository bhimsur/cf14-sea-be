package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.dto.BaseResponse;
import io.bhimsur.cf14seabe.dto.GetUserProfileRequest;
import io.bhimsur.cf14seabe.dto.UserRegistrationRequest;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.Wallet;
import io.bhimsur.cf14seabe.exception.DataAlreadyExistException;
import io.bhimsur.cf14seabe.exception.DataNotFoundException;
import io.bhimsur.cf14seabe.exception.GenericException;
import io.bhimsur.cf14seabe.repository.UserProfileRepository;
import io.bhimsur.cf14seabe.repository.WalletRepository;
import io.bhimsur.cf14seabe.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private WalletRepository walletRepository;

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

    /**
     * @param request UserRegistrationRequest
     * @return UserProfile
     */
    @Override
    public BaseResponse userRegistration(UserRegistrationRequest request) {
        log.info("start userRegistration request : {}", request);
        var userProfileCheck = userProfileRepository.getUserProfileByUserId(Integer.parseInt(request.getUserId()));
        if (userProfileCheck.isPresent()) {
            throw new DataAlreadyExistException("User already exists");
        }
        if (Boolean.FALSE.equals(userIdValidation(request.getUserId()))) {
            throw new GenericException("UserId is not valid");
        }
        UserProfile userProfile = userProfileRepository.save(UserProfile.builder()
                .userId(Integer.parseInt(request.getUserId()))
                .nameAlias(request.getNameAlias())
                .password(request.getPassword())
                .createDate(new Timestamp(System.currentTimeMillis()))
                .build());
        Wallet wallet = Wallet.builder()
                .userProfile(userProfile)
                .amount(BigDecimal.ZERO)
                .createDate(new Timestamp(System.currentTimeMillis()))
                .build();
        return BaseResponse.builder()
                .success(walletRepository.save(wallet).getId() > 0)
                .build();
    }

    /**
     * @param userId String
     * @return boolean
     */
    @Override
    public boolean userIdValidation(String userId) {
        var requestId = Arrays.stream(userId.substring(0, 3).split("")).map(Integer::parseInt).reduce(0, Integer::sum);
        var resultId = Integer.parseInt(userId.substring(3, 5));
        return requestId == resultId;
    }
}

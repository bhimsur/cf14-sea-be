package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.config.JwtUtil;
import io.bhimsur.cf14seabe.dto.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * @param metadata Metadata
     * @return UserProfile
     */
    @Override
    public UserProfile getUserProfile(Metadata metadata) {
        log.info("start getUserProfile metadata : {}", metadata);
        try {
            var result = userProfileRepository.getUserProfileByUserId(metadata.getUserId());
            return result.orElseThrow(() -> new DataNotFoundException("User not found"));
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }

    /**
     * @param request UserRegistrationRequest
     * @return UserProfile
     */
    @Override
    public BaseResponse userRegistration(UserRegistrationRequest request) {
        log.info("start userRegistration request : {}", request);
        try {
            var userProfileCheck = userProfileRepository.getUserProfileByUserId(request.getUserId());
            if (userProfileCheck.isPresent()) {
                throw new DataAlreadyExistException("User already exists");
            }
            if (Boolean.FALSE.equals(userIdValidation(String.valueOf(request.getUserId())))) {
                throw new GenericException("UserId is not valid");
            }
            UserProfile userProfile = userProfileRepository.save(UserProfile.builder()
                    .userId(request.getUserId())
                    .nameAlias(request.getNameAlias())
                    .password(passwordEncoder.encode(request.getPassword()))
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
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
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

    /**
     * @param request UserLoginRequest
     * @return true
     */
    @Override
    public UserLoginResponse userLogin(UserLoginRequest request, HttpServletResponse httpServletResponse) {
        log.info("start Login request : {}", request);
        try {
            Optional<UserProfile> userProfileOptional = userProfileRepository.getUserProfileByUserId(request.getUserId());
            if (userProfileOptional.isEmpty()) {
                throw new DataNotFoundException("user not found");
            }
            UserProfile userProfile = userProfileOptional.get();
            if (!passwordEncoder.matches(request.getPassword(), userProfile.getPassword())) {
                throw new GenericException("Invalid credentials");
            }
            httpServletResponse.setHeader("Access-Token", jwtUtil.generateToken(String.valueOf(userProfile.getUserId())));
            return UserLoginResponse.builder()
                    .nameAlias(userProfile.getNameAlias())
                    .build();
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }
}

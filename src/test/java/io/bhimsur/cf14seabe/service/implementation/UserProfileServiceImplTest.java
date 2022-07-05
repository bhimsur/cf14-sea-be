package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.config.JwtUtil;
import io.bhimsur.cf14seabe.dto.Metadata;
import io.bhimsur.cf14seabe.dto.UserLoginRequest;
import io.bhimsur.cf14seabe.dto.UserRegistrationRequest;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.Wallet;
import io.bhimsur.cf14seabe.repository.UserProfileRepository;
import io.bhimsur.cf14seabe.repository.WalletRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UserProfileServiceImpl.class)
public class UserProfileServiceImplTest {

    private final UserProfile userProfile = UserProfile.builder()
            .userId(1)
            .nameAlias("")
            .createDate(new Timestamp(System.currentTimeMillis()))
            .password("abc")
            .build();
    private final Metadata metadata = Metadata.builder()
            .ipAddress("10.1.1.1")
            .userAgent("Alamofire")
            .userId(1)
            .build();
    @Autowired
    private UserProfileServiceImpl service;
    @MockBean
    private UserProfileRepository userProfileRepository;
    @MockBean
    private WalletRepository walletRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private HttpServletResponse httpServletResponse;

    @Test
    public void getUserProfile() {
        when(userProfileRepository.getUserProfileByUserId(anyInt()))
                .thenReturn(Optional.of(userProfile));
        var response = service.getUserProfile(metadata);
        assertNotNull(response);
    }

    @Test
    public void userRegistration() {
        when(userProfileRepository.getUserProfileByUserId(any()))
                .thenReturn(Optional.empty());
        when(userProfileRepository.save(any()))
                .thenReturn(userProfile);
        when(walletRepository.save(any()))
                .thenReturn(Wallet.builder()
                        .id(1L)
                        .build());
        var response = service.userRegistration(UserRegistrationRequest.builder()
                .userId(45615)
                .password("")
                .nameAlias("")
                .build());
        assertNotNull(response);
    }

    @Test
    public void userIdValidation() {
        var response = service.userIdValidation("45615");
        assertTrue(response);
    }

    @Test
    public void userLogin() {
        when(userProfileRepository.getUserProfileByUserId(anyInt()))
                .thenReturn(Optional.of(userProfile));
        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);
        var response = service.userLogin(UserLoginRequest.builder()
                .password("abc")
                .userId(1)
                .build(), httpServletResponse);
        assertNotNull(response);
    }
}
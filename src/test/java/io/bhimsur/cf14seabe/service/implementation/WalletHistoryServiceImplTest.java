package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.constant.TransactionType;
import io.bhimsur.cf14seabe.dto.Metadata;
import io.bhimsur.cf14seabe.dto.StoreWalletHistoryRequest;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.Wallet;
import io.bhimsur.cf14seabe.entity.WalletHistory;
import io.bhimsur.cf14seabe.repository.WalletHistoryRepository;
import io.bhimsur.cf14seabe.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WalletHistoryServiceImpl.class)
public class WalletHistoryServiceImplTest {
    private final Metadata metadata = Metadata.builder()
            .ipAddress("10.1.1.1")
            .userAgent("Alamofire")
            .userId(1)
            .build();
    private final UserProfile userProfile = UserProfile.builder()
            .userId(1)
            .nameAlias("")
            .createDate(new Timestamp(System.currentTimeMillis()))
            .password("abc")
            .build();
    @Autowired
    private WalletHistoryServiceImpl service;
    @MockBean
    private WalletHistoryRepository walletHistoryRepository;
    @MockBean
    private UserProfileService userProfileService;

    @Test
    public void get() {
        when(userProfileService.getUserProfile(any()))
                .thenReturn(userProfile);
        when(walletHistoryRepository.findAllByUserProfile(any()))
                .thenReturn(List.of(WalletHistory.builder()
                        .createDate(new Timestamp(System.currentTimeMillis()))
                        .id(1L)
                        .transactionType(TransactionType.TOP_UP)
                        .amount(BigDecimal.TEN)
                        .build()));
        var response = service.get(metadata);
        assertNotNull(response);
    }

    @Test
    public void store() {
        when(walletHistoryRepository.save(any()))
                .thenReturn(WalletHistory.builder().build());
        service.store(StoreWalletHistoryRequest.builder()
                        .wallet(Wallet.builder().build())
                        .userProfile(userProfile)
                        .amount(BigDecimal.TEN)
                        .transactionType(TransactionType.TOP_UP)
                .build());
        assertNotNull(metadata.getUserId());
    }

    @Test
    public void getSummary() {
        when(userProfileService.getUserProfile(any()))
                .thenReturn(userProfile);
        when(walletHistoryRepository.findAllByUserProfile(any()))
                .thenReturn(List.of(WalletHistory.builder()
                        .createDate(new Timestamp(System.currentTimeMillis()))
                        .id(1L)
                        .transactionType(TransactionType.TOP_UP)
                        .amount(BigDecimal.TEN)
                        .build()));
        var response = service.getSummary(metadata);
        assertNotNull(response);
    }
}
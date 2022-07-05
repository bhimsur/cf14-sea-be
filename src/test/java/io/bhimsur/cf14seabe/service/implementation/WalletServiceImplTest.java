package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.constant.TransactionType;
import io.bhimsur.cf14seabe.dto.BalanceTransactionRequest;
import io.bhimsur.cf14seabe.dto.Metadata;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.Wallet;
import io.bhimsur.cf14seabe.repository.WalletRepository;
import io.bhimsur.cf14seabe.service.UserProfileService;
import io.bhimsur.cf14seabe.service.WalletHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WalletServiceImpl.class)
@Slf4j
public class WalletServiceImplTest {

    @Autowired
    private WalletServiceImpl service;
    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private UserProfileService userProfileService;

    @MockBean
    private WalletHistoryService walletHistoryService;

    @MockBean
    private Executor executor;

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

    @Before
    public void setUp() {
        when(userProfileService.getUserProfile(any()))
                .thenReturn(userProfile);
        when(walletRepository.findWalletByUserProfile(any()))
                .thenReturn(Optional.of(Wallet.builder().amount(BigDecimal.TEN).build()));
    }

    @Test
    public void getBalance() {
        var response = service.getBalance(metadata);
        assertNotNull(response);
    }

    @Test
    public void balanceTransaction() {
        when(walletRepository.save(any()))
                .thenReturn(Wallet.builder().id(1L).build());
        var response = service.balanceTransaction(BalanceTransactionRequest.builder()
                .transactionType(TransactionType.TOP_UP)
                .amount(BigDecimal.TEN)
                .build(), metadata);
        assertNotNull(response);
    }

    @Test
    public void getWalletByUserProfile() {
        var response = service.getWalletByUserProfile(metadata);
        assertNotNull(response);
    }
}
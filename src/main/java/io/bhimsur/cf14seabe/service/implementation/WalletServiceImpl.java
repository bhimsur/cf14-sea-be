package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.constant.TransactionType;
import io.bhimsur.cf14seabe.dto.*;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.Wallet;
import io.bhimsur.cf14seabe.exception.InsufficientBalanceException;
import io.bhimsur.cf14seabe.repository.WalletRepository;
import io.bhimsur.cf14seabe.service.UserProfileService;
import io.bhimsur.cf14seabe.service.WalletHistoryService;
import io.bhimsur.cf14seabe.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private WalletHistoryService walletHistoryService;

    /**
     * @param request GetBalanceRequest
     * @return GetBalanceResponse
     */
    @Override
    public GetBalanceResponse getBalance(GetBalanceRequest request) {
        log.info("start getBalance request : {}", request);
        GetWalletByUserProfileResponse dbWallet = getWalletByUserProfile(GetWalletRequest.builder().userProfile(request.getUserProfileId()).build());
        Wallet wallet = dbWallet.getWallet();
        return GetBalanceResponse.builder()
                .amount(wallet.getAmount())
                .build();
    }

    /**
     * @param request BalanceTransactionRequest
     * @return BaseResponse
     */
    @Override
    public BaseResponse balanceTransaction(BalanceTransactionRequest request) {
        log.info("start balanceTransaction request : {}", request);
        GetWalletByUserProfileResponse wallet = getWalletByUserProfile(GetWalletRequest.builder().userProfile(request.getUserProfileId()).build());
        Wallet dbWallet = wallet.getWallet();
        UserProfile userProfile = wallet.getUserProfile();

        if (request.getTransactionType().equals(TransactionType.TOP_UP)) {
            dbWallet.setAmount(dbWallet.getAmount().add(request.getAmount()));
        } else {
            if (dbWallet.getAmount().compareTo(request.getAmount()) < 0) {
                throw new InsufficientBalanceException("Insufficient funds");
            }
            dbWallet.setAmount(dbWallet.getAmount().subtract(request.getAmount()));
        }
        dbWallet.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        var status = walletRepository.save(dbWallet).getId() > 1;

        walletHistoryService.store(StoreWalletHistoryRequest.builder()
                .wallet(dbWallet)
                .userProfile(userProfile)
                .amount(request.getAmount())
                .transactionType(request.getTransactionType())
                .build());
        return BaseResponse.builder()
                .success(status)
                .build();

    }

    /**
     * @param request GetWalletRequest
     * @return GetWalletByUserProfileResponse
     */
    @Override
    public GetWalletByUserProfileResponse getWalletByUserProfile(GetWalletRequest request) {
        log.info("start getWalletByUserProfile request : {}", request);
        UserProfile userProfile = userProfileService.getUserProfile(GetUserProfileRequest.builder()
                .userProfileId(request.getUserProfile())
                .build());
        Optional<Wallet> dbWallet = walletRepository.findWalletByUserProfile(userProfile);
        return GetWalletByUserProfileResponse.builder()
                .userProfile(userProfile)
                .wallet(dbWallet.orElse(null))
                .build();
    }
}

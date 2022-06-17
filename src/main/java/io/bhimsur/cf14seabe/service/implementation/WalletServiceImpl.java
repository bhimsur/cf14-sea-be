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

import javax.transaction.Transactional;
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
     * @param metadata Metadata
     * @return GetBalanceResponse
     */
    @Override
    public GetBalanceResponse getBalance(Metadata metadata) {
        log.info("start getBalance request : {}", metadata);
        try {
            GetWalletByUserProfileResponse dbWallet = getWalletByUserProfile(metadata);
            Wallet wallet = dbWallet.getWallet();
            return GetBalanceResponse.builder()
                    .amount(wallet.getAmount())
                    .build();
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }

    /**
     * @param request BalanceTransactionRequest
     * @return BaseResponse
     */
    @Override
    @Transactional
    public BaseResponse balanceTransaction(BalanceTransactionRequest request, Metadata metadata) {
        log.info("start balanceTransaction request : {}, metadata : {}", request, metadata);
        try {
            GetWalletByUserProfileResponse wallet = getWalletByUserProfile(metadata);
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
            var status = walletRepository.save(dbWallet).getId() > 0;

            walletHistoryService.store(StoreWalletHistoryRequest.builder()
                    .wallet(dbWallet)
                    .userProfile(userProfile)
                    .amount(request.getAmount())
                    .transactionType(request.getTransactionType())
                    .build());
            return BaseResponse.builder()
                    .success(status)
                    .build();
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }

    /**
     * @param metadata Metadata
     * @return GetWalletByUserProfileResponse
     */
    @Override
    public GetWalletByUserProfileResponse getWalletByUserProfile(Metadata metadata) {
        log.info("start getWalletByUserProfile request : {}", metadata);
        try {
            UserProfile userProfile = userProfileService.getUserProfile(metadata);
            Optional<Wallet> dbWallet = walletRepository.findWalletByUserProfile(userProfile);
            return GetWalletByUserProfileResponse.builder()
                    .userProfile(userProfile)
                    .wallet(dbWallet.orElse(null))
                    .build();
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }
}

package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.constant.TransactionType;
import io.bhimsur.cf14seabe.dto.*;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.WalletHistory;
import io.bhimsur.cf14seabe.repository.WalletHistoryRepository;
import io.bhimsur.cf14seabe.service.UserProfileService;
import io.bhimsur.cf14seabe.service.WalletHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WalletHistoryServiceImpl implements WalletHistoryService {
    @Autowired
    private WalletHistoryRepository walletHistoryRepository;

    @Autowired
    private UserProfileService userProfileService;

    /**
     * @param metadata Metadata
     * @return GetWalletHistoryResponse
     */
    @Override
    public GetWalletHistoryResponse get(Metadata metadata) {
        log.info("Start getWalletHistory metadata : {}", metadata);
        try {
            UserProfile userProfile = userProfileService.getUserProfile(metadata);
            List<WalletHistory> dbResult = walletHistoryRepository.findAllByUserProfile(userProfile);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return GetWalletHistoryResponse.builder()
                    .result(dbResult.stream()
                            .sorted(Comparator.comparing(WalletHistory::getCreateDate).reversed())
                            .map(data -> WalletHistoryDto.builder()
                                    .id(data.getId())
                                    .transactionType(data.getTransactionType())
                                    .amount(data.getAmount())
                                    .timestamp(simpleDateFormat.format(data.getCreateDate()))
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }

    /**
     * @param request StoreWalletHistoryRequest
     */
    @Async
    @Override
    public void store(StoreWalletHistoryRequest request) {
        log.info("Start storeWalletHistory request : {}", request);
        try {
            WalletHistory walletHistory = WalletHistory.builder()
                    .wallet(request.getWallet())
                    .userProfile(request.getUserProfile())
                    .createDate(new Timestamp(System.currentTimeMillis()))
                    .amount(request.getAmount())
                    .transactionType(request.getTransactionType())
                    .build();
            walletHistoryRepository.save(walletHistory);
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }

    /**
     * @param metadata Metadata
     * @return GetWalletSummaryResponse
     */
    @Override
    public GetWalletSummaryResponse getSummary(Metadata metadata) {
        log.info("start getWalletSummary request : {}", metadata);
        try {
            var history = get(metadata);
            return GetWalletSummaryResponse.builder()
                    .income(WalletHistoryDto.builder()
                            .id(null)
                            .transactionType(TransactionType.WITHDRAW)
                            .amount(history.getResult().stream()
                                    .filter(data -> data.getTransactionType().equals(TransactionType.WITHDRAW))
                                    .map(WalletHistoryDto::getAmount)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add))
                            .build())
                    .outcome(WalletHistoryDto.builder()
                            .id(null)
                            .transactionType(TransactionType.TOP_UP)
                            .amount(history.getResult().stream()
                                    .filter(data -> data.getTransactionType().equals(TransactionType.TOP_UP))
                                    .map(WalletHistoryDto::getAmount)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add))
                            .build())
                    .build();
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }
}

package io.bhimsur.cf14seabe.service;

import io.bhimsur.cf14seabe.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {
    GetBalanceResponse getBalance(Metadata metadata);

    BaseResponse balanceTransaction(BalanceTransactionRequest request, Metadata metadata);

    GetWalletByUserProfileResponse getWalletByUserProfile(Metadata metadata);
}

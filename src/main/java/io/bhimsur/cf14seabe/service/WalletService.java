package io.bhimsur.cf14seabe.service;

import io.bhimsur.cf14seabe.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {
    GetBalanceResponse getBalance(GetBalanceRequest request);

    BaseResponse balanceTransaction(BalanceTransactionRequest request);

    GetWalletByUserProfileResponse getWalletByUserProfile(GetWalletRequest request);
}

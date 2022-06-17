package io.bhimsur.cf14seabe.service;

import io.bhimsur.cf14seabe.dto.GetWalletHistoryResponse;
import io.bhimsur.cf14seabe.dto.GetWalletSummaryResponse;
import io.bhimsur.cf14seabe.dto.Metadata;
import io.bhimsur.cf14seabe.dto.StoreWalletHistoryRequest;
import org.springframework.stereotype.Service;

@Service
public interface WalletHistoryService {
    GetWalletHistoryResponse get(Metadata metadata);

    void store(StoreWalletHistoryRequest request);

    GetWalletSummaryResponse getSummary(Metadata metadata);
}

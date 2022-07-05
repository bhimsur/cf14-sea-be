package io.bhimsur.cf14seabe.controller;

import io.bhimsur.cf14seabe.dto.GetWalletHistoryResponse;
import io.bhimsur.cf14seabe.dto.GetWalletSummaryResponse;
import io.bhimsur.cf14seabe.service.WalletHistoryService;
import io.bhimsur.cf14seabe.util.MetadataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wallet/history")
@Slf4j
public class WalletHistoryController {
    private final WalletHistoryService walletHistoryService;
    private final MetadataUtil metadataUtil;

    public WalletHistoryController(WalletHistoryService walletHistoryService, MetadataUtil metadataUtil) {
        this.walletHistoryService = walletHistoryService;
        this.metadataUtil = metadataUtil;
    }

    /**
     * Get Wallet History
     * @param httpServletRequest HttpServletRequest
     * @return GetWalletHistoryResponse
     */
    @GetMapping
    public GetWalletHistoryResponse getWalletHistory(HttpServletRequest httpServletRequest) {
        return walletHistoryService.get(metadataUtil.constructMetadata(httpServletRequest));
    }

    /**
     * Get Wallet Summary
     * @param httpServletRequest HttpServletRequest
     * @return GetWalletSummaryResponse
     */
    @PostMapping("/summary")
    public GetWalletSummaryResponse getWalletSummary(HttpServletRequest httpServletRequest) {
        return walletHistoryService.getSummary(metadataUtil.constructMetadata(httpServletRequest));
    }
}

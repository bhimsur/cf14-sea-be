package io.bhimsur.cf14seabe.controller;

import io.bhimsur.cf14seabe.dto.GetWalletHistoryRequest;
import io.bhimsur.cf14seabe.dto.GetWalletHistoryResponse;
import io.bhimsur.cf14seabe.dto.GetWalletSummaryResponse;
import io.bhimsur.cf14seabe.service.WalletHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet/history")
@Slf4j
public class WalletHistoryController {
    private final WalletHistoryService walletHistoryService;


    public WalletHistoryController(WalletHistoryService walletHistoryService) {
        this.walletHistoryService = walletHistoryService;
    }

    @GetMapping
    public GetWalletHistoryResponse getWalletHistory(@RequestBody GetWalletHistoryRequest request) {
        return walletHistoryService.get(request);
    }

    @GetMapping("/summary")
    public GetWalletSummaryResponse getWalletSummary(@RequestBody GetWalletHistoryRequest request) {
        return walletHistoryService.getSummary(request);
    }
}

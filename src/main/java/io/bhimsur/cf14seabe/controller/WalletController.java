package io.bhimsur.cf14seabe.controller;

import io.bhimsur.cf14seabe.dto.BalanceTransactionRequest;
import io.bhimsur.cf14seabe.dto.BaseResponse;
import io.bhimsur.cf14seabe.dto.GetBalanceRequest;
import io.bhimsur.cf14seabe.dto.GetBalanceResponse;
import io.bhimsur.cf14seabe.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@Slf4j
public class WalletController {
    private final WalletService walletService;


    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public GetBalanceResponse getBalance(@RequestBody GetBalanceRequest request) {
        return walletService.getBalance(request);
    }

    @PostMapping
    public BaseResponse balanceTransaction(@RequestBody BalanceTransactionRequest request) {
        return walletService.balanceTransaction(request);
    }
}

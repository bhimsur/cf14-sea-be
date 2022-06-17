package io.bhimsur.cf14seabe.controller;

import io.bhimsur.cf14seabe.dto.BalanceTransactionRequest;
import io.bhimsur.cf14seabe.dto.BaseResponse;
import io.bhimsur.cf14seabe.dto.GetBalanceResponse;
import io.bhimsur.cf14seabe.service.WalletService;
import io.bhimsur.cf14seabe.util.MetadataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wallet")
@Slf4j
public class WalletController {
    private final WalletService walletService;
    private final MetadataUtil metadataUtil;


    public WalletController(WalletService walletService, MetadataUtil metadataUtil) {
        this.walletService = walletService;
        this.metadataUtil = metadataUtil;
    }

    @GetMapping
    public GetBalanceResponse getBalance(HttpServletRequest httpServletRequest) {
        return walletService.getBalance(metadataUtil.constructMetadata(httpServletRequest));
    }

    @PostMapping
    public BaseResponse balanceTransaction(@RequestBody BalanceTransactionRequest request, HttpServletRequest httpServletRequest) {
        return walletService.balanceTransaction(request, metadataUtil.constructMetadata(httpServletRequest));
    }
}

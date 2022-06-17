package io.bhimsur.cf14seabe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetWalletSummaryResponse implements Serializable {
    private static final long serialVersionUID = -1696830069623369084L;
    private WalletHistoryDto income;
    private WalletHistoryDto outcome;
}

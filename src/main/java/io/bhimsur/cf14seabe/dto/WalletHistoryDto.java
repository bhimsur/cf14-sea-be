package io.bhimsur.cf14seabe.dto;

import io.bhimsur.cf14seabe.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletHistoryDto implements Serializable {
    private static final long serialVersionUID = 2005637485686272131L;
    private Long id;
    private TransactionType transactionType;
    private BigDecimal amount;
}

package io.bhimsur.cf14seabe.dto;

import io.bhimsur.cf14seabe.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BalanceTransactionRequest implements Serializable {
    private static final long serialVersionUID = 4192927361745730910L;
    private BigDecimal amount;
    private TransactionType transactionType;
    private Long userProfileId;
}

package io.bhimsur.cf14seabe.dto;

import io.bhimsur.cf14seabe.constant.TransactionType;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.Wallet;
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
public class StoreWalletHistoryRequest implements Serializable {
    private static final long serialVersionUID = -3058075269752680956L;
    private BigDecimal amount;
    private Wallet wallet;
    private UserProfile userProfile;
    private TransactionType transactionType;
}

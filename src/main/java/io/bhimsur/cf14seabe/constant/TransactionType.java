package io.bhimsur.cf14seabe.constant;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum TransactionType {
    TOP_UP,
    WITHDRAW,
    BUY,
    SELL
}

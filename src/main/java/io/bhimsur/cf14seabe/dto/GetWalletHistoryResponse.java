package io.bhimsur.cf14seabe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetWalletHistoryResponse implements Serializable {
    private static final long serialVersionUID = 5025381182961559806L;
    private List<WalletHistoryDto> result;
}

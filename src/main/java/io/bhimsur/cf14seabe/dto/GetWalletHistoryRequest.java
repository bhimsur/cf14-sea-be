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
public class GetWalletHistoryRequest implements Serializable {
    private static final long serialVersionUID = -3058075269752680956L;
    private Integer userId;
}

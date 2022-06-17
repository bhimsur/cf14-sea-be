package io.bhimsur.cf14seabe.dto;

import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetWalletByUserProfileResponse implements Serializable {
    private static final long serialVersionUID = 7145663262356379046L;
    private UserProfile userProfile;
    private Wallet wallet;
}

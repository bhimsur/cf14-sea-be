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
public class GetBalanceRequest implements Serializable {
    private static final long serialVersionUID = -6132831347247061281L;
    private Long userProfileId;
}

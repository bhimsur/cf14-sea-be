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
public class BuyProductRequest implements Serializable {
    private static final long serialVersionUID = 4547746507300687461L;
    private Long productId;
    private Long userProfileId;
}

package io.bhimsur.cf14seabe.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddNewProductRequest implements Serializable {
    private static final long serialVersionUID = 8904612995663748415L;
    private String productName;
    @ToString.Exclude
    private String productImage;
    private String description;
    private BigDecimal price;
}

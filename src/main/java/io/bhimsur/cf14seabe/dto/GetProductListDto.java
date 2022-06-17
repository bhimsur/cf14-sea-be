package io.bhimsur.cf14seabe.dto;

import io.bhimsur.cf14seabe.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductListDto implements Serializable {
    private static final long serialVersionUID = -2856857673230303585L;
    private Long id;
    private String productName;
    private String productImage;
    private String description;
    private BigDecimal price;
    private Timestamp createDate;
    private UserProfile userProfile;
}

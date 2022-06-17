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
public class GetUserProfileRequest implements Serializable {
    private static final long serialVersionUID = -7260271928120375069L;
    private Long userProfileId;
}

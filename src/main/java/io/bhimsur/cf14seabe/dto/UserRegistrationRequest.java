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
public class UserRegistrationRequest implements Serializable {
    private static final long serialVersionUID = -6433026039267787667L;
    private String userId;
    private String password;
    private String nameAlias;
}

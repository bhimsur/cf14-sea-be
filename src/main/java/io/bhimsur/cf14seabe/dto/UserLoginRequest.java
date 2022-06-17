package io.bhimsur.cf14seabe.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -4305183974869607067L;
    private Integer userId;
    private String password;
}

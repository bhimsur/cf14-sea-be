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
public class Metadata implements Serializable {
    private static final long serialVersionUID = -4832181977129804642L;
    private Integer userId;
    private String userAgent;
    private String ipAddress;
}

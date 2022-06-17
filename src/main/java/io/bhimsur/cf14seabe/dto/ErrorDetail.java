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
public class ErrorDetail implements Serializable {
    private static final long serialVersionUID = -3002613793294295206L;
    private Integer errorCode;
    private String message;
}

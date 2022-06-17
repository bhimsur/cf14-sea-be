package io.bhimsur.cf14seabe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductListResponse implements Serializable {
    private static final long serialVersionUID = -6140657263648645486L;
    private List<GetProductListDto> result;
}

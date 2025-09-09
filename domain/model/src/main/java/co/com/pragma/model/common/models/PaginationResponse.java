package co.com.pragma.model.common.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationResponse {
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
}

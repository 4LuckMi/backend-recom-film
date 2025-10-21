package com.enigmacamp.recomcinema.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingResponse {
    private Integer page;
    private Integer rowsPerPage;
    private Integer totalRows;
    private Integer totalPages;
    private Boolean hasNext;
    private Boolean hasPrevious;
}

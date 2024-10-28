package com.lawencon.jobportal.model.response;

import com.lawencon.jobportal.model.request.SortBy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {
    private Integer page;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalItem;
    private List<SortBy> sortBy;
}

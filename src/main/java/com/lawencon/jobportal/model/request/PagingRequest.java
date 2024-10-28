package com.lawencon.jobportal.model.request;

import com.lawencon.jobportal.validation.annotation.NotNullParam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingRequest {
    @NotNullParam
    private Integer page;

    @NotNullParam
    private Integer pageSize;

    private List<SortBy> sortBy;

    public Integer getPage() {
        return page-1;
    }

}

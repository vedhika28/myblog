package com.myblog8.payload;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "pageNo", "pageSize", "totalElements", "totalPages", "last", "postDto" })

public class PostResponse {

    private List<PostDto> postDto;
    private int PageSize;
    private int PageNo;
    private long totalElements;
    private int totalPages;
    private boolean last;
}

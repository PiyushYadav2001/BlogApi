package com.blogApi.playload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponce {
    private List<PostDto> content;
    private  int pageNumber;
    private int  pageSize;
    private  Long totalElement;
    private  int totalPages;
    private  boolean lastPage;
}

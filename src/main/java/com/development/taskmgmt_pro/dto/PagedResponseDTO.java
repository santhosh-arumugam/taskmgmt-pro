package com.development.taskmgmt_pro.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponseDTO<T> {
    private List<T> content;
    private int number;
    private int size;
    private Long totalElements;
    private int totalPages;
}

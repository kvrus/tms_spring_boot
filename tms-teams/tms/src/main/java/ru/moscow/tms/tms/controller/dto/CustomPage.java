package ru.moscow.tms.tms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomPage <T> {
    private int page;
    private int size;
    private boolean hasNext;
    private List<T> list;
}

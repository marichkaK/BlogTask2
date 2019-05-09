package com.example.springsocial.model;

import java.util.List;
import java.util.stream.Collectors;

public interface Dto<D> {

    D toDto();

    static List<?> toDtos(List<? extends Dto<?>> dtos) {
        return dtos.stream()
            .map(Dto::toDto)
            .collect(Collectors.toList());
    }
}

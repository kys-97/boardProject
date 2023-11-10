package com.example.boardproject.data.common;

public interface EntityMapper <DTO, Entity> {
    Entity toEntity(DTO dto);
}

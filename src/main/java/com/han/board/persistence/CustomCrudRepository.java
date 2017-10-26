package com.han.board.persistence;

import com.han.board.domain.WebBoard;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface CustomCrudRepository extends CrudRepository<WebBoard, Long>, CustomWebBoard {
}

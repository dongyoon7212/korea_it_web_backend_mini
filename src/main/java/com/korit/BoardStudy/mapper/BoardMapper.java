package com.korit.BoardStudy.mapper;

import com.korit.BoardStudy.entity.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    int addBoard(Board board);
}

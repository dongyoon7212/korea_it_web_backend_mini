package com.korit.BoardStudy.repository;

import com.korit.BoardStudy.entity.Board;
import com.korit.BoardStudy.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardRepository {

    @Autowired
    private BoardMapper boardMapper;

    public int addBoard(Board board) {
        return boardMapper.addBoard(board);
    }
}

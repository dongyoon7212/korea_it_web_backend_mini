package com.korit.BoardStudy.service;

import com.korit.BoardStudy.dto.ApiRespDto;
import com.korit.BoardStudy.dto.board.AddBoardReqDto;
import com.korit.BoardStudy.repository.BoardRepository;
import com.korit.BoardStudy.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> addBoard(AddBoardReqDto addBoardReqDto, PrincipalUser principalUser) {
        if (principalUser == null || !addBoardReqDto.getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다. 로그인 정보가 유효하지 않거나 권한이 없습니다.", null);
        }

        if (addBoardReqDto.getTitle() == null || addBoardReqDto.getTitle().trim().isEmpty()){
            return new ApiRespDto<>("failed", "제목은 필수 입력 사항입니다.", null);
        }

        if (addBoardReqDto.getContent() == null || addBoardReqDto.getContent().trim().isEmpty()){
            return new ApiRespDto<>("failed", "내용은 필수 입력 사항입니다.", null);
        }

        try{
            int result = boardRepository.addBoard(addBoardReqDto.toEntity());
            if (result != 1) {
                return new ApiRespDto<>("failed", "게시물 추가에 실패했습니다.", null);
            }

            return new ApiRespDto<>("success", "게시물이 성공적으로 추가되었습니다.", null);
        } catch (Exception e) {
            return new ApiRespDto<>("failed", "서버 오류로 게시물 추가에 실패했습니다 :" + e.getMessage(), null);
        }

    }
}

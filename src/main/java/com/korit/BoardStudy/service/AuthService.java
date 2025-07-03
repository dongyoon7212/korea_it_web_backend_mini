package com.korit.BoardStudy.service;

import com.korit.BoardStudy.dto.ApiRespDto;
import com.korit.BoardStudy.dto.auth.SignupReqDto;
import com.korit.BoardStudy.entity.User;
import com.korit.BoardStudy.entity.UserRole;
import com.korit.BoardStudy.repository.UserRepository;
import com.korit.BoardStudy.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> signup(SignupReqDto signupReqDto) {
        //아이디 중복확인
        Optional<User> userByUsername = userRepository.getUserByUsername(signupReqDto.getUsername());
        if (userByUsername.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용 중인 아이디입니다.", null);
        }

        //이메일 중복확인
        Optional<User> userByEmail = userRepository.getUserByEmail(signupReqDto.getEmail());
        if (userByEmail.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용 중인 이메일입니다.", null);
        }

        try {
            //사용자 정보 추가
            Optional<User> optionalUser = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));

            if (optionalUser.isEmpty()) {
                throw new RuntimeException("회원 정보 추가에 실패했습니다.");
            }

            User user = optionalUser.get();

            UserRole userRole = UserRole.builder()
                    .userId(user.getUserId())
                    .roleId(3)
                    .build();

            int addUserRoleResult = userRoleRepository.addUserRole(userRole);
            if (addUserRoleResult != 1) {
                throw new RuntimeException("권한 추가에 실패했습니다.");
            }

            return new ApiRespDto<>("success", "회원가입이 성공적으로 완료되었습니다.", user);

        } catch (Exception e) {
            return new ApiRespDto<>("failed", "회원가입 중 오류가 발생했습니다. :" + e.getMessage(), null);
        }

    }
}

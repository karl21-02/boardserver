package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.DuplicateIdException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.UserService;
import com.fastcampus.boardserver.util.SHA256Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.fastcampus.boardserver.util.SHA256Util.encryptSHA256;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private UserProfileMapper userProfileMapper;
    public UserServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }
    @Override
    public void register(UserDTO userProfile) {
        boolean dupleIdResult = isDuplicatedId(userProfile.getUserId());
        if(dupleIdResult){
            throw new DuplicateIdException("중복된 아이디입니다.");
        }
        userProfile.setCreateTime(new Date());
        userProfile.setPassword(encryptSHA256(userProfile.getPassword()));
        int insertCount = userProfileMapper.register(userProfile);

        if(insertCount != 1){
            log.error("insertMapper ERROR! {}", userProfile);
            throw new RuntimeException(
                    "insertMapper ERROR! 회원 가입 메서드를 확인해주세요\n" + "Params : " + userProfile);
        }
    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByUserIdAndPassword(id, cryptPassword);
        return memberInfo;
    }

    /**
     * 아이디 중복 확인
     * @param userId
     * @return
     */
    @Override
    public boolean isDuplicatedId(String userId) {
        return userProfileMapper.idCheck(userId) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = encryptSHA256(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByUserIdAndPassword(id, cryptoPassword);
        if(memberInfo != null) {
            memberInfo.setPassword(encryptSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        }
        else {
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void deleteId(String id, String password) {
        String cryptoPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByUserIdAndPassword(id, cryptoPassword);

        if(memberInfo != null) {
            int deleCount = userProfileMapper.deleteUserProfile(id);
        }
        else {
            log.error("deleteId ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}

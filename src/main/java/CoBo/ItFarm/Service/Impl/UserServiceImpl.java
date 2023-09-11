package CoBo.ItFarm.Service.Impl;

import CoBo.ItFarm.Data.Dto.User.Req.UserEmailReq;
import CoBo.ItFarm.Data.Dto.User.Res.UserEmailRes;
import CoBo.ItFarm.Data.Entity.UserEntity;
import CoBo.ItFarm.Repository.UserRepository;
import CoBo.ItFarm.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<UserEmailRes> email(Authentication authentication) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(Integer.valueOf(authentication.getName()));
        if(optionalUserEntity.isEmpty())
            throw new NullPointerException();

        UserEntity userEntity = optionalUserEntity.get();

        UserEmailRes userInfoRes = UserEmailRes.builder()
                .report_email(userEntity.isReportEmail())
                .warning_email(userEntity.isWarningEmail())
                .build();

        return new ResponseEntity<>(userInfoRes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> email(UserEmailReq userEmailReq, Authentication authentication) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(Integer.valueOf(authentication.getName()));
        if(optionalUserEntity.isEmpty())
            throw new NullPointerException();

        UserEntity userEntity = optionalUserEntity.get();

        if(userEmailReq.getReport_email() != null)
            userEntity.setReportEmail(userEmailReq.getReport_email());

        if(userEmailReq.getWarning_email() != null)
            userEntity.setWarningEmail(userEmailReq.getWarning_email());

        userRepository.save(userEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

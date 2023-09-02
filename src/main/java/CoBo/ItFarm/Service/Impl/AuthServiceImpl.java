package CoBo.ItFarm.Service.Impl;

import CoBo.ItFarm.Config.Jwt.JwtTokenProvider;
import CoBo.ItFarm.Data.Dto.Auth.Req.AuthPatchLoginReq;
import CoBo.ItFarm.Data.Dto.Auth.Res.AuthCheckRes;
import CoBo.ItFarm.Data.Dto.Auth.Res.AuthLoginRes;
import CoBo.ItFarm.Data.Entity.UserEntity;
import CoBo.ItFarm.Repository.UserRepository;
import CoBo.ItFarm.Service.AuthService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${kakao.auth.client_id}")
    private String client_id;
    @Value("${kakao.auth.redirect_uri}")
    private String redirect_uri;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public ResponseEntity<AuthLoginRes> login(String code) throws IOException{

        Optional<UserEntity> optionalUserEntity
                = userRepository.findById(getKakaoUserIdByKakaoAccessToken(getKakaoAccessToken(code)));

        if(optionalUserEntity.isEmpty())
            throw new NullPointerException();

        UserEntity userEntity = optionalUserEntity.get();

        String accessToken = jwtTokenProvider.createAccessToken(userEntity.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(userEntity.getId());

        userEntity.setRefreshToken(refreshToken);

        userRepository.save(userEntity);

        return new ResponseEntity<>(new AuthLoginRes(accessToken, refreshToken), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthLoginRes> login(AuthPatchLoginReq authPatchLoginReq) {
        UserEntity userEntity = userRepository.findByRefreshToken(authPatchLoginReq.getRefreshToken());
        return new ResponseEntity<>(new AuthLoginRes(jwtTokenProvider.createAccessToken(userEntity.getId()),authPatchLoginReq.getRefreshToken()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthCheckRes> check(Authentication authentication) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(Integer.valueOf(authentication.getName()));
        if(optionalUserEntity.isEmpty()) throw new NullPointerException();
        return new ResponseEntity<>(new AuthCheckRes(optionalUserEntity.get()), HttpStatus.OK);
    }

    private Integer getKakaoUserIdByKakaoAccessToken(String kakaoAccessToken) throws IOException {
        JsonElement element = getJsonElementByAccessToken(kakaoAccessToken);
        Integer id = element.getAsJsonObject().get("id").getAsInt();

        log.info("로그인 시도하는 유저의 KAKAO ID : {}", id);

        return id;
    }

    private JsonElement getJsonElementByAccessToken(String token) throws IOException {
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);

        return getJsonElement(httpURLConnection);
    }

    private String getKakaoAccessToken(String code) throws IOException {
        String access_token;
        String reqURL = "https://kauth.kakao.com/oauth/token";

        URL url = new URL(reqURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
        String stringBuilder = "grant_type=authorization_code" +
                "&client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
                "&code=" + code;
        bufferedWriter.write(stringBuilder);
        bufferedWriter.flush();


        httpURLConnection.getResponseCode();

        JsonElement element = getJsonElement(httpURLConnection);

        access_token = element.getAsJsonObject().get("access_token").getAsString();

        bufferedWriter.close();

        return access_token;
    }

    private JsonElement getJsonElement(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();

        while((line = bufferedReader.readLine()) != null){
            result.append(line);
        }

        bufferedReader.close();

        return JsonParser.parseString(result.toString());
    }
}

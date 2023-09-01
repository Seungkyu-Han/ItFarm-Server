package CoBo.ItFarm.Service.Impl;

import CoBo.ItFarm.Data.Dto.Auth.Res.AuthLoginRes;
import CoBo.ItFarm.Repository.UserRepository;
import CoBo.ItFarm.Service.AuthService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${kakao.auth.client_id}")
    private String client_id;
    @Value("${kakao.auth.redirect_uri}")
    private String redirect_uri;
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<AuthLoginRes> login(String code) throws IOException{

        Integer userId = getKakaoUserIdByKakaoAccessToken(getKakaoAccessToken(code));

        return null;
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

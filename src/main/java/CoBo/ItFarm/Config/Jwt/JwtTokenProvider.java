package CoBo.ItFarm.Config.Jwt;

import CoBo.ItFarm.Data.Entity.UserEntity;
import CoBo.ItFarm.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private static final Long accessTokenValidTime = Duration.ofHours(2).toMillis();
    private static final Long refreshTokenValidTime = Duration.ofDays(7).toMillis();

    private final UserRepository userRepository;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public boolean isAccessToken(String token) throws MalformedJwtException{
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getHeader().get("type").toString().equals("access");
    }

    public String createAccessToken(Integer id){
        return createJwtToken(id, "access", accessTokenValidTime);
    }

    public String createRefreshToken(Integer id){
        return createJwtToken(id, "refresh", refreshTokenValidTime);
    }

    private String createJwtToken(Integer id, String type, Long tokenValidTime){
        Claims claims = Jwts.claims();

        claims.put("id", id);

        return Jwts.builder()
                .setHeaderParam("type", type)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public Authentication authenticate(String token) throws AuthenticationException, NullPointerException {

        Integer id = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("id", Integer.class);

        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);

        if(optionalUserEntity.isEmpty())
            throw new NullPointerException();

        String authority = optionalUserEntity.get().getRole().toString();


        return new UsernamePasswordAuthenticationToken(id, token, List.of(new SimpleGrantedAuthority(authority)));
    }

}

package com.syniiq.library_management.infrastructure.service;

import com.syniiq.library_management.application.dto.User.RefereshTokenReqDTO;
import com.syniiq.library_management.presentation.exception.AccountException;
import com.syniiq.library_management.presentation.exception.ProcessFailureException;
import com.syniiq.library_management.utils.Tool;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.syniiq.library_management.domain.model.AccessToken;
import com.syniiq.library_management.domain.model.RefreshToken;
import com.syniiq.library_management.domain.model.User;
import com.syniiq.library_management.domain.repository.AccessTokenRepo;
import com.syniiq.library_management.domain.repository.UserRepo;
import com.syniiq.library_management.presentation.exception.RessourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class JWTService {

    public static final String BEARER = "bearer";
    public static final String REFRESH = "refresh";
    private final Environment environment;
    private final UserRepo userRepo;
    private final AccessTokenRepo accessTokenRepo;
    private void disableToken(User user){
        final List<AccessToken> accessTokenList = this.accessTokenRepo.fetchAllAccessTokenByEmail(user.getEmail()).peek(
                jwt -> {
                    jwt.setIsEnabled(true);
                    jwt.setIsExpired(true);
                }
        ).toList();

        this.accessTokenRepo.saveAll(accessTokenList);
    }

    public Map<String,String > generateToken(String username){
        User user = userRepo.findByEmail(username)
                .orElseThrow(()->new RessourceNotFoundException("User not found !"));
        this.disableToken(user);
        Map<String, String> jwtMap = new java.util.HashMap<>(this.buildJwtToken(user));
        //TODO à l'aide d'un cronjob desactivé le token lorsque la date d'expiration est atteinte
        //TODO invalider le refresh token au moment de la création de nouveau token
        //refreshToken expired after 2 months
        RefreshToken refreshToken = RefreshToken.build(
                null,
                UUID.randomUUID().toString(),
                Instant.now(),
                Instant.now().plusMillis( 90L * 24 * 60 * 60 * 1000),
                false);


        AccessToken accessToken = new AccessToken();
        accessToken.setValue(jwtMap.get(BEARER));
        // accessToken.setExpiredAt();
        accessToken.setIsExpired(false);
        accessToken.setIsEnabled(false);
        accessToken.setUser(user);
        accessToken.setCreatedAt(Instant.now());
        accessToken.setRefreshToken(refreshToken);
        this.accessTokenRepo.save(accessToken);
        jwtMap.put(REFRESH, refreshToken.getValue());
        return jwtMap;
    }

    private Map<String, String> buildJwtToken(User user){
        long currentTime = System.currentTimeMillis();
        //accessToken expired after 1 month
        long expirationTime = currentTime +  30L * 24 * 60 * 60 * 1000;
        log.info("expiration: {}", expirationTime);

        Map<String, Object>claims = Map.of(
                "firstname",user.getFirstname(),
                "lastname",user.getLastname(),
                "email",user.getEmail(),
                "roles",user.getRoles(),
                Claims.EXPIRATION,new Date(expirationTime),
                Claims.SUBJECT,user.getEmail()
        );

        String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, this.getKey())
                .compact();

        return  Map.of(BEARER,bearer);
    }

    private Key getKey() {

        final byte[] decode = Decoders.BASE64.decode(this.environment.getProperty("encryption.key"));
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String token){
        return this.getClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token){

        return this.getClaim(token, Claims::getExpiration).before(new Date());
    }


    private <T> T getClaim(String token, Function<Claims,T> function){
        return function.apply(this.getAllClaims(token));
    }

    private Claims getAllClaims(String token){
        //TODO mettre une exception pour gérer les erreurs lors de la manipution des tokens
        //TODO gérer les exceptions liées à la corruption d'un token
        //gérer les exceptions de tokens expirés
//        return Jwts.parserBuilder()
//                .setSigningKey(this.getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
        return null;
    }
    //Todo Remonter l'exception car s'affiche dans les logs
    public AccessToken getTokenByValue(String value) throws ProcessFailureException {
        return this.accessTokenRepo
                .findByValueAndIsEnabledAndIsExpired(value,false, false)
                .orElseThrow(()->new ProcessFailureException("Please provide correct access token!"));
    }


    @Scheduled(cron = "@weekly")
    //@Scheduled(cron = "* */1 * * * *")
    public void removeUselessAccessToken(){
        log.info("Suppression des tokens à {}", Instant.now());
        this.accessTokenRepo.deleteAllByIsEnabledAndIsExpired(true,true);
    }

    public Map<String, String> refreshToken(RefereshTokenReqDTO refereshTokenReqDTO) {
        final AccessToken accessToken = this.accessTokenRepo
                .fetchAccessTokenByRefreshTokenValue(Tool.cleanIt(refereshTokenReqDTO.refreshToken()))
                .orElseThrow(()->new RessourceNotFoundException("Refresh Token Invalid !"));
        if(accessToken.getRefreshToken().getIsExpired() || accessToken.getRefreshToken().getExpiredAt().isBefore(Instant.now()))
            throw new AccountException("Refresh Token Expired !");

        return this.generateToken(accessToken.getUser().getEmail());

    }
}
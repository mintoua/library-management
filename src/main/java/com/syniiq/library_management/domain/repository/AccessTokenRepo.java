package com.syniiq.library_management.domain.repository;

import com.syniiq.library_management.domain.model.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface AccessTokenRepo extends JpaRepository<AccessToken, Integer> {
    Optional<AccessToken> findByValueAndIsEnabledAndIsExpired(String token, Boolean isEnabled, Boolean isExpired);
    @Query("select a from AccessToken a where a.user.email=:email AND a.isExpired=:isExpired and a.isEnabled=:isEnabled")
    Optional<AccessToken> fetchValidAccessTokenByUser(@Param("email") String email,
                                                      @Param("isEnabled") Boolean isEnabled,
                                                      @Param("isExpired") Boolean isExpired);

    @Query("select a from AccessToken a where a.user.email=:email")
    Stream<AccessToken> fetchAllAccessTokenByEmail(String email);

    @Query("select a from AccessToken a where a.refreshToken.value=:value")
    Optional<AccessToken> fetchAccessTokenByRefreshTokenValue(@Param("value") String value);

    void deleteAllByIsEnabledAndIsExpired(Boolean isExpired, Boolean isEnable);

}

package vn.tizun.service;

import org.springframework.security.core.GrantedAuthority;
import vn.tizun.common.TokenType;

import java.util.Collection;

public interface IJwtService {
    String generateAccessToken(long userId, String username, Collection<? extends GrantedAuthority> authorities);
    String generateRefreshToken(long userId, String username, Collection<? extends GrantedAuthority> authorities);
    String exactUsername(String token, TokenType type);
}

package by.kharchenko.restcafe.util.jwt;

import by.kharchenko.restcafe.model.entity.Role;
import by.kharchenko.restcafe.model.entity.RoleType;
import by.kharchenko.restcafe.security.JwtAuthentication;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(Integer.parseInt(claims.getSubject()));
        Set<Role> roles = getRoles(claims);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole().name())));
        jwtInfoToken.setAuthorities(authorities);
        jwtInfoToken.setAuthenticated(true);
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {
        Set<Role> resultRoles = new HashSet<>();
        final List<LinkedHashMap<Object, Object>> roles = claims.get("roles", List.class);
        for(LinkedHashMap<Object, Object> roleInfo: roles) {
            Role userRole = new Role();
            for(Map.Entry<Object, Object> entry: roleInfo.entrySet()) {
                if(entry.getKey().equals("id")) {
                    userRole.setRoleId(Long.parseLong(entry.getValue().toString()));
                }
                if(entry.getKey().equals("type")) {
                    userRole.setRole(RoleType.valueOf(entry.getValue().toString()));
                }
                resultRoles.add(userRole);
            }
        }

        return resultRoles;
    }
}


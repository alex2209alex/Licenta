package ro.unibuc.fmi.ge.config;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String CLAIM_REALM_ACCESS = "realm_access";
    private static final String CLAIM_ROLES = "roles";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Map<String, Collection<String>> realmAccess = jwt.getClaim(CLAIM_REALM_ACCESS);
        if (realmAccess != null && !realmAccess.isEmpty()) {
            Collection<String> roles = realmAccess.get(CLAIM_ROLES);
            if (roles != null && !roles.isEmpty()) {
                List<SimpleGrantedAuthority> realmRoles = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                grantedAuthorities.addAll(realmRoles);
            }
        }
        return grantedAuthorities;
    }
}

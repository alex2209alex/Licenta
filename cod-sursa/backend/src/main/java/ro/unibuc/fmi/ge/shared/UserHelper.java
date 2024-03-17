package ro.unibuc.fmi.ge.shared;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
    private static final String CLAIM_COD_FIRMA = "cod-firma";
    private static final String CLAIM_USERNAME = "preferred_username";


    public String getUserName() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaim(CLAIM_USERNAME);
    }

    public Long getUserCompanyId() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.parseLong(jwt.getClaim(CLAIM_COD_FIRMA));
    }

    public Boolean isAgent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleConstants.ROLE_AGENT_NAVA))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean isPilotage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleConstants.ROLE_DISPECER_PILOTAJ))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean isAPMCAuthority() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleConstants.ROLE_DISPECER_APMC))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean isANRAuthority() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleConstants.ROLE_DISPECER_ANR))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean isAuthority() {
        return isANRAuthority() || isAPMCAuthority();
    }
}

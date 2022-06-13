package me.ramos.WaitForm.global.config.util;

import lombok.extern.slf4j.Slf4j;
import me.ramos.WaitForm.global.error.exception.AuthenticationNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() { }

    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new AuthenticationNotFoundException();
        }

        return Long.parseLong(authentication.getName());
    }
}

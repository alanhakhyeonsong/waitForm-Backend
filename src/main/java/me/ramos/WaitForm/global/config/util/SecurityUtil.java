package me.ramos.WaitForm.global.config.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ramos.WaitForm.global.error.exception.AuthenticationNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new AuthenticationNotFoundException();
        }

        return Long.parseLong(authentication.getName());
    }
}

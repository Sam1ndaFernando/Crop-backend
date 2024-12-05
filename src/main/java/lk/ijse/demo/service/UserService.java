package lk.ijse.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
//    boolean sendCodeToChangePassword(UserWithKey userWithKey);
}

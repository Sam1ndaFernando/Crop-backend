package lk.ijse.demo.service;

import lk.ijse.demo.response.JwtAuthResponse;
import lk.ijse.demo.secure.SignUp;
import lk.ijse.demo.secure.SingIn;

public interface AuthenticationService {
    JwtAuthResponse signUp (SignUp signUp);
    JwtAuthResponse signIn (SingIn signUp);
    JwtAuthResponse refreshToken(String refreshToken);
//    void changePassword(ChangePasswordDTO changePasswordDTO);

}

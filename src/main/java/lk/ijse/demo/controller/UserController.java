package lk.ijse.demo.controller;

import lk.ijse.demo.dto.impl.Token;
import lk.ijse.demo.response.JwtAuthResponse;
import lk.ijse.demo.secure.SignUp;
import lk.ijse.demo.secure.SingIn;
import lk.ijse.demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<JwtAuthResponse> signup(@RequestBody SignUp signup){
        return ResponseEntity.ok(authenticationService.signUp(signup));
    }


    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SingIn signIn){
        System.out.println("SIGN IN OBJ  :"+signIn);
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }

    @PostMapping(value = "/refresh",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody Token token){
        System.out.println(token.getToken());
        return ResponseEntity.ok(authenticationService.refreshToken(token.getToken()));
    }


//    @PostMapping(value = "/sendCode")
//    public ResponseEntity<Void> sendCode(@RequestBody()UserWithKey userWithKey){
//        if (userService.sendCodeToChangePassword(userWithKey)) {
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    @PostMapping(value = "/changePassword")
//    public ResponseEntity<Void> changePassword(@RequestBody() ChangePasswordDTO changePasswordDTO){
//        try {
//            authenticationService.changePassword(changePasswordDTO);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}

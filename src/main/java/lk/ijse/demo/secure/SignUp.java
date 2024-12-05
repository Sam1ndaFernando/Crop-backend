package lk.ijse.demo.secure;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@CrossOrigin

public class SignUp {
    @NotNull
    private String user_id;
    @NotNull(message = "Email Can`t be Null ")
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String role;

}

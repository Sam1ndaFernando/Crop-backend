package lk.ijse.demo.secure;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingIn {
    @NotNull(message = "Email Can`t be Null")
    @Email
    private String email;

    @NotNull(message = "Password Can`t be Null ")
    private String password;
}

package lk.ijse.demo.dto.impl;


import jakarta.persistence.Id;
import lk.ijse.demo.dto.SuperDTO;
import lk.ijse.demo.dto.UserStatus;
import lk.ijse.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements SuperDTO, UserStatus {
    @Id
    private String email;
    private String password;
    private Role role;
}

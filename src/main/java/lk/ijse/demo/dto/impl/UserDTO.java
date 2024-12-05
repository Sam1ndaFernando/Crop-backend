package lk.ijse.demo.dto.impl;


import jakarta.persistence.Id;
import lk.ijse.demo.dto.SuperDTO;
import lk.ijse.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO implements SuperDTO {
    @Id
    private String user_id;
    private String email;
    private String password;
    private Role role;
}

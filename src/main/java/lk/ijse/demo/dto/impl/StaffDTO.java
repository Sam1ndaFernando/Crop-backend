package lk.ijse.demo.dto.impl;


import jakarta.persistence.Id;
import lk.ijse.demo.dto.StaffStatus;
import lk.ijse.demo.dto.SuperDTO;
import lk.ijse.demo.entity.Gender;
import lk.ijse.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffStatus {
    @Id
    private String memberCode;
    private String firstName;
    private String lastName;
    private String joinedDate;
    private String dateOfBirth;
    private Gender gender;
    private String designation;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String contactNo;
    private String email;
    private Role role;
    private List<String> equipmentList;
    private List<String> vehicleList;
    private List<String> fieldCodeList;
    private List<String> logList;
}

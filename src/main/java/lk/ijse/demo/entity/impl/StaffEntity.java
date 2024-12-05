package lk.ijse.demo.entity.impl;


import jakarta.persistence.*;
import lk.ijse.demo.entity.Gender;
import lk.ijse.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class StaffEntity {
    @Id
    private String memberCode;
    private String firstName;
    private String lastName;
//    private String joinedDate;
//    private String dateOfBirth;
    private LocalDate joinedDate;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String designation;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String contactNo;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Role role;
    @OneToMany(mappedBy = "staff")
    private List<VehicleEntity> vehicleList;
    @ManyToMany(mappedBy = "staffList")
    private List<FieldEntity> fieldList;
    @ManyToMany(mappedBy = "staffList")
    private List<LogEntity> logList;
    @ManyToMany(mappedBy = "staffCodeList")
    private List<EquipmentEntity> equipmentList;


}


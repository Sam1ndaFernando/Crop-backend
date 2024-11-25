package lk.ijse.demo.entity.impl;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class EquipmentEntity {
    @Id
    private String equipmentCode;
    private String Name;
    private String type;
    private String status;
    private int availableCount;
    @ManyToMany
    @JoinTable(
            name = "equipment_staff_details",
            joinColumns = @JoinColumn(name = "equipmentCode"),
            inverseJoinColumns = @JoinColumn(name = "memberCode")
    )
    private List<StaffEntity> staffCodeList;
    @ManyToMany
    @JoinTable(
            name = "equipment_field_details",
            joinColumns = @JoinColumn(name = "equipmentCode"),
            inverseJoinColumns = @JoinColumn(name = "fieldCode")
    )
    private List<FieldEntity> fieldList;
}

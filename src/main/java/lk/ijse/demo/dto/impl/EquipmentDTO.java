package lk.ijse.demo.dto.impl;


import jakarta.persistence.Id;
import lk.ijse.demo.dto.EquipmentStatus;
import lk.ijse.demo.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO, EquipmentStatus {
    @Id
    private String equipmentCode;
    private String name;
    private String type;
    private String status;
    private int availableCount;
    private List<String> staffCodeList;
    private List<String> fieldList;
}

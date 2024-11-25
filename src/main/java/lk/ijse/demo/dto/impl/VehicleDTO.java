package lk.ijse.demo.dto.impl;

import jakarta.persistence.Id;
import lk.ijse.demo.dto.SuperDTO;
import lk.ijse.demo.dto.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO implements SuperDTO, VehicleStatus {
    @Id
    private String vehicleCode;
    private String licensePlateNumber;
    private String name;
    private String category;
    private String fuelType;
    private String status;
    private String remark;
    private String memberCode;
}

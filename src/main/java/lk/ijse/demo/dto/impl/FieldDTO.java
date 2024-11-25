package lk.ijse.demo.dto.impl;


import jakarta.persistence.Id;
import lk.ijse.demo.dto.FieldStatus;
import lk.ijse.demo.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO implements SuperDTO, FieldStatus {
    @Id
    private String fieldCode;
    private String name;
    private String location;
    private double extentSize;
    private String fieldImage1;
    private String fieldImage2;
    private List<String> equipmentsList;
    private List<String> memberCodeList;
    private List<String> logCodeList;
    private List<String> cropCodeList;
}

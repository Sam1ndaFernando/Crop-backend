package lk.ijse.demo.service;

import lk.ijse.demo.dto.EquipmentStatus;
import lk.ijse.demo.dto.impl.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);
    List<EquipmentDTO> getAllEquipment();
    void deleteEquipment(String id);
    void updateEquipment(String id,EquipmentDTO equipmentDTO);
    EquipmentStatus getSelectedEquipment(String equipmentId);
}

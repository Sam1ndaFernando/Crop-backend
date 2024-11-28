package lk.ijse.demo.service.impl;


import lk.ijse.demo.customerStatusCode.SelectedErrorStatus;
import lk.ijse.demo.dao.EquipmentDAO;
import lk.ijse.demo.dao.FieldDAO;
import lk.ijse.demo.dao.StaffDAO;
import lk.ijse.demo.dto.EquipmentStatus;
import lk.ijse.demo.dto.impl.EquipmentDTO;
import lk.ijse.demo.entity.impl.EquipmentEntity;
import lk.ijse.demo.entity.impl.FieldEntity;
import lk.ijse.demo.entity.impl.StaffEntity;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.exception.EquipmentNotFoundException;
import lk.ijse.demo.service.EquipmentService;
import lk.ijse.demo.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDAO equipmentDAO;
    @Autowired
    private FieldDAO fieldDAO;
    @Autowired
    private StaffDAO staffDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        int number = 0;
        EquipmentEntity equipment = equipmentDAO.findLastRowNative();
        if (equipment != null) {
            String[] parts = equipment.getEquipmentCode().split("-");
            number = Integer.parseInt(parts[1]);
        }
        equipmentDTO.setEquipmentCode("EQUIPMENT-" + ++number);
        List<FieldEntity> fieldEntities = new ArrayList<>();
        List<StaffEntity> staffEntities = new ArrayList<>();
        for (String fieldCode : equipmentDTO.getFieldList()) {
            fieldEntities.add(fieldDAO.getReferenceById(fieldCode));
        }
        EquipmentEntity equipmentEntity = mapping.toEquipmentEntity(equipmentDTO);
        for (FieldEntity field : fieldEntities) {
            field.getEquipmentsList().add(equipmentEntity);
        }
        for (String staffCode : equipmentDTO.getStaffCodeList()) {
            staffEntities.add(staffDAO.getReferenceById(staffCode));
        }
        for (StaffEntity staff : staffEntities) {
            staff.getEquipmentList().add(equipmentEntity);
        }
        equipmentEntity.setFieldList(fieldEntities);
        equipmentEntity.setStaffCodeList(staffEntities);
        EquipmentEntity equEntity = equipmentDAO.save(equipmentEntity);
        if (equEntity == null) {
            throw new DataPersistException("Equipment Not Saved!");
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        List<EquipmentDTO> equipmentDTOS = new ArrayList<>();
        for (EquipmentEntity equipmentEntity:equipmentDAO.findAll()){
            List<String> fieldCode = new ArrayList<>();
            List<String> staffCode = new ArrayList<>();
            for (FieldEntity field:equipmentEntity.getFieldList()){
                fieldCode.add(field.getFieldCode());
            }
            for (StaffEntity staff:equipmentEntity.getStaffCodeList()){
                staffCode.add(staff.getMemberCode());
            }
            EquipmentDTO equipmentDTO = mapping.toEquipmentDTO(equipmentEntity);
            equipmentDTO.setFieldList(fieldCode);
            equipmentDTO.setStaffCodeList(staffCode);
            equipmentDTOS.add(equipmentDTO);
        }
        return equipmentDTOS;
    }


    @Override
    public void deleteEquipment(String id) {
        Optional<EquipmentEntity> selectedEquipment = equipmentDAO.findById(id);
        if (!selectedEquipment.isPresent()){
            throw new EquipmentNotFoundException("Id " + id + "Not Found");
        }else {
            equipmentDAO.deleteById(id);
        }
    }



}


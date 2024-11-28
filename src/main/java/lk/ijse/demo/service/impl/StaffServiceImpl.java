package lk.ijse.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.demo.dao.FieldDAO;
import lk.ijse.demo.dao.StaffDAO;
import lk.ijse.demo.dao.VehicleDAO;
import lk.ijse.demo.dto.StaffStatus;
import lk.ijse.demo.dto.impl.StaffDTO;
import lk.ijse.demo.entity.impl.FieldEntity;
import lk.ijse.demo.entity.impl.StaffEntity;
import lk.ijse.demo.entity.impl.VehicleEntity;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.exception.StaffNotFoundException;
import lk.ijse.demo.service.StaffService;
import lk.ijse.demo.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDAO staffDAO;
    //    @Autowired
//    private StaffEquipmentDetailsServiceImpl staffEquipmentDetailsService;
    @Autowired
    private Mapping mapping;
    @Autowired
    private FieldDAO fieldDAO;
    @Autowired
    private VehicleDAO vehicleDAO;

    @Override
    public void saveStaffMember(StaffDTO staffDTO) {
        int number = 0;
        StaffEntity staff = staffDAO.findLastRowNative();
        if (staff != null) {
            String[] parts = staff.getMemberCode().split("-");
            number = Integer.parseInt(parts[1]);
        }
        staffDTO.setMemberCode("MEMBER-" + ++number);

        List<FieldEntity> fieldEntities = new ArrayList<>();
        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        for (String fieldCode : staffDTO.getFieldCodeList()){
            fieldEntities.add(fieldDAO.getReferenceById(fieldCode));
        }
        for (String vehicleCode : staffDTO.getVehicleList()){
            vehicleEntities.add(vehicleDAO.getReferenceById(vehicleCode));
        }
        StaffEntity staffEntity = mapping.toStaffEntity(staffDTO);
        staffEntity.setFieldList(fieldEntities);
        staffEntity.setVehicleList(vehicleEntities);
        staffEntity.setJoinedDate(toConvertLocalDate(staffDTO.getJoinedDate()));
        staffEntity.setDateOfBirth(toConvertLocalDate(staffDTO.getDateOfBirth()));
        for (FieldEntity field : fieldEntities){
            field.getStaffList().add(staffEntity);
        }
        StaffEntity savedStaff = staffDAO.save(staffEntity);
        if (savedStaff == null) {
            throw new DataPersistException("Staff member not saved");
        }
    }

    protected LocalDate toConvertLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        return LocalDate.parse(date,formatter);
    }

    @Override
    public List<StaffDTO> getAllStaffMember() {
        return null;
    }

    @Override
    public void deleteStaffMember(String staffId) throws StaffNotFoundException {

    }

    @Override
    public void updateStaffMember(String id, StaffDTO staffDTO) {

    }

    @Override
    public StaffStatus getSelectedStaffMember(String staffId) {
        return null;
    }
}

package lk.ijse.demo.service;

import lk.ijse.demo.dto.StaffStatus;
import lk.ijse.demo.dto.impl.StaffDTO;
import lk.ijse.demo.exception.StaffNotFoundException;

import java.util.List;

public interface StaffService {
    void saveStaffMember(StaffDTO staffDTO);
    List<StaffDTO> getAllStaffMember();
    void deleteStaffMember(String staffId) throws StaffNotFoundException;
    void updateStaffMember(String id, StaffDTO staffDTO);
    StaffStatus getSelectedStaffMember(String staffId);
}

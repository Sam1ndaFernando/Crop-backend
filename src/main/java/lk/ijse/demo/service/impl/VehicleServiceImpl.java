package lk.ijse.demo.service.impl;


import jakarta.persistence.EntityNotFoundException;
import lk.ijse.demo.customerStatusCode.SelectedErrorStatus;
import lk.ijse.demo.dao.StaffDAO;
import lk.ijse.demo.dao.VehicleDAO;
import lk.ijse.demo.dto.VehicleStatus;
import lk.ijse.demo.dto.impl.VehicleDTO;
import lk.ijse.demo.entity.impl.StaffEntity;
import lk.ijse.demo.entity.impl.VehicleEntity;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.exception.VehicleNotFoundException;
import lk.ijse.demo.service.VehicleService;
import lk.ijse.demo.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDAO vehicleDAO;
    @Autowired
    private Mapping mapping;
    @Autowired
    private StaffDAO staffDAO;

    @Override
//    public void saveVehicle(VehicleDTO vehicleDTO) {
//        int number = 0;
//        VehicleEntity vehicle = vehicleDAO.findLastRowNative();
//        if (vehicle != null){
//            String[] parts = vehicle.getVehicleCode().split("-");
//            number = Integer.parseInt(parts[1]);
//        }
//        vehicleDTO.setVehicleCode("VEHICLE-" + ++number);
//        VehicleEntity vehicleEntity = mapping.toVehicleEntity(vehicleDTO);
//        if (vehicleDTO.getMemberCode() != null){
//            StaffEntity referenceById = staffDAO.getReferenceById(vehicleDTO.getMemberCode());
//            vehicleEntity.setStaff(referenceById);
//        }
//        VehicleEntity save = vehicleDAO.save(vehicleEntity);
//        if (save == null){
//            throw new DataPersistException("Vehicle Not Saved");
//        }
//    }

    public void saveVehicle(VehicleDTO vehicleDTO) {
        int number = 0;
        VehicleEntity lastVehicle = vehicleDAO.findLastRowNative();
        if (lastVehicle != null && lastVehicle.getVehicleCode() != null) {
            String[] parts = lastVehicle.getVehicleCode().split("-");
            if (parts.length == 2) {
                number = Integer.parseInt(parts[1]);
            } else {
                throw new IllegalStateException("Invalid vehicle code format: " + lastVehicle.getVehicleCode());
            }
        }
        // Set the new vehicle code
        vehicleDTO.setVehicleCode("VEHICLE-" + ++number);

        // Map to VehicleEntity
        VehicleEntity vehicleEntity = mapping.toVehicleEntity(vehicleDTO);

        // Associate staff member if provided
        if (vehicleDTO.getMemberCode() != null) {
            StaffEntity staffReference = staffDAO.getReferenceById(vehicleDTO.getMemberCode());
            if (staffReference == null) {
                throw new EntityNotFoundException("Staff member not found for code: " + vehicleDTO.getMemberCode());
            }
            vehicleEntity.setStaff(staffReference);
        }

        // Save to the database
        VehicleEntity savedEntity = vehicleDAO.save(vehicleEntity);
        if (savedEntity == null) {
            throw new DataPersistException("Vehicle not saved.");
        }else {
            System.out.println("saveddddddddd");
        }

        // Log success
//        log.info("Vehicle saved successfully: {}", savedEntity.getVehicleCode());
    }


    @Override
    public List<VehicleDTO> getAllVehicle() {
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        List<VehicleEntity> all = vehicleDAO.findAll();
        for (VehicleEntity vehicle : all){
            VehicleDTO vehicleDTO = mapping.toVehicleDTO(vehicle);
            if (vehicle.getStaff() != null) {
                String memberCode = vehicle.getStaff().getMemberCode();
                vehicleDTO.setMemberCode(memberCode);
            }
            vehicleDTOS.add(vehicleDTO);
        }
        return vehicleDTOS;
    }

    @Override
    public void deleteVehicle(String id) throws VehicleNotFoundException {
        Optional<VehicleEntity> selectedVehicle = vehicleDAO.findById(id);
        if (!selectedVehicle.isPresent()){
            throw new VehicleNotFoundException("Vehicle Id With" + id + "Not Found");
        }else {
            vehicleDAO.deleteById(id);
        }
    }

    @Override
    public void updateVehicle(String id, VehicleDTO vehicleDTO) {
        Optional<VehicleEntity> tmpVehicle = vehicleDAO.findById(id);
        if (tmpVehicle.isPresent()){
            tmpVehicle.get().setLicensePlateNumber(vehicleDTO.getLicensePlateNumber());
            tmpVehicle.get().setName(vehicleDTO.getName());
            tmpVehicle.get().setCategory(vehicleDTO.getCategory());
            tmpVehicle.get().setFuelType(vehicleDTO.getFuelType());
            tmpVehicle.get().setStatus(vehicleDTO.getStatus());
            tmpVehicle.get().setRemark(vehicleDTO.getRemark());

            String memberCode = vehicleDTO.getMemberCode();
            StaffEntity staffEntity = staffDAO.getReferenceById(memberCode);
            tmpVehicle.get().setStaff(staffEntity);
        }
    }

    @Override
    public VehicleStatus getSelectedVehicle(String id) {
        if(vehicleDAO.existsById(id)){
            var selectedVehicle = vehicleDAO.getReferenceById(id);
            return mapping.toVehicleDTO(selectedVehicle);
        }else {
            return new SelectedErrorStatus(2,"Selected Vehicle Not Found");
        }
    }
}

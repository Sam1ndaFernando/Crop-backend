package lk.ijse.demo.service;



import lk.ijse.demo.dto.VehicleStatus;
import lk.ijse.demo.dto.impl.VehicleDTO;
import lk.ijse.demo.exception.VehicleNotFoundException;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    List<VehicleDTO> getAllVehicle();
    void deleteVehicle(String id) throws VehicleNotFoundException;
    void updateVehicle(String id, VehicleDTO vehicleDTO);
    VehicleStatus getSelectedVehicle(String id);
}

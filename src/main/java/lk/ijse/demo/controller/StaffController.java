package lk.ijse.demo.controller;

import lk.ijse.demo.dto.impl.StaffDTO;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.service.StaffService;
import lk.ijse.demo.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/staff")
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffService staffService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staffDTO){
        try{
            if (!Regex.emailValidator(staffDTO.getEmail()).matches()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.saveStaffMember(staffDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

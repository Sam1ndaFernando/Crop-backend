package lk.ijse.demo.controller;

import jakarta.annotation.security.RolesAllowed;
import lk.ijse.demo.dto.LogStatus;
import lk.ijse.demo.dto.impl.LogDTO;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.service.LogService;
import lk.ijse.demo.util.IdGenerate;
import lk.ijse.demo.util.IdListConverter;
import lk.ijse.demo.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/logs")
@CrossOrigin(origins = "http://localhost:63343")
public class LogController {
    @Autowired
    private LogService logService;
    //    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveLog(
            @RequestParam("date") String date,
            @RequestParam("logDetails") String logDetails,
            @RequestParam("observedImage") MultipartFile observedImage,
            @RequestParam("staffList") String staffList,
            @RequestParam("cropList") String cropList,
            @RequestParam("fieldList") String fieldList
    ){
        try {
            List<String> staffListt = new ArrayList<>();
            if (staffListt != null){
                staffListt = IdListConverter.spiltLists(staffList);
            }
            List<String> cropListt = new ArrayList<>();
            if (cropListt != null){
                cropListt = IdListConverter.spiltLists(cropList);
            }
            List<String> fieldListt = new ArrayList<>();
            if (fieldListt != null){
                fieldListt = IdListConverter.spiltLists(fieldList);
            }
            LogDTO logDTO = new LogDTO();
            logDTO.setDate(date);
            logDTO.setLogDetails(logDetails);
            logDTO.setObservedImage(IdGenerate.imageBase64(observedImage.getBytes()));
            // logDTO.setObservedImage(observedImage);
            logDTO.setStaffList(staffListt);
            logDTO.setCropList(cropListt);
            logDTO.setFieldList(fieldListt);
            System.out.println(logDTO);
            logService.saveLog(logDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value = "/{logId}",produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    public LogStatus getSelectedLog(@PathVariable("logId") String logId){

        return null;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed({"MANAGER","ADMINISTRATIVE","SCIENTIST"})
    public List<LogDTO> getAllLog(){
        return logService.getAllLog();
    }

    @DeleteMapping(value = "/{logId}")
//    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    public ResponseEntity<Void> deleteLog(@PathVariable ("logId") String logId){
        try {
            if (!Regex.idValidator(logId).matches()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            logService.deleteLog(logId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{logId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    public ResponseEntity<Void> updateLog(
            @PathVariable(value = "logId") String logId,
            @RequestParam(value = "date") String date,
            @RequestParam(value = "logDetails") String logDetails,
            @RequestParam(value = "observedImage") MultipartFile observedImage,
            @RequestParam(value = "staffList") String staffList,
            @RequestParam(value = "cropList") String cropList,
            @RequestParam(value = "fieldList") String fieldList
    ) throws IOException {
        List<String> staffId = new ArrayList<>();
        List<String> cropId = new ArrayList<>();
        List<String> fieldId = new ArrayList<>();

        if (staffList != null) {
            staffId = IdListConverter.spiltLists(staffList);
        }
        if (cropList != null) {
            cropId = IdListConverter.spiltLists(cropList);
        }
        if (fieldList != null) {
            fieldId = IdListConverter.spiltLists(fieldList);
        }
        String base64Img1;
        byte[] bytes = observedImage.getBytes();

        base64Img1 = IdGenerate.imageBase64(bytes);
//        LogDTO logDTO = new LogDTO(
//                logId,
//                date,
//                logDetails,
//                base64Img1,
//                new ArrayList<>(),staffId,
//                new ArrayList<>(),cropId,
//                new ArrayList<>(),fieldId
//        );

        try {
            //  logService.updateLog(logId, logDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


//        LogDTO logDTO = new LogDTO();
//        logDTO.setLogCode(logId);
//        logDTO.setDate(date);
//        logDTO.setLogDetails(logDetails);
//        logDTO.setObservedImage(observedImage);
//        logDTO.setStaffList(staffList);
//        logDTO.setCropList(cropList);
//        logDTO.setFieldList(fieldList);
//        logService.updateLog(logId,logDTO);
//        System.out.println(logDTO);

    }
}

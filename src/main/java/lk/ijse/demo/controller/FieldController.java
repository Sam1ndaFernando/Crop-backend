package lk.ijse.demo.controller;
import lk.ijse.demo.dto.FieldStatus;
import lk.ijse.demo.dto.impl.FieldDTO;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.exception.FieldNotFoundException;
import lk.ijse.demo.service.FieldService;
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
@RequestMapping("api/v1/field")
@CrossOrigin(origins = "http://localhost:63343", allowedHeaders = "*", allowCredentials = "true")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    //    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveField(
            @RequestPart("name") String fieldName,
            @RequestPart("location") String location,
            @RequestPart("extentSize") String extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2,
            @RequestPart("staffList") String staffList,
            @RequestPart("cropList") String cropList
            // @RequestPart("equipmentId")String equipmentList

    ) {
        try {
            List<String> staffCode = new ArrayList<>();
            if (staffList!=null){
                staffCode= IdListConverter.spiltLists(staffList);
            }
            List<String> cropCode = new ArrayList<>();
            if (cropList!=null){
                cropCode=IdListConverter.spiltLists(cropList);
            }
//            List<String>eid=new ArrayList<>();
//            if (equipmentList!=null){
//                eid=IdListConverter.spiltLists(equipmentList);
//            }

            var fieldDTO = new FieldDTO();
            fieldDTO.setName(fieldName);
            fieldDTO.setLocation(location);
            fieldDTO.setExtentSize(Double.parseDouble(extentSize));
            fieldDTO.setFieldImage1(IdGenerate.imageBase64(fieldImage1.getBytes()));
            fieldDTO.setFieldImage2(IdGenerate.imageBase64(fieldImage2.getBytes()));
//            fieldDTO.setCropCodeList(staffList);
//            fieldDTO.setCropCodeList(cropList);
//            fieldDTO.setCropCodeList(staffCode);
//            fieldDTO.setCropCodeList(cropCode);
            fieldDTO.setCropCodeList(staffCode); // Fixed assigning staff codes
            fieldDTO.setMemberCodeList(cropCode); // Correctly assigns crop codes
            // fieldDTO.setEquipmentsList(eid); //

            fieldService.saveField(fieldDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{fieldId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldStatus getSelectedField(@PathVariable("fieldId") String fieldId){
        return fieldService.getSelectedField(fieldId);
    }
    @GetMapping
    public List<FieldDTO> getAllField(){
        try {
            return fieldService.getAllField();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @DeleteMapping(value = "/{fieldId}")
    public ResponseEntity<Void> deleteField(@PathVariable ("fieldId") String fieldId){
        try {
            if (!Regex.idValidator(fieldId).matches()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            fieldService.deleteField(fieldId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (FieldNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{fieldId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(
            @PathVariable("fieldId") String fieldId,
            @RequestPart(value = "name") String fieldName,
            @RequestPart(value = "location") String location,
            @RequestPart(value = "extentSize") String extentSize,
            @RequestPart(value = "fieldImage1") MultipartFile fieldImage1,
            @RequestPart(value = "fieldImage2") MultipartFile fieldImage2,
            @RequestPart(value = "staffList") String staffList,
            @RequestPart(value = "cropList") String cropList
    ) {
        List<String> staffIds = new ArrayList<>();
        List<String> cropIds = new ArrayList<>();

        if (staffList != null) {
            staffIds = IdListConverter.spiltLists(staffList);
        }
        if (cropList != null) {
            cropIds = IdListConverter.spiltLists(cropList);
        }
        String base64Img1;
        String base64Img2;

        try {
            byte[] byteImg1 = fieldImage1.getBytes();
            byte[] byteImg2 = fieldImage2.getBytes();

            base64Img1 = IdGenerate.imageBase64(byteImg1);
            base64Img2 = IdGenerate.imageBase64(byteImg2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FieldDTO fieldDTO = new FieldDTO(
                fieldId,
                fieldName,
                location,
                Double.parseDouble(extentSize),
                base64Img1,base64Img2,
                new ArrayList<>(),staffIds,
                new ArrayList<>(),cropIds
        );

        try {
            fieldService.updateField(fieldId, fieldDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



//        FieldDTO fieldDTO = new FieldDTO();
//        fieldDTO.setFieldCode(fieldId);
//        fieldDTO.setName(fieldName);
//        fieldDTO.setLocation(location);
//        fieldDTO.setExtentSize(Double.parseDouble(extentSize));
//        if (fieldImage1 != null) {
//            fieldDTO.setFieldImage1(IdGenerate.imageBase64(fieldImage1.getBytes()));
//        }
//        if (fieldImage2 != null) {
//            fieldDTO.setFieldImage2(IdGenerate.imageBase64(fieldImage2.getBytes()));
//        }
//        fieldDTO.setMemberCodeList(staffList);
//        fieldDTO.setCropCodeList(cropList);
//        fieldService.updateField(fieldId, fieldDTO);
    }

}



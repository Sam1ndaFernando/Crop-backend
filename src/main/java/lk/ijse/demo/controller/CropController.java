package lk.ijse.demo.controller;

import jakarta.annotation.security.RolesAllowed;
import lk.ijse.demo.dto.impl.CropDTO;
import lk.ijse.demo.exception.CropNotFoundException;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.service.CropService;
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
@RequestMapping("api/v1/crops")
@CrossOrigin(origins = "http://localhost:63343", allowedHeaders = "*", allowCredentials = "true")
public class CropController {
    @Autowired
    private CropService cropService;
    //    private static final Logger log = LoggerFactory.getLogger(CropController.class);
//    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("cropName") String cropName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("cropImage") MultipartFile cropImage,
            @RequestPart("fieldList") String fieldList
    ) {
        try {
            List<String> fieldCodes = new ArrayList<>();
            if (fieldList != null) {
                fieldCodes = IdListConverter.spiltLists(fieldList);
            }
            var cropDTO = new CropDTO();
            cropDTO.setCropName(cropName);
            cropDTO.setScientificName(scientificName);
            cropDTO.setCategory(category);
            cropDTO.setSeason(season);
            cropDTO.setCropImage(IdGenerate.imageBase64(cropImage.getBytes()));
            cropDTO.setFieldCodeList(fieldCodes);
            // cropDTO.setFieldCodeList(fieldList);
            cropService.saveCrop(cropDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed({"MANAGER","ADMINISTRATIVE","SCIENTIST"})
    public List<CropDTO> getAllCrops() {
        return cropService.getAllCrop();
    }

    //    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @DeleteMapping(value = "/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("cropId") String cropId) {
        try {
            if (!Regex.idValidator(cropId).matches()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            cropService.deleteCrop(cropId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CropNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @DeleteMapping("/{cropId}")
//    public ResponseEntity<Void> deleteCrop(@PathVariable String cropId) {
//        try {
//            // Validate the cropId
//            if (!isValidCropId(cropId)) {
//                return ResponseEntity.badRequest().build();
//            }
//
//            // Delete the crop
//            cropService.deleteCrop(cropId);
//
//            // Return no content if successful
//            return ResponseEntity.noContent().build();
//        } catch (CropNotFoundException e) {
//            // Log the exception and return not found status
//            log.error("Crop with ID {} not found: {}", cropId, e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (Exception e) {
//            // Log unexpected exceptions
//            log.error("An unexpected error occurred while deleting crop: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    // Utility function to validate the crop ID
//    private boolean isValidCropId(String cropId) {
//        return Regex.idValidator(cropId).matches();
//    }


    //    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST','ADMINISTRATIVE')")
    @PutMapping(value = "/{cropId}")
    public void updateCrop(
            @PathVariable("cropId") String cropId,
            @RequestPart("cropName") String cropName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("cropImage") MultipartFile cropImage
    ) throws IOException {
        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCode(cropId);
        cropDTO.setCropName(cropName);
        cropDTO.setScientificName(scientificName);
        cropDTO.setCategory(category);
        cropDTO.setSeason(season);
        cropDTO.setCropImage(IdGenerate.imageBase64(cropImage.getBytes()));
        cropService.updateCrop(cropId,cropDTO);
    }



}

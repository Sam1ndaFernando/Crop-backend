package lk.ijse.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.demo.dao.CropDAO;
import lk.ijse.demo.dao.FieldDAO;
import lk.ijse.demo.dao.StaffDAO;
import lk.ijse.demo.dto.impl.FieldDTO;
import lk.ijse.demo.entity.impl.CropEntity;
import lk.ijse.demo.entity.impl.FieldEntity;
import lk.ijse.demo.entity.impl.StaffEntity;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.service.FieldService;
import lk.ijse.demo.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDAO fieldDAO;
    @Autowired
    private StaffDAO staffDAO;
    @Autowired
    private Mapping mapping;
    @Autowired
    private CropDAO cropDAO;


    @Override
    public void saveField(FieldDTO fieldDTO) {
//        int number = 0;
//        FieldEntity field = fieldDAO.findLastRowNative();
//        if (field != null) {
//            String[] parts = field.getFieldCode().split("-");
//            number = Integer.parseInt(parts[1]);
//        }
//        fieldDTO.setFieldCode("FIELD-" + ++number);
//        List<StaffEntity> staffEntities = new ArrayList<>();
//        List<CropEntity> cropEntities = new ArrayList<>();
//        if (fieldDTO.getMemberCodeList() != null || fieldDTO.getCropCodeList() != null) {
//            for (String id : fieldDTO.getMemberCodeList()) {
//                if (staffDAO.existsById(id)) {
//                    staffEntities.add(staffDAO.getReferenceById(id));
//                }
//            }
//            for (String id : fieldDTO.getCropCodeList()) {
//                if (cropDAO.existsById(id)) {
//                    cropEntities.add(cropDAO.getReferenceById(id));
//                }
//            }
//        }
//        FieldEntity fieldEntity = mapping.toFieldEntity(fieldDTO);
//        fieldEntity.setLocation(fieldDTO.getLocation());
//        fieldEntity.setStaffList(staffEntities);
//        fieldEntity.setCropList(cropEntities);
//        FieldEntity saveField = fieldDAO.save(fieldEntity);
//        if (saveField == null) {
//            throw new DataPersistException("Field is not saved.");
//        }
//    }
        int number = 0;
        FieldEntity field = fieldDAO.findLastRowNative();
        if (field != null) {
            String[] parts = field.getFieldCode().split("-");
            number = Integer.parseInt(parts[1]);
        }
        fieldDTO.setFieldCode("FIELD-" + ++number);

        // Initialize staffEntities and cropEntities lists
        List<StaffEntity> staffEntities = new ArrayList<>();
        List<CropEntity> cropEntities = new ArrayList<>();

        // Check if MemberCodeList and CropCodeList are not null before iterating
        if (fieldDTO.getMemberCodeList() != null) {
            for (String id : fieldDTO.getMemberCodeList()) {
                if (staffDAO.existsById(id)) {
                    staffEntities.add(staffDAO.getReferenceById(id));
                }
            }
        }

        if (fieldDTO.getCropCodeList() != null) {
            for (String id : fieldDTO.getCropCodeList()) {
                if (cropDAO.existsById(id)) {
                    cropEntities.add(cropDAO.getReferenceById(id));
                }
            }
        }

        // Map FieldDTO to FieldEntity
        FieldEntity fieldEntity = mapping.toFieldEntity(fieldDTO);
        fieldEntity.setLocation(fieldDTO.getLocation());
        fieldEntity.setStaffList(staffEntities);
        fieldEntity.setCropList(cropEntities);

        // Save the entity and handle the result
        FieldEntity savedField = fieldDAO.save(fieldEntity);
        if (savedField == null) {
            throw new DataPersistException("Field is not saved.");
        }
    }
}

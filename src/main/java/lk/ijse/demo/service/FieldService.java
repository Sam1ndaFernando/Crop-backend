package lk.ijse.demo.service;

import lk.ijse.demo.dto.FieldStatus;
import lk.ijse.demo.dto.impl.FieldDTO;
import lk.ijse.demo.exception.FieldNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);
    List<FieldDTO> getAllField() throws IOException, ClassNotFoundException;
    void deleteField(String id) throws FileNotFoundException, FieldNotFoundException;
    void updateField(String id,FieldDTO fieldDTO);
    FieldStatus getSelectedField(String fieldId);
}

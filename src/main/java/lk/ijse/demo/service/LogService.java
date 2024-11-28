package lk.ijse.demo.service;



import lk.ijse.demo.dto.LogStatus;
import lk.ijse.demo.dto.impl.LogDTO;

import java.util.List;

public interface LogService {
    void saveLog(LogDTO logDTO);
    List<LogDTO> getAllLog();
    void deleteLog(String id);
    void updateLog(String id, LogDTO logDTO);
    LogStatus getSelectedLog(String logId);
}

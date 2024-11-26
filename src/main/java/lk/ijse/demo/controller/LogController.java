package lk.ijse.demo.controller;

import lk.ijse.demo.dto.LogStatus;
import lk.ijse.demo.dto.impl.LogDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/logs")
public class LogController {
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveLog() {
        return null;
    }

    @GetMapping(value = "/{logId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LogStatus getSelectedLog(@PathVariable("logId") String logId) {
        return null;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogDTO> getAllLog() {
        return null;
    }

    @DeleteMapping(value = "/{logId}")
    public ResponseEntity<Void> deleteLog(@PathVariable("logId") String logId) {
        return null;
    }

    @PutMapping(value = "/{logId}")
    public void updateLog(@PathVariable("logId") String logId) throws IOException {

    }
}

package lk.ijse.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
public class HealthCheckerController {
    @GetMapping
    public String healthChecker(){
        return "note controller is working";
    }
}

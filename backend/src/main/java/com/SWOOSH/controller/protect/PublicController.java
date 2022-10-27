package com.SWOOSH.controller.protect;

import com.SWOOSH.dto.CarWashDTO;
import com.SWOOSH.dto.OrderDTO;
import com.SWOOSH.dto.ServiceDTO;
import com.SWOOSH.service.PublicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final PublicService publicService;

    @GetMapping("/getReviews")
    public List<OrderDTO> getReviews(String location) {
        return publicService.getReviews(location);
    }

    @GetMapping("/getAllServicesByCarWash")
    public List<ServiceDTO> getAllServicesByCarWash(String location) {
        return publicService.getAllServicesByCarWash(location);
    }

    @GetMapping("/getAllServices")
    public List<ServiceDTO> getAllServices() {
        return publicService.getAllServices();
    }

    @GetMapping("/getAllCarWashes")
    public List<CarWashDTO> getAllCarWashes() {
        return publicService.getAllCarWashes();
    }
}

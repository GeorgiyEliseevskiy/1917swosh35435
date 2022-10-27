package com.SWOOSH.controller.protect;

import com.SWOOSH.dto.CarWashDTO;
import com.SWOOSH.dto.OrderDTO;
import com.SWOOSH.dto.RegistrationDTO;
import com.SWOOSH.dto.ServiceDTO;
import com.SWOOSH.dto.UserStatsDTO;
import com.SWOOSH.service.AdminService;
import com.SWOOSH.service.OrderService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final OrderService orderService;

    @PostMapping("/createCarWash")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Boolean createCarWash(@RequestBody CarWashDTO carWashDTO) {
        return adminService.createCarWash(carWashDTO);
    }

    @PostMapping("/createEmployee")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Boolean createEmployee(@RequestBody RegistrationDTO registrationDTO) {
        return adminService.createEmployee(registrationDTO);
    }

    @PostMapping("/addServices")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Boolean addService(@RequestBody ServiceDTO serviceDTO) {
        return adminService.createService(serviceDTO);
    }

    @GetMapping("/getAllCarWashes")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public List<CarWashDTO> getAllCarWashes() {
        return adminService.getAllCarWashes();
    }

    @GetMapping("/getAllServicesByCarWash")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public List<ServiceDTO> getAllServicesByCarWash(String location) {
        return adminService.getAllServicesByCarWash(location);
    }

    @GetMapping("/getAllServices")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public List<ServiceDTO> getAllServices() {
        return adminService.getAllServices();
    }

    @GetMapping("/getAllOrders")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public List<OrderDTO> getAllOrders() {
        return adminService.getAllOrders();
    }

    @PutMapping("/acceptOrder")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Boolean acceptOrder(Long id, @RequestBody OrderDTO orderDTO) {
        return orderService.acceptOrder(id, orderDTO);
    }

    @PutMapping("/finishOrder")
//    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Boolean finishOrder(Long id) {
        return orderService.finishOrder(id);
    }

    @GetMapping("/getAllEmployeesByCarWash")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public List<RegistrationDTO> getAllEmployeesByCarWash(String location) {
        return adminService.getAllEmployeesByCarWash(location);
    }

    @GetMapping("/getAllEmployees")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public List<RegistrationDTO> getAllEmployees() {
        return adminService.getAllEmployees();
    }

    @GetMapping("/getNumberEmployeeOrders")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Integer getNumberEmployeeOrders(String employeeName,
            String carWashLocation,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date end
    ) {
        return adminService.getNumberEmployeeOrders(employeeName, carWashLocation, start, end);
    }

    @GetMapping("/getAvgGrade")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Double getAvgGrade(String employeeName, String carWashLocation,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date end) {
        return adminService.getAvgGrade(employeeName, carWashLocation, start, end);
    }

    @GetMapping("/getReturnAbility")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public long getReturnAbility(String employeeName,
            String carWashLocation,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date end) {
        return adminService.getReturnAbility(employeeName, carWashLocation, start, end);
    }

    @GetMapping("/getCustomerStats")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public List<UserStatsDTO> getCustomerStats(String carWashLocation) {
        return adminService.getCustomerStats(carWashLocation);
    }

    @PutMapping("/updateService")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Boolean updateService(@RequestBody ServiceDTO serviceDTO) {
        return adminService.updateService(serviceDTO);
    }

    @DeleteMapping("/deleteService")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public void deleteService(Long id) {
        adminService.deleteService(id);
    }

    @PutMapping("/updateEmployee")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public Boolean updateEmployee(@RequestBody RegistrationDTO registrationDTO) {
        return adminService.updateEmployee(registrationDTO);
    }

    @DeleteMapping("/deleteEmployee")
    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    public void deleteEmployee(Long id) {
        adminService.deleteEmployee(id);
    }
}

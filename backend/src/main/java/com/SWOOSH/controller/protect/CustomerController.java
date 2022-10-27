package com.SWOOSH.controller.protect;

import com.SWOOSH.dto.OrderDTO;
import com.SWOOSH.dto.UserDTO;
import com.SWOOSH.service.OrderService;
import com.SWOOSH.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/createOrder")
    public Boolean createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/gradeOrder")
//    @PreAuthorize("hasAnyAuthority('CUSTOMER_PERMISSION')")
    public Boolean gradeOrder(Long id, Double grade, String text) {
        return orderService.gradeOrder(id, grade, text);
    }

    @GetMapping("/getUser")
//    @PreAuthorize("hasAnyAuthority('CUSTOMER_PERMISSION')")
    public UserDTO getUser(String email) {
        return userService.getUser(email);
    }

    @GetMapping("/getUserOrders")
    public List<OrderDTO> getUserOrders(String email) {
        return orderService.getUserOrders(email);
    }
}

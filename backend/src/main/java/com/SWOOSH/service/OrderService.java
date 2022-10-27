package com.SWOOSH.service;

import com.SWOOSH.dto.CarWashDTO;
import com.SWOOSH.dto.OrderDTO;
import com.SWOOSH.dto.ServiceDTO;
import com.SWOOSH.dto.UserDTO;
import com.SWOOSH.model.CarWash;
import com.SWOOSH.model.Employee;
import com.SWOOSH.model.Order;
import com.SWOOSH.model.User;
import com.SWOOSH.repository.CarWashRepository;
import com.SWOOSH.repository.EmployeeRepository;
import com.SWOOSH.repository.OrderRepository;
import com.SWOOSH.repository.ServiceRepository;
import com.SWOOSH.repository.UserRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CarWashRepository carWashRepository;
    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;

    public Boolean createOrder(OrderDTO orderDTO) {
        User user;
        if (orderDTO.getUser().getEmail() == null) {
            user = userRepository.findByPhone(orderDTO.getUser().getPhone());
            if (user == null) {
                user = new User();
                user.setName(orderDTO.getUser().getName());
                user.setPhone(orderDTO.getUser().getPhone());
                userRepository.save(user);
            }
        } else {
            user = userRepository.findByEmailWithStatusActive(orderDTO.getUser().getEmail());
        }
        CarWash carWash = carWashRepository.getCarWashByLocation(orderDTO.getCarWash().getLocation());
        com.SWOOSH.model.Service service = serviceRepository.getById(orderDTO.getService().getId());

        Order order = new Order();
        order.setCarWash(carWash);
        order.setUser(user);
        order.setDate(new Date());
        order.setGrade(0d);
        order.setPrice(orderDTO.getPrice());
        order.setService(service);
        order.setStatus(false);
        orderRepository.save(order);
        return true;
    }

    public Boolean acceptOrder(Long id, OrderDTO orderDTO) {
        User user = userRepository.getById(id);
        Employee employee = employeeRepository.findByUser(user);
        Order oldOrder = orderRepository.getById(orderDTO.getId());
        oldOrder.setEmployee(employee);
        orderRepository.save(oldOrder);
        return true;
    }

    public Boolean finishOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setStatus(true);
        orderRepository.save(order);
        return true;
    }

    public Boolean gradeOrder(Long id, Double grade, String text) {
        Order order = orderRepository.getById(id);
        order.setGrade(grade);
        order.setText(text);
        orderRepository.save(order);
        return true;
    }

    public List<OrderDTO> getUserOrders(String email) {
        User user = userRepository.findByEmailWithStatusActive(email);
        return orderRepository.getOrdersByUser(user)
                .stream()
                .filter(e -> !e.getService().getDeleted())
                .map(e -> new OrderDTO(
                        e.getOrderId(),
                        new UserDTO(
                                user.getId(),
                                user.getName(),
                                user.getPhone(),
                                user.getEmail(),
                                user.getRole()
                        ),
                        null,
                        new CarWashDTO(
                                e.getCarWash().getCarWashId(),
                                e.getCarWash().getLocation(),
                                e.getCarWash().getName(),
                                e.getCarWash().getPhone(),
                                new ArrayList<>(),
                                new ArrayList<>()
                        ),
                        new ServiceDTO(
                                e.getService().getServiceId(),
                                e.getService().getName(),
                                e.getService().getPrice(),
                                e.getService().getCarWash().getLocation()
                        ),
                        e.getDate(),
                        e.getText(),
                        e.getPrice(),
                        e.getGrade(),
                        e.getStatus()
                ))
                .sorted(Comparator.comparing(OrderDTO::getDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}

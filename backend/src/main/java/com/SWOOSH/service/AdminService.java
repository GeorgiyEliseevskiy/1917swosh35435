package com.SWOOSH.service;

import com.SWOOSH.PasswordGenerator;
import com.SWOOSH.dto.CarWashDTO;
import com.SWOOSH.dto.OrderDTO;
import com.SWOOSH.dto.RegistrationDTO;
import com.SWOOSH.dto.ServiceDTO;
import com.SWOOSH.dto.UserDTO;
import com.SWOOSH.dto.UserStatsDTO;
import com.SWOOSH.enums.Role;
import com.SWOOSH.enums.Status;
import com.SWOOSH.model.CarWash;
import com.SWOOSH.model.Employee;
import com.SWOOSH.model.Order;
import com.SWOOSH.model.User;
import com.SWOOSH.repository.CarWashRepository;
import com.SWOOSH.repository.EmployeeRepository;
import com.SWOOSH.repository.OrderRepository;
import com.SWOOSH.repository.ServiceRepository;
import com.SWOOSH.repository.UserRepository;
import com.SWOOSH.service.util.UserUtilService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final CarWashRepository carWashRepository;
    private final UserUtilService userUtilService;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final OrderRepository orderRepository;
    private final EmailService emailService;


    public Boolean createCarWash(CarWashDTO carWashDTO) {
        if (carWashRepository.existCarWashByLocation(carWashDTO.getLocation())) {
            return false;
        }
        CarWash carWash = new CarWash();
        carWash.setName(carWashDTO.getName());
        carWash.setLocation(carWashDTO.getLocation());
        carWash.setPhone(carWashDTO.getPhone());
        System.out.println(carWash);
        carWashRepository.save(carWash);
        carWashDTO.getServices().forEach(e -> {
            ServiceDTO serviceDTO = new ServiceDTO();
            serviceDTO.setName(e.getName());
            serviceDTO.setPrice(e.getPrice());
            serviceDTO.setCarWashLocation(carWashDTO.getLocation());
            createService(serviceDTO);
        });
        carWashDTO.getEmployees().forEach(e -> {
            RegistrationDTO registrationDTO = new RegistrationDTO();
            registrationDTO.setEmail(e.getEmail());
            registrationDTO.setPhone(e.getPhone());
            registrationDTO.setName(e.getName());
            registrationDTO.setPassword(new BCryptPasswordEncoder(12).encode(e.getPassword()));
            registrationDTO.setCarWashLocation(carWashDTO.getLocation());
            createEmployee(registrationDTO);
        });
        return true;
    }

    public Boolean createEmployee(RegistrationDTO registrationDTO) {
        String passwordForEmployee = null;
        passwordForEmployee = PasswordGenerator.generate(); // Создаем случайный пароль для сотрудника
        if (userUtilService.isPresentEmail(registrationDTO.getEmail())) {
            return false;
        }
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setName(registrationDTO.getName());
        user.setPhone(registrationDTO.getPhone());

        user.setPassword(new BCryptPasswordEncoder(12).encode(passwordForEmployee));
        emailService.sendEmailWithPasswordForEmployee(registrationDTO.getEmail(), passwordForEmployee);

        System.out.println(passwordForEmployee);

        user.setRole(Role.EMPLOYEE);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        CarWash carWash = carWashRepository.getCarWashByLocation(registrationDTO.getCarWashLocation());
        Employee employee = new Employee();
        employee.setUser(user);
        employee.setCarWash(carWash);
        employee.setDeleted(false);
        employeeRepository.save(employee);
        return true;
    }

    public Boolean createService(ServiceDTO serviceDTO) {
        com.SWOOSH.model.Service service = new com.SWOOSH.model.Service();
        service.setName(serviceDTO.getName());
        service.setPrice(serviceDTO.getPrice());
        service.setDeleted(false);
        service.setCarWash(carWashRepository.getCarWashByLocation(serviceDTO.getCarWashLocation()));
        serviceRepository.save(service);
        return true;
    }

    public List<CarWashDTO> getAllCarWashes() {
        return carWashRepository.findAll().stream()
                .map(e -> new CarWashDTO(
                        e.getCarWashId(),
                        e.getLocation(),
                        e.getName(),
                        e.getPhone(),
                        getAllEmployeesByCarWash(e.getLocation()),
                        getAllServicesByCarWash(e.getLocation())
                ))
                .collect(Collectors.toList());
    }

    public List<ServiceDTO> getAllServicesByCarWash(String location) {
        return serviceRepository.findAllByCarWashLocation(location).stream()
                .filter(e -> !e.getDeleted())
                .map(e -> new ServiceDTO(e.getServiceId(), e.getName(), e.getPrice(), location))
                .collect(Collectors.toList());
    }

    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll().stream()
                .filter(e -> !e.getDeleted())
                .map(e -> new ServiceDTO(e.getServiceId(), e.getName(), e.getPrice(), e.getCarWash().getLocation()))
                .collect(Collectors.toList());
    }

    public List<RegistrationDTO> getAllEmployeesByCarWash(String location) {
        CarWash carWash = carWashRepository.getCarWashByLocation(location);
        return employeeRepository.getEmployeesByCarWash(carWash).stream()
                .filter(e -> !e.getDeleted())
                .map(Employee::getUser)
                .map(e -> new RegistrationDTO(
                        e.getId(),
                        e.getName(),
                        e.getPhone(),
                        e.getEmail(),
                        e.getPassword(),
                        carWash.getLocation()
                ))
                .collect(Collectors.toList());
    }

    public List<RegistrationDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .filter(e -> !e.getDeleted())
                .map(e -> new RegistrationDTO(
                        e.getUser().getId(),
                        e.getUser().getName(),
                        e.getUser().getPhone(),
                        e.getUser().getEmail(),
                        e.getUser().getPassword(),
                        e.getCarWash().getLocation()
                ))
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(e -> {
                    User user = e.getUser();
                    User employee = e.getEmployee() != null ? e.getEmployee().getUser() : null;
                    CarWash carWash = e.getCarWash();
                    com.SWOOSH.model.Service service = e.getService();
                    return new OrderDTO(
                            e.getOrderId(),
                            new UserDTO(
                                    user.getId(),
                                    user.getName(),
                                    user.getPhone(),
                                    user.getEmail(),
                                    user.getRole()),
                            new RegistrationDTO(
                                    employee == null ? 0 : employee.getId(),
                                    employee == null ? "" : employee.getName(),
                                    employee == null ? "" : employee.getEmail(),
                                    employee == null ? "" : employee.getPhone(),
                                    employee == null ? "" : employee.getPassword(),
                                    carWash.getLocation()
                            ),
                            new CarWashDTO(
                                    carWash.getCarWashId(),
                                    carWash.getLocation(),
                                    carWash.getName(),
                                    carWash.getPhone(),
                                    getAllEmployeesByCarWash(carWash.getLocation()),
                                    getAllServicesByCarWash(carWash.getLocation())
                            ),
                            new ServiceDTO(
                                    service.getServiceId(),
                                    service.getName(),
                                    service.getPrice(),
                                    service.getCarWash().getLocation()
                            ),
                            e.getDate(),
                            e.getText(),
                            e.getPrice(),
                            e.getGrade(),
                            e.getStatus()
                    );
                })
                .sorted(Comparator.comparing(OrderDTO::getStatus))
                .collect(Collectors.toList());
    }

    public Integer getNumberEmployeeOrders(String name, String carWashLocation, Date start, Date end) {
        User user = userRepository.findByName(name);
        Employee employee = employeeRepository.findByUser(user);
        CarWash carWash = carWashRepository.getCarWashByLocation(carWashLocation);
        return orderRepository.countFinishedOrders(employee, carWash, start, end);
    }


    public long getReturnAbility(String employeeName, String carWashLocation, Date start, Date end) {
        List<String> orders = orderRepository.getOrdersByEmployeeAndCarWash(
                        employeeRepository.findByUser(userRepository.findByName(employeeName)),
                        carWashRepository.getCarWashByLocation(carWashLocation),
                        start,
                        end
                )
                .stream()
                .map(e -> e.getUser().getPhone())
                .collect(Collectors.toList());
        Set<String> uniqueUsernames = new HashSet<>(orders);
        List<String> usernamesWithOneOrder = new ArrayList<>();
        for (String uniqueUser : uniqueUsernames) {
            int counter = 0;
            for (String order : orders) {
                if (counter > 1) {
                    break;
                } else if (order.equals(uniqueUser)) {
                    counter++;
                }
            }
            if (counter == 1) {
                usernamesWithOneOrder.add(uniqueUser);
            }
        }
        return Math.round(100 - ((double) usernamesWithOneOrder.size() / uniqueUsernames.size()) * 100);
    }

    public List<UserStatsDTO> getCustomerStats(String carWashLocation) {
        List<UserStatsDTO> result = new ArrayList<>();
        List<Order> orders = orderRepository
                .getOrdersByCarWash(carWashRepository.getCarWashByLocation(carWashLocation));
        Set<User> uniqueUsers = orders.stream().map(Order::getUser).collect(Collectors.toSet());
        uniqueUsers.forEach(user -> {
            List<Order> userOrders = orderRepository.getOrdersByUser(user);
            result.add(new UserStatsDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    orderRepository.countOrderByCarWashAndUser(
                            carWashRepository.getCarWashByLocation(carWashLocation),
                            user
                    ),
                    userOrders.size() == 0 ? 0 : (
                            (int) Math.round(userOrders.stream()
                                    .map(Order::getPrice)
                                    .reduce(Integer::sum).orElse(0) / (double) userOrders.size())
                    )
            ));
        });
        return result;
    }

    public Double getAvgGrade(String employeeName, String carWashLocation, Date start, Date end) {
        List<Double> grades = orderRepository.getOrdersByEmployeeAndCarWash(
                        employeeRepository.findByUser(userRepository.findByName(employeeName)),
                        carWashRepository.getCarWashByLocation(carWashLocation),
                        start,
                        end
                )
                .stream()
                .map(Order::getGrade)
                .collect(Collectors.toList());
        return grades.size() == 0 ? 0 : grades.stream().reduce(Double::sum).orElse(0d) / grades.size();
    }

    public Boolean updateService(ServiceDTO serviceDTO) {
        com.SWOOSH.model.Service service = serviceRepository.getById(serviceDTO.getId());

        service.setName(serviceDTO.getName());
        service.setPrice(serviceDTO.getPrice());
        service.setOrdered(service.getOrdered());
        service.setCarWash(carWashRepository.getCarWashByLocation(serviceDTO.getCarWashLocation()));
        serviceRepository.save(service);
        return true;
    }

    public Boolean updateEmployee(RegistrationDTO registrationDTO) {
        if (userUtilService.isPresentEmail(registrationDTO.getEmail())) {
            return false;
        }

        User user = userRepository.getById(registrationDTO.getId());
        Employee employee = employeeRepository.findByUser(user);
        CarWash carWash = carWashRepository.getCarWashByLocation(registrationDTO.getCarWashLocation());

        user.setEmail(registrationDTO.getEmail());
        user.setName(registrationDTO.getName());
        user.setPhone(registrationDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder(12).encode(registrationDTO.getPassword()));
        user.setRole(Role.EMPLOYEE);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);

        employee.setUser(user);
        employee.setCarWash(carWash);
        employeeRepository.save(employee);
        return true;
    }

    public void deleteService(Long id) {
        com.SWOOSH.model.Service service = serviceRepository.getById(id);
        orderRepository.findAll().stream()
                .filter(e -> e.getService().getServiceId().equals(id) && !e.getStatus())
                .forEach(orderRepository::delete);
        service.setDeleted(true);
        serviceRepository.save(service);
    }

    public void deleteEmployee(Long id) {
        User user = userRepository.getById(id);
        Employee employee = employeeRepository.findByUser(user);

        orderRepository.findAll().stream()
                .filter(e -> e.getEmployee().getUser().getId().equals(id) && !e.getStatus())
                .forEach(e -> {
                    e.setEmployee(null);
                    orderRepository.save(e);
                });

        user.setStatus(Status.BANNED);
        employee.setDeleted(true);
        employee.setUser(user);

        userRepository.save(user);
        employeeRepository.save(employee);
    }
}

package com.SWOOSH.model;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car_washes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarWash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "car_wash_id")
    private Long carWashId;

    @OneToMany(mappedBy = "carWash")
    private List<Service> services;

    @OneToMany(mappedBy = "carWash")
    private List<Employee> employee;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String name;
}

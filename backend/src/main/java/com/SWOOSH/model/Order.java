package com.SWOOSH.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "order_id")
    private Long orderId;

    @OneToOne
    @JoinColumn(nullable = false, name = "car_wash_id")
    private CarWash carWash;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "service_id")
    private Service service;

    @Column(nullable = false, name = "price")
    private Integer price;

    @Column(nullable = false, name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false, name = "grade")
    private Double grade;

    @Column(name = "text")
    private String text;

    @Column(nullable = false, name = "status")
    private Boolean status;
}

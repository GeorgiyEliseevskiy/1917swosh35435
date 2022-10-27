package com.SWOOSH.repository;

import com.SWOOSH.model.CarWash;
import com.SWOOSH.model.Service;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findAllByCarWash(CarWash carWash);

    List<Service> findAllByCarWashLocation(String carWashLocation);
}

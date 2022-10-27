package com.SWOOSH.repository;

import com.SWOOSH.model.CarWash;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarWashRepository extends JpaRepository<CarWash, Long> {

    CarWash getCarWashByLocation(String location);

    @Query("SELECT location FROM CarWash")
    List<String> getAllLocations();

    @Query("SELECT case when count(cw)>0 then TRUE else FALSE end FROM CarWash cw "
            + "WHERE cw.location = :location")
    Boolean existCarWashByLocation(@Param("location") String location);
}

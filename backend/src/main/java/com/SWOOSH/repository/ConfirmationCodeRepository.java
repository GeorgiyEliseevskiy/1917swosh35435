package com.SWOOSH.repository;

import com.SWOOSH.model.ConfirmationCode;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    ConfirmationCode getConfirmationCodeByContact(String contact);

    @Query("SELECT case when count(cc)>0 then TRUE else FALSE end FROM ConfirmationCode cc "
            + "WHERE cc.contact = :contact")
    Boolean existByContact(@Param("contact") String contact);

    @Modifying
    @Transactional
    @Query(value = "UPDATE confirmation_codes SET code = :code WHERE contact = :contact", nativeQuery = true)
    void updateCode(@Param("contact") String contact, @Param("code") String code);
}

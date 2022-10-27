package com.SWOOSH.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "confirmation_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "confirmation_code_id")
    private Long confirmationCodeId;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String code;
}

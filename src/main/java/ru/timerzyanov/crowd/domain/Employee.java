package ru.timerzyanov.crowd.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String lastName,
            firstName,
            patronymic;

}

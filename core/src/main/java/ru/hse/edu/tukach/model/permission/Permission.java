package ru.hse.edu.tukach.model.permission;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Разрешение
 */
@Getter
@Setter
@Entity
@Table(name = "role")
public class Permission {

    /**
     * Идентификатор разрешения
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Обозначение разрешения
     */
    private String name;
}

package ru.hse.edu.tukach.model.roleuser;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "role_user")
@EntityListeners(AuditingEntityListener.class)
public class RoleUser {

    @EmbeddedId
    private RoleUserId id;
}

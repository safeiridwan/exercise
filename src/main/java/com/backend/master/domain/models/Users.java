package com.backend.master.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(	name = "users", uniqueConstraints = { 
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email") 
})
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    @Column(columnDefinition = "varchar(255) default ''")
    private String modifiedBy;

}

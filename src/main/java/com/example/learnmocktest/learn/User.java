package com.example.learnmocktest.learn;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String LastName;

    private int age;
}

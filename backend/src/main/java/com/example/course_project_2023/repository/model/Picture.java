package com.example.course_project_2023.repository.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "picture")
@Data
public class Picture {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "data")
    @Lob
    private byte[] data;
}

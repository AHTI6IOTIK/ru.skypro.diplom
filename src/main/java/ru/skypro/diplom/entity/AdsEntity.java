package ru.skypro.diplom.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ads", schema = "public")
@Data
public class AdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;
    private String image;
    private int price;
    private String title;
    private String description;
}

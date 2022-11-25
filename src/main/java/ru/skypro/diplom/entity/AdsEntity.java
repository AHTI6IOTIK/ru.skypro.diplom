package ru.skypro.diplom.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@Data
public class AdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ads_id_seq")
    @SequenceGenerator(name="ads_id_seq", sequenceName="ads_id_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    private UserEntity user;
    private String image;
    private int price;
    private String title;
    private String description;
}

package ru.skypro.diplom.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="comment_id_seq")
    @SequenceGenerator(name="comment_id_seq", sequenceName="comment_id_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private AdsEntity ads;
    private LocalDateTime createdAt;
    private String text;
}

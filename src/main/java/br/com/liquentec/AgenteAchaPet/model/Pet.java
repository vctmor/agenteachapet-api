package br.com.liquentec.AgenteAchaPet.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;
    private String breed;
    private Integer age;

    @Lob
    @Column
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

}
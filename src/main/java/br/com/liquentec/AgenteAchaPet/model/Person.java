package br.com.liquentec.AgenteAchaPet.model;


import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Table(name="person")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @PrePersist
    public void ensureDefaults() {
        if (role == null)
            role = Role.REPORTER;
    }

    @OneToMany(mappedBy = "person")
    private List<Pet> pets;
    
}

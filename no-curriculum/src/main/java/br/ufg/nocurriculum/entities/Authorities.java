package br.ufg.nocurriculum.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "authorities")
public class Authorities {

    @Id
    @Column(name = "authority", nullable = false, updatable = false, length = 50)
    private String authority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username", nullable = false, foreignKey = @ForeignKey(name = "fk_authorities_username"))
    private Users username;

    public Authorities(String authority) {
        this.authority = authority;
    }
}
package br.ufg.nocurriculum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "authorities", uniqueConstraints = {
    @UniqueConstraint(name = "unique_authorities_username_authority", columnNames = {"username", "authority"})
})
public class Authorities {

    @Id
    @ManyToOne
    @JoinColumn(name = "username", nullable = false, foreignKey = @ForeignKey(name = "fk_authorities_username"))
    private Users username;

    @Column(name = "authority", nullable = false, updatable = false, length = 50)
    private String authority;

}
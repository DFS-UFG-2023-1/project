package br.ufg.nocurriculum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "profession")
public class Profession {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "profession-sequence-generator"
    )
    @SequenceGenerator(
        name = "profession-sequence-generator",
        sequenceName = "profession_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false, foreignKey = @ForeignKey(name = "fk_profession_username"))
    private UserProfile profile;
    
}

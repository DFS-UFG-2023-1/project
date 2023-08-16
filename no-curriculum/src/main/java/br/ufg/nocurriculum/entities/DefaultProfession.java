package br.ufg.nocurriculum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "default_profession", uniqueConstraints = {
    @UniqueConstraint(name = "uk_default_profession_name", columnNames = {"name"})
})
public class DefaultProfession {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "default_profession-sequence-generator"
    )
    @SequenceGenerator(
        name = "default_profession-sequence-generator",
        sequenceName = "default_profession_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "segment_default_profession",
        joinColumns = @JoinColumn(name = "profession_segment_id"),
        inverseJoinColumns = @JoinColumn(name = "default_profession_id")
    )
    private List<ProfessionSegment> professionSegment;
    
}

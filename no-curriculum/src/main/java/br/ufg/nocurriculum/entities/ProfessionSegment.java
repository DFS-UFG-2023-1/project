package br.ufg.nocurriculum.entities;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "profession_segment")
public class ProfessionSegment {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "profession_segment-sequence-generator"
    )
    @SequenceGenerator(
        name = "profession_segment-sequence-generator",
        sequenceName = "profession_segment_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "professionSegment", cascade = CascadeType.ALL)
    private List<DefaultProfession> defaultProfessions;
}

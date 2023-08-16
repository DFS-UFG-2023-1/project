package br.ufg.nocurriculum.entities;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_profile-sequence-generator"
    )
    @SequenceGenerator(
        name = "user_profile-sequence-generator",
        sequenceName = "user_profile_sequence"
    )
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "email", nullable = false, foreignKey = @ForeignKey(name = "fk_user_profile_email"))
    private Users user;

    @Column(name = "social_name", nullable = false)
    private String socialName;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Profession> professions;
//
//    @Type(ListArrayType.class)
//    @Column(name = "skills", columnDefinition = "text[]")
//    private Set<String> skills;

    @Column(name = "visible")
    private boolean visible = true;

}

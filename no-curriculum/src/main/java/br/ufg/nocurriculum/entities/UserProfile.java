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

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER)
    private List<Profession> professions;

    @Column(name = "description")
    private String description;

    @Column(name = "visible")
    private boolean visible = true;

    public String getPhone() {
        if (phone == null || phone.isBlank()) {
            return "";
        }
        return phone;
    }

    public String getDescription() {
        if (description == null || description.isBlank()) {
            return "";
        }
        return description;
    }
}

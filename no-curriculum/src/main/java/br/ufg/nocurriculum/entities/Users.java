package br.ufg.nocurriculum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @Column(name = "password", nullable = false, length = 500)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "username")
    private Set<Authorities> authorities;

    @OneToOne(mappedBy = "user")
    private UserProfile profile;

    private transient String role;

}
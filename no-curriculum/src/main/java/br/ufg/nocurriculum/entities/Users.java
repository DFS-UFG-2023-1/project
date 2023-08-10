package br.ufg.nocurriculum.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "username", nullable = false, updatable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 500)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
    private Set<Authorities> authorities;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        this.authorities = Set.of(new Authorities("DEFAULT"));
    }
}
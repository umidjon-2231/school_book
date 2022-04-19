package com.project.school_book.entity;

import com.project.school_book.entity.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(nullable = false)
    private boolean enabled=true, accountNonExpired=true,
            accountNonLocked=true, credentialsNonExpired=true;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Book> favorites;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new LinkedHashSet<>(role.getAuthorities()
                .stream().map(s -> (GrantedAuthority) new SimpleGrantedAuthority(s)).toList());
    }

}

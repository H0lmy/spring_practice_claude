package com.max.bookwishlist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false,length = 100)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role= Role.USER;



    @Column( nullable = false,length = 150,unique = true)
    private String email;

    @Column( nullable = false)
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Wishlist> wishlists = new ArrayList<>();

    public User() {}


}

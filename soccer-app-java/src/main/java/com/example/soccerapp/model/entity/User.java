//package com.example.soccerapp.model.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//
//import javax.persistence.*;
//import java.util.Collection;
//import java.util.List;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "users")
//public class User implements UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    @Column(nullable = false)
//    private String firstName;
//    @Column(nullable = false)
//    private String lastName;
//    @Column(nullable = false, unique = true)
//    private String userName;
//    @Column(nullable = false)
//    private String password;
//    @ManyToMany(fetch = FetchType.EAGER)
//    private List<Role> roleList;
//
//    public User(String firstName, String lastName, String userName, String password, List<Role> roleList) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.userName = userName;
//        this.password = password;
//        this.roleList = roleList;
//    }
//
//
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roleList  ;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.userName;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}

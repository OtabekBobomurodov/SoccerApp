//package com.example.soccerapp.model.entity;
//
//
//import com.example.soccerapp.model.entity.enums.RoleName;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Id;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Role implements GrantedAuthority {
//
//    @Id
//    private Integer id;
//
//    @Enumerated(EnumType.STRING)
//    private RoleName roleName;
//
//    public Role(RoleName roleName) {
//        this.roleName = roleName;
//    }
//
//    @Override
//    public String getAuthority() {
//        return this.roleName.name();
//    }
//}

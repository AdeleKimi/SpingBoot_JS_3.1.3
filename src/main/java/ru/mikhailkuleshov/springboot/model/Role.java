package ru.mikhailkuleshov.springboot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    private long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;
    public Role() {
    }

    public Role(long id) {
        this.id = id;
    }

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString(){
//        String smallName = getName();
//        if (smallName.equals("ROLE_ADMIN")){
//            return "Admin";
//        } else if (smallName.equals("ROLE_USER")){
//            return "User";
//        }


        return getName();
    }




}

package com.ctl.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "email")
    @Email(message = "*Email Inválido")
    @NotEmpty(message = "*Insira um Email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*A senha não pode ser menor que 5 caracteres")
    @NotEmpty(message = "*Insira uma senha")
    @Transient
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "*Insira o nome")
    private String name;

    @Column(name= "expiration_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;


    @Column(name = "active")
    private boolean active;

    @OneToMany(targetEntity = Role.class, mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List roles;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        if(expirationDate!=null){
            Date today = new Date(new Timestamp(System.currentTimeMillis()).getTime());
            return today.after(expirationDate);
        }
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

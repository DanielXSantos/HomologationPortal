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
    private Long id;

    @Column(name = "email", unique = true)
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
    private boolean active = true;

    //@OneToMany(targetEntity = Role.class, mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private Set<Role> roles;

    @ManyToOne
    private Fabricante fabricante;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public String getRolesToString(){
        String ans = "";
        for (Object o: roles){
            Role r = (Role) o;
            if(!ans.isEmpty()){
                ans += ", ";
            }
            if(r.getRole().equals("ADMIN")){
                ans += "Admin";
            }else if(r.getRole().equals("MASTER_ADMIN")){
                ans += "Master Admin";
            }else if(r.getRole().equals("USER")){
                ans += "Usuário";
            }else if(r.getRole().equals("FABRICANTE")){
                ans += "Fabricante";
            }
        }
        return ans;
    }

    public boolean hasAuthority(String auth){
        Role r;
        for(Object ga: roles){
            r = (Role) ga;
            if(r.getAuthority().equals(auth)){
                return true;
            }
        }
        return false;
    }
}

package org.ganmuren.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Boolean locked;

    @Transient //生成表时不生成该字段
    private List<Role> roles;

    //获取角色信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    //当前账号是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //当前账号是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    //当前凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //当前账号是否可用
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

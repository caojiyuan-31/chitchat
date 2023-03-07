package org.ganmuren.service;


import com.google.gson.Gson;
import org.apache.dubbo.config.annotation.DubboService;
import org.ganmuren.dao.RoleRepository;
import org.ganmuren.dao.UserRepository;
import org.ganmuren.dao.UserRoleRepository;
import org.ganmuren.entity.Role;
import org.ganmuren.entity.User;
import org.ganmuren.entity.UserRole;
import org.muren.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class UserServiceImpl implements UserService, UserDetailsService{ //Dubbo提供接口应位于首位,否则消费者无法找到

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("账户不存在");
        }
        user.setRoles(roleRepository.getUserRolesByUid(user.getId()));

        return user;
    }

    public void signUser(User user){
        User old = userRepository.findByUsername(user.getUsername());
        if(old != null){
            throw new UsernameNotFoundException("账户已存在");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setLocked(false);

        User result = userRepository.save(user);
        for(Role role : user.getRoles()){
            if(roleRepository.findById(role.getId()).isPresent()){
                userRoleRepository.save(new UserRole(result.getId(), role.getId()));
            }
        }
    }

    @Override
    public String getUserJsonById(Integer id) {
        User user = userRepository.findById(id).orElse(new User());
        return new Gson().toJson(user);
    }

}

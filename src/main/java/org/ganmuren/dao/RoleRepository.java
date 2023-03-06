package org.ganmuren.dao;

import org.ganmuren.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "select * from role r, user_role ur where r.id = ur.role_id and ur.user_id = ?1", nativeQuery = true)
    List<Role> getUserRolesByUid(Integer id);
}

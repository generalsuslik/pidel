package com.pidel.service;

import com.pidel.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(Long id);
    Role createRole(Role role);
    Role updateRole(Long id, Role roleToUpdate);
    void deleteRole(Long id);
    Role defaultRole();
}

package com.pidel.service.impl;

import com.pidel.entity.Role;
import com.pidel.repository.RoleRepository;
import com.pidel.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository
                .findById(id)
                .orElseThrow();
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role roleToUpdate) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setName(roleToUpdate.getName());
                    return roleRepository.save(role);
                })
                .orElseGet(() -> {
                    roleToUpdate.setId(id);
                    return createRole(roleToUpdate);
                });
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role defaultRole() {
        return roleRepository.findByName("ROLE_USER");
    }
}

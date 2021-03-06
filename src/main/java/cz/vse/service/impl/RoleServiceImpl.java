package cz.vse.service.impl;

import cz.vse.dto.PersonForm;
import cz.vse.entity.Person;
import cz.vse.entity.RoleEnum;
import cz.vse.entity.UserRole;
import cz.vse.repository.UserRoleRepository;
import cz.vse.service.PersonService;
import cz.vse.service.RoleService;
import ma.glasnost.orika.MapperFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcejka on 03.10.2016.
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final Logger l = Logger.getLogger(this.getClass());

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private MapperFacade mapper;

    @Override
    public List<UserRole> findAllRoles() {
        List<UserRole> userRoleList;
        userRoleList = userRoleRepository.findAll();
        return userRoleList;
    }

    @Override
    public List<UserRole> findAllUserRolesByUser(Person user) {
        List<UserRole> userRoleList = userRoleRepository.findAllUsersRoleByUserOrderById(user);
        return userRoleList;
    }

    @Override
    public List<UserRole> findAllUserRolesByUserId(long id) {
        Person user = personService.findPersonById(id);
        List<UserRole> userRoleList = userRoleRepository.findAllUsersRoleByUserOrderById(user);
        return userRoleList;
    }

    @Override
    public void createRoleForUser(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public void createRoleForUser(PersonForm personForm) {
        List<UserRole> userRoleList = new ArrayList<>();
        Person person = personService.findPersonById(personForm.getId());
        for (RoleEnum role : personForm.getUserRolesEnum()) {
            userRoleList.add(new UserRole(person, role));
        }
        userRoleRepository.save(userRoleList);
    }


    @Override
    public void updateRoleForUser(PersonForm personForm) {
        List<UserRole> userRoleList = new ArrayList<>();
        Person personFromDB = personService.findPersonById(personForm.getId());
        userRoleRepository.delete(personFromDB.getUserRole());
//
        if (personForm.getUserRolesEnum() != null) {
            for (RoleEnum role : personForm.getUserRolesEnum()) {
                userRoleList.add(new UserRole(personFromDB, role));
            }
            userRoleRepository.save(userRoleList);
        }
    }

    public List<RoleEnum> findUsersRoleEnum(long id) {
        Person user = personService.findPersonById(id);
        List<UserRole> userRoleList;
        List<RoleEnum> roleEnumList = new ArrayList<>();
        userRoleList = userRoleRepository.findAllUsersRoleByUserOrderById(user);
        for (UserRole role : userRoleList) {
            roleEnumList.add(role.getRole());
        }
        return roleEnumList;

    }


}




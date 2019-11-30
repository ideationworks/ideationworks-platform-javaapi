package ideationworks.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import springblack.common.Status;
import springblack.common.exceptions.ResourceNotFoundException;
import springblack.identity.organizations.Organization;
import springblack.identity.organizations.OrganizationsService;
import springblack.identity.privileges.UserPrivilege;
import springblack.identity.privileges.UserPrivilegesService;
import springblack.identity.roles.UserRole;
import springblack.identity.roles.UserRolesService;
import springblack.identity.users.User;
import springblack.identity.users.UsersService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private OrganizationsService organizationsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private UserPrivilegesService userPrivilegesService;

    private Organization defaultOrganization;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        System.out.println("Initial data loading...");

        try {

            defaultOrganization = organizationsService.getByName("default");

        } catch (ResourceNotFoundException e) {

            defaultOrganization = new Organization();

            defaultOrganization.setName("default");

            Organization savedOrganization = organizationsService.create(defaultOrganization);

        }

        try {

            User user = usersService.getByEmail("root@root.com");

            usersService.setPassword(user.getId(), "asdfasdf");

        } catch (ResourceNotFoundException e) {

            User user = new User();

            user.setStatus(Status.ACTIVE);
            user.setEmail("root@root.com");
            user.setPassword("asdfasdf");
            user.setOrganization(defaultOrganization);
            user.setRoles(Arrays.asList(createUserRoleWithPrivileges("root", List.of(UserPrivilege.PRIVILEGE_ADMIN_ALL, UserPrivilege.PRIVILEGE_USERS_LOGIN))));

            usersService.create(user);

        }

    }

    private List<UserPrivilege> createUserPrivileges(List<String> privileges) {

        ArrayList<UserPrivilege> created = new ArrayList<>();

        for (String privilege : privileges) {

            try {

                created.add(userPrivilegesService.getByName(privilege));

            } catch (ResourceNotFoundException e) {

                created.add(userPrivilegesService.create(new UserPrivilege(privilege)));

            }

        }

        return created;

    }

    private UserRole createUserRoleWithPrivileges(String name, List<String> privileges) {

        try {

            List<UserPrivilege> _privileges = createUserPrivileges(privileges);

            UserRole role = new UserRole(name);

            role.setOrganizations(Collections.singletonList(defaultOrganization));
            role.setPrivileges(_privileges);

            try {

                return userRolesService.create(role);

            } catch (ResourceNotFoundException e) {

                return userRolesService.getByName(name);

            }

        } catch (ResourceNotFoundException e) {

            return userRolesService.getByName(name);

        }

    }

}

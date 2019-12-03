package ideationworks.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import springblack.categories.CategoriesService;
import springblack.categories.Category;
import springblack.common.Status;
import springblack.common.exceptions.RecordAlreadyExistsException;
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

    @Autowired
    private CategoriesService categoriesService;

    private List<Category> categories = List.of(new Category("Web Application", "Desktop & mobile web-based applications."),
                                                new Category("Other", "Other ideas."));

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        System.out.println("Initial data loading...");

        categories.forEach(category -> {

            try {

                categoriesService.create(category);

            } catch (RecordAlreadyExistsException ignored) {

            }

        });

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
//            user.setRoles(Arrays.asList(createUserRoleWithPrivileges(UserRole.ROLE_ROOT, Collections.singletonList(UserPrivilege.PRIVILEGE_ADMIN_ALL)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_ADMIN, Collections.singletonList(UserPrivilege.PRIVILEGE_ROOT_ALL)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_APPS_ADMIN, Arrays.asList(UserPrivilege.PRIVILEGE_APPS_GET, UserPrivilege.PRIVILEGE_APPS_SEARCH, UserPrivilege.PRIVILEGE_APPS_CREATE, UserPrivilege.PRIVILEGE_APPS_DELETE, UserPrivilege.PRIVILEGE_APPS_UPDATE)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_APPS_READONLY, Arrays.asList(UserPrivilege.PRIVILEGE_APPS_GET, UserPrivilege.PRIVILEGE_APPS_SEARCH)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_DASHBOARD_READONLY, Collections.singletonList(UserPrivilege.PRIVILEGE_DASHBOARD_GET)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_USERS_READONLY, Arrays.asList(UserPrivilege.PRIVILEGE_USERS_LOGIN, UserPrivilege.PRIVILEGE_USERS_PROFILE_GET)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_USERS_WRITE, Arrays.asList(UserPrivilege.PRIVILEGE_USERS_PROFILE_UPDATE, UserPrivilege.PRIVILEGE_USERS_PROFILE_CHANGEPASSWORD)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_ADVERSEMEDIA_SEARCH, Arrays.asList(UserPrivilege.PRIVILEGE_ADVERSEMEDIA_SEARCH, UserPrivilege.PRIVILEGE_ADVERSEMEDIA_FEEDBACK_GET)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_ADVERSEMEDIA_FEEDBACK, Arrays.asList(UserPrivilege.PRIVILEGE_ADVERSEMEDIA_FEEDBACK_ADD, UserPrivilege.PRIVILEGE_ADVERSEMEDIA_FEEDBACK_GET, UserPrivilege.PRIVILEGE_ADVERSEMEDIA_FEEDBACK_SELF_CHANGE, UserPrivilege.PRIVILEGE_ADVERSEMEDIA_FEEDBACK_SELF_DELETE)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_MLFABRIC_ADMIN, Arrays.asList(UserPrivilege.PRIVILEGE_MLFABRIC_MODEL_SEARCH, UserPrivilege.PRIVILEGE_MLFABRIC_MODEL_GET, UserPrivilege.PRIVILEGE_MLFABRIC_MODEL_CREATE, UserPrivilege.PRIVILEGE_MLFABRIC_MODEL_DELETE, UserPrivilege.PRIVILEGE_MLFABRIC_MODEL_UPDATE)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_MLFABRIC_READONLY, Arrays.asList(UserPrivilege.PRIVILEGE_MLFABRIC_MODEL_SEARCH, UserPrivilege.PRIVILEGE_MLFABRIC_MODEL_GET)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_SERVICE_ADMIN, Arrays.asList(UserPrivilege.PRIVILEGE_SERVICE_SEARCH, UserPrivilege.PRIVILEGE_SERVICE_GET, UserPrivilege.PRIVILEGE_SERVICE_RUN, UserPrivilege.PRIVILEGE_SERVICE_CREATE, UserPrivilege.PRIVILEGE_SERVICE_UPDATE, UserPrivilege.PRIVILEGE_SERVICE_DELETE)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_SERVICE_READONLY, Arrays.asList(UserPrivilege.PRIVILEGE_SERVICE_SEARCH, UserPrivilege.PRIVILEGE_SERVICE_GET, UserPrivilege.PRIVILEGE_SERVICE_RUN)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_TERMINAL_READONLY, Arrays.asList(UserPrivilege.PRIVILEGE_TERMINAL_SEARCH, UserPrivilege.PRIVILEGE_TERMINAL_RUN)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_ELASTICSEARCH_ADMIN, Arrays.asList(UserPrivilege.PRIVILEGE_ELASTICSEARCH_SEARCH)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_SETTINGS_ADMIN, Arrays.asList(UserPrivilege.PRIVILEGE_SETTINGS_VIEW)),
//                                        createUserRoleWithPrivileges(UserRole.ROLE_SETTINGS_READONLY, Arrays.asList(UserPrivilege.PRIVILEGE_SETTINGS_VIEW))
//                                       )
//                         );

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

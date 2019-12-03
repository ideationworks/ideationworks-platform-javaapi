package ideationworks.ideas;

import ideationworks.ideas.categories.IdeaCategoriesService;
import ideationworks.ideas.objects.IdeaCreate;
import ideationworks.ideas.objects.IdeaUser;
import ideationworks.ideas.tags.IdeaTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springblack.categories.CategoriesService;
import springblack.common.exceptions.ResourceNotFoundException;
import springblack.identity.users.UsersService;
import springblack.tags.TagsService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IdeasService {

    private final IdeasRepository       ideasRepository;
    private final UsersService          usersService;
    private final CategoriesService     categoriesService;
    private final IdeaCategoriesService ideaCategoriesService;
    private final TagsService           tagsService;
    private final IdeaTagsService       ideaTagsService;

    @Autowired
    public IdeasService(final IdeasRepository ideasRepository,
                        final UsersService usersService,
                        final CategoriesService categoriesService,
                        final TagsService tagsService,
                        final IdeaCategoriesService ideaCategoriesService,
                        final IdeaTagsService ideaTagsService) {

        this.ideasRepository = ideasRepository;
        this.usersService = usersService;
        this.ideaCategoriesService = ideaCategoriesService;
        this.categoriesService = categoriesService;
        this.tagsService = tagsService;
        this.ideaTagsService = ideaTagsService;

    }

    public Idea create(IdeaCreate ideaCreate, Principal principal) {

        Idea idea = new Idea();

        idea.setOrganization(usersService.getByPrincipal(principal).getOrganization());
        idea.setUser(usersService.getByPrincipal(principal));

        Idea entity = ideasRepository.save(idea);

        ideaCreate.getCategories().forEach(category -> {

            ideaCategoriesService.create(entity, categoriesService.getById(category));

        });

        return idea;

    }

    /**
     * Retrieve an idea by it's unique identifier.
     *
     * @param id Unique identiifer (UUIDv4)
     *
     * @return Returns the corresponding idea.
     *
     * @throws ResourceNotFoundException Throws when idea could not be located.
     */
    public Idea getById(UUID id) {

        return ideasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("could not locate Idea"));

    }

    public Idea getByIdAndPrincipalOrganization(UUID id, Principal principal) {

        return ideasRepository.getByIdAndOrganization(id, usersService.getByPrincipal(principal).getOrganization()).orElseThrow(() -> new ResourceNotFoundException("could not locate Idea"));

    }

    public Page<Idea> getByPrincipalOrganization(Principal principal, Pageable pageable) {

        return ideasRepository.getByOrganization(usersService.getByPrincipal(principal).getOrganization(), pageable);

    }

    public Page<Idea> getPageable(Pageable pageable) {

        Page<Idea> ideas = ideasRepository.findAll(pageable);

        for (int i = 0; i < ideas.getContent().size(); i++) {

            ideas.getContent().get(i).set_user(new IdeaUser(ideas.getContent().get(i).getUser()));

        }

        return ideas;

    }

//    public Page<Idea> getByCategories(List<String> categories, Pageable pageable) {
//
//        return ideasRepository.getByCategory(categoriesService.getByName(categoryName), pageable);
//
//    }

    public Page<Idea> search(String terms, List<String> tagNames, List<String> categoryNames, Pageable pageable) {

        List<UUID> tagIds = new ArrayList<>();

        tagNames.forEach(tagName -> {

            tagIds.add(tagsService.getByName(tagName).getId());

        });

        return ideaTagsService.getByTagIds(tagIds, pageable);


    }

}

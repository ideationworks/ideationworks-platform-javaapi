package ideationworks.ideas.categories;

import ideationworks.ideas.Idea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springblack.categories.CategoriesService;
import springblack.categories.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IdeaCategoriesService {

    private final IdeaCategoriesRepository ideaCategoriesRepository;
    private final CategoriesService        categoriesService;

    @Autowired
    public IdeaCategoriesService(final IdeaCategoriesRepository ideaCategoriesRepository,
                                 final CategoriesService categoriesService) {

        this.ideaCategoriesRepository = ideaCategoriesRepository;
        this.categoriesService = categoriesService;

    }

    public Page<Idea> getIdeasByCategoryNames(List<String> categoryNames, Pageable pageable) {

        List<UUID> categories = new ArrayList<>();

        categoryNames.forEach(categoryName -> {

            categories.add(categoriesService.getByName(categoryName).getId());

        });

        return ideaCategoriesRepository._getByTagCategoryIds(categories, pageable);

    }

    public IdeaCategory create(Idea idea, Category category) {

        return ideaCategoriesRepository.save(new IdeaCategory(idea, category));

    }

}


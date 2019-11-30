package ideationworks.ideas.categories;

import ideationworks.ideas.Idea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springblack.categories.CategoriesService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IdeaCategorysService {

    private final IdeaCategoriesRepository ideaCategoriesRepository;
    private final CategoriesService        categoriesService;

    @Autowired
    public IdeaCategorysService(final IdeaCategoriesRepository ideaCategoriesRepository,
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

}

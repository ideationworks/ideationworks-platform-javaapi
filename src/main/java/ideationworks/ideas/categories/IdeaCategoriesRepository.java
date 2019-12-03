package ideationworks.ideas.categories;

import ideationworks.ideas.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IdeaCategoriesRepository extends PagingAndSortingRepository<IdeaCategory, UUID> {

    @Query(value = "" +
            "SELECT idea.*                                                          " +
            "                                                                       " +
            "FROM ideas_categories AS t                                             " +
            "                                                                       " +
            "INNER JOIN ideas i ON i.id = t.idea_id                                 " +
            "                                                                       " +
            "WHERE t.category_id IN :categoryIds", nativeQuery = true)
    Page<Idea> _getByTagCategoryIds(@Param("categoryIds") List<UUID> categoryIds, Pageable pageable);

}

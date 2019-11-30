package ideationworks.ideas.tags;

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
public interface IdeaTagsRepository extends PagingAndSortingRepository<IdeaTag, UUID> {

    @Query(value = "" +

            "SELECT idea.*                                                          " +
            "                                                                       " +
            "FROM ideas_tags AS t                                                   " +
            "                                                                       " +
            "INNER JOIN ideas i ON i.id = t.idea_id                                 " +
            "                                                                       " +
            "WHERE t.tag_id IN :tags")
    Page<Idea> _getByTagIds(@Param("tags") List<UUID> tags, Pageable pageable);

}

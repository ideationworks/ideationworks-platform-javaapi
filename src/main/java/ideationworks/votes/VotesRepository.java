package ideationworks.votes;

import ideationworks.ideas.Idea;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VotesRepository extends PagingAndSortingRepository<Vote, UUID> {

    List<Vote> getByIdea(Idea idea);

}

package ideationworks.votes;

import ideationworks.ideas.IdeasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
public class VotesService {

    private final VotesRepository votesRepository;
    private final IdeasService    ideasService;

    @Autowired
    public VotesService(final VotesRepository votesRepository,
                        final IdeasService ideasService) {

        this.votesRepository = votesRepository;
        this.ideasService = ideasService;

    }

    public boolean create(UUID ideaId, VoteType voteType, Principal principal) {

//        Idea idea = ideasService.


        return true;

    }

}

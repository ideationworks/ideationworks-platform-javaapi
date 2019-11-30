package ideationworks.ideas.tags;

import ideationworks.ideas.Idea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IdeaTagsService {

    private final IdeaTagsRepository ideaTagsRepository;

    @Autowired
    public IdeaTagsService(final IdeaTagsRepository ideaTagsRepository) {

        this.ideaTagsRepository = ideaTagsRepository;

    }

    public Page<Idea> getByTagIds(List<UUID> tagIds, Pageable pageable) {

        return ideaTagsRepository._getByTagIds(tagIds, pageable);

    }

}

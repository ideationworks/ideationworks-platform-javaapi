package ideationworks.ideas.objects;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class IdeaCreate {

    private String name;
    private String description;

    private List<UUID> tags;
    private List<UUID> categories;

}

package ideationworks.ideas.objects;

import lombok.Data;

@Data
public class Idea {

    private IdeaUser user;

    private String name;
    private String description;

    public Idea(ideationworks.ideas.Idea idea) {

        user = new IdeaUser(idea.getUser());

        name = idea.getName();
        description = idea.getDescription();

    }

}

package ideationworks.ideas.objects;

import lombok.Data;
import springblack.categories.Category;
import springblack.tags.Tag;

import javax.persistence.OneToMany;
import java.util.List;

@Data
public class Idea {

    private IdeaUser user;

    private String name;
    private String description;

    @OneToMany
    private List<Tag> tags;

    @OneToMany
    private List<Category> categories;

    public Idea(ideationworks.ideas.Idea idea) {

        user = new IdeaUser(idea.getUser());

        name = idea.getName();
        description = idea.getDescription();

    }

}

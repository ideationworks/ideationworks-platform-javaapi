package ideationworks.ideas.objects;

import lombok.Data;
import springblack.identity.users.User;

import java.util.UUID;

@Data
public class IdeaUser {

    private UUID   id;
    private String email;

    public IdeaUser(User user) {

        id = user.getId();
        email = user.getEmail();

    }

}

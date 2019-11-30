package ideationworks.ideas;

import ideationworks.ideas.categories.IdeaCategorysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springblack.common.Patterns;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Api(value = "Ideas", tags = { "Ideas" })
@RestController
@RequestMapping("/ideas")
public class IdeasController {

    private final IdeasService         ideasService;
    private final IdeaCategorysService ideaCategorysService;

    @Autowired
    public IdeasController(final IdeasService ideasService,
                           final IdeaCategorysService ideaCategorysService) {

        this.ideasService = ideasService;
        this.ideaCategorysService = ideaCategorysService;

    }

    @ApiOperation(value = "Get ideas by category(s).", notes = "Retrieve ideas by category name(s).")
    @GetMapping
    public ResponseEntity<Page<Idea>> getByCategoryIds(@RequestParam("categories") List<String> categories, Pageable pageable) {

        return ResponseEntity.ok(ideaCategorysService.getIdeasByCategoryNames(categories, pageable));

    }

    @ApiOperation(value = "Get ideas by tag(s).", notes = "Retrieve ideas by tag name(s).")
    @GetMapping
    public ResponseEntity<Page<Idea>> getByTagNames(@PathParam("tags") List<String> tagNames, Pageable pageable) {

        return ResponseEntity.ok(ideasService.getByTags(tagNames, pageable));

    }

    @ApiOperation(value = "Retrieve ideas owned by me.", notes = "Will retrieve ideas from the logged in user.")
    @GetMapping
    public ResponseEntity<Page<Idea>> getAllByPrincipal(Principal principal, Pageable pageable) {

        return ResponseEntity.ok(ideasService.getPageable(pageable));

    }

    @ApiOperation(value = "Retrieve ideas owned by my team(s).", notes = "Will retrieve ideas from the logged in users team(s).")
    @GetMapping(Patterns.UUIDv4)
    public ResponseEntity<Idea> getByIdAndPrincipalOrganization(@PathVariable("uuid") UUID id, Principal principal) {

        return ResponseEntity.ok(ideasService.getByIdAndPrincipalOrganization(id, principal));

    }

    @ApiOperation(value = "Create a new idea.", notes = "Create a new idea.")
    @PostMapping
    public ResponseEntity<Idea> create(@RequestBody Idea idea, Principal principal) {

        return ResponseEntity.ok(ideasService.create(idea, principal));

    }

}

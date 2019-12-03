package ideationworks.votes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ideas/")
public class VotesController {

    private final VotesService votesService;

    @Autowired
    public VotesController(final VotesService votesService) {

        this.votesService = votesService;

    }

//    @GetMapping
//    public ResponseEntity<Page<Vote>> getAllByPrincipal(Principal principal, Pageable pageable) {
//
//        return ResponseEntity.ok(votesService.getAllByPrincipal(principal, pageable));
//
//    }
//
//    @PostMapping("{uuid:[0-9a-fxA-FX]{8}-[0-9a-fxA-FX]{4}-[0-9a-fxA-FX]{4}-[0-9a-fxA-FX]{4}-[0-9a-fxA-FX]{12}}")
//    public ResponseEntity<Vote> create(@RequestBody VoteType vote, Principal principal) {
//
//        return ResponseEntity.ok(votesService.create(vote, principal));
//
//    }

}

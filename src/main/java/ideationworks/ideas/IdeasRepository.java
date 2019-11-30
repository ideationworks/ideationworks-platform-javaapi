package ideationworks.ideas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import springblack.identity.organizations.Organization;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdeasRepository extends PagingAndSortingRepository<Idea, UUID> {

    Page<Idea> getByOrganization(Organization organization, Pageable pageable);

    Optional<Idea> getByIdAndOrganization(UUID id, Organization organization);

    Optional<Idea> getByOrganizationAndName(Organization organization, String name);

}

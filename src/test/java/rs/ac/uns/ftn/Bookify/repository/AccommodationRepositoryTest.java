package rs.ac.uns.ftn.Bookify.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;

@DataJpaTest
@ActiveProfiles("iss")
public class AccommodationRepositoryTest {

    @Autowired
    private IAccommodationRepository accommodationRepository;

}

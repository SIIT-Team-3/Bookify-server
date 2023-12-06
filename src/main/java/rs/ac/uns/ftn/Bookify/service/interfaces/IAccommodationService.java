package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.Accommodation;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public interface IAccommodationService {
    public Page<Accommodation> getAccommodationsForSearch(Integer persons, String location, Date begin, Date end, Pageable pageable);
    public long countByLocationAndGuestRange(Integer persons, String location, Date begin, Date end);
    public FileSystemResource getImages(Long id);
}
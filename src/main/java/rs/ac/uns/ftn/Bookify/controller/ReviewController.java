package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.ReviewDTO;
import rs.ac.uns.ftn.Bookify.enumerations.ReviewType;

import java.util.Date;
import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
//    @Autowired
//    private IReviewService reviewService;

    @GetMapping(value = "/created", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getAllCreatedReviews(){
        //returns all created reviews
        ReviewDTO review1 = new ReviewDTO(1L, 4, "Nice", new Date(), false, false, 1L, ReviewType.ACCOMMODATION);
        ReviewDTO review2 = new ReviewDTO(2L, 3, "Bad", new Date(), false, false, 1L, ReviewType.ACCOMMODATION);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/reported", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getAllReportedReviews(){
        //returns all reported reviews
        ReviewDTO review1 = new ReviewDTO(1L, 4, "Nice", new Date(), false, true, 1L, ReviewType.ACCOMMODATION);
        ReviewDTO review2 = new ReviewDTO(2L, 3, "Bad", new Date(), false, true, 1L, ReviewType.ACCOMMODATION);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getReviewsForAccommodation(@PathVariable Long accommodationId){
        //returns reviews for accommodation
        ReviewDTO review1 = new ReviewDTO(1L, 4, "Nice", new Date(), true, false, 2L, ReviewType.ACCOMMODATION);
        ReviewDTO review2 = new ReviewDTO(1L, 3, "Bad", new Date(), true, false, 1L, ReviewType.ACCOMMODATION);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getReviewsForOwner(@PathVariable Long ownerId){
        //returns reviews for owner
        ReviewDTO review1 = new ReviewDTO(3L, 4, "Nice", new Date(), true, false, 2L, ReviewType.OWNER);
        ReviewDTO review2 = new ReviewDTO(4L, 3, "Bad", new Date(), true, false, 1L, ReviewType.OWNER);
        Collection<ReviewDTO> reviewDTO = new HashSet<>();
        reviewDTO.add(review1);
        reviewDTO.add(review2);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/new-accommodation/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> newReviewAccommodation(@PathVariable Long accommodationId, @RequestBody ReviewDTO review) {
        //insert new review for accommodation
        ReviewDTO savedReview = new ReviewDTO(1L, 4, "Nice", new Date(), true, false, 2L, ReviewType.ACCOMMODATION);
        return new ResponseEntity<ReviewDTO>(savedReview, HttpStatus.CREATED);
    }

    @PostMapping(value = "/new-owner/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> newReviewOwner(@PathVariable Long ownerId, @RequestBody ReviewDTO review) {
        //insert new review for owner
        ReviewDTO savedReview = new ReviewDTO(1L, 4, "Nice", new Date(), true, false, 2L, ReviewType.OWNER);
        return new ResponseEntity<ReviewDTO>(savedReview, HttpStatus.CREATED);
    }

    @PutMapping(value="/reject/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> rejectReview(@PathVariable Long reviewId) {
        //change to rejected
        ReviewDTO rejectReview = new ReviewDTO(1L, 4, "Nice", new Date(), false, true, 2L, ReviewType.ACCOMMODATION);
        return new ResponseEntity<ReviewDTO>(rejectReview, HttpStatus.OK);
    }

    @PutMapping(value="/accept/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> acceptReview(@PathVariable Long reviewId) {
        //change to accepted
        ReviewDTO acceptReview = new ReviewDTO(1L, 4, "Nice", new Date(), true, false, 2L, ReviewType.ACCOMMODATION);
        return new ResponseEntity<ReviewDTO>(acceptReview, HttpStatus.OK);
    }

    @PutMapping(value="/report/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> reportReview(@PathVariable Long reviewId) {
        //change to report
        ReviewDTO reportReview = new ReviewDTO(1L, 4, "Nice", new Date(), true, true, 2L, ReviewType.OWNER);
        return new ResponseEntity<ReviewDTO>(reportReview, HttpStatus.OK);
    }

    @DeleteMapping("/accommodation-delete/{accommodationId}/{reviewId}")
    public ResponseEntity<ReviewDTO> deleteAccommodationReview(@PathVariable Long reviewId, @PathVariable Long accommodationId) {
        //delete review for accommodation
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/owner-delete/{ownerId}/{reviewId}")
    public ResponseEntity<ReviewDTO> deleteOwnerReview(@PathVariable Long reviewId, @PathVariable Long ownerId) {
        //delete review for owner
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }
}
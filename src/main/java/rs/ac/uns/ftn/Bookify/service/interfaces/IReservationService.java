package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.util.Collection;

public interface IReservationService {

    public Reservation get(Long reservationId);
    public boolean hasFutureReservationsGuest(Long userId);
    public boolean hasFutureReservationsAccommodation(Accommodation accommodation);

}
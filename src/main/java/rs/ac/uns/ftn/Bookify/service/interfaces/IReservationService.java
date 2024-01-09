package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IReservationService {

    public Reservation get(Long reservationId);
    public boolean hasFutureReservationsGuest(Long userId);
    public boolean hasFutureReservationsAccommodation(Accommodation accommodation);
    public boolean hasReservationInRange(Long accommodationId, LocalDate start, LocalDate end);
    Reservation save(Reservation reservation);
    public void setAccommodation(Accommodation accommodation, Reservation reservation);
    public void setGuest(Guest guest, Reservation reservation);
    public List<Reservation> getReservations(Long guestId, Long ownerId);
    public List<Reservation> getAllForGuest(Long userId);
    public List<Object[]> getGuestAccommodations(Long UserId);
    public List<Reservation> filterForGuest(Long userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses);
    List<Reservation> getAllForOwner(Long userId);
    public List<Object[]> getOwnerAccommodations(Long UserId);
    public List<Reservation> filterForOwner(Long userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses);
    public void setReservationStatus(Long reservationId, Status status);
    public void delete(Long reservationId);
    public boolean cancelGuestsReservations(Long guestId);
}

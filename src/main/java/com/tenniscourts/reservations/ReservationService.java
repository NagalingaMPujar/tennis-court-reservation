package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    private final ReservationMapper reservationMapper;

    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestMapper guestMapper;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleMapper scheduleMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {

        return saveReservationDTO(createReservationRequestDTO);

    }

    public List<ReservationDTO> bookReservation(List<CreateReservationRequestDTO> createReservationRequestDTOs) {
        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for(CreateReservationRequestDTO reservationDTO: createReservationRequestDTOs) {
            reservationDTOs.add(saveReservationDTO(reservationDTO));
        }

        return reservationDTOs;

    }

    private ReservationDTO saveReservationDTO(CreateReservationRequestDTO createReservationRequestDTO) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setRefundValue(BigDecimal.ZERO);
        reservationDTO.setValue(BigDecimal.TEN);
        reservationDTO.setSchedule(scheduleService.findSchedule(createReservationRequestDTO.getScheduleId()));
        reservationDTO.setGuestId(createReservationRequestDTO.getGuestId());
        reservationDTO.setReservationStatus(ReservationStatus.READY_TO_PLAY.name());

        Reservation reservation = reservationMapper.map(reservationDTO);
        reservation.setGuest(guestMapper.map(guestService.findById(createReservationRequestDTO.getGuestId())));
        reservationRepository.save(reservation);
        return reservationDTO;
    }


    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).<BadRequestException>orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).<BadRequestException>orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);

        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours >= 24) {
            return reservation.getValue();
        }
        else if (hours >=12 && hours <24) {
            return reservation.getValue().divide(BigDecimal.valueOf(3/4L));
        }
        else if (hours >=2 && hours <12) {
            return reservation.getValue().divide(BigDecimal.valueOf(1/2L));
        } else if (hours < 2) {
            return reservation.getValue().divide(BigDecimal.valueOf(1/4L));
        }

        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            previousReservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
            previousReservation.setValue(previousReservation.getValue().add(previousReservation.getRefundValue()));
            previousReservation.setRefundValue(BigDecimal.ZERO);

            reservationRepository.save(previousReservation);
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }
}

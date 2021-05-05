package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value="/reservation")
@Api(value = "Reservation Operations")
public class ReservationController extends BaseRestController {

    @Autowired
    private final ReservationService reservationService;

    @PostMapping(value="/book")
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @PostMapping(value="/book-all")
    @ApiOperation(value="create list of reservations", response = ResponseEntity.class)
    public ResponseEntity<List<ReservationDTO>> bookManyReservation(@RequestBody List<CreateReservationRequestDTO> createReservationRequestDTOs) {
        return ResponseEntity.ok(reservationService.bookReservation(createReservationRequestDTOs));
    }

    @GetMapping(value="/get/{reservationId}")
    @ApiOperation(value="get reservation by id", response = ResponseEntity.class)
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable(value="reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @PostMapping(value="/cancel")
    @ApiOperation(value="cancel reservation", response = ResponseEntity.class)
    public ResponseEntity<ReservationDTO> cancelReservation(@RequestParam Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PostMapping(value="/reschedule")
    @ApiOperation(value="reschedule reservation", response = ResponseEntity.class)
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestParam Long reservationId, @RequestParam Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}

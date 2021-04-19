package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@AllArgsConstructor
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    public ResponseEntity<GuestDTO> findById(Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    public ResponseEntity<Guest> findByName(String name) {
        return ResponseEntity.ok(guestService.findByName(name));
    }

    public ResponseEntity<List<Guest>> findAll(String name) {
        return ResponseEntity.ok(guestService.findAll());
    }

    public ResponseEntity<Guest> create(CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.ok(guestService.createGuest(createGuestRequestDTO));
    }

    public ResponseEntity<Guest> update(CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(createGuestRequestDTO));
    }

    public void delete(CreateGuestRequestDTO createGuestRequestDTO) {
        guestService.deleteGuest(createGuestRequestDTO.getId());
    }
}

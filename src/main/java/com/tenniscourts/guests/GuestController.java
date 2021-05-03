package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class GuestController extends BaseRestController {

    @Autowired
    private GuestService guestService;

    public GuestController () {

    }

    @GetMapping(value="/get/{id}")
    public ResponseEntity<GuestDTO> findById(@PathVariable(value="id") final Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    @GetMapping(value="/getByName")
    public ResponseEntity<Guest> findByName(@RequestParam(value="name") String name) {
        return ResponseEntity.ok(guestService.findByName(name));
    }

    @GetMapping(value="/getAll")
    public ResponseEntity<List<Guest>> findAll() {
        return ResponseEntity.ok(guestService.findAll());
    }

    @PostMapping(value="/create")
    public ResponseEntity<Guest> create(@RequestBody CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.ok(guestService.createGuest(createGuestRequestDTO));
    }

    @PutMapping(value="/update/{id}")
    public ResponseEntity<Guest> update(@PathVariable Long id, @RequestBody CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(id, createGuestRequestDTO));
    }

    @DeleteMapping(value="/delete/{id}")
    public void delete(@PathVariable Long id) {
        guestService.deleteGuest(id);
    }
}

package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/guest")
@Api(value="guest", description="Operations pertaining to guests in tennis court")
public class GuestController extends BaseRestController {

    @Autowired
    private GuestService guestService;

    @GetMapping(value="/get/{id}")
    @ApiOperation(value="find guest by id", response = ResponseEntity.class)
    public ResponseEntity<GuestDTO> findById(@PathVariable(value="id") final Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    @GetMapping(value="/get-by-name")
    @ApiOperation(value="find list of guests by name", response = ResponseEntity.class)
    public ResponseEntity<List<Guest>> findByName(@RequestParam(value="name") String name) {
        return ResponseEntity.ok(guestService.findByName(name));
    }

    @GetMapping(value="/get-all")
    @ApiOperation(value="find all list of guests", response = ResponseEntity.class)
    public ResponseEntity<List<Guest>> findAll() {
        return ResponseEntity.ok(guestService.findAll());
    }

    @PostMapping(value="/create")
    @ApiOperation(value="create guest", response = ResponseEntity.class)
    public ResponseEntity<Guest> create(@RequestBody CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.ok(guestService.createGuest(createGuestRequestDTO));
    }

    @PutMapping(value="/update/{id}")
    @ApiOperation(value="update guest", response = ResponseEntity.class)
    public ResponseEntity<Guest> update(@PathVariable Long id, @RequestBody CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(id, createGuestRequestDTO));
    }

    @DeleteMapping(value="/delete/{id}")
    @ApiOperation(value="delete guest")
    public void delete(@PathVariable Long id) {
        guestService.deleteGuest(id);
    }
}

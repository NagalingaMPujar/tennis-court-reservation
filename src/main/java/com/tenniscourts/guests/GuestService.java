package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GuestService {

    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final GuestMapper guestMapper;

    public GuestDTO findById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).<BadRequestException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public Guest findByName(String name) {
        return guestRepository.findByName(name);
    }

    public List<Guest> findAll() {
        return guestRepository.findAll();
    }

    public Guest createGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        Guest guest = new Guest();
        guest.setId(createGuestRequestDTO.getId());
        guest.setName(createGuestRequestDTO.getName());

        return guestRepository.save(guest);

    }

    public Guest updateGuest(Long id, CreateGuestRequestDTO createGuestRequestDTO) {
        Optional<Guest> guestList = guestRepository.findById(id);
        Guest guest = new Guest();
        if (guestList.isPresent()) {
            guest = guestList.get();
            guest.setName(createGuestRequestDTO.getName());
            guestRepository.save(guest);
        } else {
            throw new EntityNotFoundException(" No guest found to update");
        }

        return guest;
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }


}

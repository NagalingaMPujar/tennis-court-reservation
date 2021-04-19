package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.jvnet.hk2.annotations.Service;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO findById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
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

    public Guest updateGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        Optional<Guest> guestList = guestRepository.findById(createGuestRequestDTO.getId());
        Guest guest = new Guest();
        if (guestList.isPresent()) {
            guest = guestList.get();
            guest.setName(createGuestRequestDTO.getName());
        }

        return guestRepository.save(guest);
    }

    public void deleteGuest(Long id) {
        Optional<Guest> guestList = guestRepository.findById(id);
        if (guestList.isPresent()) {
            guestRepository.delete(guestList.get());
        }
    }


}

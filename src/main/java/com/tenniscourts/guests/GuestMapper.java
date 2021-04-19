package com.tenniscourts.guests;

import com.tenniscourts.reservations.CreateReservationRequestDTO;
import com.tenniscourts.reservations.Reservation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    Guest map(GuestDTO source);

    @InheritInverseConfiguration
    GuestDTO map(Guest source);

    @Mapping(target = "guest.id", source = "id")
    @Mapping(target = "guest.name", source = "name")
    Guest map(CreateGuestRequestDTO source);

}

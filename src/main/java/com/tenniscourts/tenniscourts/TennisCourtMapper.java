package com.tenniscourts.tenniscourts;

import com.tenniscourts.reservations.CreateReservationRequestDTO;
import com.tenniscourts.reservations.Reservation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface TennisCourtMapper {

    TennisCourtDTO map(TennisCourt source);

    @InheritInverseConfiguration
    TennisCourt map(TennisCourtDTO source);

    TennisCourtDTO map(Optional<TennisCourt> source);
}

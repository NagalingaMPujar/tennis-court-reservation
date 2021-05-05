package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService {

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final ScheduleMapper scheduleMapper;

    @Autowired
    private final TennisCourtRepository tennisCourtRepository;

    @Autowired
    private final TennisCourtMapper tennisCourtMapper;

    @Autowired
    private final TennisCourtMapperImpl tennisCourtMapperImpl;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setTennisCourtId(tennisCourtId);
        scheduleDTO.setTennisCourt(tennisCourtMapperImpl.map(tennisCourtRepository.findById(tennisCourtId)));
        scheduleDTO.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        scheduleDTO.setEndDateTime(createScheduleRequestDTO.getEndDateTime());

        Schedule schedule = scheduleMapper.map(scheduleDTO);
        Optional<TennisCourt> tennisCourtOptional = tennisCourtRepository.findById(tennisCourtId);
        if(tennisCourtOptional.isPresent()) {
            schedule.setTennisCourt(tennisCourtOptional.get());
        }
        scheduleRepository.save(schedule);

        return scheduleDTO;
    }

    public List<Schedule> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return scheduleRepository.findScheduleByDates(startDate, endDate);
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement

        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map)
                .<BadRequestException>orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    public List<StringBuffer> getFreeSlots() {
        List<LocalDateTime> slots = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.of("Asia/Kolkata"));

        LocalDateTime endOfDay = LocalDateTime.of(localDate, LocalTime.MAX);
        List<Schedule> scheduleList = findSchedulesByDates(startOfDay, endOfDay);
        List<LocalDateTime> startTimeList = new ArrayList<>();
        for(Schedule schedule : scheduleList) {
            startTimeList.add(schedule.getStartDateTime());
        }
        while(zonedDateTime.toLocalDateTime().isBefore(endOfDay)) {
            if(!startTimeList.contains(zonedDateTime.toLocalDateTime())) {
                slots.add(zonedDateTime.toLocalDateTime());
            }
            zonedDateTime = zonedDateTime.plusHours(1);
        }

        List<StringBuffer> startEndTime = new ArrayList<>();
        for (int i=0; i< slots.size(); i++) {
            if(i== slots.size()-1) {
                startEndTime.add(new StringBuffer(slots.get(i).toString()).append("---").append(endOfDay));
            } else {
                startEndTime.add(new StringBuffer(slots.get(i).toString()).append("---").append(slots.get(i).plusHours(1).toString()));
            }
        }


        return startEndTime;
    }


}

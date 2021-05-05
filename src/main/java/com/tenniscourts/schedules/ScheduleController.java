package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value="/schedule")
@Api(value = "schedule")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    //TODO: implement rest and swagger
    @PostMapping("/add-schedule")
    @ApiOperation(value = "add schedule operation", response = ResponseEntity.class)
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    @GetMapping(value = "/get-by-dates")
    @ApiOperation(value = "find schedule by dates", response = ResponseEntity.class)
    public ResponseEntity<List<Schedule>> findSchedulesByDates(
            @RequestParam("startDate") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate startDate ,
            @RequestParam("endDate") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    //TODO: implement rest and swagger
    @GetMapping(value="/get-by-id")
    @ApiOperation(value="find schedule by id", response = ResponseEntity.class)
    public ResponseEntity<ScheduleDTO> findByScheduleId(@RequestParam Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @GetMapping(value = "/free-slots")
    @ApiOperation(value=" get all free slots for current date", response = List.class)
    public List<StringBuffer> getFreeSlots() {
        return scheduleService.getFreeSlots();
    }
}

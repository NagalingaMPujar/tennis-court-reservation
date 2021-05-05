package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/tennis")
@Api(value="tennis court related operations")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    //TODO: implement rest and swagger
    @PostMapping(value="/add")
    @ApiOperation(value="add tennis court", response = ResponseEntity.class)
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    @GetMapping(value="/get-by-id")
    @ApiOperation(value="get tennis court by id", response = ResponseEntity.class)
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    //TODO: implement rest and swagger
    @GetMapping(value = "/get-with-schedules")
    @ApiOperation(value=" get tennis court schedules by id", response = ResponseEntity.class)
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }

}

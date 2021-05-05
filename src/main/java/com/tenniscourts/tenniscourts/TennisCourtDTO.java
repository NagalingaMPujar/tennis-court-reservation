package com.tenniscourts.tenniscourts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tenniscourts.schedules.ScheduleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TennisCourtDTO {

    private Long id;

    @NotNull
    private String name;

    @JsonIgnore
    private List<ScheduleDTO> tennisCourtSchedules;

}

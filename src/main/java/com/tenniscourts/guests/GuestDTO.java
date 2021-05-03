package com.tenniscourts.guests;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
@Component
public class GuestDTO {

    private Long id;

    private String name;
}

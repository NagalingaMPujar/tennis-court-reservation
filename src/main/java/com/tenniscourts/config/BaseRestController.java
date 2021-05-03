package com.tenniscourts.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;

@ApiIgnore
@RestController
public class BaseRestController {

  protected URI locationByEntity(Long entityId){
        return ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(entityId).toUri();
    }
}

package com.playground.microservice.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MicroserviceDto {

    @NotBlank(message = "{microservice.name_not_blank}")
    @Size(min = 3, max = 20, message = "{microservice.name_size}")
    private String name;

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}

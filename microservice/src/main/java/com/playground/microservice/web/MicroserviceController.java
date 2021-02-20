package com.playground.microservice.web;

import com.playground.microservice.db.model.Microservice;
import com.playground.microservice.db.repo.MicroserviceRepository;
import com.playground.microservice.web.dto.MicroserviceDto;
import com.playground.microservice.web.exceptions.MicroserviceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MicroserviceController {

    public static final String MICROSERVICE_CREATED_TOPIC = "microservice.created";

    @Autowired
    private MicroserviceRepository microserviceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private KafkaTemplate<String, String> template;

    @Operation(summary = "Get all microservices")
    @GetMapping
    public Iterable<Microservice> findAll() {
        return microserviceRepository.findAll();
    }

    @Operation(summary = "Get microservice by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the manufacturer",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Microservice.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Manufacturer not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public Microservice findById(@Parameter(description = "id of microservice") @PathVariable Integer id) {
        return microserviceRepository.findById(id).orElseThrow(MicroserviceNotFoundException::new);
    }

    @Operation(summary = "Create microservice")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Microservice create(@Valid @RequestBody MicroserviceDto microservice) {
        Microservice created = microserviceRepository.save(modelMapper.map(microservice, Microservice.class));
        template.send(MICROSERVICE_CREATED_TOPIC, String.valueOf(created.getId()), created.getName());
        return created;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(MicroserviceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleMicroserviceNotFound(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

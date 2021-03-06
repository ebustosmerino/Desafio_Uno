package com.previred.periodos.swagger.codegen.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.previred.periodos.servicios.PeriodosFaltantesService;
import com.previred.periodos.servicios.PeriodosService;
import com.previred.periodos.swagger.codegen.model.Periodo;
import com.previred.periodos.swagger.codegen.model.PeriodosFaltantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class ApiApiController implements ApiApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private PeriodosService periodosService;

    @Autowired
    private PeriodosFaltantesService periodosFaltantesService;

    @org.springframework.beans.factory.annotation.Autowired
    public ApiApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Periodo> periodos() {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    Periodo detalle = periodosService.getPeriodos();
                    ResponseEntity<Periodo> respuesta = new ResponseEntity<>(detalle, HttpStatus.OK);
                    return respuesta;
                } catch (Exception e) {
                    log.error("Couldn't serialize response for content type application/xml", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default DefaultApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<PeriodosFaltantes> periodosFaltantes() {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    Periodo detalle = periodosService.getPeriodos();
                    PeriodosFaltantes faltantes = periodosFaltantesService.getPeriodosFaltantes(detalle);
                    ResponseEntity<PeriodosFaltantes> respuesta = new ResponseEntity<>(faltantes, HttpStatus.OK);
                    return respuesta;
                } catch (Exception e) {
                    log.error("Couldn't serialize response for content type application/xml", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default DefaultApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}

package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.service.LimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/rest/limits")
@RestController
@Tag(name = "Limits", description = "Controller for limits work")
public class LimitController {

    private final LimitService service;

    @Autowired
    public LimitController(LimitService service) {
        this.service = service;
    }

//    @ApiOperation("Getting all limits")
//    @GetMapping
//    public List<Limit> getLimits() {
//        return service.getAll();
//    }

    @Operation(summary = "Adding a new limit")
    @PostMapping
    Optional<Limit> addLimit(@RequestBody Limit limit) {
        String pattern = "yyyy-MM-dd' 'HH:mm:ssX";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        Objects.requireNonNull(limit).setDateLimit(date);
        return service.save(limit);
    }
}

package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.enums.ExpensesType;
import com.flvtisv.testsolva.service.LimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequestMapping("/rest/limits")
@RestController
@Tag(name = "Limits", description = "Controller for limits work")
public class LimitController {

    private final LimitService service;

    @Autowired
    public LimitController(LimitService service) {
        this.service = service;
    }

    @Operation(summary = "Getting all limits by account number")
    @GetMapping("/{number}")
    public ResponseEntity<List<Limit>> getLimits(@PathVariable String number) {
        List<Limit> limits = service.getAllLimitsByNumber(number);
        return limits == null || limits.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(limits, HttpStatus.OK);
    }

    @Operation(summary = "Adding a new limit")
    @PostMapping
    ResponseEntity<?> addLimit(@RequestBody Limit limit) {
        if (limit.getLimit() == null || limit.getAccount() == null || limit.getType() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!limit.getType().equals(ExpensesType.PRODUCT.name()) && !limit.getType().equals(ExpensesType.SERVICE.name())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String pattern = "yyyy-MM-dd' 'HH:mm:ssX";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        Objects.requireNonNull(limit).setDateLimit(date);
        return new ResponseEntity<>(service.save(limit), HttpStatus.CREATED);
    }
}

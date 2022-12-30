package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.enums.ExpensesType;
import com.flvtisv.testsolva.service.LimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Getting all limits by account number", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Currency not found")})
    @GetMapping("/{number}")
    public ResponseEntity<List<Limit>> getLimits(
            @Parameter(description = "account number")
            @PathVariable String number) {
        List<Limit> limits = service.getAllLimitsByNumber(number);
        return limits == null || limits.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(limits, HttpStatus.OK);
    }

    @Operation(summary = "Adding a new limit", responses = {
            @ApiResponse(responseCode = "201", description = "Limit created"),
            @ApiResponse(responseCode = "400", description = "Wrong format or incorrect data")})
    @PostMapping
    ResponseEntity<?> addLimit(@RequestBody Limit limit) {
        if (limit.getLimit() == null || limit.getAccount() == null || limit.getType() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!limit.getType().equals(ExpensesType.PRODUCT.name()) && !limit.getType().equals(ExpensesType.SERVICE.name())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Objects.requireNonNull(limit).setDateLimit(service.getFormatDate());
        return new ResponseEntity<>(service.save(limit), HttpStatus.CREATED);
    }
}

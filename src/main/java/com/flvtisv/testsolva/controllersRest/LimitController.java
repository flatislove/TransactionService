package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.dto.LimitAdd;
import com.flvtisv.testsolva.entity.enums.ExpensesType;
import com.flvtisv.testsolva.service.AccountService;
import com.flvtisv.testsolva.service.LimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/rest/limits")
@RestController
@Tag(name = "Limits", description = "Controller for limits work")
public class LimitController {

    private final LimitService service;
    private final AccountService accountService;

    @Autowired
    public LimitController(LimitService service, AccountService accountService) {
        this.service = service;
        this.accountService = accountService;
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
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(limits);
    }

    @Operation(summary = "Adding a new limit", responses = {
            @ApiResponse(responseCode = "201", description = "Limit created"),
            @ApiResponse(responseCode = "400", description = "Wrong format or incorrect data")})
    @PostMapping
    ResponseEntity<?> addLimit(@RequestBody LimitAdd limitAdd, UriComponentsBuilder uriComponentsBuilder) {
        if (limitAdd.getLimit() == null || limitAdd.getAccountId() == null || limitAdd.getType() == null
        || (!limitAdd.getType().equals(ExpensesType.PRODUCT.name()) && !limitAdd.getType().equals(ExpensesType.SERVICE.name()))) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).build();
        }
        Optional<Account> account = accountService.findById(limitAdd.getAccountId());
        Limit limit = new Limit();
        account.ifPresent(limit::setAccount);
        limit.setLimit(limitAdd.getLimit());
        limit.setType(limitAdd.getType());
        Objects.requireNonNull(limit).setDateLimit(service.getFormatDate());
        service.save(limit);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/rest/limits/{id}").build(Map.of("id",limit.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(limit);
    }
}

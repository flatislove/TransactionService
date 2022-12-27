package com.flvtisv.testsolva.controllers;

import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class LimitController {

    private final LimitService service;

    @Autowired
    public LimitController(LimitService service) {
        this.service = service;
    }

    @GetMapping("/limits/getall")
    public List<Limit> getLimits() {
        return service.getAll();
    }

    @PostMapping("/limits/add")
    Optional<Limit> newAccount(@RequestBody Limit limit) {
        Objects.requireNonNull(limit).setDateLimit(new Date());
        return service.save(limit);
    }

}

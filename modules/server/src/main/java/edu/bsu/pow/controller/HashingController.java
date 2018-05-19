package edu.bsu.pow.controller;

import edu.bsu.pow.exception.ApiExceptionWrapper;
import edu.bsu.pow.model.Solution;
import edu.bsu.pow.service.HashingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HashingController
{
    private final HashingService hashingService;

    @Autowired
    public HashingController(HashingService hashingService)
    {
        this.hashingService = hashingService;
    }

    @PostMapping("/calculateNonce")
    @ResponseBody
    public Solution revokeCertificate(
            @RequestHeader(value = "d") Long d,
            @RequestHeader(value = "puzzle") String puzzle,
            @RequestBody String body)
    {
        return hashingService.getSolution(puzzle, d, body);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleServiceException(Exception e)
    {
        return ResponseEntity.status(400).body(new ApiExceptionWrapper(e.getMessage()));
    }
}

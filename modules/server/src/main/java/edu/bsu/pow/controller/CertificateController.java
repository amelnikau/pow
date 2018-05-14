package edu.bsu.pow.controller;

import edu.bsu.pow.exception.ApiExceptionWrapper;
import edu.bsu.pow.service.PowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CertificateController
{
    private final PowService powService;

    @Autowired
    public CertificateController(PowService powService)
    {
        this.powService = powService;
    }

    @PostMapping("/revokeCertificate")
    @ResponseBody
    public String revokeCertificate(
            @RequestHeader(value = "nonce") Long nonce,
            @RequestHeader(value = "solution") Long solution,
            @RequestBody String revokeCertificateContent)
    {
        powService.validateSolution(nonce, solution);
        // if no exception occurred, than it means that nonce & solution are valid
        // we can check password & revoke certificate if everything is ok
        // TODO
        return "Certificate has been revoked";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleServiceException(Exception e)
    {
        return ResponseEntity.status(400).body(new ApiExceptionWrapper(e.getMessage()));
    }
}

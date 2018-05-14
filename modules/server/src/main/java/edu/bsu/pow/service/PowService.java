package edu.bsu.pow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PowService
{
    private final NonceService nonceService;

    @Autowired
    public PowService(NonceService nonceService)
    {
        this.nonceService = nonceService;
    }

    public void validateSolution(long nonce, long solution)
    {
        nonceService.validateNonce(nonce);
        if (!isValid(nonce, solution))
        {
            throw new IllegalArgumentException(String.format("%s is not valid solution for %s", solution, nonce));
        }
    }

    private boolean isValid(long nonce, long solution)
    {
        boolean valid = false;
        // fake implementation, should be replaced by real one
        if (nonce * 60 == solution)
        {
            valid = true;
        }
        return valid;
    }
}

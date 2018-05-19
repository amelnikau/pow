package edu.bsu.pow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PowService
{
    private final PuzzleService nonceService;
    private final HashingService hashingService;

    @Autowired
    public PowService(PuzzleService nonceService, HashingService hashingService)
    {
        this.nonceService = nonceService;
        this.hashingService = hashingService;
    }

    public void validateSolution(String puzzle, long d, String body, long nonce, String solution)
    {
        nonceService.validatePuzzle(puzzle);
        hashingService.validateSolution(puzzle, d, body, nonce, solution);
    }
}

package edu.bsu.pow.controller;

import edu.bsu.pow.model.Puzzle;
import edu.bsu.pow.service.NonceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PuzzleController
{
    private final NonceService nonceService;

    @Autowired
    public PuzzleController(NonceService nonceService)
    {
        this.nonceService = nonceService;
    }

    @GetMapping("/getPuzzle")
    @ResponseBody
    public Puzzle getPuzzle()
    {
        return new Puzzle(nonceService.getNewNonce());
    }
}

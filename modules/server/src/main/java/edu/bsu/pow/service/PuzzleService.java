package edu.bsu.pow.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class PuzzleService
{
    private final Cache<String, Boolean> activePuzzles = CacheBuilder
            .newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    public String getNewPuzzle()
    {
        String generatedPuzzle = UUID.randomUUID().toString();
        activePuzzles.put(generatedPuzzle, true);
        return generatedPuzzle;
    }

    public void validatePuzzle(String puzzle)
    {
        if (activePuzzles.getIfPresent(puzzle) == null)
        {
            throw new IllegalArgumentException(String.format("%s has expired or was never generated", puzzle));
        }
        activePuzzles.invalidate(puzzle);
    }
}

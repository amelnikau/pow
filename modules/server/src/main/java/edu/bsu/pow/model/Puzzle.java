package edu.bsu.pow.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Puzzle
{
    private final String puzzle;
    private final long d = 11;

    @JsonCreator
    public Puzzle(@JsonProperty("puzzle") String puzzle)
    {
        this.puzzle = puzzle;
    }

    public String getPuzzle()
    {
        return puzzle;
    }

    public long getD()
    {
        return d;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Puzzle{");
        sb.append("puzzle=").append(puzzle);
        sb.append(", d=").append(d);
        sb.append('}');
        return sb.toString();
    }
}

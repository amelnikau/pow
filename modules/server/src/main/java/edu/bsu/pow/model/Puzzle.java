package edu.bsu.pow.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Puzzle
{
    private final long nonce;

    @JsonCreator
    public Puzzle(@JsonProperty("nonce") long nonce)
    {
        this.nonce = nonce;
    }

    public long getNonce()
    {
        return nonce;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Puzzle{");
        sb.append("nonce=").append(nonce);
        sb.append('}');
        return sb.toString();
    }
}

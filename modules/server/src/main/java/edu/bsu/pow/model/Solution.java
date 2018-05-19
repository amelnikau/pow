package edu.bsu.pow.model;

public class Solution
{
    private long nonce;
    private String solution;

    public Solution(long nonce, String solution)
    {
        this.nonce = nonce;
        this.solution = solution;
    }

    public long getNonce()
    {
        return nonce;
    }

    public void setNonce(long nonce)
    {
        this.nonce = nonce;
    }

    public String getSolution()
    {
        return solution;
    }

    public void setSolution(String solution)
    {
        this.solution = solution;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Solution{");
        sb.append("nonce=").append(nonce);
        sb.append(", solution='").append(solution).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

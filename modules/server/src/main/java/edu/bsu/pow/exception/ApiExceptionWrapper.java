package edu.bsu.pow.exception;

public class ApiExceptionWrapper
{
    private String message;

    public ApiExceptionWrapper(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}

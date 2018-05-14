/**
 * Copyright 2017 Expedia, Inc. All rights reserved.
 * EXPEDIA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
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

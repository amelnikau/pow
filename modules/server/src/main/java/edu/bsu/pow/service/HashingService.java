package edu.bsu.pow.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import edu.bsu.pow.model.Solution;

import org.springframework.stereotype.Service;

@Service
public class HashingService
{

    public Solution getSolution(String puzzle, long d, String body)
    {
        boolean expression;
        long nonce = 0;
        String bodyPuzzleOr = or(puzzle, body);
        byte[] bytes = new byte[0];

        long startTime = System.nanoTime();
        for (int currentD = 0; currentD <= d; currentD++)
        {
            nonce = 0;
            expression = false;

            int nullBytes = currentD / 8;
            int shift = (8 - (currentD % 8));

            while (!expression)
            {
                bytes = getHash(or(String.valueOf(nonce), bodyPuzzleOr));
                boolean temporaryExpression = true;
                for (int i = 0; i < nullBytes && temporaryExpression; i++)
                {
                    temporaryExpression = (bytes[i] == 0);
                }
                expression = ((bytes[nullBytes]) >> shift == 0);
                expression = expression && temporaryExpression;
                nonce++;
            }
            nonce--;
            System.out.println(String.format("d=%s nonce=%s took %s milliseconds", currentD, nonce,
                    TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));
        }

        return new Solution(nonce, toBinaryRepresentation(new String(bytes)));
    }

    public void validateSolution(String puzzle, long d, String body, long nonce, String solution)
    {
        long zeroesInSolution = countZeroes(solution);
        if (zeroesInSolution < d)
        {
            throw new IllegalArgumentException(String.format("Expected %d zeroes in solution, but got only %s", d, zeroesInSolution));
        }
        byte[] bytes = getHash(or(String.valueOf(nonce), or(puzzle, body)));
        if (!solution.equals(toBinaryRepresentation(new String(bytes))))
        {
            throw new IllegalArgumentException(String.format("Solution is not correct for given d=%s and nonce=%s", d, nonce));
        }
    }

    private long countZeroes(String solution)
    {
        long zeroesCount = 0;
        for (char ch : solution.toCharArray())
        {
            if (ch == '0')
            {
                zeroesCount++;
            } else
            {
                break;
            }
        }
        return zeroesCount;
    }

    private byte[] getHash(String string)
    {
        byte[] digest = null;
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(string.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return digest;
    }

    private String or(String left, String right)
    {
        BigInteger leftBinary = new BigInteger(toBinaryRepresentation(left), 2);
        BigInteger rightBinary = new BigInteger(toBinaryRepresentation(right), 2);

        return leftBinary.or(rightBinary).toString(2);
    }

    private String toBinaryRepresentation(String value)
    {
        byte[] valueBytes = value.getBytes();
        StringBuilder valueBinary = new StringBuilder();
        for (byte valueByte : valueBytes)
        {
            valueBinary.append(String.format("%8s", Integer.toBinaryString(valueByte & 0xFF)).replace(' ', '0'));
        }
        return valueBinary.toString();
    }
}

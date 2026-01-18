package com.example.javaprojektmusikapp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class DHLNIO extends DHL
{
    public String path;

    // empty constructor
    public DHLNIO()
    {

    }

    // constructor that opens file for reading right away
    public DHLNIO(String path)
    {
        this.path = path;
        try
        {
            in = Files.newBufferedReader(Paths.get(path));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    // creates BufferedWriter using NIO instead of IO
    @Override
    public BufferedWriter schreiben(String speicherort, String dateiname)
    {
        try
        {
            out = Files.newBufferedWriter(Paths.get(speicherort + dateiname));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return out;
    }

    // creates BufferedReader using NIO for reading
    public BufferedReader lesen(String speicherort, String dateiname)
    {
        try
        {
            in = Files.newBufferedReader(Paths.get(speicherort + dateiname));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return in;
    }
}

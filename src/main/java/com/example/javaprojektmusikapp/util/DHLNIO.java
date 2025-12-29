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

    public DHLNIO()
    {

    }

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

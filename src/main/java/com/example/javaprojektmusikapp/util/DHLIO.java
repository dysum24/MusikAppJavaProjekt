package com.example.javaprojektmusikapp.util;

import java.io.*;

public class DHLIO extends DHL
{
    // creates BufferedWriter for file, makes directory if needed
    @Override
    public BufferedWriter schreiben(String speicherort, String dateiname)
    {
        try
        {
            // create directory if it doesn't exist yet
            File dir = new File(speicherort);
            if(!dir.exists())
            {
                dir.mkdirs();
            }

            File file = new File(speicherort, dateiname);
            this.out = new BufferedWriter(new FileWriter(file));
            return this.out;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    // creates BufferedReader for reading file, returns null if file not found
    public BufferedReader lesen(String speicherort, String dateiname)
    {
        try
        {
            File file = new File(speicherort, dateiname);
            this.in = new BufferedReader(new FileReader(file));
            return this.in;
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}

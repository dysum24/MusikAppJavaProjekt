package com.example.javaprojektmusikapp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import com.example.javaprojektmusikapp.model.Song;

public abstract class DHL
{
    protected BufferedReader in;
    protected BufferedWriter out;

    // subclasses need to implement this for their specific file writing method
    public abstract BufferedWriter schreiben(String speicherort, String dateiname);

    // reads csv file and converts each line into song objects
    public ArrayList<Song> auslesen(BufferedReader in)
    {
        ArrayList<Song> songs = new ArrayList<>();

        String zeile = null;
        try
        {
            //skips header line
            zeile = in.readLine();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            // read each line and pasrse into song
            while((zeile = in.readLine()) != null)
            {
                if(zeile.trim().isEmpty())
                {
                    continue;
                }

                String[] split = zeile.split(";");

                // need at least 3 fields(id, title, artist)
                if(split.length < 3)
                {
                    System.err.println("Ungültige Zeile (zu wenig Felderr): " + zeile);
                    continue;
                }

                // parse all fields, use default values if missing
                Song song = new Song(
                        split[0], //id
                        split[1],
                        split[2],
                        split.length > 3 ? split[3] : "",
                        split.length > 4 ? split[4] : "",
                        split.length > 5 ? Integer.parseInt(split[5]) : 0,
                        split.length > 6 ? split[6] : "",
                        split.length > 7 ? Double.parseDouble(split[7]) : 0.0,
                        split.length > 8 ? split[8] : ""
                );
                songs.add(song);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                in.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        return songs;
    }

    // writes song list to CSV with header and semicolon seperator
    public void schreibenDateiCSV(ArrayList<Song> songs, BufferedWriter out)
    {
        try
        {
            // write header first
            out.write("ID;Title;Artist;Album;ArtworkUrl;Duration;PreviewUrl;Price;ReleaseDate");
            out.newLine();

            // write each song as one long
            for(Song song : songs)
            {
                out.write(song.getTrackId() + ";" + song.getTrackName() + ";" + song.getArtistName() + ";" + song.getAlbumName() + ";" + song.getArtworkUrl() + ";" + song.getTrackTimeMillis() + ";" + song.getPreviewUrl() + ";" + song.getTrackPrice() + ";" + song.getReleaseDate());
                out.newLine();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    // serializes any object to file for backup purposes
    public void speichernObjekt(Object objekt, String speicherort, String dateiname)
    {
        try
        {
            // make sure directory exists
            File dir = new File(speicherort);
            if(!dir.exists())
            {
                dir.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(speicherort + dateiname);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(objekt);
            oos.close();
            fos.close();
            System.out.println("Objekt gespeichert: " + dateiname);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    // deserializes object from file, returns null if something goes wrong
    public Object ladenObjekt(String speicherort, String dateiname)
    {
        try
        {
            FileInputStream fis = new FileInputStream(speicherort + dateiname);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object objekt = ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Objekt geladen: " + dateiname);
            return objekt;
        }
        catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedWriter getOut()
    {
        return out;
    }

    public void setOut(BufferedWriter out)
    {
        this.out = out;
    }

    public BufferedReader getIn()
    {
        return in;
    }

    public void setIn(BufferedReader in)
    {
        this.in = in;
    }
}

package Model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Storage
{
    private static Storage ourInstance = new Storage();

    public static Storage getInstance()
    {
        return ourInstance;
    }

    private ArrayList<Note> notes;
    private HashMap<String, Bitmap> images;

    public static void putImage(String url, Bitmap image)
    {
        Storage.getInstance().images.put(url, image);
    }

    public static Bitmap getImage(String url)
    {
        Bitmap image;
        if ((image = Storage.getInstance().images.get(url)) != null)
        {
            return image;
        }
        return null;
    }


    private Storage()
    {
        notes = new ArrayList<>();
        images = new HashMap<>();

        mockValues();
    }

    public  static ArrayList<Note> getNotes()
    {
        return Storage.getInstance().notes;
    }

    private void mockValues()
    {
        notes.add(new Note("Place", "Good", "World", new Date(), "http://i.imgur.com/0Pdm4rg.jpg",
                true));
        notes.add(new Note("Better Place", "Good", "World", new Date(),
                "http://i.imgur.com/SiYjT3Z.png", false));
        notes.add(new Note("Place", "Good", "World", new Date(), "http://i.imgur.com/0Pdm4frg.jpg",
                false));
        notes.add(new Note("Place", "Good", "World", new Date(), null,
                false));
    }

    public void addNote(Note note)
    {
        notes.add(note);
    }

    public void removeNote(Note note)
    {
        notes.remove(note);
    }



}

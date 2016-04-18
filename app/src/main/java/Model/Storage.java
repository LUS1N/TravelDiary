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

    public static Bitmap getScaledBitmap(Bitmap image)
    {
        return Bitmap.createScaledBitmap(image, (int) (image.getWidth() * 0.5),
                (int) (image.getHeight() * 0.5), true);
    }

    private ArrayList<Note> notes;
    private HashMap<String, Bitmap> images;

    public void putImage(String url, Bitmap image)
    {
        images.put(url, image);
    }

    public Bitmap getImage(String url, boolean thumbnail)
    {
        Bitmap image;
        if ((image = images.get(url)) != null)
        {
            if (thumbnail)
            {
                return getScaledBitmap(image);
            }
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

    public ArrayList<Note> getNotes()
    {
        return notes;
    }

    private void mockValues()
    {
        notes.add(new Note("Place", "Good", "World", new Date(), "http://i.imgur.com/0Pdm4rg.jpg",
                false));
        notes.add(new Note("Better Place", "Good", "World", new Date(),
                "http://i.imgur.com/SiYjT3Z.png", false));
    }
}

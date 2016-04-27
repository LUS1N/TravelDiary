package Model;

import android.graphics.Bitmap;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import baaa.traveldiary.Tasks.ImageDownloaderTask;

public class Storage
{
    private static Storage ourInstance = new Storage();

    private ArrayList<Note> notes;
    private HashMap<String, Bitmap> images;
    public BaseExpandableListAdapter adapter;


    public static Storage getInstance()
    {
        return ourInstance;
    }

    public static void setAdapter(BaseExpandableListAdapter adapter)
    {
        Storage.getInstance().adapter = adapter;
    }

    public static void loadImages()
    {
        for (Note n : Storage.getNotes())
        {
            new ImageDownloaderTask(null).execute(n.getImageURL());
        }
    }

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

    }

    public static void addNote(Note note)
    {
        if (!ourInstance.notes.contains(note))
        {
            getInstance().notes.add(note);
            if (ourInstance.adapter != null)
                Storage.getInstance().adapter.notifyDataSetChanged();
        }
    }

    public static ArrayList<Note> getNotes()
    {
        return new ArrayList<>(Storage.getInstance().notes);
    }

    public static void removeNote(Note note)
    {
        getInstance().notes.remove(note);
        Storage.getInstance().adapter.notifyDataSetChanged();
    }

    public void mockValues()
    {
        addNote(new Note("Place", "Good", "World", new Date(), "http://i.imgur.com/0Pdm4rg.jpg",
                true));
        addNote(new Note("Better Place", "Good", "World", new Date(),
                "http://i.imgur.com/SiYjT3Z.png", false));
        addNote(new Note("Place", "Good", "World", new Date(), "http://i.imgur.com/0Pdm4frg.jpg",
                false));
        addNote(new Note("Place", "Good", "World", new Date(), null,
                false));
    }


}

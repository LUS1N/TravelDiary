package Model;

import android.graphics.Bitmap;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
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
        for (Note n : ourInstance.notes)
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
        return ourInstance.notes;
    }

    public static void removeNote(Note note)
    {
        getInstance().notes.remove(note);
        Storage.getInstance().adapter.notifyDataSetChanged();
    }

    public void mockValues()
    {
        Calendar c = Calendar.getInstance();
        c.set(2014, 8, 22);
        addNote(new Note("Aarhus",
                "Aarhus is the second-largest city in Denmark and the seat of Aarhus Municipality. It is located on the east coast of the Jutland peninsula, in the geographical centre of Denmark, 187 kilometres (116 mi) northwest of Copenhagen and 289 kilometres (180 mi) north of Hamburg, Germany. ",
                "Aarhus, Denmark", c.getTime(), "http://i.imgur.com/7JgRVZD.jpg",
                true));

        c.set(2015, 10, 18);
        addNote(new Note("Randers",
                "Randers is a city in Randers Municipality, Central Denmark Region on the Jutland peninsula. It is Denmark's sixth-largest city, with a population of 61,163,",
                "Randers, Denmark", c.getTime(),
                "http://i.imgur.com/hVnli8u.jpg", false));

        c.set(2015, 8, 15);
        addNote(new Note("Copenhagen",
                "Copenhagen is the capital and most populated city of Denmark. It has a municipal population of 591,481 and a larger urban population of 1,280,371",
                "Copenhagen, Denmark", c.getTime(), "http://i.imgur.com/NuNbHIJ.jpg",
                true));
    }


}

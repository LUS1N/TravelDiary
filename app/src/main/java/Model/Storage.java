package Model;

import java.util.ArrayList;
import java.util.Date;

public class Storage
{
    private static Storage ourInstance = new Storage();
    public static Storage getInstance()
    {
        return ourInstance;
    }

    private ArrayList<Note> notes;
    private Storage()
    {
        notes = new ArrayList<>();
        mockValues();
    }

    public ArrayList<Note> getNotes()
    {
        return notes;
    }

    private void mockValues()
    {
        notes.add(new Note("Place","Good", "World", null, new Date(), false));
    }
}

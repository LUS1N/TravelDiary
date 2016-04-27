package baaa.traveldiary;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.google.gson.Gson;

import Model.Note;
import Model.Storage;
import baaa.traveldiary.Adapters.NoteExpandableListAdapter;
import baaa.traveldiary.Fragments.NoteDialogFragment;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener
{
    public static MainActivity activity;
    public static int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 5;
    public static final String NOTES = "NOTES";
    private static final String DEFAULT = "";
    ExpandableListView noteExpandable;
    SharedPreferences mPrefs;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPrefs = getPreferences(MODE_PRIVATE);

        loadStorage();
        Storage.loadImages();
        getPermissions();

        setContentView(R.layout.activity_main);
        activity = this;

        SearchView search = (SearchView) findViewById(R.id.searchView);
        search.setOnQueryTextListener(this);
        noteExpandable = (ExpandableListView) findViewById(R.id.NoteExpandableListView);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        LayoutInflater inflater = (LayoutInflater) this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        setupNoteExpandableListView(noteExpandable, inflater);
    }

    private void loadStorage()
    {
        gson = new Gson();

        String notesJSON = mPrefs.getString(NOTES, DEFAULT);
        Note[] notes = gson.fromJson(notesJSON, Note[].class);

        if (notes != null)
        {
            for (Note n : notes)
            {
                Storage.addNote(n);
            }
        }
        else
        {
            Storage.getInstance().mockValues();
        }
    }

    private void setupNoteExpandableListView(ExpandableListView noteExpandable, LayoutInflater inflater)
    {
        noteExpandable.addHeaderView(
                inflater.inflate(R.layout.new_note_header, noteExpandable, false));
        BaseExpandableListAdapter adapter = new NoteExpandableListAdapter(noteExpandable, inflater);
        noteExpandable.setAdapter(adapter);
        Storage.setAdapter(adapter);
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        saveData();
    }

    private void saveData()
    {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        // remove old values
        clearPrefs(prefsEditor);

        String noteseJSON = gson.toJson(Storage.getNotes().toArray());
        prefsEditor.putString(NOTES, noteseJSON);
        prefsEditor.commit();
    }

    private void clearPrefs(SharedPreferences.Editor prefsEditor)
    {
        prefsEditor.remove(NOTES);
        prefsEditor.apply();
    }

    public void addNewNoteEvent(View view)
    {
        DialogFragment dialog = new NoteDialogFragment();
        dialog.show(getFragmentManager(), "NoticeDialogFragment");
    }

    private void getPermissions()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))
            {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
            else
            {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        hideKeyboard(this);
        filterData(query);
        return false;
    }

    private void filterData(String query)
    {
        ((NoteExpandableListAdapter) Storage.getInstance().adapter).filterData(query);
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        filterData(newText);
        return false;
    }

    public static void hideKeyboard(Activity activity)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null)
        {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @Override
    public boolean onClose()
    {
        hideKeyboard(this);
        filterData("");
        return false;
    }
}

package baaa.traveldiary;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import Model.Storage;
import baaa.traveldiary.Adapters.NoteExpandableListAdapter;
import baaa.traveldiary.Fragments.NoteDialogFragment;

public class MainActivity extends AppCompatActivity
{
    Storage storage = Storage.getInstance();
    public static  MainActivity activity;
    public static int   MY_PERMISSIONS_REQUEST_READ_CONTACTS = 5;
    ExpandableListView noteExpandable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getPermissions();


        setContentView(R.layout.activity_main);
        activity = this;

         noteExpandable = (ExpandableListView) findViewById(R.id.NoteExpandableListView);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        setupNoteExpandableListView(noteExpandable, inflater);
    }

    private void getPermissions()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

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

    private void setupNoteExpandableListView(ExpandableListView noteExpandable, LayoutInflater inflater)
    {
        noteExpandable.addHeaderView(inflater.inflate(R.layout.new_note_header, noteExpandable, false));
        noteExpandable.setAdapter(new NoteExpandableListAdapter(noteExpandable, inflater));
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        // Restarting the app only restarts the activity, so this is needed if some changes to Storage are made
        storage = Storage.getInstance();
    }

    public void addNewNoteEvent(View view)
    {
        DialogFragment dialog = new NoteDialogFragment();
        dialog.show(getFragmentManager(), "NoticeDialogFragment");
    }

}

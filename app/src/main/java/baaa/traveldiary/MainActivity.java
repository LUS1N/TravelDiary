package baaa.traveldiary;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import Model.Note;
import Model.Storage;

public class MainActivity extends AppCompatActivity
{
    Storage storage = Storage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView noteExpandable = (ExpandableListView) findViewById(R.id.NoteExpandableListView);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        setupNoteExpandableListView(noteExpandable, inflater);
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

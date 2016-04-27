package baaa.traveldiary.Listeners;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import Model.Note;
import Model.Storage;
import baaa.traveldiary.Fragments.NoteDialogFragment;
import baaa.traveldiary.MainActivity;

/**
 * Created by Andy on 27/04/2016.
 */


/**
 * Listener for removing lists
 */
public class EditNoteListener implements View.OnClickListener
{
    Note note;

    public EditNoteListener(Note note)
    {
        this.note = note;

    }

    @Override
    public void onClick(View v)
    {

        final Context context = ((View) v.getParent()).getContext();
        FragmentManager fm = ((Activity) context).getFragmentManager();

        DialogFragment dialog = NoteDialogFragment.newInstance(1,note);
        dialog.show(fm,"editDialogFragment");
    }
}
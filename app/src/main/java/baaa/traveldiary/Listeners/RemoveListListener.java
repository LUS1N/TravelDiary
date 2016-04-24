package baaa.traveldiary.Listeners;

import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import Model.Note;
import Model.Storage;

/**
 * Listener for removing lists
 */
public class RemoveListListener implements View.OnClickListener
{
    Note note;

    public RemoveListListener(Note note)
    {
        this.note = note;

    }

    @Override
    public void onClick(View v)
    {
        Storage.getInstance().removeNote(note);
        View parent = (View) v.getParent().getParent();
        ((BaseExpandableListAdapter) ((ExpandableListView) parent).getExpandableListAdapter()).notifyDataSetChanged();
    }
}
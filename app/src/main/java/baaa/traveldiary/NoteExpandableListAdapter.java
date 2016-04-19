package baaa.traveldiary;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Model.Note;
import Model.Storage;

public class NoteExpandableListAdapter extends BaseExpandableListAdapter
{

    LayoutInflater inflater;
    ExpandableListView noteListView;
    ArrayList<Note> notes = Storage.getInstance().getNotes();
    Storage storage = Storage.getInstance();
    int lastExpandedGroupPosition;

    public NoteExpandableListAdapter(ExpandableListView noteListView, final LayoutInflater inflater)
    {
        this.noteListView = noteListView;
        this.inflater = inflater;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        View noteGroupView = getView(R.layout.note_list_row, convertView, parent);

        Note currentNote = (Note) getGroup(groupPosition);

        initialiseGroupViewValues(noteGroupView, currentNote);
        return noteGroupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        View noteExpandedView = getView(R.layout.note_expanded, convertView, parent);
        ImageView imageView = (ImageView) noteExpandedView.findViewById(R.id.expanded_image);
        Note currentNote = storage.getNotes().get(groupPosition);

        addImageToView(currentNote, imageView);

        return noteExpandedView;
    }

    private void initialiseGroupViewValues(View noteGroupView, Note currentNote)
    {
        // Title
        ((TextView) noteGroupView.findViewById(R.id.title_textView)).setText(
                currentNote.getTitle());
        // Date
        DateFormat sdt = SimpleDateFormat.getDateInstance();
        ((TextView) noteGroupView.findViewById(R.id.date_textView)).setText(
                sdt.format(currentNote.getDateOfVisit()));

        // Image
        ImageView imageView = (ImageView) noteGroupView.findViewById(R.id.note_imageView);
        addImageToView(currentNote, imageView);
    }

    private void addImageToView(Note currentNote, ImageView imageView)
    {
        Bitmap image;
        if ((image = storage.getImage(currentNote.getImageURL())) != null)
        {
            imageView.setImageBitmap(image);
        }
        else
            new ImageDownloaderTask(imageView).execute(currentNote.getImageURL());
    }

    private View getView(int resource, View convertView, ViewGroup parent)
    {
        return convertView == null ? inflater.inflate(resource, parent, false) : convertView;
    }

    @Override
    public void onGroupExpanded(int groupPosition)
    {
        //collapse the old expanded group, if not the same
        //as new group to expand
        if (groupPosition != lastExpandedGroupPosition)
        {
            noteListView.collapseGroup(lastExpandedGroupPosition);
        }

        super.onGroupExpanded(groupPosition);
        lastExpandedGroupPosition = groupPosition;
    }
    @Override
    public int getGroupCount()
    {
        return notes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return notes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }
}

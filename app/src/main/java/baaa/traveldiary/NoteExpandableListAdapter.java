package baaa.traveldiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Model.Note;
import Model.Storage;

public class NoteExpandableListAdapter extends BaseExpandableListAdapter
{

    LayoutInflater inflater;
    ExpandableListView noteListView;
    ArrayList<Note> notes = Storage.getInstance().getNotes();

    public NoteExpandableListAdapter(ExpandableListView noteListView, final LayoutInflater inflater)
    {
        this.noteListView = noteListView;
        this.inflater = inflater;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        View noteGroupview = getRowView(convertView, parent);

        Note currentNote = (Note) getGroup(groupPosition);

        ((TextView) noteGroupview.findViewById(R.id.title_textView)).setText(currentNote.getTitle());
        SimpleDateFormat sdt = new SimpleDateFormat("y-MM-d");
        ((TextView) noteGroupview.findViewById(R.id.date_textView)).setText(sdt.format(currentNote.getDateOfVisit()));



        return noteGroupview;
    }

    private View getRowView(View convertView, ViewGroup parent)
    {
        return convertView == null ? inflater.inflate(R.layout.note_list_row, parent, false): convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        return null;
    }

    @Override
    public int getGroupCount()
    {
        return notes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return 0;
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
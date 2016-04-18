package baaa.traveldiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

public class NoteExpandableListAdapter extends BaseExpandableListAdapter
{

    LayoutInflater inflater;
    ExpandableListView noteListView;

    public NoteExpandableListAdapter(ExpandableListView noteListView, final LayoutInflater inflater)
    {
        this.noteListView = noteListView;
        this.inflater = inflater;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        View noteGroupview = convertView;

        if(noteGroupview == null)
        {
            noteGroupview = inflater.inflate(R.layout.note_list_row, parent, false);
        }
        return noteGroupview;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        return null;
    }

    @Override
    public int getGroupCount()
    {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return null;
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

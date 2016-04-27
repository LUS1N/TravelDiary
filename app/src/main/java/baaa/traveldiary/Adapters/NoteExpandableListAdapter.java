package baaa.traveldiary.Adapters;

import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Model.Note;
import Model.Storage;
import baaa.traveldiary.Listeners.EditNoteListener;
import baaa.traveldiary.Listeners.RemoveNoteListener;
import baaa.traveldiary.MainActivity;
import baaa.traveldiary.R;
import baaa.traveldiary.Tasks.ImageDownloaderTask;

public class NoteExpandableListAdapter extends BaseExpandableListAdapter
{

    public static NoteExpandableListAdapter instance;
    LayoutInflater inflater;
    ExpandableListView noteListView;
    ArrayList<Note> notes = Storage.getNotes();
    int lastExpandedGroupPosition;

    public NoteExpandableListAdapter(ExpandableListView noteListView, final LayoutInflater inflater)
    {
        instance = this;
        this.noteListView = noteListView;
        this.inflater = inflater;
    }

    public void filterData(String query)
    {
        query = query.toLowerCase();
        notes.clear();

        if (query.isEmpty())
        {
            notes = Storage.getNotes();
        }
        else
        {
            for (Note n : Storage.getNotes())
            {
                if (contains(n.getTitle(), query) || contains(n.getAddress(), query) || contains(
                        n.getDescription(), query))
                {
                    notes.add(n);
                }
            }
        }
        notifyDataSetChanged();
    }

    private boolean contains(String field, String query)
    {
        return field.toLowerCase().contains(query);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        View noteGroupView = getView(R.layout.note_list_row, convertView, parent);

        Note currentNote = (Note) getGroup(groupPosition);

        View removeButton = noteGroupView.findViewById(R.id.removeListButton);
        removeButton.setOnClickListener(new RemoveNoteListener(currentNote));

        View editButton = noteGroupView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new EditNoteListener(currentNote));

        initialiseGroupViewValues(noteGroupView, currentNote);

        return noteGroupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        View noteExpandedView = getView(R.layout.note_expanded, convertView, parent);
        Note currentNote = Storage.getNotes().get(groupPosition);

        addValuesToExpandedView(noteExpandedView, currentNote);

        return noteExpandedView;
    }

    public void addValuesToExpandedView(View noteExpandedView, Note currentNote)
    {
        TextView addresText = (TextView) noteExpandedView.findViewById(
                R.id.expanded_address_textView);
        addresText.setText(currentNote.getAddress());

        TextView descriptionText = (TextView) noteExpandedView.findViewById(
                R.id.expanded_description_textView);
        descriptionText.setText(currentNote.getDescription());

        CheckBox visitBox = (CheckBox) noteExpandedView.findViewById(
                R.id.expanded_visit_again_check);
        visitBox.setChecked(currentNote.isVisitAgain());

        ImageView imageView = (ImageView) noteExpandedView.findViewById(R.id.expanded_image);
        proccessImageToView(currentNote, imageView);

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
        proccessImageToView(currentNote, imageView);
    }

    private void proccessImageToView(Note currentNote, ImageView imageView)
    {
        if (currentNote.getImageURL() == null)
        {
            imageView.setVisibility(View.GONE);
            return;
        }
        else
            imageView.setVisibility(View.VISIBLE);

        addImageToView(imageView, currentNote.getImageURL());
    }

    private void addImageToView(ImageView imageView, String url)
    {
        Bitmap image;
        if ((image = Storage.getImage(url)) != null)
        {
            imageView.setImageBitmap(image);
        }
        else
            new ImageDownloaderTask(imageView).execute(url);

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
        MainActivity.hideKeyboard(MainActivity.activity);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {
        super.registerDataSetObserver(observer);
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
        return Storage.getNotes().get(groupPosition).getTitle().hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }
}

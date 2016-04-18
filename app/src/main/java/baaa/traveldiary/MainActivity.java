package baaa.traveldiary;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView noteExpandable = (ExpandableListView) findViewById(R.id.NoteExpandableListView);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        Toast toast = Toast.makeText(this, inflater.toString(), Toast.LENGTH_LONG);
        toast.show();

        noteExpandable.addHeaderView(inflater.inflate(R.layout.new_note_header, noteExpandable, false));
        noteExpandable.setAdapter(new NoteExpandableListAdapter(noteExpandable, inflater));
    }
}

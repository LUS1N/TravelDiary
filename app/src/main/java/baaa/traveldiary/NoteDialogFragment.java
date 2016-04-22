package baaa.traveldiary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import Model.Note;
import Model.Storage;

/**
 * Created by Andy on 22/04/2016.
 */
public class NoteDialogFragment extends DialogFragment {



    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View noteDialogView = inflater.inflate(R.layout.add_note_input,null );

        final TextView dateTextView=(TextView) noteDialogView.findViewById(R.id.new_note_date);

        final Calendar c = Calendar.getInstance();
        dateTextView.setText((c.get(Calendar.MONTH)+1) + "-"
                + c.get(Calendar.DATE)  + "-" + c.get(Calendar.YEAR));


       final DatePickerDialog dpd = new DatePickerDialog(noteDialogView.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateTextView.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                        c.set(year,monthOfYear,dayOfMonth);

                    }
                }, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE));

        dateTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dpd.show();
            }
        });


        builder.setView(noteDialogView)
                // Add action buttons
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // get title
                        EditText title = (EditText) noteDialogView.findViewById(
                                R.id.new_note_title);
                        String titleString = title.getText().toString();
                        //get description
                        String description = ((EditText) noteDialogView.findViewById(
                                R.id.new_note_description)).getText().toString();

                        String address = ((EditText) noteDialogView.findViewById(
                                R.id.new_note_address)).getText().toString();



                        boolean visitAgain = ((CheckBox) noteDialogView.findViewById(R.id.new_note_visit)).isChecked();

                        if (!titleString.isEmpty())
                        {
                            Storage storage = Storage.getInstance();
                            storage.addNote(new Note(titleString,description, address, c.getTime(), null, visitAgain));


                            // TODO notifyDataSetChanged. I cant figure it out
                            //noteExpandable.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });


        return builder.create();
    }
}

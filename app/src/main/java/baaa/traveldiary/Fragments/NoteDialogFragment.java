package baaa.traveldiary.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import Model.Note;
import Model.Storage;
import baaa.traveldiary.Listeners.AddImageFromCameraListener;
import baaa.traveldiary.Listeners.AddImageFromGalleryListener;
import baaa.traveldiary.Listeners.AddImageURLListener;
import baaa.traveldiary.R;

/**
 * Created by Andy on 22/04/2016.
 */
public class NoteDialogFragment extends DialogFragment
{
    View noteDialogView;

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        noteDialogView = inflater.inflate(R.layout.add_note_input, null);

        final TextView dateTextView = (TextView) noteDialogView.findViewById(R.id.new_note_date);
        final Calendar c = Calendar.getInstance();

        setupCalendar(noteDialogView, dateTextView, c);
        setupDialogActions(builder, noteDialogView, c);

        attachListenersToImageActions();

        return builder.create();
    }

    private void attachListenersToImageActions()
    {
        noteDialogView.findViewById(R.id.URL_button).setOnClickListener(new AddImageURLListener());

        noteDialogView.findViewById(R.id.gallery_imageButton).setOnClickListener(
                new AddImageFromGalleryListener());

        noteDialogView.findViewById(R.id.photo_imageButton).setOnClickListener(
                new AddImageFromCameraListener());
    }


    private void setupDialogActions(AlertDialog.Builder builder, final View noteDialogView, final Calendar c)
    {
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


                        boolean visitAgain = ((CheckBox) noteDialogView.findViewById(
                                R.id.new_note_visit)).isChecked();

                        // TODO implement url
                        String url = "";


                        if (!titleString.isEmpty())
                        {
                            Storage.addNote(
                                    new Note(titleString, description, address, c.getTime(), url,
                                            visitAgain));
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
    }

    private void setupCalendar(View noteDialogView, final TextView dateTextView, final Calendar c)
    {
        dateTextView.setText((c.get(Calendar.MONTH) + 1) + "-"
                + c.get(Calendar.DATE) + "-" + c.get(Calendar.YEAR));


        final DatePickerDialog dpd = new DatePickerDialog(noteDialogView.getContext(),
                new DatePickerDialog.OnDateSetListener()
                {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                            int monthOfYear, int dayOfMonth)
                    {
                        dateTextView.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                        c.set(year, monthOfYear, dayOfMonth);

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));

        dateTextView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                dpd.show();
            }
        });
    }
}

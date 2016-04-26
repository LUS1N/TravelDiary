package baaa.traveldiary.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

import Model.Note;
import Model.Storage;
import baaa.traveldiary.R;
import baaa.traveldiary.Tasks.ImgurUploaderTask;

/**
 * Created by Andy on 22/04/2016.
 */

public class NoteDialogFragment extends DialogFragment
{
    View noteDialogView;
    private int PICK_IMAGE_REQUEST = 1;
    Note note;

    public static NoteDialogFragment newInstance(int arg, Note note) {
        NoteDialogFragment f = new NoteDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("arg", arg);
        f.setArguments(args);
        f.setNote(note);
        f.setArguments(args);

        return f;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // I have to set the field with the note value but i dont know exactly where.
        // also to check if it's null or not so basically i will have to do the same thing twice, dont think it is so smart
        // to figure it out itself if it exists or not
        //setRetainInstance(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        noteDialogView = inflater.inflate(R.layout.add_note_input, null);

        ((EditText) noteDialogView.findViewById(R.id.url_EditText)).setText("");

        final TextView dateTextView = (TextView) noteDialogView.findViewById(R.id.new_note_date);
        final Calendar c = Calendar.getInstance();


        ImageButton galleryBtn = (ImageButton) noteDialogView.findViewById(
                R.id.gallery_imageButton);
        galleryBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhotoIntent, PICK_IMAGE_REQUEST);
            }
        });

        setupCalendar(noteDialogView, dateTextView, c);
        setupDialogActions(builder, noteDialogView, c);

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data != null)
            {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                new ImgurUploaderTask(
                        ((EditText) noteDialogView.findViewById(R.id.url_EditText))).execute(
                        BitmapFactory
                                .decodeFile(imgDecodableString));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

                        String url = ((EditText) noteDialogView.findViewById(
                                R.id.url_EditText)).getText().toString();

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

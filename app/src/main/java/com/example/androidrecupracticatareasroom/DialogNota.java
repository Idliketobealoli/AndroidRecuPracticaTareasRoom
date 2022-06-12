package com.example.androidrecupracticatareasroom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.androidrecupracticatareasroom.data.Nota;
import com.example.androidrecupracticatareasroom.data.Priority;
import com.example.androidrecupracticatareasroom.data.RoomDB;

import java.util.List;

public class DialogNota extends DialogFragment {
    List<Nota> notas;
    RoomDB db;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        db = RoomDB.getInstance(getActivity());
        notas = db.mainDao().findAll();
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.add_dialog, null);

        EditText etTitle = dialog.findViewById(R.id.et_title_dialog);
        EditText etDescription = dialog.findViewById(R.id.et_description_dialog);
        EditText etDate = dialog.findViewById(R.id.et_date_dialog);

        RadioButton rbLow = dialog.findViewById(R.id.rb_lowPrio_dialog);
        RadioButton rbHigh = dialog.findViewById(R.id.rb_highPrio_dialog);
        RadioButton rbMedium = dialog.findViewById(R.id.rb_medPrio_dialog);

        Button bOk = dialog.findViewById(R.id.b_ok_dialog);
        Button bCancel = dialog.findViewById(R.id.b_cancel_dialog);

        adBuilder.setView(dialog).setMessage("Add nota");

        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Priority prio;
                if (rbLow.isChecked()) {
                    prio = Priority.BAJA;
                } else if (rbMedium.isChecked()) {
                    prio = Priority.MEDIA;
                } else if (rbHigh.isChecked()) {
                    prio = Priority.ALTA;
                } else prio = Priority.ALTA;

                Nota newNota = new Nota();
                newNota.setTitle(etTitle.getText().toString());
                newNota.setDescription(etDescription.getText().toString());
                newNota.setPriority(prio);
                String[] daymonthyear = etDate.getText().toString().split("/");
                newNota.setDay(Integer.parseInt(daymonthyear[0]));
                newNota.setMonth(Integer.parseInt(daymonthyear[1]));
                newNota.setYear(Integer.parseInt(daymonthyear[2]));
                db.mainDao().insert(newNota);
                /*
                notas.clear();
                notas.addAll(db.mainDao().findAll());
                 */
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return adBuilder.create();
    }
}

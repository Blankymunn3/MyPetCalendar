package io.kim_kong.mypetcalendar.util;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.kim_kong.mypetcalendar.R;

public class YearMonthPicker extends DialogFragment {

    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 1980;

    private DatePickerDialog.OnDateSetListener listener;
    public GregorianCalendar cal = new GregorianCalendar();

    public YearMonthPicker() {

    }

    public YearMonthPicker(int year, int month) {
        cal = new GregorianCalendar(year, month, 1, 0,0, 0);
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    Button btnConfirm;
    Button btnCancel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.year_month_picker, null);

        btnConfirm = dialog.findViewById(R.id.btn_confirm);
        btnCancel = dialog.findViewById(R.id.btn_cancel);

        final NumberPicker monthPicker = dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = dialog.findViewById(R.id.picker_year);

        btnCancel.setOnClickListener(view -> YearMonthPicker.this.getDialog().cancel());

        btnConfirm.setOnClickListener(view -> {
            listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
            YearMonthPicker.this.getDialog().cancel();
        });

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);

        yearPicker.setValue(cal.get(Calendar.YEAR));
        monthPicker.setValue(cal.get(Calendar.MONTH));

        builder.setView(dialog);

        return builder.create();
    }
}

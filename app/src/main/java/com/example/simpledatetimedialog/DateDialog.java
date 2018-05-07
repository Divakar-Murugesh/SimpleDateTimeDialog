package com.example.simpledatetimedialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateDialog extends Dialog {

    public static abstract class DateListener {
        public abstract void onDateSet(String date);

        public void onDateCancel() {

        }
    }

    private Context context;

    public DateDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public DateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public DateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    /* Date Dialog */
    public void DisplayDialog(String InitialDate, final DateListener dateTimeListener) {
        final DateDialog dateDialog = new DateDialog(context, R.style.DateTimeDialog);
        dateDialog.setTitle("");
        dateDialog.setContentView(R.layout.dialog_date);

        LinearLayout linearLayoutDate = (LinearLayout) dateDialog.findViewById(R.id.layoutDateOnly);

        final TextView ErrorTextView = (TextView) dateDialog.findViewById(R.id.errorTextView1);
        ErrorTextView.setVisibility(View.GONE);

        final EditText editTextDate = (EditText) dateDialog.findViewById(R.id.editTextDate);
        final Spinner spinnerMonth = (Spinner) dateDialog.findViewById(R.id.spinnerMonths);
        final EditText editTextYear = (EditText) dateDialog.findViewById(R.id.editTextYear);

        Button buttonDateSet = (Button) dateDialog.findViewById(R.id.dateButtonSet);
        Button buttonDateCancel = (Button) dateDialog.findViewById(R.id.dateButtonCancel);

        editTextDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                ErrorTextView.setVisibility(View.GONE);
                if (!charSequence.toString().isEmpty()) {
                    int day = Integer.parseInt(charSequence.toString());
                    if (day == 0) {
                        ErrorTextView.setText("Min Date - 01");
                        ErrorTextView.setVisibility(View.VISIBLE);
                    } else if (day > 31) {
                        ErrorTextView.setText("Max Date - 31");
                        ErrorTextView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                ErrorTextView.setVisibility(View.GONE);
                if (!charSequence.toString().isEmpty()) {
                    if (charSequence.length() == 4) {
                        int year = Integer.parseInt(charSequence.toString());
                        if (year > 2100 || year < 1900) {
                            ErrorTextView.setText("Min Year - 1900\nMax Year - 2100");
                            ErrorTextView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ErrorTextView.setText("Please enter proper year");
                        ErrorTextView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonDateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ErrorTextView.setVisibility(View.GONE);
                String day = editTextDate.getText().toString();
                String month = spinnerMonth.getSelectedItem().toString();
                String year = editTextYear.getText().toString();
                if (day.length() == 0) {
                    ErrorTextView.setText("Please enter Day");
                    ErrorTextView.setVisibility(View.VISIBLE);
                } else if (year.length() == 0 || year.length() < 4) {
                    ErrorTextView.setText("Please enter Year");
                    ErrorTextView.setVisibility(View.VISIBLE);
                } else if (Integer.parseInt(day) == 0) {
                    ErrorTextView.setText("Min Date - 01");
                    ErrorTextView.setVisibility(View.VISIBLE);
                } else if (Integer.parseInt(day) > 31) {
                    ErrorTextView.setText("Max Date - 31");
                    ErrorTextView.setVisibility(View.VISIBLE);
                } else if (Integer.parseInt(year) > 2100 || Integer.parseInt(year) < 1900) {
                    ErrorTextView.setText("Min Year - 1900\nMax Year - 2100");
                    ErrorTextView.setVisibility(View.VISIBLE);
                } else {

                    int iYear = Integer.parseInt(year);
                    int iMonth = spinnerMonth.getSelectedItemPosition();
                    int iDay = 1;

                    Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);
                    int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

                    if (daysInMonth < Integer.parseInt(day)) {
                        ErrorTextView.setText("Max Date in Selected Month - " + daysInMonth);
                        ErrorTextView.setVisibility(View.VISIBLE);
                    } else {
                        if (dateTimeListener != null) {
                            String string = (day.length() == 1 ? "0" + day : day) + " " + month + " " + year;
                            dateTimeListener.onDateSet(string);
                            dateDialog.dismiss();
                        }
                    }
                }
            }
        });
        buttonDateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateTimeListener != null) {
                    dateTimeListener.onDateCancel();
                    dateDialog.dismiss();
                }
            }
        });

        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, context.getResources().getStringArray(R.array.months));
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerMonth.setAdapter(langAdapter);

        String[] strings = InitialDate.split(" ");
        String[] months = context.getResources().getStringArray(R.array.months);
        if (strings.length == 3) {
            editTextDate.setText(strings[0]);

            for (int i = 0; i < months.length; i++) {
                if (months[i].equals(strings[1])) {
                    spinnerMonth.setSelection(i);
                }
            }
            editTextYear.setText(strings[2]);
            editTextYear.requestFocus();
        }

        dateDialog.setCancelable(false);
        dateDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dateDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dateDialog.getWindow().setAttributes(lp);
        dateDialog.show();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        //ImageView imageView = findViewById(R.id.spinnerImageView);
        //AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        //spinner.start();
    }
}

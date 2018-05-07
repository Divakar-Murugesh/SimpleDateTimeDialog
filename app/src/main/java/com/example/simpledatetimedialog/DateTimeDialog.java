package com.example.simpledatetimedialog;

import android.app.Dialog;
import android.content.Context;
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

import java.util.Date;

public class DateTimeDialog extends Dialog {

    public static abstract class DateTimeListener {
        public abstract void onDateTimeSet(String string);

        public void onDateTimeCancel() {

        }
    }

    public DateTimeDialog(Context context, int theme) {
        super(context, theme);
    }

    private DateTimeListener dateTimeListener;
    private Date mInitialDate;

    public void setDateTimeListener(DateTimeListener dateTimeListener) {
        this.dateTimeListener = dateTimeListener;
    }

    public void setInitialDate(Date initialDate) {
        mInitialDate = initialDate;
    }

    public void displayDialog() {
        if (dateTimeListener == null) {
            throw new NullPointerException("Attempting to bind null listener to SlideDateTimePicker");
        }

        if (mInitialDate == null) {
            setInitialDate(new Date());
        }

        show();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        //ImageView imageView = findViewById(R.id.spinnerImageView);
        //AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        //spinner.start();
    }

    public static class Builder {

        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        private DateTimeListener dateTimeListener;
        private Date mInitialDate;

        public Builder setDateTimeListener(DateTimeListener dateTimeListener) {
            this.dateTimeListener = dateTimeListener;
            return this;
        }

        public Builder setInitialDate(Date initialDate) {
            mInitialDate = initialDate;
            return this;
        }

        public DateTimeDialog DateDialog() {
            final DateTimeDialog dateTimeDialog = new DateTimeDialog(context, R.style.DateTimeDialog);
            dateTimeDialog.setTitle("");
            dateTimeDialog.setContentView(R.layout.dialog_date_time);

            // Date Views
            LinearLayout linearLayoutDate = (LinearLayout) dateTimeDialog.findViewById(R.id.layoutDateOnly);
            final TextView ErrorTextView = (TextView) dateTimeDialog.findViewById(R.id.errorTextView1);
            ErrorTextView.setVisibility(View.GONE);
            final EditText editTextDate = (EditText) dateTimeDialog.findViewById(R.id.editTextDate);
            final Spinner spinnerMonth = (Spinner) dateTimeDialog.findViewById(R.id.spinnerMonths);
            final EditText editTextYear = (EditText) dateTimeDialog.findViewById(R.id.editTextYear);
            Button buttonDateSet = (Button) dateTimeDialog.findViewById(R.id.dateButtonSet);
            Button buttonDateCancel = (Button) dateTimeDialog.findViewById(R.id.dateButtonCancel);

            editTextDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                    try {
                        int day = Integer.parseInt(charSequence.toString());
                        if (day > 31) {
                            ErrorTextView.setText("max date is 31");
                            ErrorTextView.setVisibility(View.VISIBLE);
                        } else {
                            ErrorTextView.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                    try {
                        if (charSequence.length() == 4) {
                            int year = Integer.parseInt(charSequence.toString());
                            if (year >= 1900 && year <= 2100) {
                                ErrorTextView.setVisibility(View.GONE);
                            } else {
                                ErrorTextView.setText("min year is 1900\nmax year is 2100");
                                ErrorTextView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            ErrorTextView.setText("please enter proper year");
                            ErrorTextView.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            buttonDateSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String day = editTextDate.getText().toString().trim();
                    String month = spinnerMonth.getSelectedItem().toString().trim();
                    String year = editTextYear.getText().toString().trim();
                    if (day.length() == 0) {
                        ErrorTextView.setText("Please enter Day");
                        ErrorTextView.setVisibility(View.VISIBLE);
                    } else if (year.length() == 0 || year.length() < 4) {
                        ErrorTextView.setText("Please enter Year");
                        ErrorTextView.setVisibility(View.VISIBLE);
                    } else {
                        ErrorTextView.setVisibility(View.GONE);
                        if (dateTimeListener != null) {
                            String string = day + " " + month + " " + year;
                            dateTimeListener.onDateTimeSet(string);
                            dateTimeDialog.dismiss();
                        }
                    }
                }
            });
            buttonDateCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dateTimeListener != null) {
                        dateTimeListener.onDateTimeCancel();
                        dateTimeDialog.dismiss();
                    }
                }
            });

            // Time Views
            LinearLayout linearLayoutTime = (LinearLayout) dateTimeDialog.findViewById(R.id.layoutTimeOnly);
            linearLayoutTime.setVisibility(View.GONE);

            ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, context.getResources().getStringArray(R.array.months));
            langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
            spinnerMonth.setAdapter(langAdapter);

            dateTimeDialog.setCancelable(false);
            dateTimeDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dateTimeDialog.getWindow().getAttributes();
            lp.dimAmount = 0.2f;
            dateTimeDialog.getWindow().setAttributes(lp);
            //dateTimeDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            //dateTimeDialog.show();

            dateTimeDialog.setDateTimeListener(dateTimeListener);
            dateTimeDialog.setInitialDate(mInitialDate);

            return dateTimeDialog;
        }

        public DateTimeDialog TimeDialog() {
            final DateTimeDialog dateTimeDialog = new DateTimeDialog(context, R.style.DateTimeDialog);
            dateTimeDialog.setTitle("");
            dateTimeDialog.setContentView(R.layout.dialog_date_time);

            // Date Views
            LinearLayout linearLayoutDate = (LinearLayout) dateTimeDialog.findViewById(R.id.layoutDateOnly);
            linearLayoutDate.setVisibility(View.GONE);

            // Time Views
            LinearLayout linearLayoutTime = (LinearLayout) dateTimeDialog.findViewById(R.id.layoutTimeOnly);
            final TextView ErrorTextView = (TextView) dateTimeDialog.findViewById(R.id.errorTextView2);
            ErrorTextView.setVisibility(View.GONE);
            final EditText editTextHour = (EditText) dateTimeDialog.findViewById(R.id.editTextHour);
            final EditText editTextMinutes = (EditText) dateTimeDialog.findViewById(R.id.editTextMinutes);
            Button buttonTimeSet = (Button) dateTimeDialog.findViewById(R.id.timeButtonSet);
            Button buttonTimeCancel = (Button) dateTimeDialog.findViewById(R.id.timeButtonCancel);

            editTextHour.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                    try {
                        int hours = Integer.parseInt(charSequence.toString());
                        if (hours > 24) {
                            ErrorTextView.setText("max hours is 24");
                            ErrorTextView.setVisibility(View.VISIBLE);
                        } else {
                            ErrorTextView.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editTextMinutes.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                    try {
                        int minutes = Integer.parseInt(charSequence.toString());
                        if (minutes > 59) {
                            ErrorTextView.setText("max minutes is 59");
                            ErrorTextView.setVisibility(View.VISIBLE);
                        } else {
                            ErrorTextView.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            buttonTimeSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String hours = editTextHour.getText().toString().trim();
                    String minutes = editTextMinutes.getText().toString().trim();
                    if (hours.length() == 0) {
                        ErrorTextView.setText("Please enter Hours");
                        ErrorTextView.setVisibility(View.VISIBLE);
                    } else if (minutes.length() == 0) {
                        ErrorTextView.setText("Please enter Minutes");
                        ErrorTextView.setVisibility(View.VISIBLE);
                    } else {
                        ErrorTextView.setVisibility(View.GONE);
                        if (dateTimeListener != null) {
                            String string = editTextHour.getText().toString() + ":" + editTextMinutes.getText().toString();
                            dateTimeListener.onDateTimeSet(string);
                            dateTimeDialog.dismiss();
                        }
                    }
                }
            });
            buttonTimeCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dateTimeListener != null) {
                        dateTimeListener.onDateTimeCancel();
                        dateTimeDialog.dismiss();
                    }
                }
            });

            dateTimeDialog.setCancelable(false);
            dateTimeDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dateTimeDialog.getWindow().getAttributes();
            lp.dimAmount = 0.2f;
            dateTimeDialog.getWindow().setAttributes(lp);
            //dateTimeDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            //dateTimeDialog.show();

            dateTimeDialog.setDateTimeListener(dateTimeListener);
            dateTimeDialog.setInitialDate(mInitialDate);

            return dateTimeDialog;
        }
    }
}

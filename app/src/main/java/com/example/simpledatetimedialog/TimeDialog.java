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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TimeDialog extends Dialog {

    public static abstract class TimeListener {
        public abstract void onTimeSet(String time);

        public void onTimeCancel() {

        }
    }

    private Context context;

    public TimeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public TimeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public TimeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public void DisplayDialog(String InitialTime, final TimeListener timeListener) {
        final TimeDialog timeDialog = new TimeDialog(context, R.style.DateTimeDialog);
        timeDialog.setTitle("");
        timeDialog.setContentView(R.layout.dialog_time);

        LinearLayout linearLayoutTime = (LinearLayout) timeDialog.findViewById(R.id.layoutTimeOnly);

        final TextView ErrorTextView = (TextView) timeDialog.findViewById(R.id.errorTextView2);
        ErrorTextView.setVisibility(View.GONE);

        final EditText editTextHour = (EditText) timeDialog.findViewById(R.id.editTextHour);
        final EditText editTextMinutes = (EditText) timeDialog.findViewById(R.id.editTextMinutes);

        Button buttonTimeSet = (Button) timeDialog.findViewById(R.id.timeButtonSet);
        Button buttonTimeCancel = (Button) timeDialog.findViewById(R.id.timeButtonCancel);

        editTextHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                validateFields(ErrorTextView, editTextHour, editTextMinutes, null, timeDialog);
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
                validateFields(ErrorTextView, editTextHour, editTextMinutes, null, timeDialog);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonTimeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields(ErrorTextView, editTextHour, editTextMinutes, timeListener, timeDialog);
            }
        });
        buttonTimeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeListener != null) {
                    timeListener.onTimeCancel();
                    timeDialog.dismiss();
                }
            }
        });

        String[] strings = InitialTime.split(":");
        if (strings.length == 2) {
            editTextHour.setText(strings[0]);
            editTextMinutes.setText(strings[1]);
        }

        timeDialog.setCancelable(false);
        timeDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = timeDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        timeDialog.getWindow().setAttributes(lp);
        timeDialog.show();
    }

    private void validateFields(TextView ErrorTextView, EditText editTextHour, EditText editTextMinutes, TimeListener timeListener, TimeDialog timeDialog) {
        ErrorTextView.setVisibility(View.GONE);
        String hours = editTextHour.getText().toString();
        String minutes = editTextMinutes.getText().toString();
        if (hours.length() == 0) {
            ErrorTextView.setText("Please enter Hours");
            ErrorTextView.setVisibility(View.VISIBLE);
        } else if (Integer.parseInt(hours) > 23) {
            ErrorTextView.setText("Max Hours - 23");
            ErrorTextView.setVisibility(View.VISIBLE);
        } else if (minutes.length() == 0) {
            ErrorTextView.setText("Please enter Minutes");
            ErrorTextView.setVisibility(View.VISIBLE);
        } else if (Integer.parseInt(minutes) > 59) {
            ErrorTextView.setText("Max Minutes - 59");
            ErrorTextView.setVisibility(View.VISIBLE);
        } else if (Integer.parseInt(hours) == 0 && Integer.parseInt(minutes) == 0) {
            ErrorTextView.setText("Please add at least one Minute");
            ErrorTextView.setVisibility(View.VISIBLE);
        } else {
            if (timeListener != null) {
                String time = (hours.length() == 1 ? "0" + hours : hours) + ":" + (minutes.length() == 1 ? "0" + minutes : minutes);
                timeListener.onTimeSet(time);
                timeDialog.dismiss();
            }
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        //ImageView imageView = findViewById(R.id.spinnerImageView);
        //AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        //spinner.start();
    }
}

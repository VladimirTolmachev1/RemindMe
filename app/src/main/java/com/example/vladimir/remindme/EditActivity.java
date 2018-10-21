package com.example.vladimir.remindme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;

import com.example.vladimir.remindme.models.Item;

import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView itemTitle;
    private TextView itemContent;
    private RadioGroup typeGroup;
    private int selectedTypeId;


    private Button btnDatePicker, btnTimePicker, saveBtn;
    private EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    //Item
    private Item cardItem;

    //Realm Db
    private Realm realmDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        realmDb = Realm.getDefaultInstance();

        itemTitle = (TextView) findViewById(R.id.item_title);
        itemContent = (TextView) findViewById(R.id.item_content);

        typeGroup = (RadioGroup) findViewById(R.id.item_type);

        saveBtn = (Button) findViewById(R.id.btn_save);

        //Date and time pickers
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        initToolBar();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == saveBtn){
            cardItem = new Item();

            cardItem.setId(UUID.randomUUID().toString());

            cardItem.setTitle(itemTitle.getText().toString());
            cardItem.setContent(itemContent.getText().toString());

            // Get selected type
            selectedTypeId = typeGroup.getCheckedRadioButtonId();
            View radioButton = typeGroup.findViewById(selectedTypeId);
            int idx = typeGroup.indexOfChild(radioButton);

            cardItem.setType(idx);
            cardItem.setDate(txtDate.getText().toString());
            cardItem.setTime(txtTime.getText().toString());

            insertUpdateItem(cardItem);
        }
    }

    public void insertUpdateItem(final Item cardItem){
        realmDb.beginTransaction();

        realmDb.copyToRealm(cardItem);

        realmDb.commitTransaction();
    }
}

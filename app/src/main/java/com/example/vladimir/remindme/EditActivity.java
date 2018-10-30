package com.example.vladimir.remindme;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;

import com.example.vladimir.remindme.models.Item;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    //Radio options
    private RadioButton ideaBtn;
    private RadioButton todoBtn;
    private RadioButton bdayBtn;

    // Item uuid
    private String itemId;

    //Item
    private Item cardItem;

    //Realm Db
    private Realm realmDb;

    //isEdit flag
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        realmDb = Realm.getDefaultInstance();

        itemTitle = (TextView) findViewById(R.id.item_title);
        itemContent = (TextView) findViewById(R.id.item_content);

        // Radio Group
        typeGroup = (RadioGroup) findViewById(R.id.item_type);
        ideaBtn = (RadioButton) findViewById(R.id.type_idea);
        todoBtn = (RadioButton) findViewById(R.id.type_todo);
        bdayBtn = (RadioButton) findViewById(R.id.type_bday);

        saveBtn = (Button) findViewById(R.id.btn_save);

        //Date and time pickers
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        // Get uuid from intent if its existing item and set data into form.
        Intent intent = getIntent();
        if(intent.hasExtra("itemId")){
            isEdit = true;
            itemId = intent.getStringExtra("itemId");
            Item editItem = getEditData(itemId);
            itemTitle.setText(editItem.getTitle());
            itemContent.setText(editItem.getContent());
            switch (editItem.getType()){
                case 0:
                    ideaBtn.setChecked(true);
                    break;
                case 1:
                    todoBtn.setChecked(true);
                    break;
                case 2:
                    bdayBtn.setChecked(true);
                    break;

                default:
                    ideaBtn.setChecked(true);
                    break;
            }

            txtDate.setText(editItem.getDate());
            txtTime.setText(editItem.getTime());
        }

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

            if(isEdit){
                cardItem = realmDb.where(Item.class).equalTo("id", itemId).findFirst();
                updateItem(cardItem);
            }else{
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

                insertItem(cardItem);

                // if we have date and time - set Alarm.
                if(!TextUtils.isEmpty(txtDate.getText().toString()) && !TextUtils.isEmpty(txtTime.getText().toString())){
                    setAlarm();
                }
            }

            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);

            finish();
        }
    }

    public void insertItem(final Item cardItem){
        realmDb.beginTransaction();

        realmDb.copyToRealm(cardItem);

        realmDb.commitTransaction();
    }

    public void updateItem(Item cardItem){
        realmDb.beginTransaction();

        cardItem.setTitle(itemTitle.getText().toString());
        cardItem.setContent(itemContent.getText().toString());
        // Get selected type
        selectedTypeId = typeGroup.getCheckedRadioButtonId();
        View radioButton = typeGroup.findViewById(selectedTypeId);
        int idx = typeGroup.indexOfChild(radioButton);

        cardItem.setType(idx);
        cardItem.setDate(txtDate.getText().toString());
        cardItem.setTime(txtTime.getText().toString());

        realmDb.commitTransaction();
    }

    public Item getEditData(String itemId){
        realmDb.beginTransaction();

        Item result = realmDb.where(Item.class).equalTo("id", itemId).findFirst();

        realmDb.commitTransaction();

        return result;
    }

    public void setAlarm(){
        Context context = this;
        Calendar cal = Calendar.getInstance();
        Intent activate = new Intent(context, AlarmReceiver.class);
        activate.putExtra("alarmTxt", itemTitle.getText().toString());
        AlarmManager alarms ;
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, activate, 0);
        alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
        LocalDate localDate = LocalDate.parse( txtDate.getText().toString() , dateFormatter );

        String [] timeParts = txtTime.getText().toString().split(":");

        cal.set(Calendar.DAY_OF_YEAR, localDate.getDayOfYear());
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
        cal.set(Calendar.SECOND, 00);
        alarms.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
    }
}

package com.interapt.togglit.ui.updateProgress;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.interapt.togglit.R;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.model.lists.LessonsUndone;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableButtonView;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.util.ViewUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by stevebowling on 2/22/17.
 */

public class UpdateProgressActivity extends BaseActivity implements UpdateProgressView,  RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.student_profile_iv)
    ImageView imageView1;

    @BindView(R.id.update_toolbar)
    CustomizableToolbar updateBar;

    @BindView(R.id.lesson_spinner)
    Spinner spinner;

    @BindView(R.id.in_date)
    TextView date;

    @BindView(R.id.date_wrapper)
    TextInputLayout mDateWrapper;

    @BindView(R.id.update_student_name)
    TextView studentName;

    @BindView(R.id.update_student_school_name)
    TextView schoolName;

    @BindView(R.id.student_update_initial)
    TextView initials;

    @BindView(R.id.update_time)
    TextView time;

    @BindView(R.id.date_line)
    View dateLine;

    @BindView(R.id.lesson_line)
    View lessonLine;

    @BindView(R.id.lesson_wrapper)
    TextInputLayout lessonWrapper;

    @BindView(R.id.words_missed)
    EditText wordsMissed;

    @BindView(R.id.button_submit)
    CustomizableButtonView submitButton;

    @BindView(R.id.time_wrapper)
    TextInputLayout timeWrapper;

    @BindView(R.id.time_line)
    View timeLine;

    @BindView(R.id.missed_wrapper)
    TextInputLayout missedWrapper;

    @BindView(R.id.missed_line)
    View missedLine;

    @BindView(R.id.mood_wrapper)
    TextInputLayout moodWrapper;

    @Inject
    UpdateProgressPresenter updateProgressPresenter;

    @Inject
    SharedPreferencesManager sharedPreferences;

    Handler handler = new Handler();
    RadioGroup group;
    int mood = 0;
    String dateChosen;
    String fluency;
    String sWordsMissed;
    int studentId;
    int wordsXMissed =0;
    int yseconds;
    List<LessonsUndone> unDone;
    int totalWords;
    int lessonDone;
    ArrayList<String> lessons = new ArrayList<>();
    Date dateSelected;
    SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_update_progress);
        ButterKnife.bind(this);
        updateProgressPresenter.attachView(this);
        Intent intent = getIntent();
        studentId = intent.getIntExtra("StudentId", 0);
        String stName = intent.getExtras().getString("studentName");
        String schName = intent.getExtras().getString("schoolName");
        String iD = intent.getExtras().getString("initials");
        String imAge =intent.getExtras().getString("imageString");
        studentName.setText(stName);
        schoolName.setText(schName);
        initials.setText(iD);
        if (imAge != null) {
            if (imAge.length() > 50) {
                setItemImage(imAge, imageView1);
                initials.setVisibility(View.INVISIBLE);
            }
        }
        updateProgressPresenter.onCreate(studentId);
        lessons.add("Lesson");

        setSupportActionBar(updateBar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }
        group=(RadioGroup) findViewById(R.id.radio_group);
        group.setOnCheckedChangeListener(this);
    }

    Runnable r = this::back;
    public void back(){
        super.onBackPressed();
    }


    @OnClick(R.id.date_layout)
    public void dateClicked() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.TimePickerStyle,
                (view, year, monthOfYear, dayOfMonth) -> date.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year), mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.update_time)
    public void updateTime() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View theView = inflater.inflate(R.layout.time_picker_dialog, null);

        final NumberPicker minutes = (NumberPicker) theView.findViewById(R.id.euro_picker);
        final NumberPicker seconds = (NumberPicker) theView.findViewById(R.id.cent_picker);
        final String[] second = new String[1];
        builder.setView(theView)

                .setPositiveButton("OK", (dialog, which) -> {
                    if (seconds.getValue() < 10) {
                        second[0] = "0" + seconds.getValue();
                    } else {
                        second[0] = String.valueOf(seconds.getValue());
                    }
                    time.setText(String.valueOf(minutes.getValue()) + ":" + second[0]);
                    yseconds = (minutes.getValue() * 60) + seconds.getValue();

                });

        minutes.setMinValue(0);
        minutes.setMaxValue(59);
        seconds.setMinValue(1);
        seconds.setMaxValue(59);

        AlertDialog alert11 = builder.create();
        alert11.show();
        Button bq = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.bad_frown:
                mood= 1;
                break;
            case R.id.frown:
                mood = 2;
                break;
            case R.id.neutral:
                mood = 3;
                break;
            case R.id.happy:
                mood = 4;
                break;
            case R.id.very_happy:
                mood = 5;
                break;
            default:
                break;
        }
    }
    @Override
    public void setLesson(List<LessonsUndone> lesson) {
        unDone = lesson;

        for (int i = 0; i < lesson.size(); i++){
            lessons.add(lesson.get(i).getLessonIdentifier());
        }
        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, lessons);
        spinner.setAdapter(staticAdapter);

    }

    @Override
    public void showSnackbar() {
        submitButton.setClickable(false);
        Snackbar snack = ViewUtil.snackbar(this, findViewById(android.R.id.content), "     Lesson " + spinner.getSelectedItem() + " has been updated.");
        snack.setDuration(Snackbar.LENGTH_SHORT).show();
        handler.postDelayed(r,1500);
    }

    @OnClick(R.id.button_submit)
    public void submit() {
        lessonDone = 0;
        if (spinner.getSelectedItem() != null) {
            if (spinner.getSelectedItem() !="Lesson") {
                for (int i = 0; i < unDone.size(); i++) {
                    if (unDone.get(i).getLessonIdentifier().equals(spinner.getSelectedItem())) {
                        lessonDone = unDone.get(i).getId();
                        totalWords = unDone.get(i).getTotalWords();
                    }
                }
            }
            fluency = time.getText().toString();
            sWordsMissed = wordsMissed.getText().toString();
            dateChosen = date.getText().toString();
            try {
                dateSelected = fmt.parse(dateChosen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date.getText().toString().equals("")) {
                mDateWrapper.setError("Please select a Date.");
                dateLine.setBackgroundColor(Color.rgb(226, 45, 67));
            } else {
                mDateWrapper.setErrorEnabled(false);
                dateLine.setBackgroundColor(Color.rgb(155, 155, 155));
            }
            if (spinner.getSelectedItem() != "Lesson") {
                lessonWrapper.setErrorEnabled(false);
                lessonLine.setBackgroundColor(Color.rgb(155, 155, 155));
            } else {
                lessonWrapper.setError("Please select a Lesson.");
                lessonLine.setBackgroundColor(Color.rgb(226, 45, 67));
            }
            if (fluency.equals("")) {
                timeWrapper.setError("Please select Fluency.");
                timeLine.setBackgroundColor(Color.rgb(226, 45, 67));
            } else {
                timeWrapper.setErrorEnabled(false);
                timeLine.setBackgroundColor(Color.rgb(155, 155, 155));
            }
            if (sWordsMissed.equals("Words Missed") || sWordsMissed.equals("")) {
                missedWrapper.setError("Please select Words Missed.");
                missedLine.setBackgroundColor(Color.rgb(226, 45, 67));
            }else if(Integer.parseInt(sWordsMissed) > totalWords){
                missedWrapper.setError("Words missed is higher than total words ("+ totalWords +").");
                missedLine.setBackgroundColor(Color.rgb(226, 45, 67));

            }else {
                missedWrapper.setErrorEnabled(false);
                missedLine.setBackgroundColor(Color.rgb(155, 155, 155));
                wordsXMissed = Integer.parseInt(sWordsMissed);
            }
            if (mood == 0) {
                moodWrapper.setError("Please select a Mood.");
            } else {
                moodWrapper.setErrorEnabled(false);
            }
            if (mood == 0 || dateChosen.equals("") || fluency.equals("") || wordsXMissed == 0 || lessonDone ==0) {

            } else {

                updateProgressPresenter.callCreateSession(dateSelected, studentId, mood, wordsXMissed, lessonDone,sharedPreferences.getLoggedUserProfile().getId(),
                        sharedPreferences.getLoggedUserProfile().getFirstName()+" "+sharedPreferences.getLoggedUserProfile().getLastName(), yseconds);
            }
        }
    }

    public void setItemImage(String bitImage, ImageView imageView) {
        byte[] imageByteArray = Base64.decode(bitImage, Base64.DEFAULT);

        Glide.with(UpdateProgressActivity.this).load(imageByteArray).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(UpdateProgressActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @OnClick(R.id.words_missed)
    public void words(){
        wordsMissed.setText("");
    }

}








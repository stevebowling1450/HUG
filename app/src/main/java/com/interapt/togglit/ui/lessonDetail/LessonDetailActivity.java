package com.interapt.togglit.ui.lessonDetail;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.interapt.togglit.R;
import com.interapt.togglit.data.model.user.Lesson;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nicholashall on 3/9/17.
 */

public class LessonDetailActivity extends BaseActivity implements LessonDetailView {

    @Inject
    LessonDetailPresenter lessonPresenter;


    @BindView(R.id.lesson_title)
    TextView lessonTitle;

    @BindView(R.id.lesson_body)
    TextView lessonBody;

    @BindView(R.id.lesson_image)
    ImageView lessonImage;

    @BindView(R.id.lesson_words_missed)
    TextView lessonWordsMissed;

    @BindView(R.id.lesson_detail_toolbar)
    CustomizableToolbar lessonDetailToolbar;

    @BindView(R.id.lesson_detail_title)
    TextView lessonDetailTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_lesson_detail);

        int id = getIntent().getIntExtra("id", 0);

        ButterKnife.bind(this);

        lessonPresenter.attachView(this);
        lessonPresenter.onCreate(id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }

        setSupportActionBar(lessonDetailToolbar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }


    }




    @Override
    public void setLesson(Lesson lesson) {

        Timber.d("Hi Lesson",lesson.getLessonName());

        lessonDetailTitle.setText(getString(R.string.Lesson) + " " + lesson.getLessonIdentifier());
        lessonTitle.setText(lesson.getLessonName());
        lessonBody.setText(lesson.getSummary());
        setLessonImage(lesson.getImage(),lessonImage,LessonDetailActivity.this);
        lessonWordsMissed.setText(lesson.getTotalWord().toString());

    }

    public void setLessonImage(String bitImage, ImageView imageView, Context context) {

        byte[] decodedString = Base64.decode(bitImage, Base64.DEFAULT);

        Glide.with(context)
                .load(decodedString)
                .asBitmap()
                .into(imageView);
    }

}

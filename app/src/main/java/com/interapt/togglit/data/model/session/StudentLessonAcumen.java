package com.interapt.togglit.data.model.session;

import android.os.Parcel;
import android.os.Parcelable;


public class StudentLessonAcumen implements Parcelable {
    private String Lesson;
    private Integer attemptOne;
    private Integer attemptTwo;
    private String date1;
    private String date2;
    private Integer fluency1;
    private Integer fluency2;
    private Integer mood1;
    private Integer mood2;
    private String lessonIdentifier;

    public StudentLessonAcumen(String lesson, Integer attemptOne, Integer attemptTwo, String date1, String date2, Integer fluency1, Integer fluency2, Integer mood1, Integer mood2, String lessonIdentifier) {
        Lesson = lesson;
        this.attemptOne = attemptOne;
        this.attemptTwo = attemptTwo;
        this.date1 = date1;
        this.date2 = date2;
        this.fluency1 = fluency1;
        this.fluency2 = fluency2;
        this.mood1 = mood1;
        this.mood2 = mood2;
        this.lessonIdentifier = lessonIdentifier;
    }

    public String getLessonIdentifier() {
        return lessonIdentifier;
    }

    public void setLessonIdentifier(String lessonIdentifierA) {
        this.lessonIdentifier = lessonIdentifierA;
    }

    public StudentLessonAcumen(){

    }


    public String getDate1() {
        if (date1 == null) {
            return "-";
        }
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {

        if (date2 == null) {
            return "-";
        }
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public Integer getFluency1() {
        if (fluency1 == null) {
            return 0;
        }
        return fluency1;
    }

    public void setFluency1(Integer fluency1) {
        this.fluency1 = fluency1;
    }

    public Integer getFluency2() {
        if (fluency2 == null) {
            return 0;
        }
        return fluency2;
    }

    public void setFluency2(Integer fluency2) {
        this.fluency2 = fluency2;
    }

    public Integer getMood1() {
        if (mood1 == null) {
            return 0;
        }
        return mood1;
    }

    public void setMood1(Integer mood1) {
        this.mood1 = mood1;
    }

    public Integer getMood2() {
        if (mood2 == null) {
            return 0;
        }
        return mood2;
    }

    public void setMood2(Integer mood2) {
        this.mood2 = mood2;
    }

    public String getLesson() {
        if (Lesson == null) {
            return "-";
        }
        return Lesson;
    }

    public void setLesson(String lesson) {
        Lesson = lesson;
    }

    public Integer getAttemptOne() {
        if (attemptOne == null) {
            return 0;
        }
        return attemptOne;
    }

    public void setAttemptOne(Integer attemptOne) {
        this.attemptOne = attemptOne;
    }

    public Integer getAttemptTwo() {
        if (attemptTwo == null) {
            return 0;
        }
        return attemptTwo;
    }

    public void setAttemptTwo(Integer attemptTwo) {
        this.attemptTwo = attemptTwo;
    }

    protected StudentLessonAcumen(Parcel in) {
        Lesson = in.readString();
        lessonIdentifier = in.readString();
        attemptOne = in.readByte() == 0x00 ? null : in.readInt();
        attemptTwo = in.readByte() == 0x00 ? null : in.readInt();
        date1 = in.readString();
        date2 = in.readString();
        fluency1 = in.readByte() == 0x00 ? null : in.readInt();
        fluency2 = in.readByte() == 0x00 ? null : in.readInt();
        mood1 = in.readByte() == 0x00 ? null : in.readInt();
        mood2 = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Lesson);
        if (attemptOne == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(attemptOne);
        }
        if (attemptTwo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(attemptTwo);
        }
        dest.writeString(lessonIdentifier);
        dest.writeString(date1);
        dest.writeString(date2);
        if (fluency1 == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(fluency1);
        }
        if (fluency2 == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(fluency2);
        }
        if (mood1 == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(mood1);
        }
        if (mood2 == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(mood2);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StudentLessonAcumen> CREATOR = new Parcelable.Creator<StudentLessonAcumen>() {
        @Override
        public StudentLessonAcumen createFromParcel(Parcel in) {
            return new StudentLessonAcumen(in);
        }

        @Override
        public StudentLessonAcumen[] newArray(int size) {
            return new StudentLessonAcumen[size];
        }
    };
}
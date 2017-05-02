package com.interapt.togglit.data.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Matthew.Watson on 3/6/17.
 */

public class Session implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("lesson_identifier")
    @Expose
    private String lessonIdentifier;
    @SerializedName("lesson_id")
    @Expose
    private Integer lessonId;
    @SerializedName("word_missed")
    @Expose
    private Integer wordMissed;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("student_mood")
    @Expose
    private Integer studentMood;
    @SerializedName("recorded_by")
    @Expose
    private String recordedBy;
    @SerializedName("acumen")
    @Expose
    private Double acumen;
    @SerializedName("first_attempt")
    @Expose
    private Boolean firstAttempt;
    @SerializedName("second_attempt")
    @Expose
    private Boolean secondAttempt;

    public Session() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {

        return date;

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getWordMissed() {
        return wordMissed;
    }

    public void setWordMissed(Integer wordMissed) {
        this.wordMissed = wordMissed;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getStudentMood() {
        return studentMood;
    }

    public void setStudentMood(Integer studentMood) {
        this.studentMood = studentMood;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public Integer getAcumen() {
        if (acumen != null) {
            return acumen.intValue();
        }
        return null;
    }

    public String getLessonIdentifier() {
        return lessonIdentifier;
    }

    public void setLessonIdentifier(String lessonIdentifier) {
        this.lessonIdentifier = lessonIdentifier;
    }

    public void setAcumen(Double acumen) {
        this.acumen = acumen;
    }

    public Boolean getFirstAttempt() {
        return firstAttempt;
    }

    public void setFirstAttempt(Boolean firstAttempt) {
        this.firstAttempt = firstAttempt;
    }

    public Boolean getSecondAttempt() {
        return secondAttempt;
    }

    public void setSecondAttempt(Boolean secondAttempt) {
        this.secondAttempt = secondAttempt;
    }

    protected Session(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        studentId = in.readByte() == 0x00 ? null : in.readInt();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        lessonIdentifier = in.readString();
        lessonId = in.readByte() == 0x00 ? null : in.readInt();
        wordMissed = in.readByte() == 0x00 ? null : in.readInt();
        time = in.readByte() == 0x00 ? null : in.readInt();
        studentMood = in.readByte() == 0x00 ? null : in.readInt();
        recordedBy = in.readString();
        acumen = in.readByte() == 0x00 ? null : in.readDouble();
        byte firstAttemptVal = in.readByte();
        firstAttempt = firstAttemptVal == 0x02 ? null : firstAttemptVal != 0x00;
        byte secondAttemptVal = in.readByte();
        secondAttempt = secondAttemptVal == 0x02 ? null : secondAttemptVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (studentId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(studentId);
        }
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeString(lessonIdentifier);
        if (lessonId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(lessonId);
        }
        if (wordMissed == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(wordMissed);
        }
        if (time == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(time);
        }
        if (studentMood == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(studentMood);
        }
        dest.writeString(recordedBy);
        if (acumen == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(acumen);
        }
        if (firstAttempt == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (firstAttempt ? 0x01 : 0x00));
        }
        if (secondAttempt == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (secondAttempt ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Session> CREATOR = new Parcelable.Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };
}

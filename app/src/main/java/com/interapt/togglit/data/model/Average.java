package com.interapt.togglit.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nicholashall on 3/9/17.
 */

public class Average {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("acumen_one")
        @Expose
        private Double acumenOne;
        @SerializedName("acumen_two")
        @Expose
        private Double acumenTwo;
        @SerializedName("mood_one")
        @Expose
        private Integer moodOne;
        @SerializedName("mood_two")
        @Expose
        private Integer moodTwo;
        @SerializedName("time_one")
        @Expose
        private Integer timeOne;
        @SerializedName("time_two")
        @Expose
        private Integer timeTwo;
        @SerializedName("student_id")
        @Expose
        private Integer studentId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAcumenOne() {
            //// TODO: 3/30/17 change to round up 
            return acumenOne.intValue();
        }

        public void setAcumenOne(Double acumenOne) {
            this.acumenOne = acumenOne;
        }

        public Integer getAcumenTwo() {

            return acumenTwo.intValue();
        }

        public void setAcumenTwo(Double acumenTwo) {
            this.acumenTwo = acumenTwo;
        }

        public Integer getMoodOne() {
            return moodOne;
        }

        public void setMoodOne(Integer moodOne) {
            this.moodOne = moodOne;
        }

        public Integer getMoodTwo() {
            return moodTwo;
        }

        public void setMoodTwo(Integer moodTwo) {
            this.moodTwo = moodTwo;
        }

        public Integer getTimeOne() {
            return timeOne;
        }

        public void setTimeOne(Integer timeOne) {
            this.timeOne = timeOne;
        }

        public Integer getTimeTwo() {
            return timeTwo;
        }

        public void setTimeTwo(Integer timeTwo) {
            this.timeTwo = timeTwo;
        }

        public Integer getStudentId() {
            return studentId;
        }

        public void setStudentId(Integer studentId) {
            this.studentId = studentId;
        }


}

package com.interapt.togglit.data.model.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interapt.togglit.data.model.user.School;
import com.interapt.togglit.data.model.user.Student;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Matthew.Watson on 3/1/17.
 */

public class Volunteers {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("commitment_level")
        @Expose
        private String commitmentLevel;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("second_phone")
        @Expose
        private String secondPhone;
        @SerializedName("persona")
        @Expose
        private String persona;
        @SerializedName("background_check")
        @Expose
        private Boolean backgroundCheck;
        @SerializedName("code_of_ethics")
        @Expose
        private Boolean codeOfEthics;
        @SerializedName("e_first_name")
        @Expose
        private String eFirstName;
        @SerializedName("e_last_name")
        @Expose
        private String eLastName;
        @SerializedName("emergency_contact_number")
        @Expose
        private String emergencyContactNumber;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("notes")
        @Expose
        private String notes;
        @SerializedName("specialist")
        @Expose
        private Boolean specialist;
        @SerializedName("share_phone")
        @Expose
        private Boolean sharePhone;
        @SerializedName("school_id")
        @Expose
        private List<Integer> schoolId = null;
        @SerializedName("students")
        @Expose
        private List<Student> students = null;
        @SerializedName("schools")
        @Expose
        private List<School> schools = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCommitmentLevel() {
            return commitmentLevel;
        }

        public void setCommitmentLevel(String commitmentLevel) {
            this.commitmentLevel = commitmentLevel;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSecondPhone() {
            return secondPhone;
        }

        public void setSecondPhone(String secondPhone) {
            this.secondPhone = secondPhone;
        }

        public Integer getPersona() {
            if (persona == null) {
                return 0;
            }
            Integer personaLevel = 0;
            switch (persona) {
                case "Admin":
                    personaLevel = 3;
                    break;
                case "Specialist":
                    personaLevel = 2;
                    break;
                case "Volunteer":
                    personaLevel = 1;
                    break;
            }
            Timber.d("PersonaLevel:", personaLevel);
            return personaLevel;
        }

        public void setPersona(String persona) {
            this.persona = persona;
        }

        public Boolean getBackgroundCheck() {
            return backgroundCheck;
        }

        public void setBackgroundCheck(Boolean backgroundCheck) {
            this.backgroundCheck = backgroundCheck;
        }

        public Boolean getCodeOfEthics() {
            return codeOfEthics;
        }

        public void setCodeOfEthics(Boolean codeOfEthics) {
            this.codeOfEthics = codeOfEthics;
        }

        public String getEFirstName() {
            return eFirstName;
        }

        public void setEFirstName(String eFirstName) {
            this.eFirstName = eFirstName;
        }

        public String getELastName() {
            return eLastName;
        }

        public void setELastName(String eLastName) {
            this.eLastName = eLastName;
        }

        public String getEmergencyContactNumber() {
            return emergencyContactNumber;
        }

        public void setEmergencyContactNumber(String emergencyContactNumber) {
            this.emergencyContactNumber = emergencyContactNumber;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public Boolean getSpecialist() {
            return specialist;
        }

        public void setSpecialist(Boolean specialist) {
            this.specialist = specialist;
        }

        public Boolean getSharePhone() {
            return sharePhone;
        }

        public void setSharePhone(Boolean sharePhone) {
            this.sharePhone = sharePhone;
        }

        public List<Integer> getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(List<Integer> schoolId) {
            this.schoolId = schoolId;
        }

        public List<Student> getStudents() {
            return students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
        }

        public List<School> getSchools() {
            return schools;
        }

        public void setSchools(List<School> schools) {
            this.schools = schools;
        }


        public String geteFirstName() {
            return eFirstName;
        }

        public void seteFirstName(String eFirstName) {
            this.eFirstName = eFirstName;
        }

        public String geteLastName() {
            return eLastName;
        }

        public void seteLastName(String eLastName) {
            this.eLastName = eLastName;
        }

    public boolean isVolunteerForSchool(List<Integer> filteredList) {
        for (int school : filteredList) {
            if (schoolId.contains(school)) {
                return true;
            }
        }
        return false;
    }

}

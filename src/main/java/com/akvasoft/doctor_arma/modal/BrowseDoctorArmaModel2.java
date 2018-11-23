package com.akvasoft.doctor_arma.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personal")
public class BrowseDoctorArmaModel2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "academicTitle")
    private String academicTitle;
    @Column(name = "title")
    private String title;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "workplace")
    private String workplace;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "Gender")
    private String Gender;
    @Column(name = "birth_year")
    private String birth_year;
    @Column(name = "personnel_type")
    private String personnel_type;
    @Column(name = "undergraduate_school")
    private String undergraduate_school;
    @Column(name = "proffessional_area")
    private String proffessional_area;
    @Column(name = "city")
    private String city;
    @Column(name = "branch")
    private String branch;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personel", fetch = FetchType.LAZY)
    private List<BrowseDoctorArmaModel> eduList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doc", fetch = FetchType.LAZY)
    private List<BrowseDoctorArmaModel3> workplaces = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getPersonnel_type() {
        return personnel_type;
    }

    public void setPersonnel_type(String personnel_type) {
        this.personnel_type = personnel_type;
    }

    public String getUndergraduate_school() {
        return undergraduate_school;
    }

    public void setUndergraduate_school(String undergraduate_school) {
        this.undergraduate_school = undergraduate_school;
    }

    public String getProffessional_area() {
        return proffessional_area;
    }

    public void setProffessional_area(String proffessional_area) {
        this.proffessional_area = proffessional_area;
    }

    public List<BrowseDoctorArmaModel> getEduList() {
        return eduList;
    }

    public void setEduList(List<BrowseDoctorArmaModel> eduList) {
        this.eduList = eduList;
    }

    public List<BrowseDoctorArmaModel3> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaces(List<BrowseDoctorArmaModel3> workplaces) {
        this.workplaces = workplaces;
    }
}

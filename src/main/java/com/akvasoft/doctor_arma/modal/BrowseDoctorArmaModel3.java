package com.akvasoft.doctor_arma.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "workspace")
public class BrowseDoctorArmaModel3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idworkspace")
    private int idworkspace;
    @Column(name = "name_of_the_institution")
    private String name_of_the_institution;
    @Column(name = "institution_city")
    private String institution_city;
    @Column(name = "start_date")
    private String start_date;
    @Column(name = "final_date")
    private String final_date;
    @Column(name = "subject_of_contract")
    private String subject_of_contract;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc")
    private BrowseDoctorArmaModel2 doc;

    public int getIdworkspace() {
        return idworkspace;
    }

    public void setIdworkspace(int idworkspace) {
        this.idworkspace = idworkspace;
    }

    public String getName_of_the_institution() {
        return name_of_the_institution;
    }

    public void setName_of_the_institution(String name_of_the_institution) {
        this.name_of_the_institution = name_of_the_institution;
    }

    public String getInstitution_city() {
        return institution_city;
    }

    public void setInstitution_city(String institution_city) {
        this.institution_city = institution_city;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getFinal_date() {
        return final_date;
    }

    public void setFinal_date(String final_date) {
        this.final_date = final_date;
    }

    public String getSubject_of_contract() {
        return subject_of_contract;
    }

    public void setSubject_of_contract(String subject_of_contract) {
        this.subject_of_contract = subject_of_contract;
    }

    public BrowseDoctorArmaModel2 getDoc() {
        return doc;
    }

    public void setDoc(BrowseDoctorArmaModel2 doc) {
        this.doc = doc;
    }
}

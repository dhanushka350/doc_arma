package com.akvasoft.doctor_arma.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "education")
public class BrowseDoctorArmaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "eduid")
    private int eduid;
    @Column(name = "branch_name")
    private String Branş_Adı;
    @Column(name = "school_graduated_from")
    private String Mezun_Olduğu_Okul;
    @Column(name = "diploma_registration_year")
    private String Diploma_Tescil_Yılı;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor")
    private BrowseDoctorArmaModel2 personel;

    public int getEduid() {
        return eduid;
    }

    public void setEduid(int eduid) {
        this.eduid = eduid;
    }

    public String getBranş_Adı() {
        return Branş_Adı;
    }

    public void setBranş_Adı(String branş_Adı) {
        Branş_Adı = branş_Adı;
    }

    public String getMezun_Olduğu_Okul() {
        return Mezun_Olduğu_Okul;
    }

    public void setMezun_Olduğu_Okul(String mezun_Olduğu_Okul) {
        Mezun_Olduğu_Okul = mezun_Olduğu_Okul;
    }

    public String getDiploma_Tescil_Yılı() {
        return Diploma_Tescil_Yılı;
    }

    public void setDiploma_Tescil_Yılı(String diploma_Tescil_Yılı) {
        Diploma_Tescil_Yılı = diploma_Tescil_Yılı;
    }

    public BrowseDoctorArmaModel2 getPersonel() {
        return personel;
    }

    public void setPersonel(BrowseDoctorArmaModel2 personel) {
        this.personel = personel;
    }
}

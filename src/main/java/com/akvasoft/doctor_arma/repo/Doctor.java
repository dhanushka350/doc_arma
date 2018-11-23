package com.akvasoft.doctor_arma.repo;

import com.akvasoft.doctor_arma.modal.BrowseDoctorArmaModel2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Doctor extends JpaRepository<BrowseDoctorArmaModel2, Integer> {
    boolean existsByAcademicTitleEqualsAndTitleEqualsAndNameEqualsAndSurnameEqualsAndWorkplaceEqualsAndNationalityEquals(String Akademik_Unvan, String Unvan, String Adı, String Soyadı, String workplace, String Uyruk);

    List<BrowseDoctorArmaModel2> findAllByBranch(String b);
    List<BrowseDoctorArmaModel2> findAllByOrderByIdDesc();

    BrowseDoctorArmaModel2 getFirstByAcademicTitleEqualsAndTitleEqualsAndSurnameEqualsAndNameEqualsAndBranchEqualsAndCityEquals
            (String atitle, String title, String surName, String name, String branch, String city);

    BrowseDoctorArmaModel2 getFirstByNameEqualsAndSurnameEqualsAndBranchEqualsAndCityEquals(String name, String surname, String branch, String city);

}

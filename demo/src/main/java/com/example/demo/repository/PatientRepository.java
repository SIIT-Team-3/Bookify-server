package com.example.demo.repository;

import com.example.demo.model.Patient;

import java.util.Optional;

public interface PatientRepository {

    Optional<Patient> findBySocialNumber(Long socialNumber);
}

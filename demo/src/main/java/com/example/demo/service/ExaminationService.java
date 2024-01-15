package com.example.demo.service;

import com.example.demo.exception.PatientNotFoundException;
import com.example.demo.model.Patient;
import com.example.demo.model.Symptom;
import com.example.demo.model.Treatment;
import com.example.demo.repository.PatientRepository;

import java.util.List;

public class ExaminationService {

    private PatientRepository patientRepository;

    public ExaminationService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /***
     * Metoda na osnovu simptoma i prethodnog stanja pacijenta, propisuje odgovarajuci nacin lecenja
     *
     * @param socialNumber
     * @param symptoms
     * @return
     */
    public Treatment mentalHealthExamination(Long socialNumber, List<Symptom> symptoms) {

        if (symptoms.size() != 2)
            throw new IllegalStateException();

        boolean alarm = this.containsAlarmKeyword(symptoms);

        if (alarm)
            return Treatment.HOSPITALIZATION;

        Patient patient = patientRepository.findBySocialNumber(socialNumber)
                .orElseThrow(() -> new PatientNotFoundException("Patient unknown to database."));

        Treatment possibleTreatment = this.getPossibleTreatment(symptoms);
        if (patient.isAddict() && possibleTreatment == Treatment.MEDICINE)
            return Treatment.THERAPY;

        return possibleTreatment;
    }

    private boolean containsAlarmKeyword(List<Symptom> symptoms) {
        return symptoms.stream().anyMatch(s -> s == Symptom.SUICIDAL_THOUGHTS);
    }

    private Treatment getPossibleTreatment(List<Symptom> symptoms) {
        if (symptoms.stream().anyMatch(s -> s == Symptom.SADNESS))
            return Treatment.MEDICINE;

        return Treatment.THERAPY;
    }

}

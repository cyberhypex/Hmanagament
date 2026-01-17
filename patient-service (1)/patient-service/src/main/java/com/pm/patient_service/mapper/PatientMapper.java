package com.pm.patient_service.mapper;

import com.pm.patient_service.DTOs.PatientResponseDTO;
import com.pm.patient_service.model.Patient;

public class PatientMapper  {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientDTO=new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName().toString());
        patientDTO.setEmail(patient.getEmail().toString());
        patientDTO.setAddress(patient.getAddress().toString());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;

    }
}

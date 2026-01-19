package com.pm.patient_service.service;

import com.pm.patient_service.DTOs.PatientRequestDTO;
import com.pm.patient_service.DTOs.PatientResponseDTO;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatient(){
        List<Patient> patients=patientRepository.findAll();
        return patients.stream()
                .map(PatientMapper::toDTO).toList();


    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with the same e-mail already exists"+patientRequestDTO.getEmail());
        }
        Patient newPatient=patientRepository.save(
                PatientMapper.fromDTO(patientRequestDTO)
        );
        return PatientMapper.toDTO(newPatient);
    }



}


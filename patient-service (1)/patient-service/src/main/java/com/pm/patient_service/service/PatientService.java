package com.pm.patient_service.service;

import com.pm.patient_service.DTOs.PatientRequestDTO;
import com.pm.patient_service.DTOs.PatientResponseDTO;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.grpc.BillingServiceGrpcClient;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PatientService {


    private final PatientRepository patientRepository;
    private  final BillingServiceGrpcClient billingServiceGrpcClient;

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
    }

    public List<PatientResponseDTO> getPatient(){
        List<Patient> patients=patientRepository.findAll();
        return patients.stream()
                .map(PatientMapper::toDTO).toList();


    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        log.info("createPatient() called");

        Patient newPatient = patientRepository.save(
                PatientMapper.fromDTO(patientRequestDTO)
        );

        log.info("Calling billing-service via gRPC");

        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail()
        );

        log.info("Returned from billing-service gRPC call");

        return PatientMapper.toDTO(newPatient);
    }


    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient=patientRepository.findById(id).orElseThrow(
                ()-> new PatientNotFoundException("Patient with id {} not found",id)
        );
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
            throw new EmailAlreadyExistsException("A patient with the same e-mail already exists"+patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
      //  System.out.println("DOB = [" + patientRequestDTO.getDateOfBirth() + "]");

        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient=patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id){

        patientRepository.deleteById(id);
    }




}


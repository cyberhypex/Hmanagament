package com.pm.patient_service.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(max=100,message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "E-mail is required")
    @Email(message = "E-mail should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n",message = "Regex not matched")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "DOB is required")
    private String dateOfBirth;

    @NotNull(message = "Registered Date is required")
    private String registeredDate;

}

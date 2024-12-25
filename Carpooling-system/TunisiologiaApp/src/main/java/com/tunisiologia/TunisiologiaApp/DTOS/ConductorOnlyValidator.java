package com.tunisiologia.TunisiologiaApp.DTOS;

import com.tunisiologia.TunisiologiaApp.Repositories.UserRepo;
import com.tunisiologia.TunisiologiaApp.models.Users;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ConductorOnlyValidator implements ConstraintValidator<com.example.project.validation.ConductorOnly, TrajetDto> {

    @Autowired
    private UserRepo userRepository;

    @Override
    public boolean isValid(TrajetDto trajectoryDTO, ConstraintValidatorContext context) {
        Users user = userRepository.findById(trajectoryDTO.getUserId()).orElse(null);
        return user != null && "CONDUCTOR".equalsIgnoreCase(user.getRole().toString());
    }
}
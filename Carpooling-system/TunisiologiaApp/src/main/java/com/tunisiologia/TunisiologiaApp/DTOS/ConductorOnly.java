package com.example.project.validation;

import com.tunisiologia.TunisiologiaApp.DTOS.ConductorOnlyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConductorOnlyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConductorOnly {
    String message() default "Only conductors can add trajectories";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
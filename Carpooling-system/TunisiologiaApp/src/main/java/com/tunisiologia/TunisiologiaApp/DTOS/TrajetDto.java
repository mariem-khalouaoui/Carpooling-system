package com.tunisiologia.TunisiologiaApp.DTOS;

import com.tunisiologia.TunisiologiaApp.models.Users;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrajetDto {
    @NotNull(message = "User ID is required")
    private int userId;
    private int id;
    private int placesDisponibles;
    private int placesReservees;
    private int evaluation;
    private long coutPlace;
    private String destination;
    private String depart;
    private String description;
    private Date dateDeDepart;
}

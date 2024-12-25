package com.tunisiologia.TunisiologiaApp.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tunisiologia.TunisiologiaApp.models.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Trajet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTrajet;

    private int placesDisponibles;
    private int placesReservees;
    private long coutPlace;
    private int evaluation;

    private String destination;
    private String depart;
    private String description;
    private Date dateDeDepart;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}

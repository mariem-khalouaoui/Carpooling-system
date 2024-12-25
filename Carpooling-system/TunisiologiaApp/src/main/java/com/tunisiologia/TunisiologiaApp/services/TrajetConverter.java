package com.tunisiologia.TunisiologiaApp.services;

import com.tunisiologia.TunisiologiaApp.DTOS.TrajetDto;
import com.tunisiologia.TunisiologiaApp.Entities.Trajet;
import com.tunisiologia.TunisiologiaApp.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrajetConverter {
    @Autowired
    UserRepo userRepo;

    public TrajetDto convertToTrajetDTO(Trajet trajet) {
        TrajetDto trajetDTO = new TrajetDto();
        trajetDTO.setId(trajet.getIdTrajet());
        trajetDTO.setDepart(trajet.getDepart());
        trajetDTO.setDescription(trajet.getDescription());
        trajetDTO.setDateDeDepart(trajet.getDateDeDepart());
        trajetDTO.setDestination(trajet.getDestination());
        trajetDTO.setUserId(trajet.getUser().getId());
        trajetDTO.setPlacesDisponibles(trajet.getPlacesDisponibles());
        trajetDTO.setCoutPlace(trajet.getCoutPlace());
        trajetDTO.setPlacesReservees(trajet.getPlacesReservees());
        trajetDTO.setEvaluation(trajet.getEvaluation());
        return trajetDTO;
    }

    public Trajet convertToTrajet(TrajetDto trajetDTO) {
        Trajet trajet = new Trajet();
        trajet.setIdTrajet(trajetDTO.getId());
        trajet.setDestination(trajetDTO.getDestination());
        trajet.setDescription(trajetDTO.getDescription());
        trajet.setDepart(trajetDTO.getDepart());
        trajet.setDateDeDepart(trajetDTO.getDateDeDepart());
        trajet.setUser(userRepo.findById(trajetDTO.getUserId()).orElse(null));
        trajet.setPlacesDisponibles(trajetDTO.getPlacesDisponibles());
        trajet.setCoutPlace(trajetDTO.getCoutPlace());
        trajet.setPlacesReservees(trajetDTO.getPlacesReservees());
        trajet.setEvaluation(trajetDTO.getEvaluation());
        return trajet;
    }


}


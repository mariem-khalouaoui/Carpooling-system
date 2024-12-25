package com.tunisiologia.TunisiologiaApp.services;

import com.tunisiologia.TunisiologiaApp.DTOS.TrajetDto;

import java.util.List;

public interface TrajetService {
    TrajetDto getTrajetById(int id);
    TrajetDto createTrajet(TrajetDto trajetDto);
    TrajetDto updateTrajet(int id, int nombreDePlace);
    TrajetDto evaluerTrajet(int id, int evaluation);
    void deleteTrajet(int id);
    List<TrajetDto> listTrajetsParUtilisateur(int userId);
    List<TrajetDto> listTrajets();
}

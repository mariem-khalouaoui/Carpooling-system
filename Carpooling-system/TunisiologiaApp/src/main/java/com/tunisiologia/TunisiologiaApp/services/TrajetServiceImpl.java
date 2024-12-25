package com.tunisiologia.TunisiologiaApp.services;

import com.tunisiologia.TunisiologiaApp.DTOS.TrajetDto;
import com.tunisiologia.TunisiologiaApp.Entities.Trajet;
import com.tunisiologia.TunisiologiaApp.Repositories.ITrajetRepository;
import com.tunisiologia.TunisiologiaApp.Repositories.UserRepo;
import com.tunisiologia.TunisiologiaApp.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrajetServiceImpl implements TrajetService {

    @Autowired
    private ITrajetRepository trajetRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrajetConverter trajetConverter;

    @Override
    public TrajetDto getTrajetById(int id) {
        Trajet trajet = trajetRepository.findById(id).orElse(null);
        return trajet != null ? trajetConverter.convertToTrajetDTO(trajet) : null;
    }

    @Override
    public TrajetDto createTrajet(TrajetDto trajetDTO) {
        Users user = userRepo.findById(trajetDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!"CONDUCTOR".equalsIgnoreCase(user.getRole().toString())) {
            throw new IllegalArgumentException("Only conductors can add trajectories");
        }
        trajetDTO.setPlacesReservees(0);

        Trajet trajet = trajetConverter.convertToTrajet(trajetDTO);
        trajet.setUser(user);

        Trajet savedTrajet = trajetRepository.save(trajet);
        return trajetConverter.convertToTrajetDTO(savedTrajet);
    }

    @Override
    public TrajetDto updateTrajet(int id, int nombreDePlace) {
        Optional<Trajet> optionalTajet = trajetRepository.findById(id);

        if (optionalTajet.isPresent()) {

            Trajet trajet = optionalTajet.get();

            if(trajet.getPlacesReservees()+nombreDePlace>trajet.getPlacesDisponibles()) {
                throw new IllegalArgumentException("Only conductors can add trajectories");
            }

            trajet.setPlacesReservees(trajet.getPlacesReservees() + nombreDePlace);

            Trajet updatedTrajet = trajetRepository.save(trajet);
            return trajetConverter.convertToTrajetDTO(updatedTrajet);
        }
        return null;
    }

    @Override
    public TrajetDto evaluerTrajet(int id, int evaluation) {
        Optional<Trajet> optionalTajet = trajetRepository.findById(id);

        if (optionalTajet.isPresent()) {

            Trajet trajet = optionalTajet.get();

            trajet.setEvaluation(evaluation);

            Trajet updatedTrajet = trajetRepository.save(trajet);
            return trajetConverter.convertToTrajetDTO(updatedTrajet);
        }
        return null;
    }

    @Override
    public void deleteTrajet(int id) {
        if (trajetRepository.existsById(id)) {
            trajetRepository.deleteById(id);
        }
    }

    @Override
    public List<TrajetDto> listTrajetsParUtilisateur(int userId) {
        Users user = userRepo.findById(userId).orElse(null);
        List<Trajet> trajets = trajetRepository.findByUser(user);
        return trajets.stream()
                .map(trajetConverter::convertToTrajetDTO)
                .toList();
    }

    @Override
    public List<TrajetDto> listTrajets() {
        List<Trajet> trajets = trajetRepository.findAll();
        return trajets.stream()
                .map(trajetConverter::convertToTrajetDTO)
                .toList();
    }
}

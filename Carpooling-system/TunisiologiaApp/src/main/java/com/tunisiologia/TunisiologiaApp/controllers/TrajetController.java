package com.tunisiologia.TunisiologiaApp.controllers;


import com.tunisiologia.TunisiologiaApp.DTOS.TrajetDto;
import com.tunisiologia.TunisiologiaApp.services.TrajetServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trajets")
public class TrajetController {

    @Autowired
    private TrajetServiceImpl trajetService;

    @GetMapping
    public List<TrajetDto> listTrajets() {
        return trajetService.listTrajets();
    }

    @GetMapping("/{userId}")
    public List<TrajetDto> listTrajetsParUtilisateur(@PathVariable int userId) {
        return trajetService.listTrajetsParUtilisateur(userId);
    }


     /* @GetMapping("/{id}")
    public TrajetDto getTrajetById(@PathVariable int id) {
        return trajetService.getTrajetById(id);
    }
*/
    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<TrajetDto> createTrajet(@Valid @RequestBody TrajetDto trajectoryDTO) {
        TrajetDto trajectory = trajetService.createTrajet(trajectoryDTO);
        return ResponseEntity.ok(trajectory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrajetDto> reserverPlaces(@PathVariable int id, @RequestBody int nombreDePlace) {
        TrajetDto updatedTrajet = trajetService.updateTrajet(id, nombreDePlace);
        if (updatedTrajet != null) {
            return new ResponseEntity<>(updatedTrajet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/evaluer/{id}")
    public ResponseEntity<TrajetDto> evaluerTrajet(@PathVariable int id, @RequestBody int evaluation) {
        TrajetDto updatedTrajet = trajetService.evaluerTrajet(id, evaluation);
        if (updatedTrajet != null) {
            return new ResponseEntity<>(updatedTrajet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajet(@PathVariable int id) {
        trajetService.deleteTrajet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
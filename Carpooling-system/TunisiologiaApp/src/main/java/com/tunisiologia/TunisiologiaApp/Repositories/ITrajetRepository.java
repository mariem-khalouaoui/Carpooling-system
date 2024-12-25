package com.tunisiologia.TunisiologiaApp.Repositories;
import com.tunisiologia.TunisiologiaApp.Entities.Trajet;
import com.tunisiologia.TunisiologiaApp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITrajetRepository  extends JpaRepository<Trajet, Integer>{
    List<Trajet> findByUser(Users user);
}





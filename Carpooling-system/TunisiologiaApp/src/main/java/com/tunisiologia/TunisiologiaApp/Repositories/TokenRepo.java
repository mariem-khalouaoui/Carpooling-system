package com.tunisiologia.TunisiologiaApp.Repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tunisiologia.TunisiologiaApp.models.Token;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer>{

	
    @Query("""
			select t from Token t inner join Users u on t.user.id = u.id
			where t.user.id = :userId and t.loggedOut = false
			""")
	List<Token> findAllTokenByUser(Integer userId);
	
	
	Optional<Token> findByAccessToken(String token);
	Optional<Token> findByRefreshToken(String token);
	
	
}

package br.com.games.gamesranking.repository;

import br.com.games.gamesranking.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {

    Optional<Player> findByName(String name);

    @Query("select pl from Player pl where pl.name = :name and pl.id <> :id")
    Optional<Player> findByNameAndId(@Param("name") String name, @Param("id") Long id);

    Optional<Player> findById(Long id);

    List<Player> findAllByOrderByVictoriesDesc();

}

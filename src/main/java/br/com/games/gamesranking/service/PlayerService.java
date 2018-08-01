package br.com.games.gamesranking.service;

import br.com.games.gamesranking.exception.ValidationException;
import br.com.games.gamesranking.model.Player;
import br.com.games.gamesranking.util.ObjectUtil;

import java.util.List;

public interface PlayerService {

    Player save(Player player) throws ValidationException;

    Player findPlayerByName(String name);

    List<Player> playersRanking();

    Player findPlayerById(Long id);

    Player returPlayerFinded(Player player);

    void deletePlayer(Player player);

}

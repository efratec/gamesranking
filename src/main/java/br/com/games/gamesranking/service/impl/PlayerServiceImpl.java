package br.com.games.gamesranking.service.impl;

import br.com.games.gamesranking.exception.ValidationException;
import br.com.games.gamesranking.model.Player;
import br.com.games.gamesranking.repository.PlayerRepository;
import br.com.games.gamesranking.service.AbstractService;
import br.com.games.gamesranking.service.PlayerService;
import br.com.games.gamesranking.util.MessageUtil;
import br.com.games.gamesranking.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl extends AbstractService<Player> implements PlayerService {

    @Autowired
    private PlayerRepository repository;

    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Player save(Player player) throws ValidationException {
        validate(player);
        return repository.save(player);
    }

    @Override 
    public Player findPlayerByName(String name) {
        Optional<Player> playerOptional = repository.findByName(name);
        return playerOptional.orElse(null);
    }

    @Override
    protected void validate(Player player) throws ValidationException {
    	validateNullOrEmptyField(player);
        validatePlayerWithSameName(player);
        validateVictoriesGreaterThanMatches(player);
    }

    @Override
    protected void validateNullOrEmptyField(Player player) throws ValidationException {

        if (ObjectUtil.isObjectNull(player)){
            throw new ValidationException(MessageUtil.CHECK_EMPTY_FIELD);
        }

        Object victories = player.getVictories() == null ? "" : player.getVictories();
        Object matches = player.getMatches() == null ? "" : player.getMatches();

        if (!ObjectUtil.verifyNullOrEmptyObjects(player.getName(), victories, matches)) {
            throw new ValidationException(MessageUtil.CHECK_EMPTY_FIELD);
        }
    }

    private void validatePlayerWithSameName(Player player) throws ValidationException {

        Optional<Player> playerOptional = null;

        if (ObjectUtil.isObjectNull(player.getId())) {
            playerOptional = repository.findByName(player.getName());

            if (playerOptional.isPresent()){
                throw new ValidationException(MessageUtil.NAME_ALREADY_IN_USE);
            }

        }else{
            playerOptional = repository.findByNameAndId(player.getName(), player.getId());

            if (playerOptional.isPresent()){
                throw new ValidationException(MessageUtil.NAME_ALREADY_IN_USE);
            }
        }
    }

    private void validateVictoriesGreaterThanMatches(Player player) throws ValidationException{
         if (player.getVictories() > player.getMatches()) {
             throw new ValidationException(MessageUtil.VICTORIES_GREATER_THAN_MATCHES);
         }
    }

    @Override
    public List<Player> playersRanking() {
        return repository.findAllByOrderByVictoriesDesc();
    }

    @Override
    public Player findPlayerById(Long id) {
        Optional<Player> player = repository.findById(id);
        return player.orElse(null);
    }

    @Override
    public Player returPlayerFinded(Player player) {

        Player findedPlayer =  findPlayerByName(player.getName());

        if (!ObjectUtil.isObjectNull(findedPlayer)){

            if (!findedPlayer.getVictories().equals(player.getVictories())){
                findedPlayer.setVictories(player.getVictories());
            }

            if (!findedPlayer.getMatches().equals(player.getMatches())){
                findedPlayer.setMatches(player.getMatches());
            }
        }

        return findedPlayer == null ? player : findedPlayer;
    }

    @Override
    public void deletePlayer(Player player){
        repository.delete(repository.findById(player.getId()).get());
    }

}

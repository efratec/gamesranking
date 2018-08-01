package br.com.games.gamesranking.resource;

import br.com.games.gamesranking.exception.ValidationException;
import br.com.games.gamesranking.model.Error;
import br.com.games.gamesranking.model.Player;
import br.com.games.gamesranking.service.PlayerService;
import br.com.games.gamesranking.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerResource {

    @Autowired
    private PlayerService service;

    @GetMapping("/{name}")
    public ResponseEntity<Player> findPlayerByName(@PathVariable("name") String name) {

        Player findedPlayer = service.findPlayerByName(name);

        return new ResponseEntity<>(findedPlayer, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Player>> listPlayersRanking(){
        List<Player> rankingList = service.playersRanking();
        return new ResponseEntity<>(rankingList,HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Player> save(@RequestBody Player player, HttpServletResponse response) throws ValidationException {

        final Player newPlayer = service.save(player);

        adcionaHeaderLocation(player,response);

        return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Player> update(@RequestBody Player player, HttpServletResponse response) throws ValidationException {

        Player findedPlayer = service.returPlayerFinded(player);

        final Player updatedPlayer = service.save(findedPlayer);

        adcionaHeaderLocation(player,response);

        return new ResponseEntity<>(updatedPlayer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deletePlayer(service.findPlayerById(id));
    }

    private void adcionaHeaderLocation(Player player, HttpServletResponse response){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(player.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Error> handleValidationException(ValidationException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}

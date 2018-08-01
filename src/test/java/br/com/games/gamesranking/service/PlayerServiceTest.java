package br.com.games.gamesranking.service;

import br.com.games.gamesranking.exception.ValidationException;
import br.com.games.gamesranking.model.Player;
import br.com.games.gamesranking.repository.PlayerRepository;
import br.com.games.gamesranking.service.impl.PlayerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PlayerServiceTest {

	private final static String PLAYER_NAME = "Iago";
	private final static Integer VICTORIES = 10;
	private final static Integer MATCHES = 20;

	@MockBean
	private PlayerRepository repository;

	private PlayerService service;

	private Player player;

	@Before
	public void setUp() throws Exception {
		service = new PlayerServiceImpl(repository);

		player = new Player();
		player.setId(1L);
		player.setName(PLAYER_NAME);
		player.setMatches(MATCHES);
		player.setVictories(VICTORIES);

		when(repository.findByName(PLAYER_NAME)).thenReturn(Optional.empty());
	}

	@Test
	public void mustSavePlayerInRepository() throws ValidationException {
		Player newPlayer = new Player();
		newPlayer.setName("Efraim");
		newPlayer.setVictories(20);
		newPlayer.setMatches(20);

		service.save(newPlayer);

		verify(repository).save(newPlayer);
	}

	@Test(expected = ValidationException.class)
	public void notMustSavePlayerWithFieldsNull() throws Exception {
		Player newPlayer = null;
		service.save(newPlayer);
	}
	//naoDeveSalvarJogadorComCamposVazio
	@Test(expected = ValidationException.class)
	public void notMustSavePlayerWithFieldsEmpty() throws Exception {
		Player novoJogador = new Player();
		novoJogador.setName("");
		service.save(novoJogador);
	}

	@Test(expected = ValidationException.class)
	public void notMustSavePlayerWithSameField() throws Exception {
		Player novoJogador = new Player();
		novoJogador.setName("Iago");
		service.save(novoJogador);
	}

	@Test(expected = ValidationException.class)
	public void notMustUpdatePlayerWithSameName() throws Exception {
		when(repository.findByName(PLAYER_NAME)).thenReturn(Optional.of(player));
		service.save(player);
	}

	@Test(expected = ValidationException.class)
	public void notMustSavePlayerWithMoreVictoryThanMatch() throws Exception {
		Player newPlayer = new Player();
		newPlayer.setName("Sandrao");
		newPlayer.setVictories(20);
		newPlayer.setMatches(10);

		service.save(newPlayer);
	}

	@Test(expected = ValidationException.class)
	public void notMustUpdatePlayerWithMoreVictoryThanMatch() throws Exception {
		when(repository.findByNameAndId(PLAYER_NAME, 1L)).thenReturn(Optional.of(player));
		player.setId(1L);
		player.setName(PLAYER_NAME);
		player.setVictories(10);
		player.setMatches(30);

		service.save(player);
	}

}

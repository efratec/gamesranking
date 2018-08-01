package br.com.games.gamesranking.repository;

import br.com.games.gamesranking.model.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void mustSearchForPlayerByName() throws  Exception {
        Optional<Player> optional = playerRepository.findByName("Iago");

        Player player = optional.get();

        assertThat(player.getId()).isEqualTo(1L);
        assertThat(player.getName()).isEqualTo("Iago");
        assertThat(player.getVictories()).isEqualTo(10);
        assertThat(player.getMatches()).isEqualTo(20);
    }

    @Test
    public void mustListAllPlayersSortedByVictories() throws Exception {
        List<Player> ranking = playerRepository.findAllByOrderByVictoriesDesc();
        ranking.forEach(p -> System.out.println("Jogador " + p.getName() + " - Vitória = " + p.getVictories()));
        assertThat(ranking.size()).isEqualTo(3);
    }

}

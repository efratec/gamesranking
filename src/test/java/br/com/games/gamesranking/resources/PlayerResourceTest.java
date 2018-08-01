package br.com.games.gamesranking.resources;

import br.com.games.gamesranking.GameRankingApplicationTests;
import br.com.games.gamesranking.model.Player;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlayerResourceTest extends GameRankingApplicationTests {

    @Test
    public void mustSearchPlayerByName () throws Exception {
        given()
                .pathParam("name", "Iago")
                .get("/players/{name}")
                .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Iago"));
    }

    @Test
    public void mustListAllPlayerSortedByVictories() throws Exception {
        given()
                .get("/players")
                .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void mustSavePlayer() throws Exception {

        Player player = new Player();
        player.setName("Fabricio");
        player.setMatches(20);
        player.setVictories(10);

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(player)
                .when()
                .post("/players/save")
                .then()
                .log().headers()
                .and()
                .log().body()
                .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:"+port+"/players/save/4"))
                .body("name", equalTo("Fabricio"),
                        "victories", equalTo(10),
                        "matches", equalTo(20));
    }

    @Test
    public void notMustSavePlayerWithMoreVictoryThanMatch() throws Exception {

        Player player = new Player();
        player.setName("aaaa");
        player.setMatches(20);
        player.setVictories(30);

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(player)
                .when()
                .post("/players/save")
                .then()
                .log().body()
                .and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("error", equalTo("Quantidade de Vitórias não pode ultrapassar Quantidades de Partidas!"));
    }

    @Test
    public void notMustSavePlayerWithSameName() throws Exception {

        Player player = new Player();
        player.setName("Iago");
        player.setMatches(20);
        player.setVictories(30);

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(player)
                .when()
                .post("/players/save")
                .then()
                .log().headers()
                .and()
                .log().body()
                .and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("error", equalTo("Já existe jogador com esse nome!"));
    }

    @Test
    public void mustDeletePlayer() throws Exception {

        Player player = new Player();
        player.setName("Iago");

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(player)
                .when()
                .delete("/players/1")
                .then()
                .log().headers()
                .and()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

}

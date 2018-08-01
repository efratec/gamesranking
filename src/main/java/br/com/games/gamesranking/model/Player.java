package br.com.games.gamesranking.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_player")
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private Integer victories;
	@Column
	private Integer matches;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVictories() {
		return victories;
	}

	public void setVictories(Integer victories) {
		this.victories = victories;
	}

	public Integer getMatches() {
		return matches;
	}

	public void setMatches(Integer matches) {
		this.matches = matches;
	}

}
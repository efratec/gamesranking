package br.com.games.gamesranking.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public  ValidationException(String message) {
        super(message);
    }

}
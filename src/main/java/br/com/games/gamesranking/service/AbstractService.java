package br.com.games.gamesranking.service;

public abstract class AbstractService<E> {

    protected abstract void validate(E entity) throws  Exception;

    protected abstract void validateNullOrEmptyField(E entity)  throws  Exception;

}

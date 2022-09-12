package by.kharchenko.restcafe.model.service;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends AbstractEntity> {
    boolean delete(T t) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    boolean add(T userData) throws ServiceException;

    Optional<T> findById(Long id) throws ServiceException;

    List<T> findAll() throws ServiceException;

    boolean update(T t) throws ServiceException;

    Long count() throws ServiceException;
}

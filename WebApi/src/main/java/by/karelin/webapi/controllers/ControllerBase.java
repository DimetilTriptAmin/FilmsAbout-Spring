package by.karelin.webapi.controllers;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;


public class ControllerBase<T> {
    /*
    private IBaseRepository<T> baseRepository;

    public ControllerBase (IBaseRepository<T> baseRepository){
        this.baseRepository = baseRepository;
    }

    public List<T> FindAll() {
        return baseRepository.findAll();
    }

    public List<T> FindAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    public List<T> FindAll(Example<T> example) {
        return baseRepository.findAll(example);
    }

    public List<T> FindAll(Example<T> example, Sort sort) {
        return baseRepository.findAll(example, sort);
    }

    public Long Count() {
        return baseRepository.count();
    }

    public Long Count(Example<T> example) {
        return baseRepository.count(example);
    }
     */
}

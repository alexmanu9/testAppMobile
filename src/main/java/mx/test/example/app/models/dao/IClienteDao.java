package mx.test.example.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import mx.test.example.app.models.entity.Organizaciones;


public interface IClienteDao extends CrudRepository<Organizaciones, Long> {

}

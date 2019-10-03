package mx.test.example.app.service;

import java.util.List;

import mx.test.example.app.models.entity.Organizaciones;


public interface IClienteService {

	public List<Organizaciones> findAll();

	public void save(Organizaciones cliente);
	
	public Organizaciones findOne(Long id);
	
	public void delete(Long id);
	
	public String generaAleatorioConsecutivo();
	
	public String codigoEncriptacion(String texto);
	
	public String generaIdExterno(String nombre, String telefono);

}

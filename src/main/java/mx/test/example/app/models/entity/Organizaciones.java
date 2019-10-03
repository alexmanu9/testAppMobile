package mx.test.example.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Entity
@Table(name = "organizaciones")
@Data
public class Organizaciones implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String direccion;
	
	@NotEmpty
	private String telefono;
	
	@NotEmpty
	private String id_externo;
	
	@NotEmpty
	private String codigo_encriptacion;
	

	
}

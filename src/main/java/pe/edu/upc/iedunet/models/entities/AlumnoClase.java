package pe.edu.upc.iedunet.models.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alumno_clase")
public class AlumnoClase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "alumno_id")
	private Alumno alumno;
	
	@ManyToOne
	@JoinColumn(name = "clase_id")
	private Clase clase;
	
	@Column(name = "promedio", nullable = true)
	private float promedio;
	
	@Column(name = "inasistencias", nullable = true)
	private Integer inasistencias;


	public AlumnoClase() {}
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Alumno getIdAlumno() {
		return alumno;
	}

	public void setIdAlumno(Alumno idAlumno) {
		this.alumno = idAlumno;
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public float getPromedio() {
		return promedio;
	}

	public void setPromedio(float promedio) {
		this.promedio = promedio;
	}

	public Integer getInasistencias() {
		return inasistencias;
	}

	public void setInasistencias(Integer inasistencias) {
		this.inasistencias = inasistencias;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	

}
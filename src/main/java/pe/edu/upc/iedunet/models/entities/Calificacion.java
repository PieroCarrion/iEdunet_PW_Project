package pe.edu.upc.iedunet.models.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "calificaciones")
public class Calificacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	@ManyToOne
	@JoinColumn(name = "actividad_academica_id")
	private ActividadAcademica actividadAcademica;
	
	@Column(name = "nota", nullable = true)
	private float nota;
	
	@Column(name = "fecha_revision", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaRevision;
	
	@Column(name = "fecha_entrega", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaEntrega;
	
	@ManyToOne
	@JoinColumn(name = "alumno_id")
	private Alumno alumno;
	
	@Column(name = "comentario", length = 100, nullable = true)
	private String comentario;

	public Calificacion() {	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumnos(Alumno alumno) {
		this.alumno = alumno;
	}

	public ActividadAcademica getActividadAcademica() {
		return actividadAcademica;
	}

	public void setActividadAcademica(ActividadAcademica actividadAcademica) {
		this.actividadAcademica = actividadAcademica;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

	public Date getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}



}
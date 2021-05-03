package pe.edu.upc.iedunet.models.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clases")
public class Clase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "link_to_class", length = 2000, nullable = true)
	private String linktoClass;
	
	
	@Column(name = "max_inasistencias", nullable = true)
	private Integer maxInasistencias;
	
	@ManyToOne
	@JoinColumn(name="curso_id")
	private Curso curso;
	
	@ManyToOne
	@JoinColumn(name="profesor_id")
	private Profesor profesor;
	
	@ManyToOne
	@JoinColumn(name="periodo_id")
	private Periodo periodo;
	
	@Column(name = "grado", length = 15, nullable = true)
	private String grado;
	
	@OneToMany(mappedBy = "clase")
	private List<Comunicado> comunicados;
	
	@OneToMany(mappedBy = "clase")
	private List<Horario> horarios;
	
	@OneToMany(mappedBy = "clase")
	private List<Recurso> recursos;
	
	@OneToMany(mappedBy = "clase")
	private List<ActividadAcademica> actividadesAcademicas;
	
	@OneToMany(mappedBy = "clase")
	private List<AlumnoClase> alumnosClases;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLinktoClass() {
		return linktoClass;
	}

	public void setLinktoClass(String linktoClass) {
		this.linktoClass = linktoClass;
	}

	public Integer getMaxInasistencias() {
		return maxInasistencias;
	}

	public void setMaxInasistencias(Integer maxInasistencias) {
		this.maxInasistencias = maxInasistencias;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public List<Comunicado> getComunicados() {
		return comunicados;
	}

	public void setComunicados(List<Comunicado> comunicados) {
		this.comunicados = comunicados;
	}

	public List<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}
	
	
	
}

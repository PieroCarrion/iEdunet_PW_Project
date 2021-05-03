package pe.edu.upc.iedunet.models.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "periodos")
public class Periodo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "descripcion", length = 30, nullable = true)
	private String descripcion;
	
	@Column(name = "fecha_inicio", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Column(name = "fecha_fin", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaFin;
	
	@OneToMany(mappedBy = "periodo")
	private List<Matricula> matricula;
	
	@OneToMany(mappedBy = "periodo")
	private List<Clase> clases;
	
	@ManyToOne
	@JoinColumn(name = "colegio_id")
	private Colegio colegio;

	public Periodo() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<Matricula> getMatricula() {
		return matricula;
	}

	public void setMatricula(List<Matricula> matriculas) {
		this.matricula = matriculas;
	}

	public List<Clase> getClases() {
		return clases;
	}

	public void setClases(List<Clase> clases) {
		this.clases = clases;
	}

	public Colegio getColegio() {
		return colegio;
	}

	public void setColegio(Colegio colegio) {
		this.colegio = colegio;
	}
	
	
}

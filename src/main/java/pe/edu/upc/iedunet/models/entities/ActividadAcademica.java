package pe.edu.upc.iedunet.models.entities;

import java.util.Date;
import java.util.List;

import javax.mail.search.DateTerm;
import javax.mail.search.IntegerComparisonTerm;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "actividad_academicas")
public class ActividadAcademica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "fecha_publicacion", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaPublicacion;
	
	@Column(name = "fecha_maxentrega", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaMaxEntrega;
	
	@ManyToOne
	@JoinColumn(name = "clase_id")
	private Clase clase;
	
	@OneToMany(mappedBy = "actividadAcademica")
	private List<Calificacion> calificaciones;
	
	@Column(name = "descripcion", length = 500, nullable = false)
	private String descripcion;
	
	
	public ActividadAcademica() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Date getFechaMaxEntrega() {
		return fechaMaxEntrega;
	}

	public void setFechaMaxEntrega(Date fechaMaxEntrega) {
		this.fechaMaxEntrega = fechaMaxEntrega;
	}

	public List<Calificacion> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}
	
}

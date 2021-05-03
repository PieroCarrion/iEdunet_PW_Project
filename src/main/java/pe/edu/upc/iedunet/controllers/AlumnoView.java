package pe.edu.upc.iedunet.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Colegio;
import pe.edu.upc.iedunet.models.entities.Usuario;
import pe.edu.upc.iedunet.services.AlumnoService;
import pe.edu.upc.iedunet.services.ColegioService;
import pe.edu.upc.iedunet.utils.Action;

@Named("alumnoView")
@ViewScoped
public class AlumnoView implements Serializable {

	private static final long serialVersionUID = 1L;

	// Lista de alumnos que se muestra en el table
	private List<Alumno> alumnos;

	// Alumno asociado al Formulario para crear o editar
	private Alumno alumno;

	// Alumno asociado al Row Select del Table
	private Alumno alumnoSelected;

	/////// REGION VARIABLES AUXILIARES///////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	private int idColegio;

	private List<Colegio> colegios;
	
	private Colegio colegioSelected;
	/////// END REGION VARIABLES AUXILIARES/////////
	/*---------------------------------------------*/

	/////// REGION VARIABLES DE ESTADO O ESTILOS///////
	////////////////////////////////////////////////
	///////////////////////////////////////////////
	private Action action;

	// Disables for commandButton
	private boolean disabledButtonNuevo;
	private boolean disabledButtonGrabar;
	private boolean disabledButtonCancelar;
	private boolean disabledButtonEditar;
	private boolean disabledButtonEliminar;

	// Text in Confirm Dialog
	private String messageConfirmDialog;

	// Style for panelGrid and dataTable
	private String stylePanelAlumno;
	private String styleTableAlumno;

	/////// END REGION VARIABLES DE ESTADO O ESTILOS/////////
	/*-----------------------------------------------------*/

	@Inject
	private AlumnoService alumnoService;

	@Inject
	private ColegioService colegioService;

	@PostConstruct
	public void init() {
		this.cleanForm();
		this.loadAlumnos();
		this.loadColegios();
		this.action = Action.NONE;
		this.stateList();
	}

	////////// REGION METODOS CRUD /////////////////////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	public void loadAlumnos() {
		try {
			this.alumnos = alumnoService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}

	// Metodo cuando se hace click en el boton Nuevo
	public void newAlumno() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
	}

	public void saveAlumno() {
		boolean uniqueCodigoAlumno = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				/*
				 * Optional<Alumno> optional =
				 * alumnoService.findByCodigoUsuario(alumno.getUsuario().getCodigoUsuario()); if
				 * (optional.isPresent()) { uniqueCodigoAlumno = false; }
				 */
				if (uniqueCodigoAlumno == true) {
					// alumno.getUsuario().setColegio(colegioService.findById(idColegio).get());

					changeColegio();

					if (this.action == Action.NEW) {
						alumno.getUsuario().setFechaRegistro(new Date());
						alumnoService.save(this.alumno);
						this.addMessage("SE ESTA GUARDANDO " + alumno.getUsuario().getFechaNacimiento());
					} else {
						alumnoService.update(this.alumno);

						this.addMessage("SE ESTA UPDATING " + alumno.getUsuario().getFechaNacimiento());
					}

					cleanForm();
					loadAlumnos();
					this.action = Action.NONE;
					this.stateList();
				} else {
					this.addMessage("El número de documento: " + alumno.getUsuario().getCodigoUsuario() + " ya existe");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void editAlumno() {
		if (this.alumnoSelected != null) {
			this.alumno = this.alumnoSelected;
			this.alumno.setUsuario(this.alumnoSelected.getUsuario());
			if (this.alumno.getUsuario().getColegio() != null) {
				this.idColegio = this.alumno.getUsuario().getColegio().getId();
			}
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteAlumno() {
		if (this.alumnoSelected != null) {
			try {
				String x = alumnoSelected.getUsuario().getCodigoUsuario() + '-' + alumnoSelected.getId();
				alumnoService.deleteById(this.alumnoSelected.getId());
				cleanForm();
				loadAlumnos();
				this.action = Action.NONE;
				this.stateList();
				System.out.println("Eliminado " + x);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelAlumno() {
		cleanForm();
		this.stateList();
	}

	// Se cargan los colegios para el Combobox
	public void loadColegios() {
		try {
			this.colegios = colegioService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}

	public void loadColegio(Integer id) {
		try {
			this.colegioSelected = (colegioService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void changeColegio() {
		if (this.alumno.getUsuario().getColegio() != null) {
			if (!this.alumno.getUsuario().getColegio().getId().equals(this.idColegio)) {
				loadColegio(this.idColegio);
				this.alumno.getUsuario().setColegio(this.colegioSelected);
			}
		} // Cuando es un nuevo Cliente
		else {
			loadColegio(this.idColegio);
			this.alumno.getUsuario().setColegio(this.colegioSelected);
		}
	}
	////////// END REGION METODOS CRUD//////////////////////
	/*-----------------------------------------------------*/

	////////// REGION METODOS PARA MANEJO DE UI Y EVENTOS///////
	////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	public void cleanForm() {
		this.alumno = new Alumno();
		alumno.setUsuario(new Usuario());
		this.alumnoSelected = null;
	}

	// Metodo que se ejecuta cuando el evento rowSelect ocurra
	public void selectAlumno(SelectEvent<Alumno> e) {
		this.alumnoSelected = e.getObject();
		this.messageConfirmDialog = this.alumnoSelected.getUsuario().getCodigoUsuario();
		this.stateSelectRow();
	}

	// Metodo que se ejecuta cuando el evento rowUnselect ocurra
	public void unselectAlumno(UnselectEvent<Alumno> e) {
		this.alumnoSelected = null;
		this.stateList();
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	////////// END REGION METODOS PARA MANEJO DE UI Y EVENTOS////////
	/*------------------------------------------------------------*/

	////////// REGION METODOS PARA CONTROL DE ESTADOS/////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	public void stateList() {
		this.stylePanelAlumno = "display:none;";
		this.styleTableAlumno = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGrabar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelAlumno = "display:block;";
		this.styleTableAlumno = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGrabar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelAlumno = "display:none;";
		this.styleTableAlumno = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGrabar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = false;
		this.disabledButtonEliminar = false;
	}
	////////// END REGION METODOS PARA CONTROL DE ESTADOS////
	/*-----------------------------------------------------*/

	/////////// REGION GETTERS AND SETTERS///////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Alumno getAlumnoSelected() {
		return alumnoSelected;
	}

	public void setAlumnoSelected(Alumno alumnoSelected) {
		this.alumnoSelected = alumnoSelected;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public AlumnoService getAlumnoService() {
		return alumnoService;
	}

	public void setAlumnoService(AlumnoService alumnoService) {
		this.alumnoService = alumnoService;
	}

	public int getIdColegio() {
		return idColegio;
	}

	public void setIdColegio(int idColegio) {
		this.idColegio = idColegio;

		System.out.println(this.idColegio);
	}

	public Action getAction() {
		return action;
	}

	public boolean isDisabledButtonNuevo() {
		return disabledButtonNuevo;
	}

	public boolean isDisabledButtonGrabar() {
		return disabledButtonGrabar;
	}

	public boolean isDisabledButtonCancelar() {
		return disabledButtonCancelar;
	}

	public boolean isDisabledButtonEditar() {
		return disabledButtonEditar;
	}

	public boolean isDisabledButtonEliminar() {
		return disabledButtonEliminar;
	}

	public ColegioService getColegioService() {
		return colegioService;
	}

	public String getMessageConfirmDialog() {
		return messageConfirmDialog;
	}

	public String getStylePanelAlumno() {
		return stylePanelAlumno;
	}

	public String getStyleTableAlumno() {
		return styleTableAlumno;
	}

	public List<Colegio> getColegios() {
		return colegios;
	}
}

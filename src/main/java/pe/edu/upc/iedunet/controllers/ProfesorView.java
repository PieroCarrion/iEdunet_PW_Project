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

import pe.edu.upc.iedunet.models.entities.Colegio;
import pe.edu.upc.iedunet.models.entities.Profesor;
import pe.edu.upc.iedunet.models.entities.Usuario;
import pe.edu.upc.iedunet.services.ColegioService;
import pe.edu.upc.iedunet.services.ProfesorService;
import pe.edu.upc.iedunet.utils.Action;

@Named("profesorView")
@ViewScoped
public class ProfesorView implements Serializable {

	private static final long serialVersionUID = 1L;

	// Lista de profesores que se muestra en el table
	private List<Profesor> profesores;

	// Profesor asociado al Formulario para crear o editar
	private Profesor profesor;

	// Profesor asociado al Row Select del Table
	private Profesor profesorSelected;

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
	private String stylePanelProfesor;
	private String styleTableProfesor;

	/////// END REGION VARIABLES DE ESTADO O ESTILOS/////////
	/*-----------------------------------------------------*/

	@Inject
	private ProfesorService profesorService;

	@Inject
	private ColegioService colegioService;

	@PostConstruct
	public void init() {
		this.cleanForm();
		this.loadProfesores();
		this.loadColegios();
		this.action = Action.NONE;
		this.stateList();
	}

	////////// REGION METODOS CRUD /////////////////////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	public void loadProfesores() {
		try {
			this.profesores = profesorService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}

	// Metodo cuando se hace click en el boton Nuevo
	public void newProfesor() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
	}

	public void saveProfesor() {
		boolean uniqueCodigoProfesor = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				/*
				 * Optional<Profesor> optional =
				 * profesorService.findByCodigoUsuario(profesor.getUsuario().getCodigoUsuario()); if
				 * (optional.isPresent()) { uniqueCodigoProfesor = false; }
				 */
				if (uniqueCodigoProfesor == true) {
					// profesor.getUsuario().setColegio(colegioService.findById(idColegio).get());

					changeColegio();

					if (this.action == Action.NEW) {
						profesor.getUsuario().setFechaRegistro(new Date());
						profesorService.save(this.profesor);
						this.addMessage("SE ESTA GUARDANDO " + profesor.getUsuario().getFechaNacimiento());
					} else {
						profesorService.update(this.profesor);

						this.addMessage("SE ESTA UPDATING " + profesor.getUsuario().getFechaNacimiento());
					}

					cleanForm();
					loadProfesores();
					this.action = Action.NONE;
					this.stateList();
				} else {
					this.addMessage("El número de documento: " + profesor.getUsuario().getCodigoUsuario() + " ya existe");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void editProfesor() {
		if (this.profesorSelected != null) {
			this.profesor = this.profesorSelected;
			this.profesor.setUsuario(this.profesorSelected.getUsuario());
			if (this.profesor.getUsuario().getColegio() != null) {
				this.idColegio = this.profesor.getUsuario().getColegio().getId();
			}
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteProfesor() {
		if (this.profesorSelected != null) {
			try {
				String x = profesorSelected.getUsuario().getCodigoUsuario();
				profesorService.deleteById(this.profesorSelected.getId());
				cleanForm();
				loadProfesores();
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
	public void cancelProfesor() {
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
		if (this.profesor.getUsuario().getColegio() != null) {
			if (!this.profesor.getUsuario().getColegio().getId().equals(this.idColegio)) {
				loadColegio(this.idColegio);
				this.profesor.getUsuario().setColegio(this.colegioSelected);
			}
		} // Cuando es un nuevo Cliente
		else {
			loadColegio(this.idColegio);
			this.profesor.getUsuario().setColegio(this.colegioSelected);
		}
	}
	////////// END REGION METODOS CRUD//////////////////////
	/*-----------------------------------------------------*/

	////////// REGION METODOS PARA MANEJO DE UI Y EVENTOS///////
	////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	public void cleanForm() {
		this.profesor = new Profesor();
		profesor.setUsuario(new Usuario());
		this.profesorSelected = null;
	}

	// Metodo que se ejecuta cuando el evento rowSelect ocurra
	public void selectProfesor(SelectEvent<Profesor> e) {
		this.profesorSelected = e.getObject();
		this.messageConfirmDialog = this.profesorSelected.getUsuario().getCodigoUsuario();
		this.stateSelectRow();
	}

	// Metodo que se ejecuta cuando el evento rowUnselect ocurra
	public void unselectProfesor(UnselectEvent<Profesor> e) {
		this.profesorSelected = null;
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
		this.stylePanelProfesor = "display:none;";
		this.styleTableProfesor = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGrabar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelProfesor = "display:block;";
		this.styleTableProfesor = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGrabar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelProfesor = "display:none;";
		this.styleTableProfesor = "display:block;";
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
	public List<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(List<Profesor> profesores) {
		this.profesores = profesores;
	}

	public Profesor getProfesorSelected() {
		return profesorSelected;
	}

	public void setProfesorSelected(Profesor profesorSelected) {
		this.profesorSelected = profesorSelected;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public ProfesorService getProfesorService() {
		return profesorService;
	}

	public void setProfesorService(ProfesorService profesorService) {
		this.profesorService = profesorService;
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

	public String getStylePanelProfesor() {
		return stylePanelProfesor;
	}

	public String getStyleTableProfesor() {
		return styleTableProfesor;
	}

	public List<Colegio> getColegios() {
		return colegios;
	}

	/////////// END REGION GETTERS AND SETTERS///////////////
	/*-----------------------------------------------------*/

}
package pe.edu.upc.iedunet.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.services.CursoService;
import pe.edu.upc.iedunet.utils.Action;

@Named("cursoView")
@ViewScoped
public class CursoView implements Serializable {

	private static final long serialVersionUID = 1L;

	// Lista de cursos //
	private List<Curso> cursos;

	// Curso asociado al Formulario para crear o editar//
	private Curso curso;

	// Curso asociado al Row Select del Table//
	private Curso cursoSelected;
	/////// REGION VARIABLES AUXILIARES///////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

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
	private String stylePanelCurso;
	private String styleTableCurso;

	/////// END REGION VARIABLES DE ESTADO O ESTILOS/////////
	/*-----------------------------------------------------*/

	@Inject
	private CursoService cursoService;

	@PostConstruct
	public void init() {
		this.cleanForm();
		this.loadCursos();
		this.action = Action.NONE;
		this.stateList();
	}

	////////// REGION METODOS CRUD /////////////////////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	public void loadCursos() {
		try {
			this.cursos = cursoService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}

	// Metodo cuando se hace click en el boton Nuevo
	public void newCurso() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
	}

	public void saveCurso() {
		boolean uniqueCodigoCurso = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				/*
				 * Optional<Alumno> optional =
				 * alumnoService.findByCodigoUsuario(alumno.getUsuario().getCodigoUsuario()); if
				 * (optional.isPresent()) { uniqueCodigoAlumno = false; }
				 */
				if (uniqueCodigoCurso == true) {
					// alumno.getUsuario().setColegio(colegioService.findById(idColegio).get());

					// changeColegio();

					if (this.action == Action.NEW) {
						// curso.getUsuario().setFechaRegistro(new Date());
						cursoService.save(this.curso);
						this.addMessage("SE ESTA GUARDANDO " + curso.getNombre());
					} else {
						cursoService.update(this.curso);

						this.addMessage("SE ESTA UPDATING " + curso.getNombre());
					}

					cleanForm();
					loadCursos();
					this.action = Action.NONE;
					this.stateList();
				} else {
					this.addMessage("El número de documento: " + curso.getId() + curso.getNombre() + " ya existe");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void editCurso() {
		if (this.cursoSelected != null) {
			this.curso = this.cursoSelected;
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	public void deleteCurso() {
		try {
			cursoService.deleteById(cursoSelected.getId());
			System.out.println(cursoSelected.getNombre());
			loadCursos();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void cancelCurso() {
		cleanForm();
		this.stateList();
	}

	////// END METODOS CRUD//////////////////////
	/*-----------------------------------------------------*/

	//////////REGION METODOS PARA MANEJO DE UI Y EVENTOS///////
	////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////

	public void cleanForm() {
		this.curso = new Curso();
		this.cursoSelected = null;
	}

	public void selectCurso(SelectEvent<Curso> e) {
		this.cursoSelected = e.getObject();
		this.messageConfirmDialog = this.cursoSelected.getNombre();
		System.out.println(cursoSelected.getNombre() + ' ' + cursoSelected.getNombre());
		this.stateSelectRow();
	}

	public void unselectCurso(UnselectEvent<Curso> e) {
		this.cursoSelected = null;
		this.stateList();
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	///// END DE METODOS DE MANEJO DE UI Y EVENTOS////////

	////// METODOS PARA CONTROL DE ESTADOS/////////
	public void stateList() {
		this.stylePanelCurso = "display:none;";
		this.styleTableCurso = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGrabar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelCurso = "display:block;";
		this.styleTableCurso = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGrabar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelCurso = "display:none;";
		this.styleTableCurso = "display:block;";
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
	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Curso getCursoSelected() {
		return cursoSelected;
	}

	public void setCursoSelected(Curso cursoSelected) {
		this.cursoSelected = cursoSelected;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public boolean isDisabledButtonNuevo() {
		return disabledButtonNuevo;
	}

	public void setDisabledButtonNuevo(boolean disabledButtonNuevo) {
		this.disabledButtonNuevo = disabledButtonNuevo;
	}

	public boolean isDisabledButtonGrabar() {
		return disabledButtonGrabar;
	}

	public void setDisabledButtonGrabar(boolean disabledButtonGrabar) {
		this.disabledButtonGrabar = disabledButtonGrabar;
	}

	public boolean isDisabledButtonCancelar() {
		return disabledButtonCancelar;
	}

	public void setDisabledButtonCancelar(boolean disabledButtonCancelar) {
		this.disabledButtonCancelar = disabledButtonCancelar;
	}

	public boolean isDisabledButtonEditar() {
		return disabledButtonEditar;
	}

	public void setDisabledButtonEditar(boolean disabledButtonEditar) {
		this.disabledButtonEditar = disabledButtonEditar;
	}

	public boolean isDisabledButtonEliminar() {
		return disabledButtonEliminar;
	}

	public void setDisabledButtonEliminar(boolean disabledButtonEliminar) {
		this.disabledButtonEliminar = disabledButtonEliminar;
	}

	public String getMessageConfirmDialog() {
		return messageConfirmDialog;
	}

	public String getStylePanelCurso() {
		return stylePanelCurso;
	}

	public String getStyleTableCurso() {
		return styleTableCurso;
	}

	/////////// END GETTERS AND SETTERS///////////////
	/*-----------------------------------------------------*/

}

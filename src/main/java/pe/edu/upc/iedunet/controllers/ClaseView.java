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

import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Colegio;
import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.models.entities.Profesor;
import pe.edu.upc.iedunet.models.entities.Usuario;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.services.ColegioService;
import pe.edu.upc.iedunet.services.CursoService;
import pe.edu.upc.iedunet.services.PeriodoService;
import pe.edu.upc.iedunet.services.ProfesorService;
import pe.edu.upc.iedunet.utils.Action;
import pe.edu.upc.iedunet.utils.GradosAcademicos;

@Named("claseView")
@ViewScoped
public class ClaseView implements Serializable {

	private static final long serialVersionUID = 1L;

	// Clase asociada al Formulario para crear o editar
	private Clase clase;
	
	// Lista de colegio que se muestra en el table colegioSelected
	private List<Colegio> colegios;

	// Colegio asociado al Row Select del Table colegioSelected
	private Colegio colegioSelected;
	
	// Lista de periodos del colegio que se muestra en el listbox periodoSelected
	private List<Periodo> periodos;

	// Colegio asociado al Row Select del listbox colegioSelected
	private Periodo periodoSelected;
	
	// Lista de cursos que se muestra en el listbox cursoSelected
	private List<Curso> cursos;

	// Curso asociado al Row Select del listbox colegioSelected
	private Curso cursoSelected;
	
	// Lista de profesores del colegio que se muestra en el listbox profesorSelected
	private List<Profesor> profesores;

	// Profesor asociado al Row Select del listbox profesorSelected
	private Profesor profesorSelected;
	
	//Lista de String con los Grados A.
	private List<String> gradosAcademicos = new GradosAcademicos().listaGrados;
	private String gradoSelected;

	/////// REGION VARIABLES AUXILIARES///////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	private int idColegio;
	
	private int idPeriodo;
	
	private int idCurso;
	
	private int idProfesor;


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
	private String stylePanelClase;
	private String styleTableClase;

	/////// END REGION VARIABLES DE ESTADO O ESTILOS/////////
	/*-----------------------------------------------------*/

	@Inject
	private ClaseService claseService;

	@Inject
	private ColegioService colegioService;

	@Inject
	private PeriodoService periodoService;
	
	@Inject
	private CursoService cursoService;
	
	@Inject
	private ProfesorService profesorService;
	
	@PostConstruct
	public void init() {
		this.cleanForm();
		this.loadColegios();
		//this.loadPeriodosByColegio();
		this.loadCursos();
		//this.loadProfesores();
		this.action = Action.NONE;
		this.stateList();
	}

	////////// REGION METODOS CRUD /////////////////////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////

	// Metodo cuando se hace click en el boton Nuevo
	public void newClase() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
	}

	public void saveClase() {
		boolean uniqueCodigoClase = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				/*
				 * Optional<Clase> optional =
				 * claseService.findByCodigoUsuario(clase.getUsuario().getCodigoUsuario()); if
				 * (optional.isPresent()) { uniqueCodigoClase = false; }
				 */
				if (uniqueCodigoClase == true) {
					// clase.getUsuario().setColegio(colegioService.findById(idColegio).get());

					/*changeColegio();
					changePeriodo();
					changeCurso();
					changeProfesor();

					if (this.action == Action.NEW) {
						claseService.save(this.clase);
						this.addMessage("SE ESTA GUARDANDO LA CLASE " + clase.getId() + "PARA EL COLEGIO " + colegioSelected.getCodigo());
					} else {
						claseService.update(this.clase);

						this.addMessage("SE ESTA UPDATING LA CLASE" + clase.getId() + "PARA EL COLEGIO " + colegioSelected.getCodigo());
					}

					cleanForm();
					loadColegios();
					this.action = Action.NONE;
					this.stateList();*/
				} else {
					//this.addMessage("La clase con: " + clase.getId() + " ya existe");
				}
				
				//REMOVE
				changePeriodo();
				changeColegio();
				changeCurso();
				changeProfesor();
				changeGrado();
				
				/*
				this.addMessage("Periodo: " + clase.getPeriodo().getDescripcion() + "--" + "Curso: " + clase.getCurso().getNombre() + "--" +"Grado: " + clase.getGrado()+ "--" + "Profesor: " + clase.getProfesor().getUsuario().getCodigoUsuario());
				System.out.println("Periodo: " + clase.getPeriodo().getDescripcion() + "--" + "Curso: " + clase.getCurso().getNombre() + "--" +"Grado: " + clase.getGrado()+ "--" + "Profesor: " + clase.getProfesor().getUsuario().getCodigoUsuario());
				//*/
				
				///*
				this.addMessage("Periodo: " + periodoSelected.getDescripcion() + "--" + "Curso: " + cursoSelected.getNombre() + "--" +"Grado: " + gradoSelected + "--" + "Profesor: " + profesorSelected.getUsuario().getCodigoUsuario());
				System.out.println("Periodo: " + periodoSelected.getDescripcion() + "--" + "Curso: " + cursoSelected.getNombre() + "--" +"Grado: " + gradoSelected + "--" + "Profesor: " + profesorSelected.getUsuario().getCodigoUsuario());
				//*/
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
			
		}
	}

	public void editClase() {
		/*
		if (this.claseSelected != null) {
			this.clase = this.claseSelected;
			this.clase.setUsuario(this.claseSelected.getUsuario());
			if (this.clase.getUsuario().getColegio() != null) {
				this.idColegio = this.clase.getUsuario().getColegio().getId();
			}
			this.action = Action.EDIT;
			this.stateNewEdit();
		}*/
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteClase() {
		/*
		if (this.claseSelected != null) {
			try {
				String x = claseSelected.getUsuario().getCodigoUsuario() + '-' + claseSelected.getId();
				claseService.deleteById(this.claseSelected.getId());
				cleanForm();
				loadClases();
				this.action = Action.NONE;
				this.stateList();
				System.out.println("Eliminado " + x);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}*/
	}

	// Método cuando se hace click en el boton Cancelar
	public void cancelClase() {
		cleanForm();
		this.stateList();
	}

	// Se cargan los colegios para el Table
	public void loadColegios() {
		try {
			this.colegios = colegioService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}
	
	//Se cargan los periodos para combobox
	public void loadPeriodosByColegio() {
		try {
			this.periodos = periodoService.findByColegio(colegioSelected.getId());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}
	
	// Se cargan los cursos para el Combobox
	public void loadCursos() {
		try {
			this.cursos = cursoService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}
	
	//Se cargan los profesores para combobox
	public void loadProfesoresByColegio() {
		try {
			this.profesores = profesorService.findByColegio(colegioSelected.getId());
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
	
	public void loadPeriodo(Integer id) {
		try {
			this.periodoSelected = (periodoService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void loadCurso(Integer id) {
		try {
			this.cursoSelected = (cursoService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void loadProfesor(Integer id) {
		try {
			this.profesorSelected = (profesorService.findById(id)).get();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void changeColegio() {
		/*
		if (this.clase.getPeriodo()!= null && this.clase.getPeriodo().getColegio() != null) {
			if (!this.clase.getPeriodo().getColegio().getId().equals(this.idColegio)) {
				loadColegio(this.idColegio);
				this.clase.getPeriodo().setColegio(this.colegioSelected);
			}
		}  // Cuando es una nueva Clase
		else {
			loadColegio(this.idColegio);
			this.clase.getPeriodo().setColegio(this.colegioSelected);
		}*/
		loadColegio(this.idColegio);
		this.clase.getPeriodo().setColegio(this.colegioSelected);
	}
	
	public void changePeriodo() {
		/*if (this.clase.getPeriodo() != null) {
			if (!this.clase.getPeriodo().getId().equals(this.idPeriodo)) {
				loadPeriodo(this.idPeriodo);
				this.clase.setPeriodo(this.periodoSelected);
			}
		} // Cuando es una nueva Clase
		else {
			loadPeriodo(this.idPeriodo);
			this.clase.setPeriodo(this.periodoSelected);
		}*/
		loadPeriodo(this.idPeriodo);
		this.clase.setPeriodo(this.periodoSelected);
	}
	
	public void changeCurso() {
		/*
		if (this.clase.getCurso() != null) {
			if (!this.clase.getCurso().getId().equals(this.idCurso)) {
				loadCurso(this.idCurso);
				this.clase.setCurso(this.cursoSelected);
			}
		} // Cuando es una nueva Clase
		else {
			loadCurso(this.idCurso);
			this.clase.setCurso(this.cursoSelected);
		}*/
		loadCurso(this.idCurso);
		this.clase.setCurso(this.cursoSelected);
	}
	
	public void changeProfesor() {
		/*
		if (this.clase.getProfesor() != null) {
			if (!this.clase.getProfesor().getId().equals(this.idCurso)) {
				loadProfesor(this.idCurso);
				this.clase.setProfesor(this.profesorSelected);
			}
		} // Cuando es una nueva Clase
		else {
			loadProfesor(this.idCurso);
			this.clase.setProfesor(this.profesorSelected);
		}*/
		loadProfesor(this.idCurso);
		this.clase.setProfesor(this.profesorSelected);
	}
	
	public void changeGrado() {
		this.clase.setGrado(this.gradoSelected);
	}
	////////// END REGION METODOS CRUD//////////////////////
	/*-----------------------------------------------------*/

	////////// REGION METODOS PARA MANEJO DE UI Y EVENTOS///////
	////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	public void cleanForm() {
		this.clase = new Clase();
		clase.setCurso(new Curso());
		clase.setPeriodo(new Periodo());
		clase.setProfesor(new Profesor());
		//clase.getProfesor().setUsuario(new Usuario());
	}

	// Metodo que se ejecuta cuando el evento rowSelect de tableColegio ocurra
	public void selectColegio(SelectEvent<Colegio> e) {
		this.colegioSelected = e.getObject();
		loadPeriodosByColegio();
		loadProfesoresByColegio();
		this.messageConfirmDialog = this.colegioSelected.getCodigo();
		this.stateSelectRow();
	}

	// Metodo que se ejecuta cuando el evento rowUnselect ocurra
	public void unselectColegio(UnselectEvent<Colegio> e) {
		this.colegioSelected = null;
		this.periodos = null;
		this.profesores=null;
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
		this.stylePanelClase = "display:none;";
		this.styleTableClase = "display:block;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGrabar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelClase = "display:block;";
		this.styleTableClase = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGrabar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelClase = "display:none;";
		this.styleTableClase = "display:block;";
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
	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public ClaseService getClaseService() {
		return claseService;
	}

	public void setClaseService(ClaseService claseService) {
		this.claseService = claseService;
	}

	public int getIdColegio() {
		return idColegio;
	}

	public void setIdColegio(int idColegio) {
		this.idColegio = idColegio;

		System.out.println(this.idColegio);
	}

	/*
	public void setColegios(List<Colegio> colegios) {
		this.colegios = colegios;
	}*/

	public void setColegioSelected(Colegio colegioSelected) {
		this.colegioSelected = colegioSelected;
	}

	public void setPeriodoSelected(Periodo periodoSelected) {
		this.periodoSelected = periodoSelected;
	}

	public void setCursoSelected(Curso cursoSelected) {
		this.cursoSelected = cursoSelected;
	}


	public void setProfesorSelected(Profesor profesorSelected) {
		this.profesorSelected = profesorSelected;
	}


	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public void setIdProfesor(int idProfesor) {
		this.idProfesor = idProfesor;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setDisabledButtonNuevo(boolean disabledButtonNuevo) {
		this.disabledButtonNuevo = disabledButtonNuevo;
	}

	public void setDisabledButtonGrabar(boolean disabledButtonGrabar) {
		this.disabledButtonGrabar = disabledButtonGrabar;
	}

	public void setDisabledButtonCancelar(boolean disabledButtonCancelar) {
		this.disabledButtonCancelar = disabledButtonCancelar;
	}

	public void setDisabledButtonEditar(boolean disabledButtonEditar) {
		this.disabledButtonEditar = disabledButtonEditar;
	}

	public void setDisabledButtonEliminar(boolean disabledButtonEliminar) {
		this.disabledButtonEliminar = disabledButtonEliminar;
	}

	public void setMessageConfirmDialog(String messageConfirmDialog) {
		this.messageConfirmDialog = messageConfirmDialog;
	}

	public void setStylePanelClase(String stylePanelClase) {
		this.stylePanelClase = stylePanelClase;
	}

	public void setStyleTableClase(String styleTableClase) {
		this.styleTableClase = styleTableClase;
	}

	public void setColegioService(ColegioService colegioService) {
		this.colegioService = colegioService;
	}

	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
	}

	public void setCursoService(CursoService cursoService) {
		this.cursoService = cursoService;
	}

	public void setProfesorService(ProfesorService profesorService) {
		this.profesorService = profesorService;
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

	public String getStylePanelClase() {
		return stylePanelClase;
	}

	public String getStyleTableClase() {
		return styleTableClase;
	}

	public List<Colegio> getColegios() {
		return colegios;
	}

	public Colegio getColegioSelected() {
		return colegioSelected;
	}

	public List<Periodo> getPeriodos() {
		return periodos;
	}

	public Periodo getPeriodoSelected() {
		return periodoSelected;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public Curso getCursoSelected() {
		return cursoSelected;
	}

	public List<Profesor> getProfesores() {
		return profesores;
	}

	public Profesor getProfesorSelected() {
		return profesorSelected;
	}


	public int getIdPeriodo() {
		return idPeriodo;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public int getIdProfesor() {
		return idProfesor;
	}

	public PeriodoService getPeriodoService() {
		return periodoService;
	}

	public CursoService getCursoService() {
		return cursoService;
	}

	public ProfesorService getProfesorService() {
		return profesorService;
	}

	public String getGradoSelected() {
		return gradoSelected;
	}

	public List<String> getGradosAcademicos() {
		return gradosAcademicos;
	}

	public void setGradoSelected(String gradoSelected) {
		this.gradoSelected = gradoSelected;
	}
	
	

	/////////// END REGION GETTERS AND SETTERS///////////////
	/*-----------------------------------------------------*/

}

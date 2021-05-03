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

import pe.edu.upc.iedunet.models.entities.Matricula;
import pe.edu.upc.iedunet.models.entities.Colegio;
import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Usuario;
import pe.edu.upc.iedunet.services.MatriculaService;
import pe.edu.upc.iedunet.services.ColegioService;
import pe.edu.upc.iedunet.services.CursoService;
import pe.edu.upc.iedunet.services.PeriodoService;
import pe.edu.upc.iedunet.services.AlumnoService;
import pe.edu.upc.iedunet.utils.Action;
import pe.edu.upc.iedunet.utils.GradosAcademicos;

@Named("matriculaView")
@ViewScoped
public class MatriculaView implements Serializable {

	private static final long serialVersionUID = 1L;

	// Matricula asociada al Formulario para crear o editar
	private Matricula matricula;
	
	// Lista de colegio que se muestra en el table colegioSelected
	private List<Colegio> colegios;

	// Colegio asociado al Row Select del Table colegioSelected
	private Colegio colegioSelected;
	
	// Lista de periodos del colegio que se muestra en el listbox periodoSelected
	private List<Periodo> periodos;

	// Colegio asociado al Row Select del listbox colegioSelected
	private Periodo periodoSelected;
	
	
	// Lista de alumnos del colegio que se muestra en el listbox alumnoSelected
	private List<Alumno> alumnos;

	// Alumno asociado al Row Select del listbox alumnoSelected
	private Alumno alumnoSelected;
	
	//Lista de String con los Grados A.
	private List<String> gradosAcademicos = new GradosAcademicos().listaGrados;
	private String gradoSelected;

	/////// REGION VARIABLES AUXILIARES///////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	private int idColegio;
	
	private int idPeriodo;
	
	private int idAlumno;


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
	private String stylePanelMatricula;
	private String styleTableMatricula;

	/////// END REGION VARIABLES DE ESTADO O ESTILOS/////////
	/*-----------------------------------------------------*/

	@Inject
	private MatriculaService claseService;

	@Inject
	private ColegioService colegioService;

	@Inject
	private PeriodoService periodoService;
	
	@Inject
	private AlumnoService alumnoService;
	
	@PostConstruct
	public void init() {
		this.cleanForm();
		this.loadColegios();
		//this.loadPeriodosByColegio();
		//this.loadCursos();
		//this.loadAlumnoes();
		this.action = Action.NONE;
		this.stateList();
	}

	////////// REGION METODOS CRUD /////////////////////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////

	// Metodo cuando se hace click en el boton Nuevo
	public void newMatricula() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
	}

	public void saveMatricula() {
		boolean uniqueCodigoMatricula = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				/*
				 * Optional<Matricula> optional =
				 * claseService.findByCodigoUsuario(clase.getUsuario().getCodigoUsuario()); if
				 * (optional.isPresent()) { uniqueCodigoMatricula = false; }
				 */
				if (uniqueCodigoMatricula == true) {
					// clase.getUsuario().setColegio(colegioService.findById(idColegio).get());

					/*changeColegio();
					changePeriodo();
					changeCurso();
					changeAlumno();

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
				changeAlumno();
				changeGrado();
				
				/*
				this.addMessage("Periodo: " + clase.getPeriodo().getDescripcion() + "--" + "Curso: " + clase.getCurso().getNombre() + "--" +"Grado: " + clase.getGrado()+ "--" + "Alumno: " + clase.getAlumno().getUsuario().getCodigoUsuario());
				System.out.println("Periodo: " + clase.getPeriodo().getDescripcion() + "--" + "Curso: " + clase.getCurso().getNombre() + "--" +"Grado: " + clase.getGrado()+ "--" + "Alumno: " + clase.getAlumno().getUsuario().getCodigoUsuario());
				//*/
				
				///*
				this.addMessage("Periodo: " + periodoSelected.getDescripcion() +  "--" +"Grado: " + gradoSelected + "--" + "Alumno: " + alumnoSelected.getUsuario().getCodigoUsuario());
				System.out.println("Periodo: " + periodoSelected.getDescripcion() + "--" +"Grado: " + gradoSelected + "--" + "Alumno: " + alumnoSelected.getUsuario().getCodigoUsuario());
				//*/
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
			
		}
	}

	public void editMatricula() {
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
	public void deleteMatricula() {
		/*
		if (this.claseSelected != null) {
			try {
				String x = claseSelected.getUsuario().getCodigoUsuario() + '-' + claseSelected.getId();
				claseService.deleteById(this.claseSelected.getId());
				cleanForm();
				loadMatriculas();
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
	public void cancelMatricula() {
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
	

	//Se cargan los alumnos para combobox
	public void loadAlumnosByColegio() {
		try {
			this.alumnos = alumnoService.findByColegio(colegioSelected.getId());
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
	
	public void loadAlumno(Integer id) {
		try {
			this.alumnoSelected = (alumnoService.findById(id)).get();
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
		}  // Cuando es una nueva Matricula
		else {
			loadColegio(this.idColegio);
			this.clase.getPeriodo().setColegio(this.colegioSelected);
		}*/
		loadColegio(this.idColegio);
		//this.clase.getPeriodo().setColegio(this.colegioSelected);
	}
	
	public void changePeriodo() {
		/*if (this.clase.getPeriodo() != null) {
			if (!this.clase.getPeriodo().getId().equals(this.idPeriodo)) {
				loadPeriodo(this.idPeriodo);
				this.clase.setPeriodo(this.periodoSelected);
			}
		} // Cuando es una nueva Matricula
		else {
			loadPeriodo(this.idPeriodo);
			this.clase.setPeriodo(this.periodoSelected);
		}*/
		loadPeriodo(this.idPeriodo);
		this.matricula.setPeriodo(this.periodoSelected);
	}
		
	public void changeAlumno() {
		/*
		if (this.clase.getAlumno() != null) {
			if (!this.clase.getAlumno().getId().equals(this.idCurso)) {
				loadAlumno(this.idCurso);
				this.clase.setAlumno(this.alumnoSelected);
			}
		} // Cuando es una nueva Matricula
		else {
			loadAlumno(this.idCurso);
			this.clase.setAlumno(this.alumnoSelected);
		}*/
		loadAlumno(this.idAlumno);
		this.matricula.setAlumno(this.alumnoSelected);
	}
	
	public void changeGrado() {
		this.matricula.getAlumno().setGrado(this.gradoSelected);
	}
	////////// END REGION METODOS CRUD//////////////////////
	/*-----------------------------------------------------*/

	////////// REGION METODOS PARA MANEJO DE UI Y EVENTOS///////
	////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	public void cleanForm() {
		this.matricula = new Matricula();
		matricula.setPeriodo(new Periodo());
		matricula.setAlumno(new Alumno());
		//clase.getAlumno().setUsuario(new Usuario());
	}

	// Metodo que se ejecuta cuando el evento rowSelect de tableColegio ocurra
	public void selectColegio(SelectEvent<Colegio> e) {
		this.colegioSelected = e.getObject();
		loadPeriodosByColegio();
		loadAlumnosByColegio();
		this.messageConfirmDialog = this.colegioSelected.getCodigo();
		this.stateSelectRow();
		System.out.println("SELECT COLEGIO");
	}

	// Metodo que se ejecuta cuando el evento rowUnselect ocurra
	public void unselectColegio(UnselectEvent<Colegio> e) {
		this.colegioSelected = null;
		this.periodos = null;
		this.alumnos=null;
		this.stateList();
	}
	
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
		this.stylePanelMatricula = "display:none;";
		this.styleTableMatricula = "display:block;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGrabar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelMatricula = "display:block;";
		this.styleTableMatricula = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGrabar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelMatricula = "display:none;";
		this.styleTableMatricula = "display:block;";
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
	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula clase) {
		this.matricula = clase;
	}

	public MatriculaService getMatriculaService() {
		return claseService;
	}

	public void setMatriculaService(MatriculaService claseService) {
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


	public void setAlumnoSelected(Alumno alumnoSelected) {
		this.alumnoSelected = alumnoSelected;
	}


	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}


	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
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

	public void setStylePanelMatricula(String stylePanelMatricula) {
		this.stylePanelMatricula = stylePanelMatricula;
	}

	public void setStyleTableMatricula(String styleTableMatricula) {
		this.styleTableMatricula = styleTableMatricula;
	}

	public void setColegioService(ColegioService colegioService) {
		this.colegioService = colegioService;
	}

	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
	}

	public void setAlumnoService(AlumnoService alumnoService) {
		this.alumnoService = alumnoService;
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

	public String getStylePanelMatricula() {
		return stylePanelMatricula;
	}

	public String getStyleTableMatricula() {
		return styleTableMatricula;
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

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public Alumno getAlumnoSelected() {
		return alumnoSelected;
	}


	public int getIdPeriodo() {
		return idPeriodo;
	}


	public int getIdAlumno() {
		return idAlumno;
	}

	public PeriodoService getPeriodoService() {
		return periodoService;
	}



	public AlumnoService getAlumnoService() {
		return alumnoService;
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


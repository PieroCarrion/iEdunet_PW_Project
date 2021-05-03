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
import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.services.ColegioService;
import pe.edu.upc.iedunet.services.PeriodoService;
import pe.edu.upc.iedunet.utils.Action;

@Named("periodoView")
@ViewScoped
public class PeriodoView implements Serializable {

	private static final long serialVersionUID = 1L;

	// Lista de periodoes que se muestra en el table
	private List<Periodo> periodos;

	// Periodo asociado al Formulario para crear o editar
	private Periodo periodo;

	// Periodo asociado al Row Select del Table
	private Periodo periodoSelected;

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
	private String stylePanelPeriodo;
	private String styleTablePeriodo;

	/////// END REGION VARIABLES DE ESTADO O ESTILOS/////////
	/*-----------------------------------------------------*/

	@Inject
	private PeriodoService periodoService;

	@Inject
	private ColegioService colegioService;

	@PostConstruct
	public void init() {
		this.cleanForm();
		this.loadPeriodos();
		this.loadColegios();
		this.action = Action.NONE;
		this.stateList();
	}

	////////// REGION METODOS CRUD /////////////////////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	public void loadPeriodos() {
		try {
			this.periodos = periodoService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

	}

	// Metodo cuando se hace click en el boton Nuevo
	public void newPeriodo() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
	}

	public void savePeriodo() {
		boolean uniqueCodigoPeriodo = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				/*
				 * Optional<Periodo> optional =
				 * periodoService.findByCodigoUsuario(periodo.getUsuario().getCodigoUsuario()); if
				 * (optional.isPresent()) { uniqueCodigoPeriodo = false; }
				 */
				if (uniqueCodigoPeriodo == true) {
					// periodo.getUsuario().setColegio(colegioService.findById(idColegio).get());

					changeColegio();

					if (this.action == Action.NEW) {
						periodo.setFechaInicio(new Date());
						periodoService.save(this.periodo);
						this.addMessage("SE ESTA GUARDANDO " + periodo.getFechaInicio());
					} else {
						periodoService.update(this.periodo);

						this.addMessage("SE ESTA ACTUALIZANDO " + periodo.getFechaInicio());
					}

					cleanForm();
					loadPeriodos();
					this.action = Action.NONE;
					this.stateList();
				} else {
					this.addMessage("El número de documento: " + periodo.getDescripcion() + " ya existe");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	public void editPeriodo() {
		if (this.periodoSelected != null) {
			this.periodo = this.periodoSelected;
			
			if (this.periodo.getColegio() != null) {
				this.idColegio = this.periodo.getColegio().getId();
			}
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deletePeriodo() {
		if (this.periodoSelected != null) {
			try {
				String x = periodoSelected.getId().toString();
				periodoService.deleteById(this.periodoSelected.getId());
				cleanForm();
				loadPeriodos();
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
	public void cancelPeriodo() {
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
		if (this.periodo.getColegio() != null) {
			if (!this.periodo.getColegio().getId().equals(this.idColegio)) {
				loadColegio(this.idColegio);
				this.periodo.setColegio(this.colegioSelected);
			}
		} // Cuando es un nuevo Cliente
		else {
			loadColegio(this.idColegio);
			this.periodo.setColegio(this.colegioSelected);
		}
	}
	////////// END REGION METODOS CRUD//////////////////////
	/*-----------------------------------------------------*/

	////////// REGION METODOS PARA MANEJO DE UI Y EVENTOS///////
	////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	public void cleanForm() {
		this.periodo = new Periodo();
		setPeriodo(new Periodo());
		this.periodoSelected = null;
	}

	// Metodo que se ejecuta cuando el evento rowSelect ocurra
	public void selectPeriodo(SelectEvent<Periodo> e) {
		this.periodoSelected = e.getObject();
		this.messageConfirmDialog = this.periodoSelected.getId().toString();
		this.stateSelectRow();
	}

	// Metodo que se ejecuta cuando el evento rowUnselect ocurra
	public void unselectPeriodo(UnselectEvent<Periodo> e) {
		this.periodoSelected = null;
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
		this.stylePanelPeriodo = "display:none;";
		this.styleTablePeriodo = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGrabar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelPeriodo = "display:block;";
		this.styleTablePeriodo = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGrabar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelPeriodo = "display:none;";
		this.styleTablePeriodo = "display:block;";
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
	public List<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}

	public Periodo getPeriodoSelected() {
		return periodoSelected;
	}

	public void setPeriodoSelected(Periodo periodoSelected) {
		this.periodoSelected = periodoSelected;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public PeriodoService getPeriodoService() {
		return periodoService;
	}

	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
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

	public String getStylePanelPeriodo() {
		return stylePanelPeriodo;
	}

	public String getStyleTablePeriodo() {
		return styleTablePeriodo;
	}

	public List<Colegio> getColegios() {
		return colegios;
	}

	/////////// END REGION GETTERS AND SETTERS///////////////
	/*-----------------------------------------------------*/

}
package pe.edu.upc.iedunet.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import pe.edu.upc.iedunet.services.ColegioService;
import pe.edu.upc.iedunet.utils.Action;

@Named("colegioView")
@ViewScoped
public class ColegioView implements Serializable {

	private static final long serialVersionUID = 1L;

	// Lista de colegios que se muestra en el table
	private List<Colegio> colegios;
	// Colegio asociado al Formulario para crear o editar
	private Colegio colegio;
	// Colegio asociado al Row Select del Table
	private Colegio colegioSelected;

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
	private String stylePanelColegio;
	private String styleTableColegio;

	/////// END REGION VARIABLES DE ESTADO O ESTILOS/////////
	/*-----------------------------------------------------*/
	@Inject
	private ColegioService colegioService;

	@PostConstruct
	public void init() {
		this.cleanForm();
		this.loadColegios();
		this.action = Action.NONE;
		this.stateList();
	}

	////////// REGION METODOS CRUD /////////////////////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	// Metodo cuando se hace click en el boton Nuevo
	public void loadColegios() {
		try {
			this.colegios = colegioService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	// Metodo cuando se hace click en el boton Nuevo
	public void newColegio() {
		cleanForm();
		this.action = Action.NEW;
		this.stateNewEdit();
	}

	public void saveColegio() {
		boolean uniqueCodigoColegio = true;
		if (this.action == Action.NEW || this.action == Action.EDIT) {
			try {
				/*
				 * Optional<Alumno> optional =
				 * alumnoService.findByCodigoUsuario(alumno.getUsuario().getCodigoUsuario()); if
				 * (optional.isPresent()) { uniqueCodigoAlumno = false; }
				 */
				Optional<Colegio> optional = colegioService.findByCodigo(((colegio.getCodigo())));
				if (optional.isPresent()) {
					if (!optional.get().getCodigo().equals(colegio.getCodigo())) {
						uniqueCodigoColegio = false;
					}
				}
				if (uniqueCodigoColegio == true) {
					// alumno.getUsuario().setColegio(colegioService.findById(idColegio).get());

					// changeColegio();

					if (this.action == Action.NEW) {
						colegioService.save(this.colegio);
						this.addMessage("SE ESTA GUARDANDO " + colegio.getNombre());
					} else {
						colegioService.update(this.colegio);

						this.addMessage("SE ESTA ACTUALIZANDO " + colegio.getNombre());
					}

					cleanForm();
					loadColegios();
					this.action = Action.NONE;
					this.stateList();
				} else {
					this.addMessage("El código del colegio: " + colegio.getCodigo() + " ya existe");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}

		try {
			colegioService.save(this.colegio);
			System.out.println(colegio.getNombre());
			cleanForm();
			loadColegios();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	public void editColegio() {
		if (this.colegioSelected != null) {
			this.colegio = this.colegioSelected;
			// this.colegio.set(this.colegioSelected.getUsuario());
			// if (this.colegio.getUsuario().getColegio() != null) {
			// this.idColegio = this.alumno.getUsuario().getColegio().getId();
			// }
			this.action = Action.EDIT;
			this.stateNewEdit();
		}
	}

	// Método que se ejecuta cuando hago click en el boton Eliminar
	public void deleteColegio() {
		if (this.colegioSelected != null) {
			try {
				String x = colegioSelected.getCodigo() + '-' + colegioSelected.getNombre();
				colegioService.deleteById(this.colegioSelected.getId());
				cleanForm();
				loadColegios();
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
	public void cancelColegio() {
		cleanForm();
		this.stateList();
	}
	////////// END REGION METODOS CRUD//////////////////////
	/*-----------------------------------------------------*/

	////////// REGION METODOS PARA MANEJO DE UI Y EVENTOS///////
	////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	public void cleanForm() {
		this.colegio = new Colegio();
		// colegio.setUsuario(new Usuario());
		this.colegioSelected = null;
	}

	// Metodo que se ejecuta cuando el evento rowSelect ocurra
	public void selectColegio(SelectEvent<Colegio> e) {
		this.colegioSelected = e.getObject();
		this.messageConfirmDialog = this.colegioSelected.getCodigo();
		this.stateSelectRow();
	}

	// Metodo que se ejecuta cuando el evento rowUnselect ocurra
	public void unselectColegio(UnselectEvent<Colegio> e) {
		this.colegioSelected = null;
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
		this.stylePanelColegio = "display:none;";
		this.styleTableColegio = "display:block;";
		this.disabledButtonNuevo = false;
		this.disabledButtonGrabar = true;
		this.disabledButtonCancelar = true;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateNewEdit() {
		this.stylePanelColegio = "display:block;";
		this.styleTableColegio = "display:none;";
		this.disabledButtonNuevo = true;
		this.disabledButtonGrabar = false;
		this.disabledButtonCancelar = false;
		this.disabledButtonEditar = true;
		this.disabledButtonEliminar = true;
	}

	public void stateSelectRow() {
		this.stylePanelColegio = "display:none;";
		this.styleTableColegio = "display:block;";
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

	public List<Colegio> getColegios() {
		return colegios;
	}

	public void setColegios(List<Colegio> colegios) {
		this.colegios = colegios;
	}

	public Colegio getColegio() {
		return colegio;
	}

	public void setColegio(Colegio colegio) {
		this.colegio = colegio;
	}

	public Colegio getColegioSelected() {
		return colegioSelected;
	}

	public void setColegioSelected(Colegio colegioSelected) {
		this.colegioSelected = colegioSelected;
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

	public void setMessageConfirmDialog(String messageConfirmDialog) {
		this.messageConfirmDialog = messageConfirmDialog;
	}

	public void setStylePanelColegio(String stylePanelColegio) {
		this.stylePanelColegio = stylePanelColegio;
	}

	public void setStyleTableColegio(String styleTableColegio) {
		this.styleTableColegio = styleTableColegio;
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

	public String getStylePanelColegio() {
		return stylePanelColegio;
	}

	public String getStyleTableColegio() {
		return styleTableColegio;
	}

	/////////// END REGION GETTERS AND SETTERS///////////////
	/*-----------------------------------------------------*/

}

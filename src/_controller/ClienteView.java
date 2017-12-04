package _controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import _business.AcervoManager;
import _business.ClienteManager;
import _business.CommonsManager;
import _business.ModuloManager;
import _model.vo.Acervo;
import _model.vo.Cliente;
import _model.vo.ClienteModulo;
import _model.vo.Modulo;
import _model.vo.Usuario;
import exception.ControllerException;
import exception.DaoException;
import util.ContextApp;
import util.Funcoes;

@ManagedBean(name = "clienteView")
@RequestScoped
public class ClienteView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1481143286085074745L;
	private Cliente cliente;
	private Cliente filtro;
	private Cliente clienteSel = null;
	private List<Cliente> clientes;
	private CommonsManager commonsManager;
	private UploadedFile file;
	private List<Modulo> selecionados = new ArrayList<Modulo>();
	private Modulo moduloSel = new Modulo();
	

	public ClienteView() throws Exception {

		commonsManager = new CommonsManager();

		cliente = new Cliente();
		filtro = new Cliente();

	}
	
	public void handleFileUpload(FileUploadEvent event) {
		file = event.getFile();

		cliente.setClie_tx_logo(new String(file.getFileName()));
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String retorna() throws Exception {

		return "/pages/principal/principal.xhtml";
	}

	public String listaClientes() throws Exception {
		ClienteManager clienteManager = new ClienteManager();
		clientes = clienteManager.listaClientes();
		return "/pages/cliente/cadcliente.xhtml";
	}

	public boolean validaSelecao() {
		if (cliente.getClienteModulos() != null && cliente.getClienteModulos().size()>0) {
			List<ClienteModulo> listaModulos = new ArrayList<ClienteModulo>();
			listaModulos = cliente.getClienteModulos();
			Integer i = 0;
			for (ClienteModulo modsel : listaModulos) {
				i++;
			}
		}
		return true;
	}
	
	public List<Cliente> pesquisar() throws Exception {
		ClienteManager clienteManager = new ClienteManager();
		clientes = clienteManager.pesquisa(filtro);
		return clientes;
	}

	private void atualizaStatus() {
		if (cliente.getClie_in_delecao() == null || cliente.getClie_in_delecao() == 0) {
			cliente.setClie_in_status("Ativo");
		} else {
			cliente.setClie_in_status("Inativo");
		}
	}
	
	private List<Modulo> buscaModulos() {
		ModuloManager moduloManager = new ModuloManager();
		try {
			selecionados = moduloManager.pesquisaPorCliente(this.cliente);
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selecionados;
	}

	public String inserir() throws Exception {
		cliente = new Cliente();
		atualizaStatus();
		return "/pages/cliente/cadclientedetalhe.xhtml";
	}

	public String editar() throws Exception {
		cliente = clienteSel;
		atualizaStatus();
		buscaModulos();
		return "/pages/cliente/cadclientedetalhe.xhtml";
	}

	public String excluir() throws Exception {

		String mensagem = "";
		Integer cont=0;
    	/* 
    	 * **********************************
    	 * VERIFICANDO OS ACERVOS ASSOCIADOS
    	 * **********************************
    	 */
		AcervoManager acervoManager = new AcervoManager();
		List<Acervo> acervoAssociados  = new ArrayList<Acervo>();
		acervoAssociados = acervoManager.listaAcervoPorCliente(clienteSel);
		if (acervoAssociados!=null && acervoAssociados.size()>0) {
			mensagem="Existem acervos relacionadas a este cliente. Desvincule antes de excluir!" ;
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		} else {
	    	/* 
	    	 * **********************************
	    	 * VERIFICANDO OS MÓDULOS ASSOCIADOS
	    	 * **********************************
	    	 */
		    if (clienteSel.getClienteModulos()!=null && clienteSel.getClienteModulos().size()>0) {
				mensagem="Existem módulos relacionadas a este cliente. Desvincule antes de excluir! Veja alguns dos Módulos: ";
				cont=0;
				for (ClienteModulo clienteModulo : clienteSel.getClienteModulos()) {
					 mensagem+=clienteModulo.getObjModulo().getModu_nm_modulo() + ", ";
					 cont++;
					 if(cont>9) break;
				}
				mensagem = mensagem.substring(0, mensagem.length()-2) + ".";
				
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		    } else {
		    	/* 
		    	 * **********************************
		    	 * VERIFICANDO OS USUÁRIOS ASSOCIADOS
		    	 * **********************************
		    	 */
				if (clienteSel.getUsuarios()!=null && clienteSel.getUsuarios().size()>0) {
					mensagem="Existem usuários relacionadas a este cliente. Desvincule antes de excluir! Veja alguns dos Usuários: ";
					cont=0;
					for (Usuario usuario : clienteSel.getUsuarios()) {
						 mensagem+=usuario.getUsua_nm_usuario() + ", ";
						 cont++;
						 if(cont>9) break;
					}
					mensagem = mensagem.substring(0, mensagem.length()-2) + ".";
					
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
				} else {
			    	/* 
			    	 * **********************************
			    	 * EXCLUINDO O CLIENTE SELECIONADO
			    	 * **********************************
			    	 */
					clienteSel.setClie_in_delecao(1);
					ClienteManager clienteManager = new ClienteManager();
					clienteManager.salvar(clienteSel);
					ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
				}
		    }
		}
		
		listaClientes();

		return "/pages/cliente/cadcliente.xhtml";
	}

	public void salvar() throws Exception {
		try {
			if (validaForm()) {

				if (selecionados != null && selecionados.size() > 0) {

					List<ClienteModulo> cliModulos = new ArrayList<ClienteModulo>();
					
					for (Modulo modulo : selecionados) {
						ClienteModulo cliMdl = new ClienteModulo();
						cliMdl.setObjCliente(cliente);
						cliMdl.setObjModulo(modulo);
						cliModulos.add(cliMdl);
					}
					cliente.setClienteModulos(cliModulos);
				}					

				ClienteManager clienteManager = new ClienteManager();
				clienteManager.salvar(cliente);
				
				cliente = new Cliente();
				ContextApp.getContext().addMessage("message_info", 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}

	}

	private boolean validaForm() throws Exception {
		boolean result = true;
		// --- Validação Data de Suspensão
		if (!Funcoes.validaCNPJ(cliente.getClie_cd_cnpj())) {
			ContextApp.getContext().addMessage("message_erro",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "CNPJ inválido. Verifique!", null));
		}

		if (ContextApp.getContext().getMessages().hasNext()) {
			result = false;
		}

		return result;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getFiltro() {
		return filtro;
	}

	public void setFiltro(Cliente filtro) {
		this.filtro = filtro;
	}

	public CommonsManager getCommonsManager() {
		return commonsManager;
	}

	public Cliente getClienteSel() {
		return clienteSel;
	}

	public void setClienteSel(Cliente clienteSel) {
		this.clienteSel = clienteSel;
	}

	public List<Modulo> getSelecionados() {
		return selecionados;
	}

	public void setSelecionados(List<Modulo> selecionados) {
		this.selecionados = selecionados;
	}

	public Modulo getModuloSel() {
		return moduloSel;
	}

	public void setModuloSel(Modulo moduloSel) {
		this.moduloSel = moduloSel;
	}

	
}
 
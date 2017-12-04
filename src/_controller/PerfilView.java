package _controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;

import _business.CommonsManager;
import _business.PaginaManager;
import _business.PerfilManager;
import _model.vo.Pagina;
import _model.vo.Perfil;
import _model.vo.PerfilPagina;
import _model.vo.Usuario;
import exception.ControllerException;
import exception.DaoException;
import util.ContextApp;

@ManagedBean(name="perfilView")
@RequestScoped
public class PerfilView implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5659542661105699414L;
	private Perfil perfil = new Perfil();
	private Perfil filtro = new Perfil();
	private Perfil perfilSel = null;
	private List<Perfil> perfils;
	private CommonsManager commonsManager;
	private PaginaManager paginaManager;
	private List<PerfilPagina> perfilPaginas = new ArrayList<PerfilPagina>();
	private PerfilPagina paginaSel = null;
	private List<Pagina> pagselecionadas = new ArrayList<Pagina>();
	private List<Pagina> paginas = new ArrayList<Pagina>();
	private boolean acesso_total = true;
	private boolean acesso_leitura = true;
	private boolean acesso_escrita = true;
	private boolean acesso_exclusao = true;
	


	public PerfilView() throws Exception {

	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	
	public String listaPerfils() throws Exception
	{
		PerfilManager perfilManager = new PerfilManager();
		perfils = perfilManager.listaPerfils();
		return "/pages/perfil/cadperfil.xhtml";
	}
	
	private List<PerfilPagina> buscaPaginas() throws Exception {
		paginaManager = new PaginaManager();
		paginas = paginaManager.listaPaginas();
		Pagina objPagina;
		try {
			perfilPaginas = paginaManager.pesquisaPorPerfil(this.perfil);
			if (perfilPaginas!=null && perfilPaginas.size()>0) {
				for (PerfilPagina perfilPagina : perfilPaginas) {
					objPagina = new Pagina();
					objPagina = perfilPagina.getObjPagina();
					objPagina.setPagi_in_acesso_total(perfilPagina.isPepa_in_acesso_total());
					objPagina.setPagi_in_acesso_escrita(perfilPagina.isPepa_in_acesso_escrita());
					objPagina.setPagi_in_acesso_leitura(perfilPagina.isPepa_in_acesso_leitura());
					objPagina.setPagi_in_acesso_exclusao(perfilPagina.isPepa_in_acesso_exclusao());
					if(paginas.contains(objPagina)) {
						paginas.remove(objPagina);
						paginas.add(objPagina);
					}
					pagselecionadas.add(objPagina);
				}
			}
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return perfilPaginas;
	}
	
	public void atualizaPagina() throws Exception
	{
		PerfilPagina perfilPagina;
		String in_acesso;
		List<Pagina> listaPaginasExistentes = new ArrayList<Pagina>();
		for (PerfilPagina itemLista : perfilPaginas) {
			listaPaginasExistentes.add(itemLista.getObjPagina());
		}
		
		if(pagselecionadas!=null && pagselecionadas.size()>0) {
			for (Pagina pagina : pagselecionadas) {
				if(!listaPaginasExistentes.contains(pagina)) {
					in_acesso="";
					perfilPagina = new PerfilPagina();
					perfilPagina.setObjPerfil(perfil);
					perfilPagina.setObjPagina(pagina);
					if(pagina.isPagi_in_acesso_total()) {
						in_acesso+="TLEX";
					} else {
						if(pagina.isPagi_in_acesso_leitura()) {
							in_acesso+="L";
						}
						if(pagina.isPagi_in_acesso_escrita()) {
							in_acesso+="E";
						}
						if(pagina.isPagi_in_acesso_exclusao()) {
							in_acesso+="X";
						}
					}
					perfilPagina.setPepa_in_acesso(in_acesso);
					perfilPaginas.add(perfilPagina);
				}
			}
			perfil.setPerfilPaginas(perfilPaginas);
		}
		//buscaPaginas();
	}

	public List<Perfil> pesquisar() throws Exception
	{
		PerfilManager perfilManager = new PerfilManager();
		perfils = perfilManager.pesquisa(filtro);
		return perfils;
	}	

	public void checkboxChanged2(AjaxBehaviorEvent event) {
		if(((SelectBooleanCheckbox)event.getSource()).isSelected()) {
			acesso_total=true;
		} else {
			acesso_total=false;
		}
	}


	public void excluirPaginaDetalhe() throws Exception
	{
		perfilPaginas.remove(paginaSel);
		pagselecionadas.remove(paginaSel.getObjPagina());
	}

	public String inserir() throws Exception
	{
		perfil = new Perfil();
		return "/pages/perfil/cadperfildetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		perfil = perfilSel;
		buscaPaginas();
		return "/pages/perfil/cadperfildetalhe.xhtml";
	}

	public String excluir() throws Exception
	{
		String mensagem = "";
		Integer cont=0;
    	/* 
    	 * **********************************
    	 * VERIFICANDO OS USUARIOS ASSOCIADOS
    	 * **********************************
    	 */
		if (perfilSel.getUsuarios()!=null && perfilSel.getUsuarios().size()>0) {
			mensagem="Existem usuários relacionadas a este perfil. Desvincule antes de excluir! Veja alguns dos Usuários: ";
			cont=0;
			for (Usuario usuario : perfilSel.getUsuarios()) {
				 mensagem+=usuario.getUsua_nm_usuario() + ", ";
				 cont++;
				 if(cont>9) break;
			}
			mensagem = mensagem.substring(0, mensagem.length()-2) + ".";
			
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		} else {
	    	/* 
	    	 * **********************************
	    	 * VERIFICANDO OS PÁGINAS ASSOCIADAS
	    	 * **********************************
	    	 */
			if (perfilPaginas!=null && perfilPaginas.size()>0) {
				mensagem="Existem páginas relacionadas a este perfil. Desvincule antes de excluir! Veja algumas das Páginas: ";
				cont=0;
				for (PerfilPagina perfilPagina : perfilSel.getPerfilPaginas()) {
					 mensagem+=perfilPagina.getObjPagina().getPagi_nm_pagina() + ", ";
					 cont++;
					 if(cont>9) break;
				}
				mensagem = mensagem.substring(0, mensagem.length()-2) + ".";
				
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
			} else {
				/* 
		    	 * **********************************
		    	 * EXCLUINDO PERFIL SELECIONADO
		    	 * **********************************
		    	 */
				perfilSel.setPerf_in_delecao(1);
				
				PerfilManager perfilManager = new PerfilManager();
				perfilManager.salvar(perfilSel);
				ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
			}
		}
		
		listaPerfils();
	
		return "/pages/perfil/cadperfil.xhtml";
	}
	

	public void salvar() throws Exception
	{
		try {
			if (validaForm()) {
				PerfilManager perfilManager = new PerfilManager();
				perfil.setPerf_cd_perfil(perfil.getPerf_cd_perfil().toUpperCase());
				perfil.setPerfilPaginas(perfilPaginas);
				perfilManager.salvar(perfil);
				
				// --- Novo registro
				
				perfil = new Perfil();
				perfilPaginas.clear();
				ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
		
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		
		// --- CONVERTE SIGLA EM MAIÚSCULAS
		
		return result;
	}

	
	// --- GETs e SETs
	
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Perfil getFiltro() {
		return filtro;
	}
	public void setFiltro(Perfil filtro) {
		this.filtro = filtro;
	}
	public Perfil getPerfilSel() {
		return perfilSel;
	}
	public void setPerfilSel(Perfil perfilSel) {
		this.perfilSel = perfilSel;
	}
	public List<Perfil> getPerfils() {
		return perfils;
	}
	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}
	public CommonsManager getCommonsManager() {
		return commonsManager;
	}

	public List<PerfilPagina> getPerfilPaginas() {
		return perfilPaginas;
	}

	public void setPerfilPaginas(List<PerfilPagina> perfilPaginas) {
		this.perfilPaginas = perfilPaginas;
	}

	public PerfilPagina getPaginaSel() {
		return paginaSel;
	}

	public void setPaginaSel(PerfilPagina paginaSel) {
		this.paginaSel = paginaSel;
	}

	public List<Pagina> getPagselecionadas() {
		return pagselecionadas;
	}

	public void setPagselecionadas(List<Pagina> pagselecionadas) {
		this.pagselecionadas = pagselecionadas;
	}

	public boolean isAcesso_total() {
		return acesso_total;
	}

	public void setAcesso_total(boolean acesso_total) {
		this.acesso_total = acesso_total;
	}

	public boolean isAcesso_leitura() {
		return acesso_leitura;
	}

	public void setAcesso_leitura(boolean acesso_leitura) {
		this.acesso_leitura = acesso_leitura;
	}

	public boolean isAcesso_escrita() {
		return acesso_escrita;
	}

	public void setAcesso_escrita(boolean acesso_escrita) {
		this.acesso_escrita = acesso_escrita;
	}

	public boolean isAcesso_exclusao() {
		return acesso_exclusao;
	}

	public void setAcesso_exclusao(boolean acesso_exclusao) {
		this.acesso_exclusao = acesso_exclusao;
	}

	public List<Pagina> getPaginas() {
		return paginas;
	}

	public void setPaginas(List<Pagina> paginas) {
		this.paginas = paginas;
	}
	
	
}

package _controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import _business.CategoriaManager;
import _business.CommonsManager;
import _business.PerfilManager;
import _business.UnidadeManager;
import _business.UsuarioManager;
import _model.domain.SituacaoUsuario;
import _model.vo.Categoria;
import _model.vo.Perfil;
import _model.vo.Unidade;
import _model.vo.Usuario;
import exception.DaoException;
import util.ContextApp;
import util.Funcoes;


@ManagedBean(name="usuarioView")
@RequestScoped
public class UsuarioView implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7191793669779171612L;
	private Usuario usuario ;
	private Usuario filtro ;
	private Usuario usuarioSel = null;
	private Unidade unidadeSel = null;
	private Categoria categoriaSel = null;
	private Perfil perfilSel = null;
	private List<Usuario> usuarios;
	private CommonsManager commonsManager;
	private UnidadeManager unidadeManager;
	private PerfilManager perfilManager;
	private CategoriaManager categoriaManager;
	private List<Unidade> unidades;
	private Unidade filtroUnidade;
	private List<Perfil> perfils;
	private Perfil filtroPerfil;
	private List<Categoria> categorias;
	private Categoria filtroCategoria;
	private UploadedFile file;
	private String arquivo;
	private String arqDestino;

	
	public UsuarioView() throws Exception 
	{
	
		commonsManager = new CommonsManager();
		unidadeManager = new UnidadeManager();
		filtroUnidade = new Unidade();
		unidades = unidadeManager.listaUnidades();
		perfilManager = new PerfilManager();
		filtroPerfil = new Perfil();
		perfils = perfilManager.listaPerfils();
		categoriaManager = new CategoriaManager();
		filtroCategoria = new Categoria();
		categorias = categoriaManager.listaCategorias();
		usuario = new Usuario();
		filtro = new Usuario();
		
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	
	public void uploadDialog() {
		RequestContext.getCurrentInstance().execute("PF('popUpUpload').show()");
	}

    public void upload(FileUploadEvent event) {
    	arquivo = event.getFile().getFileName();
    	//arqDestino = FacesContext.getCurrentInstance().getExternalContext().getRealPath(File.separator+"files"+File.separator);
    	arqDestino = FacesContext.getCurrentInstance().getExternalContext().getRealPath(File.separator+"resources"+File.separator+"img"+File.separator);
		ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "O Arquivo " + arquivo + " foi carregado com sucesso!.", null));
        // Do what you want with the file        
        try {
            copyFile(arquivo, event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // --- SETANDO CAMPO COM NOME DO ARQUIVO
        usuario.setUsua_tx_foto(arquivo);
    }  

    public void copyFile(String fileName, InputStream in) {
        try {
           
           
             // write the inputStream to a FileOutputStream
             OutputStream out = new FileOutputStream(new File(arqDestino + File.separator + fileName));
           
             int read = 0;
             byte[] bytes = new byte[1024];
           
             while ((read = in.read(bytes)) != -1) {
                 out.write(bytes, 0, read);
             }
           
             in.close();
             out.flush();
             out.close();
           
             } catch (IOException e) {
            	 System.out.println(e.getMessage());
             }
    }

	public String listaUsuarios() throws Exception
	{
		UsuarioManager usuarioManager = new UsuarioManager();

		usuarios = usuarioManager.listaUsuarios();

		return "/pages/usuario/cadusuario.xhtml";
	}
	
	public List<Usuario> pesquisar() throws Exception
	{
		UsuarioManager usuarioManager = new UsuarioManager();

		usuarios = usuarioManager.pesquisa(filtro);

		return usuarios;
	}	

	public List<Unidade> pesquisarUnidades() throws Exception
	{
		UnidadeManager unidadeManager = new UnidadeManager();
		unidades = unidadeManager.pesquisa(filtroUnidade);
		return unidades;
	}

	
	public List<Perfil> pesquisarPerfils() throws Exception
	{
		PerfilManager perfilManager = new PerfilManager();
		perfils = perfilManager.pesquisa(filtroPerfil);
		return perfils;
	}

	public List<Categoria> pesquisarCategorias() throws Exception
	{
		CategoriaManager categoriaManager = new CategoriaManager();
		categorias = categoriaManager.pesquisa(filtroCategoria);
		return categorias;
	}
	
	public String inserir() throws Exception
	{
		usuario = new Usuario();
		usuario.setUsua_dt_inclusao(Funcoes.getPegaDataAtual());
		usuario.setObjCliente(((Usuario)ContextApp.getSession().getAttribute("usuariologado")).getObjCliente());
		return "/pages/usuario/cadusuariodetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		usuario = usuarioSel;

		return "/pages/usuario/cadusuariodetalhe.xhtml";
	}

	public String excluir() throws Exception
	{
		usuarioSel.setUsua_in_delecao(1); // --- MARCANDO REGISTRO COMO EXCLUIDO
		usuarioSel.setUsua_dt_exclusao(Funcoes.getPegaDataAtual());
		usuarioSel.setUsua_in_situacao(SituacaoUsuario.EX);
		UsuarioManager usuarioManager = new UsuarioManager();
		usuarioManager.salvar(usuarioSel);
		listaUsuarios();
		ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));

		return "/pages/usuario/cadusuario.xhtml";
	}
	
	public String salvar() throws Exception
	{
		
		try {
			if (validaForm()) {
			UsuarioManager usuarioManager = new UsuarioManager();
			usuarioManager.salvar(usuario);
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			inserir();
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
		
		return null;
		
	}
	
	public void selecionarUnidade() throws Exception
	{
		usuario.setObjUnidade(unidadeSel);
	}
	
	public void selecionarPerfil() throws Exception
	{
		usuario.setObjPerfil(perfilSel);
	}

	public void selecionarCategoria() throws Exception
	{
		usuario.setObjCategoria(categoriaSel);
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		int datresult=0;
		if(usuario.getUsua_dt_suspensao() == null || usuario.getUsua_dt_fimsuspensao()==null) {
			datresult = 0;
		} else {
			datresult = Funcoes.compareDate(usuario.getUsua_dt_fimsuspensao(), usuario.getUsua_dt_suspensao());
		}
		
		// --- Categoria
		if(usuario.getObjCategoria()==null || usuario.getObjCategoria().getCate_nm_categoria()==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Categoria é obrigatória.", null));
		}
		
		// --- Perfil
		if(usuario.getObjPerfil()==null || usuario.getObjPerfil().getPerf_nm_perfil()==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Perfil é obrigatório.", null));
		}

		// --- Unidade
		if(usuario.getObjUnidade()==null || usuario.getObjUnidade().getUnid_nm_unidade()==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unidade é obrigatória.", null));
		}

		// --- Validação Data de Suspensão
		if(usuario.getUsua_in_situacao().toString().equals("SP") && datresult==1) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de fim de suspensão menor que data de início de suspensão.", null));
		}
		
		// --- Se Situacao for diferente de suspensão as datas de início e fim de suspensão devem ser retiradas.
		if(!usuario.getUsua_in_situacao().toString().equals("SP")) {
			usuario.setUsua_dt_suspensao(null);
			usuario.setUsua_dt_fimsuspensao(null);
		}
		
		// --- Valida e-mail
		if(usuario.getUsua_ds_email()!=null && !usuario.getUsua_ds_email().isEmpty()) {
			if(!Funcoes.validaEmail(usuario.getUsua_ds_email())) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email inválido!", null));			
			}
		}
		
		// --- Foto
		if(usuario.getUsua_tx_foto()==null || usuario.getUsua_tx_foto().equals("")) {
			if(usuario.getUsua_ds_sexo().equals("M")) {
				usuario.setUsua_tx_foto("sem_foto_m.png");
			} else {
				usuario.setUsua_tx_foto("sem_foto_f.png");
			}
		}
		
		if (ContextApp.getContext().getMessages().hasNext())
		{
			result = false;
		}
		
		return result;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getFiltro() {
		return filtro;
	}

	public void setFiltro(Usuario filtro) {
		this.filtro = filtro;
	}

	public CommonsManager getCommonsManager() {
		return commonsManager;
	}

	public Usuario getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(Usuario usuarioSel) {
		this.usuarioSel = usuarioSel;
	}
	
	public Unidade getUnidadeSel() {
		return unidadeSel;
	}

	public void setUnidadeSel(Unidade unidadeSel) {
		this.unidadeSel = unidadeSel;
	}

	public Perfil getPerfilSel() {
		return perfilSel;
	}

	public void setPerfilSel(Perfil perfilSel) {
		this.perfilSel = perfilSel;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public Unidade getFiltroUnidade() {
		return filtroUnidade;
	}

	public void setFiltroUnidade(Unidade filtroUnidade) {
		this.filtroUnidade = filtroUnidade;
	}

	public List<Perfil> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}

	public Perfil getFiltroPerfil() {
		return filtroPerfil;
	}

	public void setFiltroPerfil(Perfil filtroPerfil) {
		this.filtroPerfil = filtroPerfil;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Categoria getFiltroCategoria() {
		return filtroCategoria;
	}

	public void setFiltroCategoria(Categoria filtroCategoria) {
		this.filtroCategoria = filtroCategoria;
	}

	public Categoria getCategoriaSel() {
		return categoriaSel;
	}

	public void setCategoriaSel(Categoria categoriaSel) {
		this.categoriaSel = categoriaSel;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
}

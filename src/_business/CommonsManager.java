package _business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import _model.domain.FaseCorrente;
import _model.domain.FaseDestinacaoFinal;
import _model.domain.SituacaoUsuario;
import _model.domain.TipoEmprestimo;
import _model.vo.Classe;
import _model.vo.Descritor;
import _model.vo.Modulo;
import _model.vo.Pagina;
import _model.vo.Perfil;
import _model.vo.Unidade;
import exception.ControllerException;

@ManagedBean(name = "commonsView")
public class CommonsManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2442260807295239317L;
	private List<SituacaoUsuario> situacoes;
	private List<TipoEmprestimo> tipoEmprestimos;
	private List<FaseCorrente> fs_correntes;
	private List<FaseDestinacaoFinal> fs_destfinal;
	private Unidade unidadeSel = null;
	private List<Unidade> unidades;
	private Unidade filtroUnidade;
	private List<Classe> classes;
	private Classe filtroClasse;
	private String ufs[] = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MT", "MS", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
	private List<Modulo> modulos;
	private Modulo filtroModulo;
	private List<Descritor> descritors;
	private List<Perfil> perfils;
	private List<Pagina> paginas = new ArrayList<Pagina>();
	private Perfil filtroPerfil;
	

	public CommonsManager() throws Exception {
	}

	public List<SituacaoUsuario> getSituacoes() 
	{
		situacoes = new ArrayList<SituacaoUsuario>();
		situacoes.add(SituacaoUsuario.NR);
		situacoes.add(SituacaoUsuario.EX);
		situacoes.add(SituacaoUsuario.SP);
		
		return situacoes;
	}

	public List<TipoEmprestimo> getTipoEmprestimos() 
	{
		tipoEmprestimos = new ArrayList<TipoEmprestimo>();
		tipoEmprestimos.add(TipoEmprestimo.D);
		tipoEmprestimos.add(TipoEmprestimo.C);
		tipoEmprestimos.add(TipoEmprestimo.P);
		tipoEmprestimos.add(TipoEmprestimo.E);
		
		return tipoEmprestimos;
	}
	
	public List<FaseCorrente> getFs_correntes() {
		
		fs_correntes = new ArrayList<FaseCorrente>();
		fs_correntes.add(FaseCorrente.STP);
		fs_correntes.add(FaseCorrente.ANO);
		fs_correntes.add(FaseCorrente.CCA);
		fs_correntes.add(FaseCorrente.CCU);
		fs_correntes.add(FaseCorrente.DRN);
		fs_correntes.add(FaseCorrente.EVG);
		fs_correntes.add(FaseCorrente.HMA);
		fs_correntes.add(FaseCorrente.HME);
		fs_correntes.add(FaseCorrente.CEV);
		fs_correntes.add(FaseCorrente.MVI);
		fs_correntes.add(FaseCorrente.RNT);
		fs_correntes.add(FaseCorrente.CCT);
		
		return fs_correntes;
	}


	public List<FaseDestinacaoFinal> getFs_destfinal() {
		
		fs_destfinal = new ArrayList<FaseDestinacaoFinal>();
		fs_destfinal.add(FaseDestinacaoFinal.STP);
		fs_destfinal.add(FaseDestinacaoFinal.ELM);
		fs_destfinal.add(FaseDestinacaoFinal.GDP);
		
		return fs_destfinal;
	}

	public String[] getUfs() {
		return ufs;
	}

	public List<Unidade> pesquisarUnidades() throws Exception
	{
		UnidadeManager unidadeManager = new UnidadeManager();
		unidades = unidadeManager.pesquisa(filtroUnidade);
		return unidades;
	}

	public List<Classe> pesquisarClasses() throws Exception
	{
		ClasseManager classeManager = new ClasseManager();
		classes = classeManager.pesquisa(filtroClasse);
		return classes;
	}
	
	public List<Modulo> pesquisarModulos() throws Exception
	{
		ModuloManager moduloManager = new ModuloManager();
		modulos = moduloManager.pesquisa(filtroModulo);
		return modulos;
	}
	
	public List<Perfil> pesquisarPerfils() throws Exception
	{
		PerfilManager perfilManager = new PerfilManager();
		filtroPerfil = new Perfil();
		perfils = perfilManager.pesquisa(filtroPerfil);
		return perfils;
	}
	
	public Unidade getUnidadeSel() {
		return unidadeSel;
	}

	public void setUnidadeSel(Unidade unidadeSel) {
		this.unidadeSel = unidadeSel;
	}
	
	public List<Unidade> getUnidades() {
		UnidadeManager unidadeManager = new UnidadeManager();
		filtroUnidade = new Unidade();
		try {
			unidades = unidadeManager.listaUnidades();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unidades;
	}
	
	public Unidade getFiltroUnidade() {
		return filtroUnidade;
	}

	public void setFiltroUnidade(Unidade filtroUnidade) {
		this.filtroUnidade = filtroUnidade;
	}

	public List<Modulo> getModulos() {
		ModuloManager moduloManager = new ModuloManager();
		filtroModulo = new Modulo();
		try {
			modulos = moduloManager.listaModulos();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modulos;
	}

	public Modulo getFiltroModulo() {
		return filtroModulo;
	}

	public void setFiltroModulo(Modulo filtroModulo) {
		this.filtroModulo = filtroModulo;
	}

	public Classe getFiltroClasse() {
		return filtroClasse;
	}

	public void setFiltroClasse(Classe filtroClasse) {
		this.filtroClasse = filtroClasse;
	}

	public List<Classe> getClasses() {
		ClasseManager classeManager = new ClasseManager();
		filtroClasse = new Classe();
		try {
			classes = classeManager.listaClasses();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}
	
	public List<Descritor> getDescritors() {
		DescritorManager descritorManager = new DescritorManager();
		try {
			descritors = descritorManager.listaDescritors();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return descritors;
	}

	public List<Perfil> getPerfils() {
		PerfilManager perfilManager = new PerfilManager();
		try {
			perfils = perfilManager.listaPerfils();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return perfils;
	}

	public Perfil getFiltroPerfil() {
		return filtroPerfil;
	}

	public void setFiltroPerfil(Perfil filtroPerfil) {
		this.filtroPerfil = filtroPerfil;
	}

	public List<Pagina> getPaginas() {
		PaginaManager paginaManager = new PaginaManager();
		try {
			paginas = paginaManager.listaPaginas();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paginas;
	}


	
	
	
	
}

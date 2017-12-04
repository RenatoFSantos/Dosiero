package _controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.print.PrintService;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import _business.AcervoManager;
import _business.BoletoManager;
import _business.CategoriaTipoManager;
import _business.EmprestimoManager;
import _business.OcorrenciaManager;
import _business.UnidadeManager;
import _business.UsuarioManager;
import _model.domain.TipoEmprestimo;
import _model.dto.BoletoDTO;
import _model.dto.EmprestimoDTO;
import _model.vo.Acervo;
import _model.vo.Boleto;
import _model.vo.CategoriaTipo;
import _model.vo.Emprestimo;
import _model.vo.Ocorrencia;
import _model.vo.Unidade;
import _model.vo.Usuario;
import exception.ControllerException;
import exception.DaoException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import service.NegocioException;
import util.ContextApp;
import util.Funcoes;

@ManagedBean(name="emprestimoView")
@ViewScoped
public class EmprestimoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codDocumentoSel = "";
	private String codEmprestimoReg = "";
	private Emprestimo emprestimo;
	private Ocorrencia filtroOcorrencia;
	private List<Emprestimo> emprestimos;
	private String mensagem = "";
	private String tipoFiltro;
	private String tipoEmprestimo;
	private Usuario pesqMatricula;
	private Usuario pesqNome;
	private Usuario pesqId;
	private Usuario usuarioEmprestimo;
	private Boleto boleto;
	private Date datPrevDevolucao;
	private boolean bloqDtDevolucao = true;
	private boolean bloqSelecao = false;
	private boolean bloqRegistro = false;
	private boolean renderMatricula=false;
	private boolean renderNome=true;
	private boolean renderId=false;
	private List<Ocorrencia> ocorrencias;
	private Ocorrencia ocorrencia;
	private Ocorrencia ocorrenciaSel;
	private boolean status = false;
	private boolean statusAnt = false;
	private PrintService impressora;
	private Unidade filtroUnidade;
	private Unidade unidadeSel = null;
	private List<Unidade> unidades;
	private UnidadeManager unidadeManager;
	private Date dat_ini_periodo;
	private Date dat_fim_periodo;
	private String fmtRlt;
	private Acervo acervo;
	
	@PostConstruct
	public void init() {
		this.usuarioEmprestimo = new Usuario();
		this.usuarioEmprestimo.setUsua_tx_foto("sem_foto_m.png");
		emprestimo = new Emprestimo();
		acervo = new Acervo();
		emprestimo.setObjAcervo(acervo);		
	}
	
	public EmprestimoView() throws Exception {
		filtroUnidade = new Unidade();
	}
	
	public String relEmprestimoUnidade() throws Exception {

		return "/pages/emprestimo/relemprestimounidade.xhtml";
	}
	
	public void listaUnidades() {
		unidades = new ArrayList<Unidade>();
		unidadeManager = new UnidadeManager();
		try {
			unidades=unidadeManager.listaUnidades();
		} catch (ControllerException e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("PF('popUpUnidadeEmprestimo').show()");
	}
	
	public List<Unidade> pesquisaUnidades() throws Exception
	{
		unidadeManager = new UnidadeManager();
		unidades = unidadeManager.pesquisa(filtroUnidade);
		return unidades;
	}
	
	public void selecionarUnidade() throws Exception
	{
		if(emprestimo!=null && emprestimo.getObjAcervo()!=null && unidadeSel!=null) {
			emprestimo.getObjAcervo().setObjUnidade(unidadeSel);
		}
	}

	public void geraRelatorioEmprestimoPorUnidade() throws Exception {
		// --- RELATÓRIO DE EMPRÉSTIMO POR UNIDADE
		try {
			if (validaFiltroEmprestimoPorUnidade()) {
				List<EmprestimoDTO> emprestimoDTOs = new ArrayList<EmprestimoDTO>();
				EmprestimoManager emprestimoManager = new EmprestimoManager();
				// --- Filtra a unidade do acervo do empréstimo
				emprestimoDTOs = emprestimoManager.listaEmprestimoPorUnidade(emprestimo.getObjAcervo().getObjUnidade(), dat_ini_periodo, dat_fim_periodo);
				// --- Gera relatório
				String caminho = "";
				String logo_rlt = "";
				String nomeRelatorio = "Rel_EmprestimoXUnidade";
				String rltXLS = "";
				caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath(File.separator + "reports" + File.separator);
				logo_rlt = FacesContext.getCurrentInstance().getExternalContext().getRealPath(File.separator + "resources" + File.separator + "img" + File.separator + "rptubm.jpg");
				rltXLS = caminho + File.separator + nomeRelatorio + ".xls";
					
				// HashMap de parametros
		        HashMap<String, Object> parameters = new HashMap<String, Object>();
		        parameters.put("Logo", logo_rlt);
		        parameters.put("DataIni", this.dat_ini_periodo);
		        parameters.put("DataFim", this.dat_fim_periodo);

		        String rltJRXML = caminho + File.separator + nomeRelatorio + ".jrxml";
					
		        FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				ServletOutputStream responseStream = response.getOutputStream();

				// - Compila o Relatório
				JasperReport pathReport = JasperCompileManager.compileReport(rltJRXML);
				// - Altera a fonte de dados
				JasperPrint preencher = JasperFillManager.fillReport(pathReport, parameters,new JRBeanCollectionDataSource(emprestimoDTOs));
					
				if (fmtRlt.equals("PDF")) {
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"" + nomeRelatorio + ".pdf\"");
					// - Exporta para PDF
					JasperExportManager.exportReportToPdfStream(preencher,responseStream);
		            responseStream.flush();
					responseStream.close();
					context.renderResponse();
					context.responseComplete();           
				}
				
				if (fmtRlt.equals("XLS")) {
					JExcelApiExporter exporter = new JExcelApiExporter();
		        	ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
		        	exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, preencher);
		        	exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
		        	exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		        	exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		        	exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		        	exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, rltXLS);

		        	exporter.exportReport();
		        	byte[] bytes = xlsReport.toByteArray();
		        	response.setContentType("application/vnd.ms-excel-download");
					response.setHeader("Content-Disposition","attachment; filename=\"" + nomeRelatorio + ".xls\"");
		        	response.setContentLength(bytes.length);
		        	xlsReport.close();
		        	OutputStream ouputStream = response.getOutputStream();
		        	ouputStream.write(bytes, 0, bytes.length);
		        	ouputStream.flush();
		        	ouputStream.close(); 
					context.renderResponse();
					context.responseComplete();           
				}
				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean validaFiltroEmprestimoPorUnidade() throws Exception {
		boolean result = true;
		// --- VALIDANDO DATAS
		if (this.dat_ini_periodo==null && this.dat_fim_periodo==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datas inválidas. Verifique!", null));
		} else {
			if (Funcoes.compareDate(dat_ini_periodo, dat_fim_periodo)==2) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de período final menor que a inicial. Verifique!", null));
			}
		}
		// --- VALIDANDO UNIDADE
		if(emprestimo.getObjAcervo().getObjUnidade()!=null && emprestimo.getObjAcervo().getObjUnidade().getUnid_nm_unidade()==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unidade inválida. Verifique!", null));
		}

		if (ContextApp.getContext().getMessages().hasNext()) {
			result = false;
		}

		return result;
	}
	
	
	public String balcao() throws Exception
	{
		emprestimo = new Emprestimo();

		return "/pages/emprestimo/cademprestimo.xhtml";
	}
	

	public String retorna() throws Exception
	{
		return "/pages/principal/principal.xhtml";
	}
	
	
	public void trocarUsuario() throws Exception {
		this.usuarioEmprestimo = new Usuario();
		this.usuarioEmprestimo.setUsua_tx_foto("sem_foto_m.png");
		this.codEmprestimoReg=null;
		this.codDocumentoSel=null;
		this.datPrevDevolucao=null;
		this.pesqId=null;
		this.pesqMatricula=null;
		this.pesqNome=null;
		this.emprestimos=null;
		bloqSelecao=false;
		bloqRegistro=false;
	}
	
	public void liberarDataDevolucao(AjaxBehaviorEvent event) throws Exception{
		
		tipoEmprestimo=event.getComponent().getAttributes().get("value").toString();
		if(tipoEmprestimo.equals("E")){
			//--- Emprestimo Especial
			bloqDtDevolucao=false;
		} else {
			bloqDtDevolucao=true;
		}
	}
	
	public void validaTipoFiltro(AjaxBehaviorEvent event) throws Exception {
		//emprestimo.setObjUsuarioEmprestimo(new Usuario()); // --- Limpando o usuário selecionado.
		tipoFiltro = event.getComponent().getAttributes().get("value").toString();
		if(tipoFiltro.equals("MT")) {
			// Matrícula
			renderMatricula=true;
			renderNome=false;
			renderId=false;
		}
		if(tipoFiltro.equals("NM")) {
			// Nome
			renderMatricula=false;
			renderNome=true;
			renderId=false;
		}
		if(tipoFiltro.equals("ID")) {
			// ID
			renderMatricula=false;
			renderNome=false;
			renderId=true;
		}
	}
	
	public List<Usuario> pesquisaSelecaoMatricula(String query) throws Exception{
        List<Usuario> results = new ArrayList<Usuario>();
        UsuarioManager usuarioManager = new UsuarioManager();
        results = usuarioManager.listaUsuarioEmprestimo("MT", query);
		return results;
	}

	public List<Usuario> pesquisaSelecaoNome(String query) throws Exception{
        List<Usuario> results = new ArrayList<Usuario>();
        UsuarioManager usuarioManager = new UsuarioManager();
        results = usuarioManager.listaUsuarioEmprestimo("NM", query);
		return results;
	}

	public List<Usuario> pesquisaSelecaoId(String query) throws Exception{
        List<Usuario> results = new ArrayList<Usuario>();
        UsuarioManager usuarioManager = new UsuarioManager();
        results = usuarioManager.listaUsuarioEmprestimo("ID", query);
		return results;
	}
	
	public void onItemSelect(SelectEvent event) throws Exception {
		// --- Define o usuário que solicitou o empréstimo
		this.usuarioEmprestimo = (Usuario) event.getObject();
		// --- Define o valor default do Tipo de empréstimo
		this.tipoEmprestimo="D";
		// --- Verifica tipo de empréstimo atual
		if(tipoEmprestimo!=null && tipoEmprestimo.equals("E")){
			//--- Emprestimo Especial
			bloqDtDevolucao=false;
		} else {
			bloqDtDevolucao=true;
		}			
		// --- Busca os emprestimos em aberto deste usuário
		emprestimos = listaEmprestimosDoUsuario();
		bloqSelecao=true;
		bloqRegistro=false;
    }
	
	public List<Emprestimo> listaEmprestimosDoUsuario() throws Exception {
		EmprestimoManager emprestimoManager = new EmprestimoManager();
		emprestimos = emprestimoManager.buscaEmprestimosEmAberto(this.usuarioEmprestimo);
		if(emprestimos!=null && emprestimos.size()>0) {
			for (Emprestimo obj : emprestimos) {
				this.boleto = obj.getObjBoleto();
			}
		}
		return emprestimos;
	}
	
	public void renovar() throws Exception
	{
		// --- Se emprestimo existir e a data de empréstimo for igual a data atual.
		if(validaRenovacao()) {
			// --- Calcula a diferença em dias entre a data de empréstimo e a data prevista de devolução
			int diferData = Funcoes.diffDate(emprestimo.getEmpr_dt_emprestimo(), emprestimo.getEmpr_dt_prev_devolucao(), "DI");
			// --- Recalcula o emprestimo renovando o mesmo número de dias calculado
			Date novaData;
			novaData = Funcoes.addDayInDate(diferData, Funcoes.getPegaDataAtual());
			emprestimo.setEmpr_dt_renovacao(novaData);
			// --- Atualizando a informação
			atualizar();
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Empréstimo renovado com sucesso!", null));
			// --- Atualiza lista de empréstimo para o Usuário
			emprestimos = listaEmprestimosDoUsuario();
		}
	}

	private boolean validaRenovacao() throws Exception {
		boolean result = true;
		
		if(this.emprestimo==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione o empréstimo desejado antes de renovar.", null));
		}
		if(this.emprestimo.getEmpr_dt_prev_devolucao()==null && this.emprestimo.getEmpr_tp_emprestimo().equals("P")) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Empréstimo permanente, sem necessidade de renovação.", null));
		}
		if(this.emprestimo.getEmpr_dt_prev_devolucao()==null && !this.emprestimo.getEmpr_tp_emprestimo().equals("P")) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Empréstimo não é Permanente, mas está sem data de devolução. Verifique!", null));
		}
		if(this.emprestimo.getEmpr_dt_renovacao()!=null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Empréstimo já foi renovado uma vez. Renovação cancelada!", null));
		}
		if(this.emprestimo.getEmpr_dt_renovacao()!=null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este empréstimo está sem data de empréstimo. Renovação cancelada!", null));
		}
		
		if (ContextApp.getContext().getMessages().hasNext()) {
			result = false;
		}

		return result;
	}

	public void excluir() throws Exception
	{
		// --- Se emprestimo existir e a data de empréstimo for igual a data atual.
		if(emprestimo!=null && Funcoes.compareDate(emprestimo.getEmpr_dt_emprestimo(), Funcoes.getPegaDataAtual())==0) {
			EmprestimoManager emprestimoManager = new EmprestimoManager();
			emprestimoManager.deletar(emprestimo);
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
			// --- Atualiza lista de empréstimo para o Usuário
			emprestimos = listaEmprestimosDoUsuario();
		} else {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não é possível excluir um documento com data de empréstimo anterior a data atual.", null));
		}
	}
	
	public void devolverDocumento() throws Exception {
		if(emprestimo!=null && emprestimo.getObjAcervo().getAcer_nr_codbarras()!=null) {
			emprestimo.setEmpr_dt_real_devolucao(Funcoes.getPegaDataAtual());
			// --- Atualizar emprestimo
			atualizar();
			// --- Atualiza lista de empréstimo para o Usuário
			emprestimos = listaEmprestimosDoUsuario();
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Devolução registrada!", null));
		} else {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um documento para devolução.", null));
		}
	}
	
	public void buscaEmprestimo() throws Exception {
		if(this.codDocumentoSel!=null && !this.codDocumentoSel.equals("")) {
			EmprestimoManager emprestimoManager = new EmprestimoManager();
			emprestimos = new ArrayList<Emprestimo>();
			String codDoc = "";
			codDoc = this.codDocumentoSel;
			emprestimos = emprestimoManager.buscaEmprestimosPorCodBarras(codDoc);
			bloqRegistro=true; // --- Não permite entrar com novos empréstimos
			if(emprestimos!=null && emprestimos.size()>0) {
				for (Emprestimo obj : emprestimos) {
					emprestimo = obj;
					this.usuarioEmprestimo = emprestimo.getObjUsuarioEmprestimo();
					this.boleto = emprestimo.getObjBoleto();
					// --- Verifica se devolução do empréstimo está atrasada e notifica em mensagem
					this.mensagem="";
					if(emprestimo!=null) {
						if(emprestimo.getEmpr_dt_renovacao()==null) {
							if(Funcoes.compareDate(emprestimo.getEmpr_dt_prev_devolucao(), Funcoes.getPegaDataAtual())==1)
								this.mensagem="Empréstimo em atraso.";
						} else {
							if(Funcoes.compareDate(emprestimo.getEmpr_dt_renovacao(), Funcoes.getPegaDataAtual())==1)
								this.mensagem="Empréstimo em atraso.";
						}
					}
				}
			} else {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Emprestimo inexistente. Verifique!", null));
			}
		} else {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o código do documento!", null));
		}
	}
	
	public Date defineDataDevolucao() throws Exception {
		// --- Define a data de devolução prevista
		CategoriaTipo categoriaTipoSel = new CategoriaTipo();
		categoriaTipoSel.setObjCategoria(this.usuarioEmprestimo.getObjCategoria());
		categoriaTipoSel.setObjTipo(emprestimo.getObjAcervo().getObjTipo());
		
		CategoriaTipoManager categoriaTipoManager = new CategoriaTipoManager();
		CategoriaTipo categoriaTipo = new CategoriaTipo();
		Date dataDevolucaoPrv = new Date();
		Integer prazoDias = 0;
		categoriaTipo = categoriaTipoManager.validaCategoriaTipo(categoriaTipoSel);
		if(this.tipoEmprestimo!=null && !this.tipoEmprestimo.equals("P")) {
			if(categoriaTipo!=null) {
				if(this.tipoEmprestimo.equals("C")) {
					prazoDias = (categoriaTipoManager.validaCategoriaTipo(categoriaTipoSel)).getCati_nr_prazo_dev_consulta();
				}
				if(this.tipoEmprestimo.equals("D")) {
					prazoDias = (categoriaTipoManager.validaCategoriaTipo(categoriaTipoSel)).getCati_nr_prazo_dev_emprestimo();
				}
				dataDevolucaoPrv = Funcoes.addDayInDate(prazoDias, Funcoes.getPegaDataAtual());
				
			} else {
				dataDevolucaoPrv = Funcoes.addDayInDate(1, Funcoes.getPegaDataAtual());
			}
		} else {
			dataDevolucaoPrv = null;
		}
		return dataDevolucaoPrv;
	}
	
	public void adicionarEmprestimo() throws Exception
	{
		try {
			if (validaForm()) {
				AcervoManager acervoManager = new AcervoManager();
				Acervo acervoSel = new Acervo();
				String codAcervo = "";
				codAcervo = this.codEmprestimoReg;
				acervoSel = acervoManager.pesquisaAcervoPorCodBarra(codAcervo);
				if(acervoSel!=null && acervoSel.getId()!=null) {
					emprestimo = new Emprestimo();
					// --- Define o acervo emprestado
					emprestimo.setObjAcervo(acervoSel);
					// --- Define a data prevista de devolução
					emprestimo.setEmpr_dt_prev_devolucao(defineDataDevolucao());
					// --- Define o usuario do Empréstimo
					emprestimo.setObjUsuarioEmprestimo(this.usuarioEmprestimo);
					// --- Define o usuário de Cadastro
					Usuario usuariologado = (Usuario)ContextApp.getSession().getAttribute("usuariologado");
					emprestimo.setObjUsuarioCadastro(usuariologado);
					// --- Define a data do empréstimo
					emprestimo.setEmpr_dt_emprestimo(Funcoes.getPegaDataAtual());
					// --- Define o tipo de empréstimo
					emprestimo.setEmpr_tp_emprestimo(this.tipoEmprestimo);
					// --- Verifica se boleto já foi gerado
					if(boleto==null) {
						// --- Define o boleto a ser impresso
						boleto = new Boleto();
						boleto.setBole_in_impressao("N");
					}
					if(boleto.getId()==null) {	
						// --- Salva o Boleto
						BoletoManager boletoManager = new BoletoManager();
						boletoManager.salvar(boleto);
						// --- Recupera o boleto Salvo
						boleto = boletoManager.pesquisaBoletoPorId(boletoManager.proximoID());
					}
					emprestimo.setObjBoleto(boleto);
						
					// --- Salva o empréstimo
					salvar();
					
					// --- Prepara tela para nova entrada
					
					limpaTela();
					
					ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
				} else {
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Documento inexistente. Verifique!", null));
				}
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void limpaTela() throws Exception {
		emprestimo.setObjAcervo(new Acervo());
		emprestimo.setEmpr_dt_prev_devolucao(null);
		emprestimo.setEmpr_tp_emprestimo("D");
		// --- Atualiza lista de empréstimo para o Usuário
		if(this.usuarioEmprestimo!=null) {
			emprestimos = listaEmprestimosDoUsuario();
		} else {
			emprestimos = new ArrayList<Emprestimo>();
		}
		// --- Desbloqueia tela de pesquisa
		this.bloqSelecao=false;
		// --- Bloqueia Data de devolução
		this.bloqDtDevolucao=true;
		// --- Limpa os campos de entrada
		this.codEmprestimoReg=null;
		this.datPrevDevolucao=null;
		this.tipoEmprestimo="D";
	}
	
	public void geraBoleto() throws Exception {
		// TODO --- GERA BOLETO DE EMPRÉSTIMO
		try {
			// --- Faz a impressão do boleto se existir algum empréstimo solicitado
			if(this.usuarioEmprestimo!=null && this.usuarioEmprestimo.getUsua_ds_email()!=null) {
				// --- Prepara objeto de impressão
				if(this.emprestimos!=null && this.emprestimos.size()>0 && this.boleto!=null) {
					List<BoletoDTO> boletoDTOs = new ArrayList<BoletoDTO>();
					BoletoDTO emprest;
					for (Emprestimo item : emprestimos) {
						emprest = new BoletoDTO();
						emprest.setBole_dt_emprestimo(item.getEmpr_dt_emprestimo());
						emprest.setBole_cd_matricula_usuariocadastro(item.getObjUsuarioCadastro().getUsua_cd_matricula());
						emprest.setBole_ds_email_usuariocadastro(item.getObjUsuarioCadastro().getUsua_ds_email());
						emprest.setBole_ds_email_usuarioemprest(item.getObjUsuarioEmprestimo().getUsua_ds_email());
						emprest.setBole_ds_telefone_usuariocadastro(item.getObjUsuarioCadastro().getUsua_ds_telefone());
						emprest.setBole_dt_prev_devolucao(item.getEmpr_dt_prev_devolucao());
						emprest.setBole_nm_documento(item.getObjAcervo().getAcer_ds_assunto());
						emprest.setBole_nm_usuariocadastro(item.getObjUsuarioCadastro().getUsua_nm_usuario());
						emprest.setBole_nm_usuarioemprest(item.getObjUsuarioEmprestimo().getUsua_nm_usuario());
						emprest.setBole_nr_codbarras(item.getObjAcervo().getAcer_nr_codbarras());
						emprest.setBole_sq_boleto(item.getObjBoleto().getId());
						emprest.setBole_tx_conteudo(item.getObjAcervo().getAcer_ds_assunto()); // --- Coloquei o mesmo conteúdo do nome do documento acima.
						boletoDTOs.add(emprest);
					}
	
					// --- Gera relatório
					String caminho = "";
					String nomeRelatorio = "Rel_BoletoEmprestimo";
					caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath(File.separator + "reports" + File.separator);
						
					// HashMap de parametros
			        HashMap<String, Object> parameters = new HashMap<String, Object>();
	
			        String rltJRXML = caminho + File.separator + nomeRelatorio + ".jrxml";
			        //String pdfFile = caminho + File.separator + nomeRelatorio + ".pdf";
			        
			        FacesContext context = FacesContext.getCurrentInstance();
					HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
					ServletOutputStream saida = response.getOutputStream();

					// compila jrxml em um arquivo .jasper
					String jasper = JasperCompileManager.compileReportToFile(rltJRXML);

					// preenche relatorio
					JasperPrint print = JasperFillManager.fillReport(jasper, parameters, new JRBeanCollectionDataSource(boletoDTOs));
					
					// --- Gera em PDF
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"" + nomeRelatorio + ".pdf\"");
					
					// - Exporta para PDF
					JasperExportManager.exportReportToPdfStream(print,saida);
		            saida.flush();
					saida.close();
					context.renderResponse();
					context.responseComplete();           
				} else {
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não existe empréstimo cadastrado. Verifique!", null));
				}
				
				/*
				 * ************************************************************
				 * ROTINA QUE APRESENTA AS IMPRESSORAS CONECTADAS AO COMPUTADOR
				 * ************************************************************
				Parametro parametro = new Parametro();
				parametro = (Parametro)ContextApp.getSession().getAttribute("parametroSistema");
				this.impressora=Bematech.detectaImpressoras(parametro.getImpressora());
				String texto="Teste de impressao na BEMATECH.";
				if(!Bematech.imprimeDefault(texto)) {
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas na impressão. Verifique!", null));
				}
				 * ************************************************************
				 */
	
				// --- Envia email
				enviarEmail();
			} else {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário sem email válido. Verifique!", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void enviarEmail() throws Exception {
		// --- Enviar email
		if(this.usuarioEmprestimo!=null && this.usuarioEmprestimo.getUsua_ds_email()!=null) {
			String mailServer = "smtp.gmail.com";
			String assunto = "Dosiero - Teste Envio email";
			String de = "adm.dosiero@gmail.com";
			String para = this.usuarioEmprestimo.getUsua_ds_email();
			String mensagem = "Este é um teste de email.";
			
			Funcoes.sendMail(mailServer, assunto, para, de, mensagem); 
			
			// --- Limpando os campos do form
			emprestimo=new Emprestimo();
			this.usuarioEmprestimo = new Usuario();
			this.usuarioEmprestimo.setUsua_tx_foto("sem_foto_m.png");
			limpaTela();
			
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Empréstimo finalizado com sucesso.", null));
		} else {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário sem email válido. Verifique!", null));
		}
	}
	
	public void salvar() throws Exception {
		EmprestimoManager emprestimoManager = new EmprestimoManager();
		// --- Salva o empréstimo
		emprestimoManager.salvar(emprestimo);
	}
	
	public void atualizar() throws Exception {
		EmprestimoManager emprestimoManager = new EmprestimoManager();
		// --- Atualizar o empréstimo
		emprestimoManager.atualizar(emprestimo);		
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		
		if(this.codEmprestimoReg==null || this.codEmprestimoReg.length()<12) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código do documento inválido. Verifique!", null));
		}
		if(tipoEmprestimo.equals(TipoEmprestimo.E)) {
			if(emprestimo.getEmpr_dt_prev_devolucao()==null)
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de devolução não informada. Verifique!", null));
		}
		if(this.datPrevDevolucao!=null && Funcoes.compareDate(this.datPrevDevolucao, Funcoes.getPegaDataAtual())==1) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de devolução menor que a data atual. Verifique!", null));
		}
		if(this.usuarioEmprestimo==null || this.usuarioEmprestimo.getId()==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Antes de emprestar, selecione um usuário!", null));
		}
		
		if (ContextApp.getContext().getMessages().hasNext()) {
			result = false;
		}

		return result;
	}
	
	/*
	 * **********************************************************************************************************************
	 * CONTROLE DE COMENTÁRIO
	 * **********************************************************************************************************************
	 */

	public void comentar() throws Exception {
		// --- Faz a chamada do popup de comentário do usuário
		if(this.usuarioEmprestimo!=null) {
			RequestContext.getCurrentInstance().execute("PF('popUpComentario').show()");
		} else {
			throw new NegocioException("Selecione um usuário antes de comentar.");
		}
	}
    
	public void atualizarComentario() throws Exception {
		// --- Grava o comentario no cadastro de usuário
		UsuarioManager usuarioManager = new UsuarioManager();
		if(this.usuarioEmprestimo!=null && this.usuarioEmprestimo.getUsua_tx_emprestimo()!=null) {
			usuarioManager.salvar(this.usuarioEmprestimo);
		}
	}
	
	/*
	 * **********************************************************************************************************************
	 * CONTROLE DE OCORRÊNCIAS
	 * **********************************************************************************************************************
	 */
	
	public List<Ocorrencia> pesquisarOcorrencia() throws Exception
	{
		OcorrenciaManager ocorrenciaManager = new OcorrenciaManager();
		ocorrencias = ocorrenciaManager.pesquisa(filtroOcorrencia);
		return ocorrencias;
	}	

	public void listaOcorrencias() throws Exception {
		// --- Faz a chamada do popup de ocorrencia
		if(emprestimo!=null) {
			OcorrenciaManager ocorrenciaManager = new OcorrenciaManager();
			ocorrencias = new ArrayList<Ocorrencia>();
			ocorrencias = ocorrenciaManager.listaOcorrenciasPorAcervo(emprestimo.getObjAcervo());
			// --- Inicializando os objetos de trabalho
			filtroOcorrencia = new Ocorrencia();
			ocorrencia = new Ocorrencia();
			ocorrenciaSel = new Ocorrencia();
			RequestContext.getCurrentInstance().execute("PF('popUpOcorrencia').show()");
		} else {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione o empréstimo desejado.", null));
		}
	}

    public void onRowSelectOcorrencia(SelectEvent event) {
        ocorrencia = (Ocorrencia) event.getObject();
        if (ocorrencia.getOcor_in_ativacao().equals("D")) {
        	this.statusAnt=false;
        	this.status=false;
        } else {
        	this.statusAnt=true;
        	this.status=true;
        }
    }
    
	public void salvarOcorrencia() {
		try {
			OcorrenciaManager ocorrenciaManager = new OcorrenciaManager();
			// --- Definindo outras informações necessárias para gravação
			ocorrencia.setObjAcervo(emprestimo.getObjAcervo());
			ocorrencia.setObjUsuarioDesativacao(null);
			if (ocorrencia.getId()==null) {
				// --- Se for inclusão da ocorrencia, gravar o nome do usuário que cadastrou
				ocorrencia.setObjUsuarioInclusao((Usuario)ContextApp.getSession().getAttribute("usuariologado"));
			} else if(!this.status && this.statusAnt && ocorrencia.getObjUsuarioDesativacao()==null) {
				// --- Se o STATUS ANTERIOR era ATIVO e o atual está DESATIVADO e o Usuário Desativação ainda está nulo, grava o usuário que desativou.
				ocorrencia.setObjUsuarioDesativacao((Usuario)ContextApp.getSession().getAttribute("usuariologado"));
			}
			if(this.status) {
				ocorrencia.setOcor_in_ativacao("A");
			} else {
				ocorrencia.setOcor_in_ativacao("D");
			}
			ocorrencia.setOcor_dt_inclusao(Funcoes.getPegaDataAtual());
			// --- Salvar
			ocorrenciaManager.salvar(ocorrencia);
			// --- Limpar variável
			ocorrencia = new Ocorrencia();
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
		} catch (Exception e) {
			throw new NegocioException("Problemas na gravação de Ocorrencia. Verifique!" + e.getMessage());
		}
	}
	
	/*
	 * **********************************************************************************************************************
	 * GET's e SET's
	 * **********************************************************************************************************************
	 */
	
	public Emprestimo getEmprestimo() {
		return emprestimo;
	}
	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}
	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public Usuario getPesqMatricula() {
		return pesqMatricula;
	}

	public void setPesqMatricula(Usuario pesqMatricula) {
		this.pesqMatricula = pesqMatricula;
	}

	public Usuario getPesqNome() {
		return pesqNome;
	}

	public void setPesqNome(Usuario pesqNome) {
		this.pesqNome = pesqNome;
	}

	public Usuario getPesqId() {
		return pesqId;
	}

	public void setPesqId(Usuario pesqId) {
		this.pesqId = pesqId;
	}

	public String getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getTipoEmprestimo() {
		return tipoEmprestimo;
	}

	public void setTipoEmprestimo(String tipoEmprestimo) {
		this.tipoEmprestimo = tipoEmprestimo;
	}

	public boolean isBloqDtDevolucao() {
		return bloqDtDevolucao;
	}

	public void setBloqDtDevolucao(boolean bloqDtDevolucao) {
		this.bloqDtDevolucao = bloqDtDevolucao;
	}

	public boolean isRenderMatricula() {
		return renderMatricula;
	}

	public void setRenderMatricula(boolean renderMatricula) {
		this.renderMatricula = renderMatricula;
	}

	public boolean isRenderNome() {
		return renderNome;
	}

	public void setRenderNome(boolean renderNome) {
		this.renderNome = renderNome;
	}

	public boolean isRenderId() {
		return renderId;
	}

	public void setRenderId(boolean renderId) {
		this.renderId = renderId;
	}
	
	public boolean isBloqSelecao() {
		return bloqSelecao;
	}

	public void setBloqSelecao(boolean bloqSelecao) {
		this.bloqSelecao = bloqSelecao;
	}

	public String getCodDocumentoSel() {
		return codDocumentoSel;
	}

	public void setCodDocumentoSel(String codDocumentoSel) {
		this.codDocumentoSel = codDocumentoSel;
	}

	public String getCodEmprestimoReg() {
		return codEmprestimoReg;
	}

	public void setCodEmprestimoReg(String codEmprestimoReg) {
		this.codEmprestimoReg = codEmprestimoReg;
	}

	public boolean isBloqRegistro() {
		return bloqRegistro;
	}

	public void setBloqRegistro(boolean bloqRegistro) {
		this.bloqRegistro = bloqRegistro;
	}
	
	public Usuario getUsuarioEmprestimo() {
		return usuarioEmprestimo;
	}

	public void setUsuarioEmprestimo(Usuario usuarioEmprestimo) {
		this.usuarioEmprestimo = usuarioEmprestimo;
	}
	
	public Date getDatPrevDevolucao() {
		return datPrevDevolucao;
	}

	public void setDatPrevDevolucao(Date datPrevDevolucao) {
		this.datPrevDevolucao = datPrevDevolucao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public List<Ocorrencia> getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(List<Ocorrencia> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public Ocorrencia getFiltroOcorrencia() {
		return filtroOcorrencia;
	}

	public void setFiltroOcorrencia(Ocorrencia filtroOcorrencia) {
		this.filtroOcorrencia = filtroOcorrencia;
	}
	
	public Ocorrencia getOcorrenciaSel() {
		return ocorrenciaSel;
	}

	public void setOcorrenciaSel(Ocorrencia ocorrenciaSel) {
		this.ocorrenciaSel = ocorrenciaSel;
	}

	public boolean isStatusAnt() {
		return statusAnt;
	}

	public void setStatusAnt(boolean statusAnt) {
		this.statusAnt = statusAnt;
	}

	public Unidade getFiltroUnidade() {
		return filtroUnidade;
	}

	public void setFiltroUnidade(Unidade filtroUnidade) {
		this.filtroUnidade = filtroUnidade;
	}
	
	public Unidade getUnidadeSel() {
		return unidadeSel;
	}

	public void setUnidadeSel(Unidade unidadeSel) {
		this.unidadeSel = unidadeSel;
	}
	
	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}
	
	public Date getDat_ini_periodo() {
		return dat_ini_periodo;
	}

	public void setDat_ini_periodo(Date dat_ini_periodo) {
		this.dat_ini_periodo = dat_ini_periodo;
	}

	public Date getDat_fim_periodo() {
		return dat_fim_periodo;
	}

	public void setDat_fim_periodo(Date dat_fim_periodo) {
		this.dat_fim_periodo = dat_fim_periodo;
	}
	
	public String getFmtRlt() {
		return fmtRlt;
	}

	public void setFmtRlt(String fmtRlt) {
		this.fmtRlt = fmtRlt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codEmprestimoReg == null) ? 0 : codEmprestimoReg.hashCode());
		result = prime * result + ((codDocumentoSel == null) ? 0 : codDocumentoSel.hashCode());
		result = prime * result + ((datPrevDevolucao == null) ? 0 : datPrevDevolucao.hashCode());
		result = prime * result + ((emprestimo == null) ? 0 : emprestimo.hashCode());
		result = prime * result + ((tipoEmprestimo == null) ? 0 : tipoEmprestimo.hashCode());
		result = prime * result + ((tipoFiltro == null) ? 0 : tipoFiltro.hashCode());
		result = prime * result + ((usuarioEmprestimo == null) ? 0 : usuarioEmprestimo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmprestimoView other = (EmprestimoView) obj;
		if (codEmprestimoReg == null) {
			if (other.codEmprestimoReg != null)
				return false;
		} else if (!codEmprestimoReg.equals(other.codEmprestimoReg))
			return false;
		if (codDocumentoSel == null) {
			if (other.codDocumentoSel != null)
				return false;
		} else if (!codDocumentoSel.equals(other.codDocumentoSel))
			return false;
		if (datPrevDevolucao == null) {
			if (other.datPrevDevolucao != null)
				return false;
		} else if (!datPrevDevolucao.equals(other.datPrevDevolucao))
			return false;
		if (emprestimo == null) {
			if (other.emprestimo != null)
				return false;
		} else if (!emprestimo.equals(other.emprestimo))
			return false;
		if (tipoEmprestimo == null) {
			if (other.tipoEmprestimo != null)
				return false;
		} else if (!tipoEmprestimo.equals(other.tipoEmprestimo))
			return false;
		if (tipoFiltro == null) {
			if (other.tipoFiltro != null)
				return false;
		} else if (!tipoFiltro.equals(other.tipoFiltro))
			return false;
		if (usuarioEmprestimo == null) {
			if (other.usuarioEmprestimo != null)
				return false;
		} else if (!usuarioEmprestimo.equals(other.usuarioEmprestimo))
			return false;
		return true;
	}


	
}

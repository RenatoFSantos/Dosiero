package _controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import _business.AcervoManager;
import _business.ClasseManager;
import _business.CommonsManager;
import _business.DescritorManager;
import _business.DocumentoManager;
import _business.UnidadeManager;
import _model.domain.FaseCorrente;
import _model.dto.AcervoDTO;
import _model.vo.Acervo;
import _model.vo.Classe;
import _model.vo.Cliente;
import _model.vo.Descritor;
import _model.vo.Documento;
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
import util.ContextApp;
import util.Funcoes;

@ManagedBean(name="movimentacaoView")
@RequestScoped
public class MovimentacaoView implements Serializable {

	private static final long serialVersionUID = 1L;
	private Acervo filtroAcervo;
	private Unidade filtroUnidade;
	private Classe filtroClasse;
	private Descritor filtroDescritor;
	private Acervo acervo;
	private Acervo acervoSel = null;
	private Unidade unidadeSel = null;
	private Classe classeSel = null;
	private CommonsManager commonsManager;
	private ClasseManager classeManager;
	private UnidadeManager unidadeManager;
	private boolean datBloqueada = true;
	private TreeNode classesTree;
	private TreeNode selectedNode;
	private Classe editada = new Classe();
	private Documento documento;
	private Documento documentoSel;
	private boolean bloqBotoes = false;
	private int datresult=0;
	private String caminho;
	private String arquivo;
	private Date dat_ini_periodo;
	private Date dat_fim_periodo;
	private Descritor descritorSel;
	private String fmtEtq;
	private String fmtRlt;
	private String tpRlt;
	private boolean manterDados = false;
	private boolean possoIncluir = true;
	private boolean bloqVigencia = false;
	private List<Unidade> unidades = new ArrayList<Unidade>();
	private List<Descritor> seldescritors = new ArrayList<Descritor>();
	private List<Classe> classes = new ArrayList<Classe>();
	private List<Descritor> descritors = new ArrayList<Descritor>();
	private List<Acervo> acervos = new ArrayList<Acervo>();
	private List<Documento> documentos = new ArrayList<Documento>();
	private Cliente cliente;
	private Integer codEtqInicial;
	private Integer codEtqFinal;
	
	public MovimentacaoView() {
		filtroAcervo = new Acervo();
		filtroUnidade = new Unidade();
		filtroClasse = new Classe();
		filtroDescritor = new Descritor();
		unidadeSel = new Unidade();
		classeSel = new Classe();
	}

	public String abrir_movimentacao() throws Exception
	{
		acervo = new Acervo();
		return "/pages/movimentacao/movimentacao.xhtml";
	}
	
	public String retorna() throws Exception
	{
		return "/pages/principal/principal.xhtml";
	}
	
	public void listaUnidades() {
		unidadeManager = new UnidadeManager();
		try {
			unidades=unidadeManager.listaUnidades();
		} catch (ControllerException e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("PF('popUpUnidade').show()");
	}
	
	public void listaClasses() {
		classeManager = new ClasseManager();
		try {
			classes=classeManager.listaClasses();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("PF('popUpClasse').show()");
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

	
	public void executaMovimentacao() throws Exception {
		if (validaFiltroMovimentacao()) {
			atualizarAcervo();
			geraRelatorioAutoMovimentacao();
		}
	}
	/*
	 * *********************************************************************************************************************************************************************
	 * ROTINA DE ATUALIZAÇÃO DAS DATAS DO ACERVO COM INDICADOR DE AUTO MOVIMENTACAO
	 * *********************************************************************************************************************************************************************
	 */
	public void atualizarAcervo() throws Exception {
		Date dataAtual = Funcoes.getPegaDataAtual();
		Date dataCalc = new Date();
		Date dataCalcProxFase = new Date();
		boolean gravaAcervo;
		datresult=0;
		// --- CARREGA CLIENTE ATUAL
		Usuario usua;
		usua=(Usuario)ContextApp.getSession().getAttribute("usuariologado");
		cliente = usua.getObjCliente();
		// --- CARREGANDO O ACERVO SELECIONADO
		AcervoManager acervoManager = new AcervoManager();
		acervos = acervoManager.listaAcervoMovimentacao(cliente, acervo.getObjUnidade(), acervo.getObjClasse(), dat_ini_periodo, dat_fim_periodo);
		// --- ITERANDO O ACERVO SELECIONADO
		for (Acervo acervo : acervos) {
			// --- SETANDO INDICADOR PARA GRAVACAO
			gravaAcervo=false; // --- Por padrão não se grava o acervo
			// --- VERIFICANDO SE DATA DE REFERENCIA ESTÁ PREENCHIDA
			if(acervo.getAcer_dt_finalvigencia()!=null || acervo.getAcer_dt_referencia()!=null) {
				// --- IDENTIFICANDO O STATUS ATUAL
				if(acervo.getAcer_in_status().equals("FC"))
				{
					if(acervo.getObjClasse().getClas_cd_classe()!=null) {
						if(acervo.getObjClasse().getClas_tx_fasecorrente().equals("ANO") && acervo.getObjClasse().getClas_nr_fasecorrente()>0 && acervo.getAcer_dt_referencia()!=null) {
							gravaAcervo=true; // --- Seta indicador para gravação do registro na base
							dataCalc = Funcoes.addYearInDate(acervo.getObjClasse().getClas_nr_fasecorrente(), acervo.getAcer_dt_referencia());
							// --- ATUALIZA A DATA PREVISTA E REALIZADA (FC ->>> FI)
							acervo.setAcer_dt_prevmvt_fc_fi(dataCalc);
							acervo.setAcer_dt_realmvt_fc_fi(null);
							// --- ATUALIZA A DATA PREVISTA E REALIZADA (FI ->>> DF)
							if(acervo.getObjClasse().getClas_tx_faseintermediaria()!=null && acervo.getObjClasse().getClas_tx_faseintermediaria()!="") {
								dataCalcProxFase = Funcoes.addYearInDate(Integer.parseInt(acervo.getObjClasse().getClas_tx_faseintermediaria()), dataCalc);
								acervo.setAcer_dt_prevmvt_fi_df(dataCalcProxFase);
								acervo.setAcer_dt_realmvt_fi_df(null);
							} else {
								acervo.setAcer_dt_prevmvt_fi_df(null);
								acervo.setAcer_dt_realmvt_fi_df(null);							
							}
							// --- SE DATA ATUAL FOR MAIOR QUE A DATA DO CALCULO AVANCE O STATUS
							datresult = Funcoes.compareDate(dataAtual,dataCalc);
							if(datresult==2 && acervo.isAcer_in_automovimentacao()) {
								// --- REALIZA A PASSAGEM E ATUALIZA A DATA REALIZADA
								acervo.setAcer_in_status("FI");
								acervo.setAcer_dt_realmvt_fc_fi(dataAtual);
							}
						} else {
							// --- PARA AS OUTRAS SITUAÇÕES
							if(acervo.getAcer_dt_finalvigencia()!=null) {
								gravaAcervo=true; // --- Seta indicador para gravação do registro na base
								dataCalc = acervo.getAcer_dt_finalvigencia();
								// --- ATUALIZA AS DATAS PREVISTAS EM FUNÇÃO DA DATA DE FINAL DE VIGÊNCIA
								acervo.setAcer_dt_prevmvt_fc_fi(dataCalc);
								acervo.setAcer_dt_realmvt_fc_fi(null);
								// --- ATUALIZANDO DATA PREVISTA DE FI ->> FC
								if(acervo.getObjClasse().getClas_tx_faseintermediaria()!=null && acervo.getObjClasse().getClas_tx_faseintermediaria()!="") {
									dataCalcProxFase = Funcoes.addYearInDate(Integer.parseInt(acervo.getObjClasse().getClas_tx_faseintermediaria()), dataCalc);
									acervo.setAcer_dt_prevmvt_fi_df(dataCalcProxFase);
									acervo.setAcer_dt_realmvt_fi_df(null);
								} else {
									acervo.setAcer_dt_prevmvt_fi_df(null);
									acervo.setAcer_dt_realmvt_fi_df(null);
								}
								// --- SE AUTO MOVIMENTAÇÃO ESTIVER MARCADO REALIZE A PASSAGEM
								if(acervo.isAcer_in_automovimentacao()) {
									// --- REALIZA A PASSAGEM E ATUALIZA A DATA REALIZADA
									acervo.setAcer_in_status("FI");
									acervo.setAcer_dt_realmvt_fc_fi(dataAtual);
								}
							}
						}
					}
				}
				if(acervo.getAcer_in_status().equals("FI"))
				{
					if(acervo.getObjClasse().getClas_cd_classe()!=null) {
						if(acervo.getObjClasse().getClas_tx_faseintermediaria()!=null && acervo.getObjClasse().getClas_tx_faseintermediaria()!="") {
							gravaAcervo=true; // --- Seta indicador para gravação do registro na base
							if(Integer.parseInt(acervo.getObjClasse().getClas_tx_faseintermediaria())>0) {
								if(acervo.getObjClasse().getClas_tx_fasecorrente().equals(FaseCorrente.ANO)) {
									// --- Se a FC for ANO incremente em função da data de referência, caso contrário em função da data de final de vigência.
									dataCalc = Funcoes.addYearInDate(acervo.getObjClasse().getClas_nr_fasecorrente(), acervo.getAcer_dt_referencia());
									dataCalc = Funcoes.addYearInDate(Integer.parseInt(acervo.getObjClasse().getClas_tx_faseintermediaria()), dataCalc);
								} else {
									if (acervo.getAcer_dt_finalvigencia()!=null) {
										dataCalc = Funcoes.addYearInDate(Integer.parseInt(acervo.getObjClasse().getClas_tx_faseintermediaria()), acervo.getAcer_dt_finalvigencia());
									} else {
										dataCalc = null;
									}
								}
								// --- SE DATA ATUAL FOR MAIOR QUE A DATA DO CALCULO AVANCE O STATUS
								datresult = Funcoes.compareDate(dataAtual,dataCalc);
								if(datresult==2 && acervo.isAcer_in_automovimentacao()) {
									// --- REALIZA A PASSAGEM E ATUALIZA A DATA REALIZADA
									acervo.setAcer_in_status("DF");
									acervo.setAcer_dt_realmvt_fi_df(dataAtual);
								} else {
									// --- ATUALIZA A DATA PREVISTA PARA PASSAGEM
									acervo.setAcer_dt_prevmvt_fi_df(dataCalc);
								}
							} else {
								// --- SE ANOS FOR IGUAL = 0 OU NULO - REALIZA A PASSAGEM PARA A PRÓXIMA FASE E ATUALIZA A DATA REALIZADA
								acervo.setAcer_in_status("DF");
								acervo.setAcer_dt_prevmvt_fi_df(dataAtual);
								acervo.setAcer_dt_realmvt_fi_df(dataAtual);
							}
						}
					}
				}
			}
			// --- GRAVA A MOVIMENTACAO DO ACERVO
			if(gravaAcervo) {
				try {
					acervoManager.salvarMovimentacao(acervo);
				} catch (Exception e) {
					// TODO: handle exception
					throw new DaoException(e);
				}
			}
		}
	}

	/*
	 * ************************************************************************************************
	 * GERA RELATÓRIO DE AUTO MOVIMENTACAO
	 * ************************************************************************************************
	 */
	
	public void geraRelatorioAutoMovimentacao() throws Exception {
		try {
			List<AcervoDTO> acervoDTOs = new ArrayList<AcervoDTO>();
			String nomeRelatorio="";
			DescritorManager descritorManager = new DescritorManager();
			DocumentoManager documentoManager = new DocumentoManager();
			// --- CARREGANDO ACERVO ATUALIZADO
			AcervoManager acervoManager = new AcervoManager();
			acervos = acervoManager.listaAcervoMovimentacao(cliente, acervo.getObjUnidade(), acervo.getObjClasse(), dat_ini_periodo, dat_fim_periodo);
			// --- CARREGADA OBJETO DE TRABALHO
			for (Acervo acervo : acervos) {
				AcervoDTO objAcervo = new AcervoDTO();
				objAcervo.setId(acervo.getId());
				objAcervo.setAcer_ds_arquivodigital(acervo.getAcer_ds_arquivodigital());
				objAcervo.setAcer_ds_assunto(acervo.getAcer_ds_assunto());
				objAcervo.setAcer_ds_localarquivo(acervo.getAcer_ds_localarquivo());
				objAcervo.setAcer_dt_finalvigencia(acervo.getAcer_dt_finalvigencia());
				objAcervo.setAcer_dt_inclusao(acervo.getAcer_dt_inclusao());
				objAcervo.setAcer_dt_prevmvt_fc_fi(acervo.getAcer_dt_prevmvt_fc_fi());
				objAcervo.setAcer_dt_prevmvt_fi_df(acervo.getAcer_dt_prevmvt_fi_df());
				objAcervo.setAcer_dt_realmvt_fc_fi(acervo.getAcer_dt_realmvt_fc_fi());
				objAcervo.setAcer_dt_realmvt_fi_df(acervo.getAcer_dt_realmvt_fi_df());
				objAcervo.setAcer_dt_referencia(acervo.getAcer_dt_referencia());
				objAcervo.setAcer_in_automovimentacao(acervo.isAcer_in_automovimentacao());
				objAcervo.setAcer_in_status(acervo.getAcer_in_status());
				objAcervo.setAcer_nr_codbarras(acervo.getAcer_nr_codbarras());
				objAcervo.setAcer_tx_hierarquia(acervo.getAcer_tx_hierarquia());
				objAcervo.setAcer_tx_observacao(acervo.getAcer_tx_observacao());
				// --- Selecionando o descritores do acervo
				descritors = descritorManager.pesquisaPorAcervo(acervo);
				objAcervo.setDescritors(descritors);
				// --- Verifica se acervo tem documentos atrelados
				documentos = documentoManager.listaDocumentosAcervo(acervo);
				if(documentos!=null && documentos.size()>0) {
					objAcervo.setAcer_in_documento(true);
				} else {
					objAcervo.setAcer_in_documento(false);
				}
				objAcervo.setDocumentos(documentos);
				objAcervo.setObjClasse(acervo.getObjClasse());
				objAcervo.setObjCliente(acervo.getObjCliente());
				objAcervo.setObjUnidade(acervo.getObjUnidade());
				acervoDTOs.add(objAcervo);
			}
			nomeRelatorio = "Rel_AcervoMovimentacao";
			// --- Gera relatório
			String caminho = "";
			String logo_rlt = "";
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
			JasperPrint preencher = new JasperPrint();
			preencher = JasperFillManager.fillReport(pathReport, parameters,new JRBeanCollectionDataSource(acervoDTOs));
				
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private boolean validaFiltroMovimentacao() throws Exception {
		boolean result = true;
		// --- VALIDANDO DATAS
		if (this.dat_ini_periodo==null && this.dat_fim_periodo==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datas inválidas. Verifique!", null));
		} else {
			if (Funcoes.compareDate(dat_ini_periodo, dat_fim_periodo)==2) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de período final menor que a inicial. Verifique!", null));
			}
		}

		return result;
	}

	public void onNodeSelect(NodeSelectEvent event) throws Exception {
		RequestContext.getCurrentInstance().execute("PF('popUpClasse').hide()");
		classeSel = (Classe) event.getTreeNode().getData();
		if (classeSel.getClas_tx_fasecorrente().equals("STP") || classeSel.getClas_tx_destinacaofinal().equals("STP")) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Classe sem temporalidade definida. Verifique!", null));
		} else {
			if (classeSel.getClas_nr_fasecorrente()!=null && classeSel.getClas_nr_fasecorrente() > 0) {
				classeSel.setClas_ds_fasecorrente(classeSel.getClas_nr_fasecorrente().toString() + " " + classeSel.getClas_tx_fasecorrente() + "(s)");
				this.bloqVigencia=true;
			} else {
				classeSel.setClas_ds_fasecorrente(classeSel.getClas_tx_fasecorrente());
				this.bloqVigencia=false;
			}
			acervo.setObjClasse(classeSel);
			// --- Inicializa os campos do acervo que sofrem impacto desta alteração de classe
			acervo.setAcer_dt_finalvigencia(null);
			acervo.setAcer_dt_prevmvt_fc_fi(null);
			acervo.setAcer_dt_prevmvt_fi_df(null);
			acervo.setAcer_dt_realmvt_fc_fi(null);
			acervo.setAcer_dt_realmvt_fi_df(null);
			acervo.setAcer_dt_referencia(null);
			acervo.setAcer_in_automovimentacao(false);
			acervo.setAcer_in_status("FC");
			if(acervo.getId()==null) {
				this.bloqBotoes=false;
			}
			buscaHierarquia();
			atualizarAcervo();
		}
	}

	private void buscaHierarquia() {
		Classe hierClasse = classeSel;
		String[] estrutura = new String[10];
		Integer cont=0;
		StringWriter hierarquia = new StringWriter();
		StringWriter prefixo = new StringWriter();
		hierarquia.write("");
		prefixo.write("");
		if (classeSel!=null) {
			while(hierClasse.getObjClasse()!=null) {
				estrutura[cont] = hierClasse.getObjClasse().getClas_cd_classe() + "-" + hierClasse.getObjClasse().getClas_ds_nome() + "\n";
				cont++;
				hierClasse = hierClasse.getObjClasse();
			}
			cont = estrutura.length-1;
			for (int i = cont; i > -1; i--) {
				if(estrutura[i]!=null) {
					if((estrutura[i].lastIndexOf("01-Classe"))<0) {
						hierarquia.append(prefixo.toString() + estrutura[i]);
						prefixo.append("  ");
					}
				}
			}
		}
		acervo.setAcer_tx_hierarquia(hierarquia.toString());
	}
	
	private void montaDadosTree(TreeNode pai, List<Classe> lista) {
		if(lista != null && lista.size()>0) {
			Classe comparator = new Classe();
			Collections.sort(lista, comparator);
			TreeNode filho = null;
			for (Classe classe : lista) {
				filho = new DefaultTreeNode(classe, pai);
				this.montaDadosTree(filho, classe.getClas_filhas());
			}
		}
	}

	public void selecionarUnidade() throws Exception
	{
		if(unidadeSel!=null) {
			acervo.setObjUnidade(unidadeSel);
		}
	}
	

	public void salvar() throws Exception
	{
		try {
			AcervoManager acervoManager = new AcervoManager();
			acervoManager.salvarMovimentacao(acervo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
	}

	/*
	 * ************************************************************************************************************************************************************************************
	 * GETs e SETs
	 * ************************************************************************************************************************************************************************************
	 */
	
	public Acervo getAcervo() {
		return acervo;
	}
	public void setAcervo(Acervo acervo) {
		this.acervo = acervo;
	}
	public Acervo getFiltro() {
		return filtroAcervo;
	}
	public void setFiltro(Acervo filtroAcervo) {
		this.filtroAcervo = filtroAcervo;
	}
	public Acervo getAcervoSel() {
		return acervoSel;
	}
	public void setAcervoSel(Acervo acervoSel) {
		this.acervoSel = acervoSel;
	}
	public List<Acervo> getAcervos() {
		return acervos;
	}
	public void setAcervos(List<Acervo> acervos) {
		this.acervos = acervos;
	}
	public CommonsManager getCommonsManager() {
		return commonsManager;
	}

	public Unidade getUnidadeSel() {
		return unidadeSel;
	}

	public void setUnidadeSel(Unidade unidadeSel) {
		this.unidadeSel = unidadeSel;
	}

	public Classe getClasseSel() {
		return classeSel;
	}

	public void setClasseSel(Classe classeSel) {
		this.classeSel = classeSel;
	}

	public List<Descritor> getSeldescritors() {
		return seldescritors;
	}

	public void setSeldescritors(List<Descritor> seldescritors) {
		this.seldescritors = seldescritors;
	}

	public boolean isDatBloqueada() {
		return datBloqueada;
	}

	public void setDatBloqueada(boolean datBloqueada) {
		this.datBloqueada = datBloqueada;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public Classe getFiltroClasse() {
		return filtroClasse;
	}

	public void setFiltroClasse(Classe filtroClasse) {
		this.filtroClasse = filtroClasse;
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

	public TreeNode getClassesTree() throws ControllerException {
		if(this.classesTree == null) {
			ClasseManager classeManager = new ClasseManager();
			List<Classe> classes = classeManager.listaClassesPai();
			this.classesTree = new DefaultTreeNode(null, null);
			this.montaDadosTree(this.classesTree, classes);
		}
		return this.classesTree;
	}

	public void setClassesTree(TreeNode classesTree) {
		this.classesTree = classesTree;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public Classe getEditada() {
		return editada;
	}

	public void setEditada(Classe editada) {
		this.editada = editada;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public boolean isBloqBotoes() {
		return bloqBotoes;
	}

	public void setBloqBotoes(boolean bloqBotoes) {
		this.bloqBotoes = bloqBotoes;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento getDocumentoSel() {
		return documentoSel;
	}

	public void setDocumentoSel(Documento documentoSel) {
		this.documentoSel = documentoSel;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

    public String getFmtRlt() {
		return fmtRlt;
	}

	public void setFmtRlt(String fmtRlt) {
		this.fmtRlt = fmtRlt;
	}

	public String getFmtEtq() {
		return fmtEtq;
	}

	public void setFmtEtq(String fmtEtq) {
		this.fmtEtq = fmtEtq;
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
	
	public boolean isManterDados() {
		return manterDados;
	}

	public void setManterDados(boolean manterDados) {
		this.manterDados = manterDados;
	}

	public boolean isPossoIncluir() {
		return possoIncluir;
	}

	public void setPossoIncluir(boolean possoIncluir) {
		this.possoIncluir = possoIncluir;
	}

	public Descritor getDescritorSel() {
		return descritorSel;
	}

	public void setDescritorSel(Descritor descritorSel) {
		this.descritorSel = descritorSel;
	}
	
	public List<Descritor> getDescritors() {
		return descritors;
	}
	
	public void setDescritors(List<Descritor> descritors) {
		this.descritors = descritors;
	}

	public Descritor getFiltroDescritor() {
		return filtroDescritor;
	}
	
	public void setFiltroDescritor(Descritor filtroDescritor) {
		this.filtroDescritor = filtroDescritor;
	}
	
	public boolean isBloqVigencia() {
		return bloqVigencia;
	}

	public void setBloqVigencia(boolean bloqVigencia) {
		this.bloqVigencia = bloqVigencia;
	}

	public Integer getCodEtqInicial() {
		return codEtqInicial;
	}

	public void setCodEtqInicial(Integer codEtqInicial) {
		this.codEtqInicial = codEtqInicial;
	}

	public Integer getCodEtqFinal() {
		return codEtqFinal;
	}

	public void setCodEtqFinal(Integer codEtqFinal) {
		this.codEtqFinal = codEtqFinal;
	}
	
	public String getTpRlt() {
		return tpRlt;
	}

	public void setTpRlt(String tpRlt) {
		this.tpRlt = tpRlt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acervo == null) ? 0 : acervo.hashCode());
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
		MovimentacaoView other = (MovimentacaoView) obj;
		if (acervo == null) {
			if (other.acervo != null)
				return false;
		} else if (!acervo.equals(other.acervo))
			return false;
		return true;
	}
	
}

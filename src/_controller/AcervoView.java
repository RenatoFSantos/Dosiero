package _controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

import _business.AcervoManager;
import _business.ClasseManager;
import _business.CommonsManager;
import _business.DescritorManager;
import _business.DocumentoManager;
import _business.UnidadeManager;
import _model.domain.FaseCorrente;
import _model.dto.AcervoDTO;
import _model.dto.UnidadeDTO;
import _model.vo.Acervo;
import _model.vo.AcervoDescritor;
import _model.vo.Classe;
import _model.vo.Cliente;
import _model.vo.Descritor;
import _model.vo.Documento;
import _model.vo.Tipo;
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

@ManagedBean(name="acervoView")
@RequestScoped
public class AcervoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2347624684261481522L;
	private Acervo acervo;
	private Acervo filtro;
	private Acervo acervoSel = null;
	private Unidade unidadeSel = null;
	private Classe classeSel = null;
	private Unidade filtroUnidade;
	private Classe filtroClasse;
	private CommonsManager commonsManager;
	private Descritor filtroDescritor;
	private ClasseManager classeManager;
	private UnidadeManager unidadeManager;
	private DocumentoManager documentoManager;
	private DescritorManager descritorManager;
	private AcervoManager acervoManager;
	private boolean datBloqueada = true;
	private TreeNode classesTree;
	private TreeNode selectedNode;
	private Classe editada = new Classe();
	private Documento documento;
	private Documento documentoSel;
	private boolean bloqBotoes = false;
	int datresult=0;
	private String caminho;
	private String arquivo;
	private StreamedContent file;
	private String arqDestino;
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
	private List<AcervoDescritor> acervoDescritors = new ArrayList<AcervoDescritor>();
	private Integer proxId;
	private Cliente cliente;
	private Integer codEtqInicial;
	private Integer codEtqFinal;
	
	public AcervoView() {
		filtro = new Acervo();
		filtroUnidade = new Unidade();
		filtroClasse = new Classe();
		filtroDescritor = new Descritor();
		//acervo = new Acervo(); // --- MNT_20160620
	}
	
	public void excluirDescritor() {
		if(seldescritors!=null && seldescritors.size()>0) {
			if (seldescritors.contains(descritorSel)) {
				seldescritors.remove(descritorSel);
			}
		}
	}

	public void selecionarDescritor()
	{
		if(!seldescritors.contains(descritorSel)) {	
			seldescritors.add(descritorSel);
		}
	}
	
	public String relUnidade() throws Exception {
		acervo = new Acervo();
		return "/pages/acervo/relacervo.xhtml";
	}
	
	public String relDigitacao() throws Exception {
		acervo = new Acervo();
		return "/pages/acervo/reldigitacao.xhtml";
	}

	public String relAcervoClasse() throws Exception {
		acervo = new Acervo();
		return "/pages/acervo/relacervoclasse.xhtml";
	}
	
	public String relAcervoLocal() throws Exception {
		acervo = new Acervo();
		return "/pages/acervo/relacervolocal.xhtml";
	}
	
	public void geraRelatorioAcervoPorClasse() throws Exception {
		// --- RELATORIO DE ACERVO X CLASSE
		try {
			if (validaFiltroRelatorioAcervoxClasse()) {
				List<AcervoDTO> acervoDTOs = new ArrayList<AcervoDTO>();
				AcervoManager acervoManager = new AcervoManager();
				// --- Identifica qual é o cliente atual
				Usuario usua;
				usua=(Usuario)ContextApp.getSession().getAttribute("usuariologado");
				acervo.setObjCliente(usua.getObjCliente());
				// --- Filtra a unidade do acervo
				acervoDTOs = acervoManager.listaAcervoPorClasse(acervo.getObjCliente(), acervo.getObjClasse(), dat_ini_periodo, dat_fim_periodo);
				// --- Gera relatório
				String caminho = "";
				String logo_rlt = "";
				String nomeRelatorio = "Rel_AcervoXClasse";
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
				JasperPrint preencher = JasperFillManager.fillReport(pathReport, parameters,new JRBeanCollectionDataSource(acervoDTOs));
					
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
	
	public void geraRelatorioUnidade() throws Exception {
		// TODO RELATORIO DE UNIDADE
		try {
			if (validaFiltroRelatorio()) {
				List<UnidadeDTO> unidadeDTOs = new ArrayList<UnidadeDTO>();
				AcervoManager acervoManager = new AcervoManager();
				// --- Filtra a unidade do acervo
				unidadeDTOs = acervoManager.listaAcervoPorUnidade(acervo.getObjUnidade(), dat_ini_periodo, dat_fim_periodo);
				// --- Gera relatório
				String caminho = "";
				String logo_rlt = "";
				String nomeRelatorio = "Rel_Unidade";
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
				JasperPrint preencher = JasperFillManager.fillReport(pathReport, parameters,new JRBeanCollectionDataSource(unidadeDTOs));
					
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
	
	public void geraRelatorioAcervoXLocal() throws Exception {
		// RELATORIO DE ACERVO POR LOCAL
		try {
			if (validaFiltroRelatorio()) {
				List<UnidadeDTO> unidadeDTOs = new ArrayList<UnidadeDTO>();
				AcervoManager acervoManager = new AcervoManager();
				// --- Filtra a unidade do acervo
				unidadeDTOs = acervoManager.listaAcervoPorUnidadeOrdemLocal(acervo.getObjUnidade(), dat_ini_periodo, dat_fim_periodo);
				// --- Gera relatório
				String caminho = "";
				String logo_rlt = "";
				String nomeRelatorio = "Rel_AcervoXLocal";
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
				JasperPrint preencher = JasperFillManager.fillReport(pathReport, parameters,new JRBeanCollectionDataSource(unidadeDTOs));
					
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

	private boolean validaFiltroRelatorioAcervoxClasse() throws Exception {
		boolean result = true;
		// --- VALIDANDO DATAS
		if (this.dat_ini_periodo==null && this.dat_fim_periodo==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datas inválidas. Verifique!", null));
		} else {
			if (Funcoes.compareDate(dat_ini_periodo, dat_fim_periodo)==2) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de período final menor que a inicial. Verifique!", null));
			}
		}

		if (ContextApp.getContext().getMessages().hasNext()) {
			result = false;
		}

		return result;
	}

	private boolean validaFiltroRelatorio() throws Exception {
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
		if(acervo.getObjUnidade()!=null && acervo.getObjUnidade().getUnid_nm_unidade()==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unidade inválida. Verifique!", null));
		}

		if (ContextApp.getContext().getMessages().hasNext()) {
			result = false;
		}

		return result;
	}

	public void geraRelatorioDigitacao() throws Exception {
		try {
			if (validaFiltroRelatorio()) {
				List<UnidadeDTO> unidadeDTOs = new ArrayList<UnidadeDTO>();
				List<AcervoDTO> acervoDTOs = new ArrayList<AcervoDTO>();
				AcervoManager acervoManager = new AcervoManager();
				String nomeRelatorio="";
				// --- Identifica qual é o cliente atual
				Usuario usua;
				usua=(Usuario)ContextApp.getSession().getAttribute("usuariologado");
				acervo.setObjCliente(usua.getObjCliente());
				// --- Filtra dados de acordo com o Tipo selecionado
				if(tpRlt.equals("D")) {
					// --- Digitação
					unidadeDTOs = acervoManager.listaAcervoPorDigitacao(acervo.getObjCliente(), acervo.getObjUnidade(), acervo.getObjClasse(), dat_ini_periodo, dat_fim_periodo);
					nomeRelatorio = "Rel_Digitacao";
				} else {
					// --- Completo
					acervoDTOs = acervoManager.listaAcervoCompleto(acervo.getObjCliente(), acervo.getObjUnidade(), acervo.getObjClasse(), dat_ini_periodo, dat_fim_periodo);
					nomeRelatorio = "Rel_AcervoCompleto";
				}
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
				if(tpRlt.equals("D")) {
					preencher = JasperFillManager.fillReport(pathReport, parameters,new JRBeanCollectionDataSource(unidadeDTOs));
				} else {
					preencher = JasperFillManager.fillReport(pathReport, parameters,new JRBeanCollectionDataSource(acervoDTOs));
				}
					
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

	public String relEtq6180() throws Exception {
		return "/pages/acervo/reletq6180.xhtml";
	}
	
	public void geraRelatorioEtq6180() throws Exception {
		try {
			if (validaFiltroEtq()) {
				List<AcervoDTO> acervoDTOs = new ArrayList<AcervoDTO>();
				AcervoManager acervoManager = new AcervoManager();
				// --- Identifica qual é o cliente atual
				Usuario usua;
				usua=(Usuario)ContextApp.getSession().getAttribute("usuariologado");
				// --- VERIFICANDO QUAL O FILTRO QUE SERÁ APLICADO (DATAS OU IDs)
				if(this.dat_ini_periodo!=null) {
					// --- Filtra dados por data de inclusão
					acervoDTOs = acervoManager.listaAcervoPorDataInclusao(usua.getObjCliente(), dat_ini_periodo, dat_fim_periodo);
				} else {
					if(this.codEtqInicial!=null) {
						acervoDTOs = acervoManager.listaAcervoPorCodigoAcervo(usua.getObjCliente(), this.codEtqInicial, this.codEtqFinal);
					}
				}
				
				// --- Gera relatório
				String caminho = "";
				String logo_rlt = "";
				String nomeRelatorio = "Rel_Etq_6180";
				String rltXLS = "";
				caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath(File.separator + "reports" + File.separator);
				logo_rlt = FacesContext.getCurrentInstance().getExternalContext().getRealPath(File.separator + "resources" + File.separator + "img" + File.separator + "rptubm.jpg");
				rltXLS = caminho + File.separator + nomeRelatorio + ".xls";
					
				// HashMap de parametros
		        HashMap<String, Object> parameters = new HashMap<String, Object>();
		        parameters.put("Logo", logo_rlt);

		        String rltJRXML = caminho + File.separator + nomeRelatorio + ".jrxml";
					
		        FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				ServletOutputStream responseStream = response.getOutputStream();

				// - Compila o Relatório
				JasperReport pathReport = JasperCompileManager.compileReport(rltJRXML);
				// - Altera a fonte de dados
				JasperPrint preencher = JasperFillManager.fillReport(pathReport, parameters,new JRBeanCollectionDataSource(acervoDTOs));
					
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
	
	private boolean validaFiltroEtq() throws Exception {
		boolean result = true;
		// --- VALIDANDO DATAS
		if (this.dat_ini_periodo!=null && this.dat_fim_periodo!=null) {
			if (Funcoes.compareDate(dat_ini_periodo, dat_fim_periodo)==2) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de período final menor que a inicial. Verifique!", null));
			}
		} else {
			if ((this.dat_ini_periodo==null && this.dat_fim_periodo!=null) || (this.dat_ini_periodo!=null && this.dat_fim_periodo==null)) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Período incorreto. Verifique!", null));
			}
		}
		// --- VALIDANDO FAIXA DE CÓDIGOS
		if (this.codEtqInicial!=null && this.codEtqFinal!=null) {
			if(this.codEtqFinal<this.codEtqInicial) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código da etiqueta final menor que inicial. Verifique!", null));
			}
		} else {
			if (this.codEtqInicial!=null) {
				this.codEtqFinal=999999; // --- Atribuindo o valor máximo para a etiqueta final.
			}
		}
		// --- VERIFICANDO SE ALGUM ALGUM DOS CAMPOS FOI PREENCHIDO
		if (this.dat_ini_periodo==null && this.dat_fim_periodo==null && this.codEtqInicial==null && this.codEtqFinal==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uma das condições de filtro deve ser preenchida. Verifique!", null));
		}
		
		if (ContextApp.getContext().getMessages().hasNext()) {
			result = false;
		}

		return result;
	}
	
	public void inserirDocumento() {
		documento = new Documento();
		documento.setDocu_dt_inclusao(Funcoes.getPegaDataAtual());
		RequestContext.getCurrentInstance().execute("PF('popUpDocumento').show()");
	}
	
	public void listaUnidades() {
		unidadeManager = new UnidadeManager();
		try {
			unidades=unidadeManager.listaUnidades();
		} catch (ControllerException e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("PF('popUpUnidadeAcervo').show()");
	}
	
	public void listaClasses() {
		classeManager = new ClasseManager();
		try {
			classes=classeManager.listaClasses();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("PF('popUpClasseAcervo').show()");
	}
	
	public void listaDescritores() {
		descritors = new ArrayList<Descritor>();
		descritorManager = new DescritorManager();
		try {
			// -- Carrega todos os descritores existentes para seleção do usuário
			descritors=descritorManager.listaDescritors();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("PF('popUpDescritor').show()");
	}
	
	public void uploadDocumento() {
		documento = documentoSel;
		RequestContext.getCurrentInstance().execute("PF('popUpUpload').show()");
	}

	public void adicionarDocumento() throws Exception {
		Documento meuDoc;
		boolean adicionaDoc;
		if(documento.getId()==null) {
			documento.setObjAcervo(acervo);
			if(acervo.getDocumentos().size()>0) {
				adicionaDoc = true;
				for (int i = 0; i < acervo.getDocumentos().size(); i++) {
					meuDoc = new Documento();
					meuDoc = (Documento)acervo.getDocumentos().get(i);
					if (meuDoc.getDocu_nm_documento().equals(documento.getDocu_nm_documento())) {
						adicionaDoc = false;
						i=acervo.getDocumentos().size()+1; // --- Forçando saída do for
					}
				}
				if(adicionaDoc) {
					acervo.getDocumentos().add(documento);
				}
			} else {
				acervo.getDocumentos().add(documento);
			}
		}
		RequestContext.getCurrentInstance().execute("PF('popUpDocumento').hide()");
	}
	
	public void validaCodBarras() throws ControllerException {
		proxId = 0;
		AcervoManager acervoManager = new AcervoManager();
		if(acervo.getAcer_nr_codbarras()==null || acervo.getAcer_nr_codbarras().equals("")) {
			// --- Gerando código de barras para acervo novo ou colocando o mesmo id do acervo para casos já existentes
			if(acervo.getId()!=null) {
				proxId = acervo.getId();
			} else {
				proxId = acervoManager.proximoID() + 1;
			}
			acervo.setAcer_nr_codbarras(geraNovoCodBarras(proxId));
			// _______________________________________________________
		}

	}
	
	public String geraNovoCodBarras(Integer proxID) {
		String result = "";
		Usuario usuariologado = (Usuario)ContextApp.getSession().getAttribute("usuariologado");
		String codBrasil = "789";
		String codCliente = String.format("%03d", usuariologado.getObjCliente().getId());
		String codDocumento = String.format("%06d", proxID);
		result =  codBrasil+codCliente+codDocumento;
		return result;
	}

	public void editarDoc() {
		documento = documentoSel;
		RequestContext.getCurrentInstance().execute("PF('popUpDocumento').show()");
	}
	
	public void excluirDoc() {
		acervo.getDocumentos().remove(documentoSel);
	}

    /*
     * ****************************************************************************************************
     * ROTINA DE UPLOAD DOS DOCUMENTOS VINCULADOS AO ACERVO
     * ****************************************************************************************************
     */

    public void upload(FileUploadEvent event) {
    	arquivo = event.getFile().getFileName();
    	arqDestino = FacesContext.getCurrentInstance().getExternalContext().getRealPath(File.separator+"files"+File.separator);
		ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "O Arquivo " + arquivo + " foi carregado com sucesso!.", null));
        // Do what you want with the file        
        try {
            copyFile(arquivo, event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // --- SETANDO CAMPO COM NOME DO ARQUIVO
        documento.setDocu_nm_arquivo(arquivo); 
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
	
	/*
	 * ************************************************************************************************************************************************************************************
	 */
	
    public String listaDocumentosAcervo() throws Exception
	{
		DocumentoManager documentoManager = new DocumentoManager();

		documentos = documentoManager.listaDocumentosAcervo(acervo);

		return "/pages/documento/caddocumento.xhtml";
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	
	public void onNodeSelect(NodeSelectEvent event) throws Exception {
		RequestContext.getCurrentInstance().execute("PF('popUpClasseAcervo').hide()");
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

	public String listaAcervos() throws Exception
	{
		AcervoManager acervoManager = new AcervoManager();
		cliente = (Cliente)((Usuario)ContextApp.getSession().getAttribute("usuariologado")).getObjCliente();
		acervos = acervoManager.listaAcervoPorCliente(cliente);
		return "/pages/acervo/cadacervo.xhtml";
	}

	public void selecionarUnidade() throws Exception
	{
		acervo.setObjUnidade(unidadeSel);
		if(acervo.getId()==null) {
			this.bloqBotoes=false;
		}
	}

	public List<Acervo> pesquisar() throws Exception
	{
		AcervoManager acervoManager = new AcervoManager();
		//filtro = new Acervo();
		//filtro.setAcer_ds_assunto(filtro.getAcer_ds_assunto().toUpperCase());
		acervos = acervoManager.pesquisa(filtro);
		return acervos;
	}	

	public List<Acervo> pesquisarAssunto() throws Exception
	{
		AcervoManager acervoManager = new AcervoManager();
		List<Acervo> listaAcervo = new ArrayList<Acervo>();
		listaAcervo = acervoManager.pesquisaAssunto(filtro);
		return listaAcervo;
	}	

	public void pesquisarDescritor() throws Exception
	{
		DescritorManager descritorManager = new DescritorManager();
		this.descritors = descritorManager.pesquisa(filtroDescritor);
		if(descritors.size()>0) {
			possoIncluir=true;
		} else {
			possoIncluir=false;
		}
	}
	
	public void incluirDescritor() throws Exception {
		DescritorManager descritorManager = new DescritorManager();
		descritorManager.salvar(filtroDescritor);
		pesquisarDescritor();
		possoIncluir=true;
	}
	
	public List<Unidade> pesquisarUnidades() throws Exception
	{
		UnidadeManager unidadeManager = new UnidadeManager();
		//filtroUnidade = new Unidade();
		unidades = unidadeManager.pesquisa(filtroUnidade);
		return unidades;
	}
	
	public List<Classe> pesquisarClasses() throws Exception
	{
		ClasseManager classeManager = new ClasseManager();
		//filtroClasse = new Classe();
		classes = classeManager.pesquisa(filtroClasse);
		return classes;
	}
	
	/*
	 * *********************************************************************************************************************************************************************
	 * ROTINA DE ATUALIZAÇÃO DAS DATAS DO ACERVO
	 * *********************************************************************************************************************************************************************
	 */
	public void atualizarAcervo() throws Exception {
		Date dataAtual = Funcoes.getPegaDataAtual();
		Date dataCalc = new Date();
		Date dataCalcProxFase = new Date();
		datresult=0;
		// --- VERIFICANDO SE DATA DE REFERENCIA ESTÁ PREENCHIDA
		if(acervo.getAcer_dt_finalvigencia()!=null || acervo.getAcer_dt_referencia()!=null) {
			// --- IDENTIFICANDO O STATUS ATUAL
			if(acervo.getAcer_in_status().equals("FC"))
			{
				if(acervo.getObjClasse().getClas_cd_classe()!=null) {
					if(acervo.getObjClasse().getClas_tx_fasecorrente().equals("ANO") && acervo.getObjClasse().getClas_nr_fasecorrente()>0 && acervo.getAcer_dt_referencia()!=null) {
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
						if(datresult==2) {
							// --- REALIZA A PASSAGEM E ATUALIZA A DATA REALIZADA
							acervo.setAcer_in_status("FI");
							acervo.setAcer_dt_realmvt_fc_fi(dataAtual);
						}
					} else {
						// --- PARA AS OUTRAS SITUAÇÕES
						if(acervo.getAcer_dt_finalvigencia()!=null) {
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
							//datresult = Funcoes.compareDate(dataAtual,dataCalc);
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
							if(datresult==2) {
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
		verificaAutoMovimentacao();
	}

	public String inserir() throws Exception
	{
		acervo = new Acervo();
		acervo.setAcer_dt_inclusao(Funcoes.getPegaDataAtual());
		acervo.setObjCliente(((Usuario)ContextApp.getSession().getAttribute("usuariologado")).getObjCliente());
		acervo.setAcer_in_status("FC");
		acervo.setDocumentos(documentos); // --- MNT_20160620
		acervo.setAcervoDescritors(acervoDescritors); // --- MNT_20160620
		acervo.setObjTipo(new Tipo());
		// this.bloqBotoes=false; // --- MNT_20160620 
		return "/pages/acervo/cadacervodetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		acervo = acervoSel;
		if (acervo.getObjClasse().getClas_nr_fasecorrente()!=null && acervo.getObjClasse().getClas_nr_fasecorrente() > 0) {
			acervo.getObjClasse().setClas_ds_fasecorrente(acervo.getObjClasse().getClas_nr_fasecorrente().toString() + " " + acervo.getObjClasse().getClas_tx_fasecorrente() + "(s)");
		} else {
			acervo.getObjClasse().setClas_ds_fasecorrente(acervo.getObjClasse().getClas_tx_fasecorrente());
		}
		
		// --- Carrega subclasses do Acervo - Documentos e Descritores
		acervoManager = new AcervoManager();
		acervo = acervoManager.carregaSubClassesDoAcervo(acervo);
		
		// --- Identifica os descritores selecionados na lista de descritores.
		seldescritors = new ArrayList<Descritor>();
		if(acervo.getAcervoDescritors()!=null && acervo.getAcervoDescritors().size()>0) {
			for (AcervoDescritor lista : acervo.getAcervoDescritors()) {
				Descritor obj = new Descritor();
				obj = lista.getObjDescritor();
				seldescritors.add(obj);
			}
		}

		/*
		 * 
		// --- Carrega documentos relacionados
		List<Documento> listaDocumentos = new ArrayList<Documento>();
		listaDocumentos = carregaDocumentosRelacionados();
		acervo.setDocumentos(listaDocumentos);
		// --- Carrega descritores relacionados
		seldescritors = new ArrayList<Descritor>();
		seldescritors=carregaDescritoresRelacionados();
		// -----------------------------------------------------------
		*/
		
		verificaAutoMovimentacao();
		return "/pages/acervo/cadacervodetalhe.xhtml";
	}
	
	public List<Documento> carregaDocumentosRelacionados() throws Exception {
		List<Documento> docRelacionados = new ArrayList<Documento>();
		documentoManager = new DocumentoManager();
		docRelacionados=documentoManager.listaDocumentosAcervo(acervo);
		return docRelacionados;
	}
	
	public List<Descritor> carregaDescritoresRelacionados() throws Exception {
		List<Descritor> descRelacionados = new ArrayList<Descritor>();
		descritorManager = new DescritorManager();
		descRelacionados=descritorManager.pesquisaPorAcervo(acervo);
		return descRelacionados;
	}


	public String excluir() throws Exception
	{
		List<AcervoDescritor> listaDesc = new ArrayList<AcervoDescritor>();
		List<Documento> listaDoc = new ArrayList<Documento>();
		acervoSel.setAcer_in_delecao(1);
		// --- Carrega todos os descritores e documentos vinculados ao acervo selecionado
		AcervoManager acervoManager = new AcervoManager();
		listaDesc = acervoManager.buscaTodosDescritoresDoAcervo(acervoSel);
		listaDoc = acervoManager.buscaTodosDocumentosDoAcervo(acervoSel);
		acervoSel.setAcervoDescritors(listaDesc);
		acervoSel.setDocumentos(listaDoc);
		
		acervoManager.salvar(acervoSel);
	
		listaAcervos();
	
		ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
		return "/pages/acervo/cadacervo.xhtml";
	}

	public void verificaAutoMovimentacao() {
		// --- VERIFICA INDICADOR DE AUTOMOVIMENTACAO
		if(acervo.isAcer_in_automovimentacao()) {
			datBloqueada=true;
		} else {
			// Solicitado para manter bloqueado sempre
			datBloqueada=true;
		}
		
	}
	
	public void checkboxChanged(AjaxBehaviorEvent event) throws Exception {
		if(((SelectBooleanCheckbox)event.getSource()).isSelected()) {
			datBloqueada=true;
			// --- EXECUTA ROTINA PARA ATUALIZAÇÃO DAS DATAS DO ACERVO
			acervo.setAcer_in_automovimentacao(true);
			atualizarAcervo();
		} else {
			// Solicitado para manter bloqueado sempre
			datBloqueada=true;
		}
		
	}
	
	public void salvar() throws Exception
	{
		try {
			if (validaForm()) {

				// Carrega todos os descritores selecionados
				if(seldescritors!=null && seldescritors.size()>0) {
					AcervoDescritor itemAcervoDescritor;
					List<AcervoDescritor> lista = new ArrayList<AcervoDescritor>();
					for (Descritor itemDescritor : seldescritors) {
						itemAcervoDescritor = new AcervoDescritor();
						itemAcervoDescritor.setObjAcervo(acervo);
						itemAcervoDescritor.setObjDescritor(itemDescritor);
						lista.add(itemAcervoDescritor);
					}
					acervo.setAcervoDescritors(lista);
				}
				
				AcervoManager acervoManager = new AcervoManager();
				validaCodBarras(); // --- Validando código de barras, se não existir um código cria um novo.
				acervoManager.salvar(acervo);

				Unidade manterUnidade=new Unidade();
				Classe manterClasse=new Classe();
				Tipo manterTipo = new Tipo();
				String estruturaHierarquica = "";
				if(manterDados) {
					manterUnidade = acervo.getObjUnidade();
					manterClasse = acervo.getObjClasse();
					manterTipo = acervo.getObjTipo();
					estruturaHierarquica = acervo.getAcer_tx_hierarquia();
				}
				acervo = new Acervo();
				seldescritors.clear(); // --- Inicializa descritores relacionados
				acervo.setObjUnidade(manterUnidade);
				acervo.setObjClasse(manterClasse);
				acervo.setObjTipo(manterTipo);
				acervo.setAcer_tx_hierarquia(estruturaHierarquica);
				acervo.setAcer_dt_inclusao(Funcoes.getPegaDataAtual());
				acervo.setObjCliente(((Usuario)ContextApp.getSession().getAttribute("usuariologado")).getObjCliente());
				acervo.setAcer_in_status("FC");
				this.bloqBotoes=false;
				
				
				ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
		
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		datresult=0;
		
		// --- Unidade
		if(acervo.getObjUnidade()==null || acervo.getObjUnidade().getUnid_nm_unidade()==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unidade é obrigatória. Verifique!", null));
		}
		
		// --- SubClasse
		if(acervo.getObjClasse()==null || acervo.getObjClasse().getClas_ds_nome()==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "SubClasse é obrigatória. Verifique!", null));
		}
		
		// --- Assunto
		if(acervo.getAcer_ds_assunto()==null || acervo.getAcer_ds_assunto().equals("")) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Assunto é obrigatória. Verifique!", null));
		} else {
			filtro=acervo;
			boolean mesmoRegistro = false;
			List<Acervo> temAcervos = new ArrayList<Acervo>();
			temAcervos=pesquisarAssunto();
			// --- Verifica se existem acervos com este assunto
			if(temAcervos!=null && temAcervos.size()>0) {
				// -- Se acervo atual tem ID nulo é porque está inserindo um novo com o mesmo nome
				if(acervo.getId()==null) {
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Assunto já existente. Verifique!", null));
				} else {
					// --- Se acervo atual está sendo editado vamos verificar se o assunto não é o mesmo do registro atual. Se for não é erro, pois se trata do mesmo registro. 
					// --- Caso contrário se assunto pertence a outro registro, vamos enviar uma mensagem de erro.
					for (Acervo iacervo : temAcervos) {
						if(acervo.getId().equals(iacervo.getId())) {
							mesmoRegistro = true;
						}
					}
					if(!mesmoRegistro) {
						ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Assunto já existente. Verifique!", null));
					}
				}
			}
				
			filtro = new Acervo();
		}
		
		

		// DATAS PREVISTAS DE PASSAGEM
		if(acervo.getAcer_dt_prevmvt_fc_fi() == null || acervo.getAcer_dt_prevmvt_fi_df()==null) {
			datresult = 0;
		} else {
			datresult = Funcoes.compareDate(acervo.getAcer_dt_prevmvt_fc_fi(), acervo.getAcer_dt_prevmvt_fi_df());
			if (datresult == 2) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datas PREVISTAS de passagem fora da ordem cronológica. Verifique!", null));
			}
		}

		// DATAS REALIZADAS DE PASSAGEM
		if(acervo.getAcer_dt_realmvt_fc_fi() == null || acervo.getAcer_dt_realmvt_fi_df()==null) {
			datresult = 0;
		} else {
			datresult = Funcoes.compareDate(acervo.getAcer_dt_realmvt_fc_fi(), acervo.getAcer_dt_realmvt_fi_df());
			if (datresult == 2) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datas REALIZADAS de passagem fora da ordem cronológica. Verifique!", null));
			}
		}
		
		// DATA DE REFERENCIA EM RELAÇÃO AS DATAS DE PASSAGEM
		if(acervo.getAcer_dt_referencia() == null) {
			datresult = 0;
		} else {
			if (acervo.getAcer_dt_prevmvt_fc_fi()!=null) {
				datresult = Funcoes.compareDate(acervo.getAcer_dt_referencia(), acervo.getAcer_dt_prevmvt_fc_fi());
				if (datresult == 2) {
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de referencia superior a data de previsão de passagem de FC para FI. Verifique!", null));
				}
			}
			if (acervo.getAcer_dt_realmvt_fc_fi()!=null) {
				datresult = Funcoes.compareDate(acervo.getAcer_dt_referencia(), acervo.getAcer_dt_realmvt_fc_fi());
				if (datresult == 2) {
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de referencia superior a data de realização da passagem de FC para FI. Verifique!", null));
				}
			}
			if (acervo.getAcer_dt_prevmvt_fi_df()!=null) {
				datresult = Funcoes.compareDate(acervo.getAcer_dt_referencia(), acervo.getAcer_dt_prevmvt_fi_df());
				if (datresult == 2) {
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de referencia superior a data de previsão de passagem de FI para DF. Verifique!", null));
				}
			}
			if (acervo.getAcer_dt_realmvt_fi_df()!=null) {
				datresult = Funcoes.compareDate(acervo.getAcer_dt_referencia(), acervo.getAcer_dt_realmvt_fi_df());
				if (datresult == 2) {
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de referencia superior a data de realização da passagem de FI para DF. Verifique!", null));
				}
			}
		}


		if (ContextApp.getContext().getMessages().hasNext())
		{
			result = false;
		}

		return result;
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
		return filtro;
	}
	public void setFiltro(Acervo filtro) {
		this.filtro = filtro;
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

    public void setFile(StreamedContent file) {  
        this.file = file;  
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

	public StreamedContent getFile() throws FileNotFoundException {
    	documento = documentoSel;
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/files/"+documento.getDocu_nm_arquivo());
        file = new DefaultStreamedContent(stream, FacesContext.getCurrentInstance().getExternalContext().getMimeType(documento.getDocu_nm_arquivo()), documento.getDocu_nm_arquivo());
        return file;  
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
		AcervoView other = (AcervoView) obj;
		if (acervo == null) {
			if (other.acervo != null)
				return false;
		} else if (!acervo.equals(other.acervo))
			return false;
		return true;
	}
	
}

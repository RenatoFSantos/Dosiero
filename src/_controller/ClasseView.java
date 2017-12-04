package _controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import _business.AcervoManager;
import _business.ClasseManager;
import _model.dto.ClasseDTO;
import _model.vo.Acervo;
import _model.vo.Classe;
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


@ManagedBean(name="classeView")
@RequestScoped
public class ClasseView implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3627422395323448083L;
	private TreeNode classesTree;
	private TreeNode selectedNode;
	private Classe editada = new Classe();
	private Classe editadaPai = new Classe();
	private List<SelectItem> classesSelect;
	private boolean mostraEdicao = false;
	private boolean manterClasse = false;
	private String fmtRlt;

	public String entrada() throws Exception {
		return "/pages/classe/cadclasse.xhtml";
	}
	
	public String retorna() throws Exception
	{
		return "/pages/principal/principal.xhtml";
	}
	
	public String relClasse() throws Exception {
		fmtRlt="PDF";
		return "/pages/classe/relclasse.xhtml";
	}

	public void geraRelatorioClasse() throws Exception {
		String caminho = "";
		String logo_rlt = "";
		String nomeRelatorio = "Rel_Classe";
		String rltXLS = "";
		List<ClasseDTO> classeDTOs = new ArrayList<ClasseDTO>();
		if(editada!=null && editada.getId()!=null) {
			ClasseManager classeManager = new ClasseManager();
			classeDTOs = classeManager.buscaDadosRelatorioClasse(editada);
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
			JasperPrint preencher = JasperFillManager.fillReport(pathReport, parameters,new JRBeanCollectionDataSource(classeDTOs));
			
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
		} else {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione uma classe para o filtro.", null));
		}
	}
	
	public String inserir() throws Exception
	{
		Classe pai = null;
		ClasseManager classeManager = new ClasseManager();
		pai = classeManager.carrega(1);
		
		this.editada = new Classe();
		this.editada.setClas_cd_classe("0");
		this.editada.setObjClasse(pai);
		this.editada.setClas_tx_fasecorrente("ANO");
		this.editada.setClas_tx_faseintermediaria("0");
		this.editada.setClas_tx_fasecorrente("0");
		this.mostraEdicao=true;
		
		return "/pages/classe/cadclassedetalhe.xhtml";
		
	}
	
	public void onNodeSelectSubClasse(NodeSelectEvent event) throws Exception {
		RequestContext.getCurrentInstance().execute("PF('popUpSubClasse').hide()");
		editadaPai = (Classe) event.getTreeNode().getData();
		editada.setObjClasse(editadaPai);
	}

	public void onNodeSelectClasseRel(NodeSelectEvent event) throws Exception {
		RequestContext.getCurrentInstance().execute("PF('popUpClasse').hide()");
		this.editada = (Classe) event.getTreeNode().getData();
	}
	
	public void onNodeSelect(NodeSelectEvent event) throws Exception {
		this.editada = (Classe) event.getTreeNode().getData();
		if(editada!=null && editada.getId()==1) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A classe raiz não pode ser editada.", null));
		}
	}
	
	public String abrirDetalhe() throws Exception {
		if(selectedNode!=null) {
			if(editada!=null && editada.getId()==1) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A classe raiz não pode ser editada.", null));
			} else {
				if(selectedNode!=null) {
					return "cadclassedetalhe.xhtml";
				}
			}
		}
		return "cadclasse.xhtml";
	}
	
	public boolean validaAno() {
		boolean result = false;
		if (this.editada.getClas_cd_classe() != null && this.editada.getClas_tx_fasecorrente().equals("ANO")) {
			result = true;
		}
		return result;
	}
	
	public void openPopUp() throws Exception {
     	Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 900);
        options.put("height", 500);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        
        RequestContext.getCurrentInstance().openDialog("popUpClasse");
	}
	

	public void salvar() throws Exception
	{
		try {
			
			if (validaForm()) {
			ClasseManager classeManager = new ClasseManager();
			classeManager.merge(this.editada);
		
			if(!manterClasse) {
				this.editada = new Classe();
				this.mostraEdicao = false;
				this.classesTree = null;
				this.classesSelect = null;
			}
			
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
		
	}
	
	public void excluir() throws Exception
	{

		ClasseManager classeManager = new ClasseManager();
		AcervoManager acervoManager = new AcervoManager();
		List<Acervo> listaAcervo = new ArrayList<Acervo>();
		String mensagem = "";
		Integer cont=0;
    	/* 
    	 * **********************************
    	 * VERIFICANDO OS ACERVOS ASSOCIADOS
    	 * **********************************
    	 */
		listaAcervo=acervoManager.listaAcervoPorClasse(editada);
		editada.setAcervos(listaAcervo);
//		editada=classeManager.carregaClasseComAcervo(editada);
		if (editada.getAcervos()!=null && editada.getAcervos().size()>0) {
			mensagem="Existem acervos relacionadas a esta classe. Desvincule antes de excluir! Veja alguns dos Acervos:" ;
			cont=0;
			for (Acervo acervo : editada.getAcervos()) {
				 mensagem+=acervo.getId() + ", ";
				 cont++;
				 if(cont>9) break;
			}
			mensagem = mensagem.substring(0, mensagem.length()-2) + ".";
			
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		} else {
	    	/* 
	    	 * **********************************
	    	 * VERIFICANDO SE CLASSE TEM FILHAS
	    	 * **********************************
	    	 */
			if(editada!=null && temFilhas(this.editada)) {
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta classe não pode ser excluída, pois possui outras classes relacionadas.", null));
			} else {
		    	/* 
		    	 * **********************************
		    	 * VERIFICANDO SE CLASSE É RAIZ
		    	 * **********************************
		    	 */
				if (editada!=null && editada.getId()==1) {
					ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A classe raiz não pode ser excluída", null));
				} else {
					/* 
			    	 * **********************************
			    	 * EXCLUINDO CLASSE SELECIONADA
			    	 * **********************************
			    	 */
					this.editada.setClas_in_delecao(1);
					classeManager.salvar(this.editada);
			
					this.editada = new Classe();
					this.mostraEdicao = false;
					this.classesTree = null;
					this.classesSelect = null;
			
					ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
				}
			}
		}
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

	private boolean temFilhas(Classe pai) {
		boolean result = true;
		ClasseManager classeManager = new ClasseManager();
		List<Classe> classes = new ArrayList<Classe>();
		try {
			classes = classeManager.temFilhas(pai);
			if (classes.isEmpty()) {
				result = false;
			} else {
				result = true;
			}
			
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public List<SelectItem> getClassesSelect() throws Exception {
		if(this.classesSelect == null) {
			this.classesSelect = new ArrayList<SelectItem>();
			ClasseManager classeManager = new ClasseManager();
			List<Classe> classes = classeManager.listaClassesPai();
			montaDadosSelect(this.classesSelect, classes, "");
		}
		return classesSelect;
	}
	
	public void montaDadosSelect(List<SelectItem> select, List<Classe> classes, String prefixo) {
		SelectItem item = null;
		if (classes != null) {
			for(Classe classe : classes) {
				item = new SelectItem(classe, prefixo + classe.getClas_ds_nome());
				item.setEscape(false);
				select.add(item);
				this.montaDadosSelect(select, classe.getClas_filhas(), prefixo);
			}
		}
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		if (this.editada.getClas_ds_nome() == null) {
			result=false;
		}
		return result;
	}


	public void setClassesTree(TreeNode classesTree) {
		this.classesTree = classesTree;
	}


	public Classe getEditada() {
		return editada;
	}


	public void setEditada(Classe editada) {
		this.editada = editada;
	}

	
	public void setClassesSelect(List<SelectItem> classesSelect) {
		this.classesSelect = classesSelect;
	}


	public boolean isMostraEdicao() {
		return mostraEdicao;
	}


	public void setMostraEdicao(boolean mostraEdicao) {
		this.mostraEdicao = mostraEdicao;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public boolean isManterClasse() {
		return manterClasse;
	}

	public void setManterClasse(boolean manterClasse) {
		this.manterClasse = manterClasse;
	}

	public String getFmtRlt() {
		return fmtRlt;
	}

	public void setFmtRlt(String fmtRlt) {
		this.fmtRlt = fmtRlt;
	}
	
	
	
}

package _controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

import _business.AcervoManager;
import _business.ClasseManager;
import _business.ClienteManager;
import _business.DescritorManager;
import _business.UnidadeManager;
import _model.dto.AcervoDTO;
import _model.vo.Acervo;
import _model.vo.AcervoDescritor;
import _model.vo.Classe;
import _model.vo.Cliente;
import _model.vo.Descritor;
import _model.vo.Unidade;
import exception.ControllerException;
import util.ContextApp;
import util.Funcoes;

@ManagedBean(name="importaXLSView")
@RequestScoped
public class ImportaXLSView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AcervoDTO acervoDTO;
	private UploadedFile arquivo;
	private String nomePlanilha;
	private String tipoInformacao;
	private Integer regImportados;
	private List<AcervoDTO> acervoDTOs;
	private AcervoDTO importaSel;
	private Classe classe;
	private Unidade unidade;
	private Descritor descritor;
	private List<Descritor> descritors;
	private String vtrab;
	private Integer vind;
	private boolean importaLinha;
	private Cliente objCliente;
	private Cliente filtroCliente;
	private Cliente clienteSel;
	private List<Cliente> clientes;
	private boolean flagImportar = false;
	private boolean flagSalvar = true;
	
	
	public ImportaXLSView() throws Exception {
	}

	public String retorna() throws Exception
	{
		return "/pages/principal/principal.xhtml";
	}

	public String abrirFormulario() throws ControllerException {
		flagSalvar=true;
		flagImportar=false;
		regImportados=0;
		acervoDTOs = new ArrayList<AcervoDTO>();
		objCliente = new Cliente();
		ClienteManager clienteManager = new ClienteManager();
		clientes = new ArrayList<Cliente>();
		clientes = clienteManager.listaClientes();
		return "/pages/importacao/importaXLS.xhtml";
	}
	
	public void excluir() {
		if(acervoDTOs.contains(importaSel)) {
			acervoDTOs.remove(importaSel);
		}
	}
	
	/*
	 * 
	public String abrirPlanilhaImportada() throws ControllerException {
		return "/pages/importacao/importaXLS.xhtml";
	}
	 */
	
	public void listaClientes() throws ControllerException {
		ClienteManager clienteManager = new ClienteManager();
		clientes = new ArrayList<Cliente>();
		clientes = clienteManager.listaClientes();
		objCliente = new Cliente();
		clienteSel = new Cliente();
		filtroCliente = new Cliente();
		RequestContext.getCurrentInstance().execute("PF('popUpCliente').show()");
	}
	
	public List<Cliente> pesquisarCliente() throws ControllerException {
		ClienteManager clienteManager = new ClienteManager();
		clientes = new ArrayList<Cliente>();
		clientes = clienteManager.pesquisa(this.filtroCliente);
		return clientes;
	}
	
	public void selecionarCliente() {
		// --- Passando o valor selecionado para a variavel objeto Cliente
		objCliente = clienteSel;
	}
	
    public void importaXLS() throws Exception {
    	if(validaForm()) {
    		nomePlanilha = arquivo.getFileName();
    		regImportados = 0;
    		// --- Faz a importação da planilha para a nuvem
	    	InputStream in = new BufferedInputStream(arquivo.getInputstream());
	    	String destino = FacesContext.getCurrentInstance().getExternalContext().getRealPath(File.separator+"resources"+File.separator+"xls"+File.separator+nomePlanilha);
			File file = new File(destino);
	    	FileOutputStream fout = new FileOutputStream(file);
	    	while (in.available()!=0) {
	    		fout.write(in.read());
	    	}
			fout.close();
			
			// --- Inicia o processo de leitura da planilha já carregada na nuvem
			
			FileInputStream fisPlanilha = null;

			fisPlanilha = new FileInputStream(file);
			
			// --- Cria um Workbook = todas as abas da planilha
			XSSFWorkbook workbook = new XSSFWorkbook(fisPlanilha);
			
			// --- Recuperando a planilha selecionada na opção
			XSSFSheet sheet = null;
		
			if(this.tipoInformacao.equals("acervo")) {
				sheet = workbook.getSheetAt(0);
			} 
			
			if(this.tipoInformacao.equals("descritor")) {
				sheet = workbook.getSheetAt(1);
			} 

			if(this.tipoInformacao.equals("documento")) {
				sheet = workbook.getSheetAt(2);
			} 
			
			// --- Retorna todas as linhas da planilha 
			Iterator<Row> rowIterator = sheet.iterator();
			
			// --- Inicializa a lista que receberá os objetos
			acervoDTOs = new ArrayList<AcervoDTO>();
			
			while(rowIterator.hasNext()) {
				
				// --- Inicializa o objeto que receberá os valores dos campos
				acervoDTO = new AcervoDTO();
				classe = new Classe();
				unidade = new Unidade();
				descritors = new ArrayList<Descritor>();
				vtrab = "";
				vind = 0;
				importaLinha=false;
				
				// --- Captura cada linha da planilha
				Row row = rowIterator.next();
				
				// --- Incrementa o contador
				regImportados++;
				
				Iterator<Cell> cellIterator = row.iterator();
				
				// --- Varrer as células da linha atual
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// --- Desconsidera a primeira linha de CABEÇALHO
					if (regImportados>1) {
						if(cell.getColumnIndex()==0 && cell.getNumericCellValue()>0) {
							importaLinha=true;
						}

						if(importaLinha) {
							switch (cell.getColumnIndex()) {
							case 0: // --- ID
								vtrab=Double.toString(cell.getNumericCellValue());
								vind=vtrab.indexOf(".");
								vtrab=vtrab.substring(0, vind);
								acervoDTO.setId(Integer.parseInt(vtrab));
								break;
							case 1: // --- UNIDADE
								unidade.setUnid_nm_unidade(cell.getStringCellValue());
								break;
							case 2: // --- CÓDIGO DA UNIDADE
								vtrab=Double.toString(cell.getNumericCellValue());
								vind=vtrab.indexOf(".");
								vtrab=vtrab.substring(0, vind);
								unidade.setId(Integer.parseInt(vtrab));
								acervoDTO.setObjUnidade(unidade);
								break;
							case 3: // --- CLASSE
								classe.setClas_ds_nome(cell.getStringCellValue());
								break;
							case 4: // --- CÓDIGO DA CLASSE
								classe.setClas_cd_classe(cell.getStringCellValue());
								acervoDTO.setObjClasse(classe);
								break;
							case 5: // --- ASSUNTO
								vtrab = cell.getStringCellValue();
								acervoDTO.setAcer_ds_assunto(vtrab);
								break;
							case 6: // --- FASE ATUAL
								acervoDTO.setAcer_in_status(cell.getStringCellValue());
								break;
							case 7: // --- DT. REFERENCIA 
								acervoDTO.setAcer_dt_referencia(cell.getDateCellValue());
								break;
							case 8: // --- DT. FINAL VIGÊNCIA
								acervoDTO.setAcer_dt_finalvigencia(cell.getDateCellValue());
								break;
							case 9: // --- LOCALIZACAO ARQUIVO
								acervoDTO.setAcer_ds_localarquivo(cell.getStringCellValue());
								break;
							case 10: // --- OBSERVACAO
								acervoDTO.setAcer_tx_observacao(cell.getStringCellValue());
								break;
							case 11: // --- DESCRITOR 1
							case 12: // --- DESCRITOR 2
							case 13: // --- DESCRITOR 3
							case 14: // --- DESCRITOR 4
							case 15: // --- DESCRITOR 5
							case 16: // --- DESCRITOR 6
							case 17: // --- DESCRITOR 7
							case 18: // --- DESCRITOR 8
							case 19: // --- DESCRITOR 9
							case 20: // --- DESCRITOR 10
								if(cell.getStringCellValue()!=null) {
									descritor = new Descritor();
									descritor.setDesc_nm_descritor(cell.getStringCellValue());
									descritors.add(descritor);
								}
								break;
							default:
								break;
							}
						}
					} else {
						// --- Imprime os cabeçalhos no console
						//System.out.println("Coluna [" + cell.getColumnIndex() + "] - " + cell.getStringCellValue());
					}
				}
				// --- Carrega os descritores
				if(descritors.size()>0) acervoDTO.setDescritors(descritors);
				// --- Carrega o objeto na lista
				if(acervoDTO.getId()!=null) acervoDTOs.add(acervoDTO);
			}
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Importação concluída com sucesso!. Total de ["+ acervoDTOs.size() + "] registros importados.", null));
			flagSalvar=false;
			flagImportar=true;
		}
	}

	private boolean validaForm() throws Exception {
		boolean result = true;
		// --- VALIDANDO DADOS
		if (this.arquivo==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Planilha não foi selecionada. Verifique!", null));
		}
		// --- VALIDANDO CLIENTE
		if(this.objCliente==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cliente inválido. Verifique!", null));
		}

		if (ContextApp.getContext().getMessages().hasNext()) {
			result = false;
		}

		return result;
	}
	
	public void salvar() throws Exception {
		if(acervoDTOs!=null && acervoDTOs.size()>0) {
			for (AcervoDTO acervoDTO : acervoDTOs) {
				Acervo acervo = new Acervo();

				// --- Carrega todos os dados para o acervo
				acervo.setAcer_ds_arquivodigital(acervoDTO.getAcer_ds_arquivodigital());
				acervo.setAcer_ds_assunto(acervoDTO.getAcer_ds_assunto());
				acervo.setAcer_ds_localarquivo(acervoDTO.getAcer_ds_localarquivo());
				acervo.setAcer_dt_finalvigencia(acervoDTO.getAcer_dt_finalvigencia());
				acervo.setAcer_dt_inclusao(Funcoes.getPegaDataAtual());
				acervo.setAcer_dt_referencia(acervoDTO.getAcer_dt_referencia());
				acervo.setAcer_in_automovimentacao(false);
				acervo.setAcer_in_delecao(0);
				acervo.setAcer_in_digitalizado(false);
				acervo.setAcer_in_status(acervoDTO.getAcer_in_status());
				acervo.setAcer_tx_hierarquia(acervoDTO.getAcer_tx_hierarquia());
				acervo.setAcer_tx_observacao(acervoDTO.getAcer_tx_observacao());
				
				// --- Buscando a classe de referência
				ClasseManager classeManager = new ClasseManager();
				Classe objClasse = new Classe();
				objClasse = classeManager.buscaClassePorCodigo(acervoDTO.getObjClasse());
				acervo.setObjClasse(objClasse);
				
				// --- Buscando a hierarquia da classe
				String varHierarquia = "";
				varHierarquia = buscaHierarquia(objClasse);
				acervo.setAcer_tx_hierarquia(varHierarquia);
				
				// --- Configura com o cliente selecionado
				acervo.setObjCliente(this.objCliente);
				
				// --- Bucando a unidade de referência
				UnidadeManager unidadeManager = new UnidadeManager();
				Unidade objUnidade = new Unidade();
				if(acervoDTO.getObjUnidade().getId()!=null) {
					objUnidade = unidadeManager.buscaUnidadePorId(acervoDTO.getObjUnidade());
				} else {
					objUnidade.setUnid_nm_unidade(acervoDTO.getObjUnidade().getUnid_nm_unidade());
					objUnidade.setUnid_sg_sigla(objUnidade.getUnid_nm_unidade().substring(0, 9));
					objUnidade.setUnid_in_delecao(0);
				}
				acervo.setObjUnidade(objUnidade);

				// --- CARREGANDO OS DESCRITORES
				// --- Verificando se os descritores já existem e se caso não existirem incluí-los na base de dados
				if(acervoDTO.getDescritors()!=null && acervoDTO.getDescritors().size()>0) {
					AcervoDescritor itemAcervoDescritor;
					List<AcervoDescritor> lista = new ArrayList<AcervoDescritor>();
					DescritorManager descritorManager = new DescritorManager();
					descritors = new ArrayList<Descritor>();
					for (Descritor itemDescritor : acervoDTO.getDescritors()) {
						// --- Verificando se descritor já existe na tabela
						descritors = descritorManager.pesquisa(itemDescritor);
						if(descritors.size()>0) {
							// --- Descritor já existe. Carrega o Descritor existente.
							itemDescritor = descritors.get(0);
						} else {
							// --- Descritor não existe. Vamos incluir um novo descritor
							descritorManager.salvar(itemDescritor);
						}
						itemAcervoDescritor = new AcervoDescritor();
						itemAcervoDescritor.setObjAcervo(acervo);
						itemAcervoDescritor.setObjDescritor(itemDescritor);
						lista.add(itemAcervoDescritor);
					}
					acervo.setAcervoDescritors(lista);
				}
				
				AcervoManager acervoManager = new AcervoManager();
				// --- Gravando o acervo na base
				acervoManager.salvar(acervo);
			}
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			// --- Limpando os registros da tela
			acervoDTOs.clear();
		}
	}

	
	private String buscaHierarquia(Classe filtro) {
		Classe hierClasse = filtro;
		String[] estrutura = new String[10];
		Integer cont=0;
		StringWriter hierarquia = new StringWriter();
		StringWriter prefixo = new StringWriter();
		hierarquia.write("");
		prefixo.write("");
		if (filtro!=null) {
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
		return hierarquia.toString();
	}


	// --- GETs e SETs
	
	
	public AcervoDTO getAcervoDTO() {
		return acervoDTO;
	}

	public void setAcervoDTO(AcervoDTO acervoDTO) {
		this.acervoDTO = acervoDTO;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public String getNomePlanilha() {
		return nomePlanilha;
	}

	public void setNomePlanilha(String nomePlanilha) {
		this.nomePlanilha = nomePlanilha;
	}

	public String getTipoInformacao() {
		return tipoInformacao;
	}

	public void setTipoInformacao(String tipoInformacao) {
		this.tipoInformacao = tipoInformacao;
	}

	public Integer getRegImportados() {
		return regImportados;
	}

	public void setRegImportados(Integer regImportados) {
		this.regImportados = regImportados;
	}

	public List<AcervoDTO> getAcervoDTOs() {
		return acervoDTOs;
	}

	public void setAcervoDTOs(List<AcervoDTO> acervoDTOs) {
		this.acervoDTOs = acervoDTOs;
	}

	public AcervoDTO getImportaSel() {
		return importaSel;
	}

	public void setImportaSel(AcervoDTO importaSel) {
		this.importaSel = importaSel;
	}

	public Cliente getObjCliente() {
		return objCliente;
	}

	public void setObjCliente(Cliente objCliente) {
		this.objCliente = objCliente;
	}

	public Cliente getFiltroCliente() {
		return filtroCliente;
	}

	public void setFiltroCliente(Cliente filtroCliente) {
		this.filtroCliente = filtroCliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getClienteSel() {
		return clienteSel;
	}

	public void setClienteSel(Cliente clienteSel) {
		this.clienteSel = clienteSel;
	}

	public boolean isFlagImportar() {
		return flagImportar;
	}

	public void setFlagImportar(boolean flagImportar) {
		this.flagImportar = flagImportar;
	}

	public boolean isFlagSalvar() {
		return flagSalvar;
	}

	public void setFlagSalvar(boolean flagSalvar) {
		this.flagSalvar = flagSalvar;
	}

}

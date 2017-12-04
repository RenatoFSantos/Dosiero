package _business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import _dao.impl.AcervoDAO;
import _dao.impl.AcervoDescritorDAO;
import _dao.impl.DocumentoDAO;
import _dao.interfaces.IAcervoDAO;
import _dao.interfaces.IAcervoDescritorDAO;
import _dao.interfaces.IDocumentoDAO;
import _dao.util.HibernateUtil;
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
import exception.ControllerException;
import exception.DaoException;

public class AcervoManager {

	private IAcervoDAO acervoDAO = new AcervoDAO();
	private IAcervoDescritorDAO acervoDescritorDAO = new AcervoDescritorDAO();
	private IDocumentoDAO documentoDAO = new DocumentoDAO();

	public AcervoManager() {

	}

	public void salvarMovimentacao(Acervo acervo) throws ControllerException {
		HibernateUtil.getSession();
		try {

			HibernateUtil.beginTransaction();
			
			acervoDAO.merge(acervo);	

			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void salvar(Acervo acervo) throws ControllerException {
		HibernateUtil.getSession();
		try {

			HibernateUtil.beginTransaction();
			
			if(acervo.getId()==null) {
				acervoDAO.save(acervo);
			} else {
				acervoDAO.merge(acervo);	
			}

			//______________________________________________________
			// --- EXCLUINDO OS DESCRITORES E DOCUMENTOS RELACIONADOS ANTES DE GRAVAR CASO NÃO SEJA UM CASO DE EXCLUSÃO DE ACERVO
			if(acervo.getAcer_in_delecao() == 0) {
				excluiTodosDescritoresDoAcervo(acervo);
				excluiTodosDocumentosDoAcervo(acervo);
			}
			
			// --- VERIFICA SE EXISTE NOVOS DESCRITORES RELACIONADOS PARA GRAVAR E SE ESTE ACERVO ESTÁ ATIVO

			if (acervo.getAcervoDescritors() != null && acervo.getAcervoDescritors().size() > 0 && acervo.getAcer_in_delecao() == 0)
			{
				AcervoDescritor novoItem;
				for (AcervoDescritor item : acervo.getAcervoDescritors()) 
				{
					novoItem = new AcervoDescritor();
					novoItem.setObjAcervo(acervo);
					novoItem.setObjDescritor(item.getObjDescritor());
					acervoDescritorDAO.save(novoItem);
				}
			}

			// --- VERIFICA SE EXISTE NOVOS DOCUMENTOS RELACIONADOS PARA GRAVAR E SE ESTE ACERVO ESTÁ ATIVO
			if (acervo.getDocumentos() != null && acervo.getDocumentos().size() > 0 && acervo.getAcer_in_delecao() == 0)
			{
				Documento novoDoc;
				for (Documento doc : acervo.getDocumentos()) 
				{
					novoDoc = new Documento();
					novoDoc.setDocu_dt_inclusao(doc.getDocu_dt_inclusao());
					novoDoc.setDocu_nm_arquivo(doc.getDocu_nm_arquivo());
					novoDoc.setDocu_nm_documento(doc.getDocu_nm_documento());
					novoDoc.setDocu_nr_codbarras(doc.getDocu_nr_codbarras());
					novoDoc.setDocu_tx_conteudo(doc.getDocu_tx_conteudo());
					novoDoc.setDocu_tx_observacao(doc.getDocu_tx_observacao());
					novoDoc.setObjAcervo(doc.getObjAcervo());
					novoDoc.setDocu_nr_codbarras(doc.getDocu_nr_codbarras());
					documentoDAO.save(novoDoc);
				}
			}

			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}
	
	public void deletar(Acervo acervo) throws ControllerException {
		HibernateUtil.getSession();
		try {
			HibernateUtil.beginTransaction();
			acervoDAO.delete(acervo);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public List<Acervo> listaAcervos() throws ControllerException {
		HibernateUtil.getSession();
		List<Acervo> acervos = new ArrayList<Acervo>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			acervos = acervoDAO.allAcervos();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervos;
	}
	
	public List<Acervo> listaAcervoPorClasse(Classe filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Acervo> acervos = new ArrayList<Acervo>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			acervos = acervoDAO.listaAcervoPorClasse(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervos;
	}

	public List<Acervo> listaAcervoPorDescritor(Descritor filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Acervo> acervos = new ArrayList<Acervo>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			acervos = acervoDAO.listaAcervoPorDescritor(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervos;
	}
	
	public List<Acervo> listaAcervoPorCliente(Cliente filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Acervo> acervos = new ArrayList<Acervo>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			acervos = acervoDAO.listaAcervoPorCliente(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervos;
	}

	public List<Acervo> pesquisa(Acervo filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Acervo> acervos = new ArrayList<Acervo>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			acervos = acervoDAO.pesquisaAcervo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervos;
	}

	public List<Acervo> pesquisaAssunto(Acervo filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Acervo> listaAcervo = new ArrayList<Acervo>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			listaAcervo = acervoDAO.pesquisaAssunto(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return listaAcervo;
	}

	public List<AcervoDescritor> buscaTodosDescritoresDoAcervo(Acervo filtro)  throws ControllerException {
		HibernateUtil.getSession();
		List<AcervoDescritor> lista = new ArrayList<AcervoDescritor>();
		try {
			// --- ROTINA PARA BUSCAR TODOS OS DESCRITORES DO ACERVO
			lista = acervoDescritorDAO.pesquisaAcervoDescritorPorAcervo(filtro);
		} catch (DaoException e) {
			throw new ControllerException(e);
		}
		return lista;
	}
	
	public List<Documento> buscaTodosDocumentosDoAcervo(Acervo filtro)  throws ControllerException {
		HibernateUtil.getSession();
		List<Documento> lista = new ArrayList<Documento>();
		try {
			// --- ROTINA PARA BUSCAR TODOS OS DOCUMENTOS DO ACERVO
			lista = documentoDAO.allDocumentosAcervo(filtro);
		} catch (DaoException e) {
			throw new ControllerException(e);
		}
		return lista;
	}

	public boolean excluiTodosDescritoresDoAcervo(Acervo filtro) throws ControllerException {
		HibernateUtil.getSession();
		boolean result = true;
		try {
			// --- ROTINA PARA EXCLUSÃO DOS DESCRITORES DO ACERVO
			List<AcervoDescritor> lista = new ArrayList<AcervoDescritor>();
			lista = buscaTodosDescritoresDoAcervo(filtro);
			if (lista != null && lista.size()>0) {
				for (AcervoDescritor acervoDescritor : lista) {
					acervoDescritorDAO.delete(acervoDescritor);
				}
			}
		} catch (DaoException e) {
			result = false;
			throw new ControllerException(e);
		}
		return result;
	}

	public boolean excluiTodosDocumentosDoAcervo(Acervo filtro) throws ControllerException {
		HibernateUtil.getSession();
		boolean result = true;
		try {
			// --- ROTINA PARA EXCLUSÃO DOS DOCUMENTOS DO ACERVO
			List<Documento> lista = new ArrayList<Documento>();
			lista = buscaTodosDocumentosDoAcervo(filtro);
			if (lista != null && lista.size()>0) {
				for (Documento item : lista) {
					documentoDAO.delete(item);
				}
			}
		} catch (DaoException e) {
			result = false;
			throw new ControllerException(e);
		}
		return result;
	}

	public List<UnidadeDTO> listaAcervoPorUnidade(Unidade unidade, Date dataIni, Date dataFim) throws ControllerException {
		HibernateUtil.getSession();
		List<UnidadeDTO> unidades = new ArrayList<UnidadeDTO>();
		List<Acervo> acervos = new ArrayList<Acervo>();
		List<Documento> documentos = new ArrayList<Documento>();
		DocumentoManager documentoManager = new DocumentoManager();

		try {
			// --- Rotina para buscar todo o acervo pela unidade selecionada.
			acervos = acervoDAO.listaAcervoPorUnidade(unidade, dataIni, dataFim);
			
			// --- Carregando objeto de impressão com os dados do acervo
			for (Acervo acervo : acervos) {
				UnidadeDTO objUnidade = new UnidadeDTO();
				objUnidade.setId(acervo.getId());
				objUnidade.setUnddto_cd_classe(acervo.getObjClasse().getClas_cd_classe());
				objUnidade.setUnddto_ds_assunto(acervo.getAcer_ds_assunto());
				objUnidade.setUnddto_ds_classe(acervo.getObjClasse().getClas_ds_nome());
				objUnidade.setUnddto_ds_localarquivo(acervo.getAcer_ds_localarquivo());
				objUnidade.setUnddto_dt_finalvigencia(acervo.getAcer_dt_finalvigencia());
				// --- Verifica se acervo tem documentos atrelados
				documentos = documentoManager.listaDocumentosAcervo(acervo);
				if(documentos!=null && documentos.size()>0) {
					objUnidade.setUnddto_in_documento("Sim");
				} else {
					objUnidade.setUnddto_in_documento("Não");
				}
				objUnidade.setUnddto_sg_sigla(acervo.getObjUnidade().getUnid_sg_sigla());
				objUnidade.setUnddto_nm_unidade(acervo.getObjUnidade().getUnid_nm_unidade());
				objUnidade.setUnddto_nr_fasecorrente(acervo.getObjClasse().getClas_nr_fasecorrente());
				objUnidade.setUnddto_tx_fasecorrente(acervo.getObjClasse().getClas_tx_fasecorrente());
				objUnidade.setUnddto_tx_faseintermediaria(acervo.getObjClasse().getClas_tx_faseintermediaria());
				objUnidade.setUnddto_tx_destinacaofinal(acervo.getObjClasse().getClas_tx_destinacaofinal());
				objUnidade.setUnddto_tx_observacao(acervo.getAcer_tx_observacao());
				objUnidade.setUnddto_in_status(acervo.getAcer_in_status());
				unidades.add(objUnidade);
			}

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return unidades;
	}

	public List<UnidadeDTO> listaAcervoPorUnidadeOrdemLocal(Unidade unidade, Date dataIni, Date dataFim) throws ControllerException {
		HibernateUtil.getSession();
		List<UnidadeDTO> unidades = new ArrayList<UnidadeDTO>();
		List<Acervo> acervos = new ArrayList<Acervo>();
		List<Documento> documentos = new ArrayList<Documento>();
		DocumentoManager documentoManager = new DocumentoManager();

		try {
			// --- Rotina para buscar todo o acervo pela unidade selecionada.
			acervos = acervoDAO.listaAcervoPorUnidadeOrdemLocal(unidade, dataIni, dataFim);
			
			// --- Carregando objeto de impressão com os dados do acervo
			for (Acervo acervo : acervos) {
				UnidadeDTO objUnidade = new UnidadeDTO();
				objUnidade.setId(acervo.getId());
				objUnidade.setUnddto_cd_classe(acervo.getObjClasse().getClas_cd_classe());
				objUnidade.setUnddto_ds_assunto(acervo.getAcer_ds_assunto());
				objUnidade.setUnddto_ds_classe(acervo.getObjClasse().getClas_ds_nome());
				objUnidade.setUnddto_ds_localarquivo(acervo.getAcer_ds_localarquivo());
				objUnidade.setUnddto_dt_finalvigencia(acervo.getAcer_dt_finalvigencia());
				// --- Verifica se acervo tem documentos atrelados
				documentos = documentoManager.listaDocumentosAcervo(acervo);
				if(documentos!=null && documentos.size()>0) {
					objUnidade.setUnddto_in_documento("Sim");
				} else {
					objUnidade.setUnddto_in_documento("Não");
				}
				objUnidade.setUnddto_sg_sigla(acervo.getObjUnidade().getUnid_sg_sigla());
				objUnidade.setUnddto_nm_unidade(acervo.getObjUnidade().getUnid_nm_unidade());
				objUnidade.setUnddto_nr_fasecorrente(acervo.getObjClasse().getClas_nr_fasecorrente());
				objUnidade.setUnddto_tx_fasecorrente(acervo.getObjClasse().getClas_tx_fasecorrente());
				objUnidade.setUnddto_tx_faseintermediaria(acervo.getObjClasse().getClas_tx_faseintermediaria());
				objUnidade.setUnddto_tx_destinacaofinal(acervo.getObjClasse().getClas_tx_destinacaofinal());
				objUnidade.setUnddto_tx_observacao(acervo.getAcer_tx_observacao());
				objUnidade.setUnddto_in_status(acervo.getAcer_in_status());
				unidades.add(objUnidade);
			}

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return unidades;
	}

	public List<Acervo> listaAcervoMovimentacao(Cliente cliente, Unidade unidade, Classe classe, Date dataIni, Date dataFim) throws ControllerException {
		HibernateUtil.getSession();
		List<Acervo> acervos = new ArrayList<Acervo>();

		try {
			// --- Rotina para buscar todo o acervo pela unidade/classe selecionada.
			acervos = acervoDAO.listaAcervoMovimentacao(cliente, unidade, classe, dataIni, dataFim);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervos;
	}

	public List<UnidadeDTO> listaAcervoPorDigitacao(Cliente cliente, Unidade unidade, Classe classe, Date dataIni, Date dataFim) throws ControllerException {
		HibernateUtil.getSession();
		List<UnidadeDTO> unidades = new ArrayList<UnidadeDTO>();
		List<Acervo> acervos = new ArrayList<Acervo>();
		List<Documento> documentos = new ArrayList<Documento>();
		DocumentoManager documentoManager = new DocumentoManager();

		try {
			// --- Rotina para buscar todo o acervo pela unidade/classe selecionada.
			acervos = acervoDAO.listaAcervoPorDigitacao(cliente, unidade, classe, dataIni, dataFim);
			
			// --- Carregando objeto de impressão com os dados do acervo
			for (Acervo acervo : acervos) {
				UnidadeDTO objUnidade = new UnidadeDTO();
				objUnidade.setId(acervo.getId());
				objUnidade.setUnddto_cd_classe(acervo.getObjClasse().getClas_cd_classe());
				objUnidade.setUnddto_ds_assunto(acervo.getAcer_ds_assunto());
				objUnidade.setUnddto_ds_classe(acervo.getObjClasse().getClas_ds_nome());
				objUnidade.setUnddto_ds_localarquivo(acervo.getAcer_ds_localarquivo());
				objUnidade.setUnddto_dt_finalvigencia(acervo.getAcer_dt_finalvigencia());
				// --- Verifica se acervo tem documentos atrelados
				documentos = documentoManager.listaDocumentosAcervo(acervo);
				if(documentos!=null && documentos.size()>0) {
					objUnidade.setUnddto_in_documento("Sim");
				} else {
					objUnidade.setUnddto_in_documento("Não");
				}
				objUnidade.setUnddto_sg_sigla(acervo.getObjUnidade().getUnid_sg_sigla());
				objUnidade.setUnddto_nm_unidade(acervo.getObjUnidade().getUnid_nm_unidade());
				objUnidade.setUnddto_nr_fasecorrente(acervo.getObjClasse().getClas_nr_fasecorrente());
				objUnidade.setUnddto_tx_fasecorrente(acervo.getObjClasse().getClas_tx_fasecorrente());
				objUnidade.setUnddto_tx_faseintermediaria(acervo.getObjClasse().getClas_tx_faseintermediaria());
				objUnidade.setUnddto_tx_destinacaofinal(acervo.getObjClasse().getClas_tx_destinacaofinal());
				objUnidade.setUnddto_tx_observacao(acervo.getAcer_tx_observacao());
				objUnidade.setUnddto_in_status(acervo.getAcer_in_status());
				unidades.add(objUnidade);
			}

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return unidades;
	}
	
	public List<AcervoDTO> listaAcervoCompleto(Cliente cliente, Unidade unidade, Classe classe, Date dataIni, Date dataFim) throws ControllerException {
		HibernateUtil.getSession();
		List<AcervoDTO> acervoDTOs = new ArrayList<AcervoDTO>();
		List<Acervo> acervos = new ArrayList<Acervo>();
		List<Documento> documentos = new ArrayList<Documento>();
		DocumentoManager documentoManager = new DocumentoManager();
		List<Descritor> descritors = new ArrayList<Descritor>();
		DescritorManager descritorManager = new DescritorManager();

		try {
			// --- Rotina para buscar todo o acervo pela unidade/classe selecionada.
			acervos = acervoDAO.listaAcervoPorDigitacao(cliente, unidade, classe, dataIni, dataFim);
			
			// --- Carregando objeto de impressão com os dados do acervo
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

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervoDTOs;
	}

	public List<AcervoDTO> listaAcervoPorClasse(Cliente cliente, Classe classe, Date dataIni, Date dataFim) throws ControllerException {
		HibernateUtil.getSession();
		List<AcervoDTO> acervoDTOs = new ArrayList<AcervoDTO>();
		List<Acervo> acervos = new ArrayList<Acervo>();
		List<Documento> documentos = new ArrayList<Documento>();
		DocumentoManager documentoManager = new DocumentoManager();

		try {
			// --- Rotina para buscar todo o acervo pela unidade/classe selecionada.
			acervos = acervoDAO.listaAcervoPorClasse(cliente, classe, dataIni, dataFim);
			
			// --- Carregando objeto de impressão com os dados do acervo
			for (Acervo acervo : acervos) {
				AcervoDTO objAcervoDTO = new AcervoDTO();
				objAcervoDTO.setId(acervo.getId());
				objAcervoDTO.setObjClasse(acervo.getObjClasse());
				objAcervoDTO.setAcer_ds_assunto(acervo.getAcer_ds_assunto());
				objAcervoDTO.setAcer_ds_localarquivo(acervo.getAcer_ds_localarquivo());
				objAcervoDTO.setAcer_dt_finalvigencia(acervo.getAcer_dt_finalvigencia());
				// --- Verifica se acervo tem documentos atrelados
				documentos = documentoManager.listaDocumentosAcervo(acervo);
				if(documentos!=null && documentos.size()>0) {
					objAcervoDTO.setAcer_in_documento(true);
				} else {
					objAcervoDTO.setAcer_in_documento(false);
				}
				objAcervoDTO.setObjUnidade(acervo.getObjUnidade());
				objAcervoDTO.setAcer_tx_observacao(acervo.getAcer_tx_observacao());
				objAcervoDTO.setAcer_in_status(acervo.getAcer_in_status());
				acervoDTOs.add(objAcervoDTO);
			}

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervoDTOs;
	}

	public List<AcervoDTO> listaAcervoPorDataInclusao(Cliente cliente, Date dataIni, Date dataFim) throws ControllerException {
		HibernateUtil.getSession();
		List<AcervoDTO> acervoDTOs = new ArrayList<AcervoDTO>();
		List<Acervo> acervos = new ArrayList<Acervo>();

		try {
			// --- Rotina para buscar todo o acervo pela data de inclusão do registro
			acervos = acervoDAO.listaAcervoPorDataInclusao(cliente, dataIni, dataFim);
			
			// --- Carregando objeto de impressão com os dados do acervo
			for (Acervo acervo : acervos) {
				AcervoDTO objAcervo = new AcervoDTO();
				objAcervo.setId(acervo.getId());
				objAcervo.setAcer_ds_assunto(acervo.getAcer_ds_assunto());
				objAcervo.setAcer_nr_codbarras(acervo.getAcer_nr_codbarras());
				acervoDTOs.add(objAcervo);
			}

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervoDTOs;
	}

	public List<AcervoDTO> listaAcervoPorCodigoAcervo(Cliente cliente, Integer codIni, Integer codFim) throws ControllerException {
		HibernateUtil.getSession();
		List<AcervoDTO> acervoDTOs = new ArrayList<AcervoDTO>();
		List<Acervo> acervos = new ArrayList<Acervo>();

		try {
			// --- Rotina para buscar todo o acervo pela data de inclusão do registro
			acervos = acervoDAO.listaAcervoPorCodigoAcervo(cliente, codIni, codFim);
			
			// --- Carregando objeto de impressão com os dados do acervo
			for (Acervo acervo : acervos) {
				AcervoDTO objAcervo = new AcervoDTO();
				objAcervo.setId(acervo.getId());
				objAcervo.setAcer_ds_assunto(acervo.getAcer_ds_assunto());
				objAcervo.setAcer_nr_codbarras(acervo.getAcer_nr_codbarras());
				acervoDTOs.add(objAcervo);
			}

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return acervoDTOs;
	}

	public Acervo carregaSubClassesDoAcervo(Acervo filtro) throws ControllerException {
		HibernateUtil.getSession();
		
		Acervo objAcervo = new Acervo();
		objAcervo=filtro;

		// --- Carrega Documento relacionados ao acervo
		
		List<Documento> documentos = new ArrayList<Documento>();
		List<AcervoDescritor> acervoDescritors = new ArrayList<AcervoDescritor>();

		try
		{
//			HibernateUtil.beginTransaction();
			/*processa tudo que tem pra fazer */
			// --- ROTINA PARA BUSCAR TODOS DOCUMENTOS RELACIONADOS AO ACERVO
			documentos = buscaTodosDocumentosDoAcervo(filtro);
			// --- ROTINA PARA BUSCAR TODOS OS DESCRITORES RELACIONADOS AO ACERVO
			acervoDescritors = buscaTodosDescritoresDoAcervo(filtro);
//			HibernateUtil.commitTransaction();
			// --- ATRIBUINDO AS SUBCLASSES AO ACERVO
			objAcervo.setDocumentos(documentos);
			objAcervo.setAcervoDescritors(acervoDescritors);
		} 
		finally
		{
 			HibernateUtil.closeSession();
		}
		
		return objAcervo;
				
	}
	
	public Integer proximoID() throws ControllerException {
		HibernateUtil.getSession();

		Integer proxDoc = 0;

		try
		{
		// --- ROTINA PARA BUSCAR O ÚLTIMO NÚMERO DE ACERVO CADASTRADO
			proxDoc = acervoDAO.ultimoID();
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
 			HibernateUtil.closeSession();
		}
		
		return proxDoc;
	}
	
	public Acervo pesquisaAcervoPorCodBarra(String filtro) throws ControllerException {
		HibernateUtil.getSession();

		Acervo acervo = new Acervo();

		try
		{
		// --- ROTINA PARA BUSCAR O ACERVO
			acervo = acervoDAO.pesquisaAcervoPorCodBarra(filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
 			HibernateUtil.closeSession();
		}
		
		return acervo;
	}
	
	public List<Acervo> listaAcervosPorTipo(Tipo filtro) throws ControllerException {
		HibernateUtil.getSession();

		List<Acervo> acervos = new ArrayList<Acervo>();

		try
		{
		// --- ROTINA PARA BUSCAR TODOS DOCUMENTOS RELACIONADOS AO TIPO
			acervos = acervoDAO.pesquisaAcervosPorTipo(filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
 			HibernateUtil.closeSession();
		}
		
		return acervos;
	}


}

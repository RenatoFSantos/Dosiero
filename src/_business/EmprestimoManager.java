package _business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import _dao.impl.EmprestimoDAO;
import _dao.interfaces.IEmprestimoDAO;
import _dao.util.HibernateUtil;
import _model.dto.EmprestimoDTO;
import _model.dto.UnidadeDTO;
import _model.vo.Boleto;
import _model.vo.Documento;
import _model.vo.Emprestimo;
import _model.vo.Unidade;
import _model.vo.Usuario;
import exception.ControllerException;
import exception.DaoException;

public class EmprestimoManager {

	private IEmprestimoDAO emprestimoDAO = new EmprestimoDAO();

	public EmprestimoManager() {
	}

	public List<EmprestimoDTO> listaEmprestimoPorUnidade(Unidade unidade, Date dataIni, Date dataFim) throws ControllerException {
		HibernateUtil.getSession();
		List<EmprestimoDTO> emprestimoDTOs = new ArrayList<EmprestimoDTO>();
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

		try {
			// --- Rotina para buscar todos os empréstimos pela unidade selecionada.
			emprestimos = emprestimoDAO.listaEmprestimoPorUnidade(unidade, dataIni, dataFim);
			
			// --- Carregando objeto de impressão com os dados do emprestimo
			for (Emprestimo emprestimo : emprestimos) {
				EmprestimoDTO objEmprestimo = new EmprestimoDTO();
				objEmprestimo.setEmpr_dt_emprestimo(emprestimo.getEmpr_dt_emprestimo());
				objEmprestimo.setEmpr_dt_prev_devolucao(emprestimo.getEmpr_dt_prev_devolucao());
				objEmprestimo.setEmpr_dt_real_devolucao(emprestimo.getEmpr_dt_real_devolucao());
				objEmprestimo.setEmpr_dt_renovacao(emprestimo.getEmpr_dt_renovacao());
				objEmprestimo.setEmpr_tp_emprestimo(emprestimo.getEmpr_tp_emprestimo());
				objEmprestimo.setObjAcervo(emprestimo.getObjAcervo());
				objEmprestimo.setObjBoleto(emprestimo.getObjBoleto());
				objEmprestimo.setObjUsuarioCadastro(emprestimo.getObjUsuarioCadastro());
				objEmprestimo.setObjUsuarioEmprestimo(emprestimo.getObjUsuarioEmprestimo());
				emprestimoDTOs.add(objEmprestimo);
			}

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return emprestimoDTOs;
	}

	public void salvar(Emprestimo emprestimo) throws ControllerException {
		HibernateUtil.getSession();
		try {
			HibernateUtil.beginTransaction();
			emprestimoDAO.merge(emprestimo);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void atualizar(Emprestimo emprestimo) throws ControllerException {
		HibernateUtil.getSession();
		try {
			HibernateUtil.beginTransaction();
			emprestimoDAO.save(emprestimo);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Emprestimo emprestimo) throws ControllerException {
		HibernateUtil.getSession();
		try {
			HibernateUtil.beginTransaction();
			emprestimoDAO.delete(emprestimo);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Emprestimo> listaEmprestimos() throws ControllerException {
		HibernateUtil.getSession();
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			emprestimos = emprestimoDAO.allEmprestimos();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return emprestimos;
	}

	public Emprestimo pesquisa(Emprestimo filtro) throws ControllerException {
		HibernateUtil.getSession();
		Emprestimo emprestimo = new Emprestimo();

		try {
			// --- Rotina para buscar todos os usuários do banco
			emprestimo = emprestimoDAO.pesquisaEmprestimo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return emprestimo;
	}

	public List<Emprestimo> buscaEmprestimosEmAberto(Usuario filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			emprestimos = emprestimoDAO.buscaEmprestimosEmAberto(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return emprestimos;
	}

	public List<Emprestimo> buscaEmprestimosPorCodBarras(String filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			emprestimos = emprestimoDAO.buscaEmprestimosPorCodBarras(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return emprestimos;
	}

	public List<Emprestimo> buscaEmprestimosPorBoleto(Boleto filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			emprestimos = emprestimoDAO.buscaEmprestimosPorBoleto(filtro);
		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return emprestimos;
	}
}

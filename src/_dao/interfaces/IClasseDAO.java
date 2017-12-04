package _dao.interfaces;

import java.util.List;

import _model.dto.ClasseDTO;
import _model.vo.Classe;
import exception.DaoException;

public interface IClasseDAO extends IGenericDAO<Classe, Integer>  {
	List<Classe> allClasses() throws DaoException;

	List<Classe> pesquisaClasse(Classe filtro) throws DaoException;

	List<Classe> pesquisaClassesPai() throws DaoException;
	
	List<Classe> pesquisaClassesPrincipais() throws DaoException;

	List<Classe> temFilhas(Classe pai) throws DaoException;
	
	Classe carrega(Integer id) throws DaoException;
	
	Classe carregaClasseComAcervo(Classe filtro) throws DaoException;

	List<ClasseDTO> buscaDadosRelatorioClasse(Classe filtro)  throws DaoException;

	Classe carregaClassePorCodigo(Classe filtro) throws DaoException;
	
}

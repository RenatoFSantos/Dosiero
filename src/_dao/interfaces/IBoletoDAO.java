package _dao.interfaces;

import java.util.List;

import _model.vo.Boleto;
import exception.DaoException;

public interface IBoletoDAO extends IGenericDAO<Boleto, Integer> 
{
	List<Boleto> allBoletos() throws DaoException;

	Integer ultimoID() throws DaoException;
	
}

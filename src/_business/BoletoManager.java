package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.BoletoDAO;
import _dao.interfaces.IBoletoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Boleto;
import exception.ControllerException;
import exception.DaoException;

public class BoletoManager {

	private IBoletoDAO boletoDAO = new BoletoDAO();

	public BoletoManager() {
	}
	
	public void salvar(Boleto boleto) throws ControllerException {
		HibernateUtil.getSession();
		try {
			HibernateUtil.beginTransaction();
			boletoDAO.save(boleto);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Boleto boleto) throws ControllerException
	{
		HibernateUtil.getSession();

		try
		{
			HibernateUtil.beginTransaction();
			boletoDAO.delete(boleto);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public List<Boleto> listaBoletos() throws ControllerException {
		HibernateUtil.getSession();

		List<Boleto> boletos = new ArrayList<Boleto>();

		try
		{
		// --- ROTINA PARA BUSCAR TODOS OS REGISTROS DO OBJETO
			boletos = boletoDAO.allBoletos();
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}
		
		return boletos;
	}

	public Integer proximoID() throws ControllerException {
		HibernateUtil.getSession();

		Integer proxDoc = 0;

		try
		{
		// --- ROTINA PARA BUSCAR O ÚLTIMO NÚMERO DE DOCUMENTO CADASTRADO
			proxDoc = boletoDAO.ultimoID();
			
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
	
	public Boleto pesquisaBoletoPorId(Integer filtro) throws ControllerException {
		HibernateUtil.getSession();

		Boleto boleto = new Boleto();

		try
		{
		// --- ROTINA PARA BUSCAR O ÚLTIMO NÚMERO DE DOCUMENTO CADASTRADO
			boleto = boletoDAO.findByID(Boleto.class, filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
 			HibernateUtil.closeSession();
		}
		
		return boleto;
		
	}

}

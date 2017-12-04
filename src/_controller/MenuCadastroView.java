package _controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="menuCadastroBean")
@RequestScoped
public class MenuCadastroView
{
	
	public String produtoCrud()
	{
	
		return "/pages/produto/produtoCrud?faces-redirect=true";
	}

}

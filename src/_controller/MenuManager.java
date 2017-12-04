package _controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean(name = "menuManagerBean")
@RequestScoped
public class MenuManager
{
	private MenuModel model;
	private MenuModel menuCrud;
	private MenuModel menuLista;

	public MenuManager()
	{
		this.loadMenuCadastro();
		this.loadMenuCrud();
		this.loadMenuLista();
	}

	public MenuModel getModel()
	{
		return model;
	}

	public MenuModel getMenuCrud()
	{
		return menuCrud;
	}

	public MenuModel getMenuLista()
	{
		return menuLista;
	}

	private void loadMenuCadastro()
	{
		model = new DefaultMenuModel();

		// First submenu
		DefaultSubMenu firstSubmenu = new DefaultSubMenu("Menu");

		DefaultMenuItem item = new DefaultMenuItem("Produto");
		// item.setUrl("http://www.primefaces.org");
		item.setCommand("/pages/produto/produtoLista?faces-redirect=true");
		item.setIcon("ui-icon-home");
		firstSubmenu.addElement(item);
		
		item = new DefaultMenuItem("Entrada");
		// item.setUrl("http://www.primefaces.org");
		item.setCommand("/pages/produto/produtoCrud?faces-redirect=true");
		item.setIcon("ui-icon-home");
		firstSubmenu.addElement(item);
		
		item = new DefaultMenuItem("Saída");
		// item.setUrl("http://www.primefaces.org");
		item.setCommand("/pages/produto/produtoCrud?faces-redirect=true");
		item.setIcon("ui-icon-home");

		firstSubmenu.addElement(item);

		model.addElement(firstSubmenu);

	}

	private void loadMenuCrud()
	{
		menuCrud = new DefaultMenuModel();

		// First submenu
		DefaultMenuItem firstSubmenu = new DefaultMenuItem("Novo");
		firstSubmenu.setIcon("ui-icon-folder-open");
		firstSubmenu.setStyleClass("menuCrud");
		menuCrud.addElement(firstSubmenu);

		firstSubmenu = new DefaultMenuItem("Salvar");
		firstSubmenu.setIcon("ui-icon-disk");
		firstSubmenu.setStyleClass("menuCrud");
		firstSubmenu.setCommand("#{produtoView.save()}");
		menuCrud.addElement(firstSubmenu);
		
		
		firstSubmenu = new DefaultMenuItem("Voltar");
		firstSubmenu.setIcon("ui-icon-arrowreturnthick-1-w");
		firstSubmenu.setStyleClass("menuCrud");
		firstSubmenu.setCommand("/pages/produto/produtoLista?faces-redirect=true");
		menuCrud.addElement(firstSubmenu);


	}

	private void loadMenuLista()
	{
		menuLista = new DefaultMenuModel();

		// First submenu
		DefaultMenuItem firstSubmenu = new DefaultMenuItem("Novo");
		firstSubmenu.setIcon("ui-icon-folder-open");
		firstSubmenu.setStyleClass("menuCrud");
		firstSubmenu.setCommand("/pages/produto/produtoCrud?faces-redirect=true");
		menuLista.addElement(firstSubmenu);

		firstSubmenu = new DefaultMenuItem("Pesquisar");
		firstSubmenu.setIcon("ui-icon-disk");
		firstSubmenu.setStyleClass("menuCrud");
		menuLista.addElement(firstSubmenu);

	}
}

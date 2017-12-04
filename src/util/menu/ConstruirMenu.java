package util.menu;


public class ConstruirMenu
{/*
	private static MenuModel model;

	private static void criarMenu() throws ControllerException
	{
		DefaultSubMenu menupai = null;

		MenuManager menuManager = new MenuManager();

		List<MenuEntity> menus = menuManager.listaMenu();

		if (menus != null)
		{
			model = new DefaultMenuModel();

			for (MenuEntity menuEntity : menus)
			{
				if (menuEntity.getMenuPai() == null)
				{
					menuManager = new MenuManager();
					Boolean existeFilho = menuManager.existeFilho(menuEntity);

					if (!existeFilho)
					{
						DefaultMenuItem menuItem = new DefaultMenuItem();
						menuItem.setValue(menuEntity.getDescricao());

						model.addElement(menuItem);
					}
					else
					{
						DefaultSubMenu subMenu = new DefaultSubMenu("");
						subMenu.setId(menuEntity.getId().toString());
						subMenu.setLabel(menuEntity.getDescricao());
						model.addElement(subMenu);
					}
				}
				else
				{
					menupai = retornaMenuPai(menuEntity.getMenuPai().getId().toString(), model.getElements(), menupai);

					if (menupai != null)
					{
						Boolean existeFilho = menuManager.existeFilho(menuEntity);

						if (!existeFilho)
						{
							DefaultMenuItem menuItem = new DefaultMenuItem();
							menuItem.setValue(menuEntity.getDescricao());
							menupai.addElement(menuItem);
						}
						else
						{
							DefaultSubMenu menuItem = new DefaultSubMenu();
							menuItem.setId(menuEntity.getId().toString());
							menuItem.setLabel(menuEntity.getDescricao());
							menupai.addElement(menuItem);
						}
					}
				}
			}
		}
	}

	private static DefaultSubMenu retornaMenuPai(String key, List<MenuElement> menuElements,
			DefaultSubMenu menuElementFind)
	{

		for (MenuElement menuElement : menuElements)
		{

			if (menuElement instanceof DefaultSubMenu)
			{
				DefaultSubMenu subMenu = (DefaultSubMenu) menuElement;

				if (subMenu.getId().equals(key))
				{
					menuElementFind = subMenu;
				}
				else if (subMenu.getElementsCount() > 0)
				{
					menuElementFind = retornaMenuPai(key, subMenu.getElements(), menuElementFind);
				}

				if (menuElementFind != null)
					break;
			}
			else
			{
				menuElementFind = null;
			}
		}

		return menuElementFind;
	}

	public static MenuModel getModel() throws ControllerException
	{
		criarMenu();
		return model;
	}*/

}
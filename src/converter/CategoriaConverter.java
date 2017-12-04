package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import _model.vo.Categoria;

@FacesConverter("categoriaConverter")
public class CategoriaConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
            return (Categoria) uiComponent.getAttributes().get(value);
        }
		return null;

	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Categoria) {
			Categoria entity = (Categoria) value;

			if (entity != null && entity instanceof Categoria && entity.getId() != null) {
				uiComponent.getAttributes().put(entity.getCate_nm_categoria(), entity);
				return entity.getCate_nm_categoria();
			}
		}
		return "";
	}

}

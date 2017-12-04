package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import _model.vo.Usuario;

@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
            return (Usuario) uiComponent.getAttributes().get(value);
        }
		return null;

	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Usuario) {
			Usuario entity = (Usuario) value;

			if (entity != null && entity instanceof Usuario && entity.getId() != null) {
				uiComponent.getAttributes().put(entity.getUsua_nm_usuario(), entity);
				return entity.getUsua_nm_usuario();
			}
		}
		return "";
	}
}

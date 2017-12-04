package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import _model.vo.Tipo;

@FacesConverter("tipoConverter")
public class TipoConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
            return (Tipo) uiComponent.getAttributes().get(value);
        }
		return null;

	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Tipo) {
			Tipo entity = (Tipo) value;

			if (entity != null && entity instanceof Tipo && entity.getId() != null) {
				uiComponent.getAttributes().put(entity.getTipo_ds_tipo(), entity);
				return entity.getTipo_ds_tipo();
			}
		}
		return "";
	}

}

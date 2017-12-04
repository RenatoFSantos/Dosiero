package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import _model.vo.Descritor;

@FacesConverter("descritorConverter")
public class DescritorConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
            return (Descritor) uiComponent.getAttributes().get(value);
        }
		return null;

	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Descritor) {
			Descritor entity = (Descritor) value;

			if (entity != null && entity instanceof Descritor && entity.getId() != null) {
				uiComponent.getAttributes().put(entity.getDesc_nm_descritor(), entity);
				return entity.getDesc_nm_descritor();
			}
		}
		return "";
	}

}

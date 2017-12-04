package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import _model.vo.Classe;

@FacesConverter(forClass = Classe.class)
public class ClasseConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
            return (Classe) uiComponent.getAttributes().get(value);
        }
		return null;

	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Classe) {
			Classe entity = (Classe) value;

			if (entity != null && entity instanceof Classe && entity.getId() != null) {
				uiComponent.getAttributes().put(entity.getClas_ds_nome(), entity);
				return entity.getClas_ds_nome();
			}
		}
		return "";
	}

}

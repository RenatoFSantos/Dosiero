package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import _model.vo.Cliente;

@FacesConverter(value="clienteConverter", forClass = Cliente.class)
public class ClienteConverter implements Converter {
		
		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
			if (value != null && !value.isEmpty()) {
	            return (Cliente) uiComponent.getAttributes().get(value);
	        }
			return null;

		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
			if (value instanceof Cliente) {
				Cliente entity = (Cliente) value;

				if (entity != null && entity instanceof Cliente && entity.getId() != null) {
					uiComponent.getAttributes().put( entity.getId().toString(), entity);
	                return entity.getId().toString();
				}
			}
			return "";
		}

}

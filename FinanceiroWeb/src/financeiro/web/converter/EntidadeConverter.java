package financeiro.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import financeiro.entidade.Entidade;
import financeiro.entidade.EntidadeRN;

@FacesConverter(value="entidadeConverter")
public class EntidadeConverter implements Converter {
	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				EntidadeRN entidadeRN = new EntidadeRN();
				return entidadeRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("Não foi possível encontrar a entidade de código " 
						+ value + ". " + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Entidade entidade = (Entidade) value;
		return String.valueOf(entidade.getEntidade());
	}

}

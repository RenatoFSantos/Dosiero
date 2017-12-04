package _model.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "parametro")
@SequenceGenerator(name = "sq_parametro", sequenceName = "sq_parametro", initialValue = 1, allocationSize = 1)
public class Parametro extends EntityBase {

	private static final long serialVersionUID = 1L;
	private String impressora;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_parametro")
	@Column(name = "para_sq_id")
	public Integer getId() {
		return super.getId();
	}
	
	@Column(name="para_nm_impressora")
	public String getImpressora() {
		return impressora;
	}

	public void setImpressora(String impressora) {
		this.impressora = impressora;
	}
	
}

package _model.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Boleto")
@SequenceGenerator(name = "sq_boleto", sequenceName = "sq_boleto", initialValue = 1, allocationSize = 1)
public class Boleto extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bole_in_impressao;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_boleto")
	@Column(name = "bole_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@Column(name="bole_in_impressao")
	public String getBole_in_impressao() {
		return bole_in_impressao;
	}

	public void setBole_in_impressao(String bole_in_impressao) {
		this.bole_in_impressao = bole_in_impressao;
	}

	
	
	
	
}

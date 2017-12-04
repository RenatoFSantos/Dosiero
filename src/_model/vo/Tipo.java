package _model.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Tipo")
@SequenceGenerator(name = "sq_tipo", sequenceName = "sq_tipo", initialValue = 1, allocationSize = 1)
public class Tipo extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipo_ds_tipo;
	private List<Acervo> acervos = new ArrayList<Acervo>();
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_tipo")
	@Column(name = "tipo_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@Column(name="tipo_ds_tipo")
	public String getTipo_ds_tipo() {
		return tipo_ds_tipo;
	}

	public void setTipo_ds_tipo(String tipo_ds_tipo) {
		this.tipo_ds_tipo = tipo_ds_tipo;
	}

	
	@OneToMany(mappedBy="objTipo", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	public List<Acervo> getAcervos() {
		return acervos;
	}

	public void setAcervos(List<Acervo> acervos) {
		this.acervos = acervos;
	}
	
	
	
	
}

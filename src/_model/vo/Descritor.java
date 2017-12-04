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
@Table(name = "Descritor")
@SequenceGenerator(name = "sq_descritor", sequenceName = "sq_descritor", initialValue = 1, allocationSize = 1)
public class Descritor extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4735616242713823L;
	private String desc_sg_descritor;
	private String desc_nm_descritor;
	private String desc_ds_complemento;
	private String desc_cd_indice;
	private Integer desc_in_delecao = 0;
	private List<AcervoDescritor> acervoDescritors = new ArrayList<AcervoDescritor>();
	
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_descritor")
	@Column(name = "desc_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@Column(name="desc_sg_descritor")
	public String getDesc_sg_descritor() {
		return desc_sg_descritor;
	}


	public void setDesc_sg_descritor(String desc_sg_descritor) {
		this.desc_sg_descritor = desc_sg_descritor;
	}

	@Column(name="desc_nm_descritor")
	public String getDesc_nm_descritor() {
		return desc_nm_descritor;
	}


	public void setDesc_nm_descritor(String desc_nm_descritor) {
		this.desc_nm_descritor = desc_nm_descritor;
	}

	@Column(name="desc_ds_complemento")
	public String getDesc_ds_complemento() {
		return desc_ds_complemento;
	}


	public void setDesc_ds_complemento(String desc_ds_complemento) {
		this.desc_ds_complemento = desc_ds_complemento;
	}

	@Column(name="desc_cd_indice")
	public String getDesc_cd_indice() {
		return desc_cd_indice;
	}


	public void setDesc_cd_indice(String desc_cd_indice) {
		this.desc_cd_indice = desc_cd_indice;
	}

	public Integer getDesc_in_delecao() {
		return desc_in_delecao;
	}

	public void setDesc_in_delecao(Integer desc_in_delecao) {
		this.desc_in_delecao = desc_in_delecao;
	}

	@OneToMany(mappedBy="objDescritor", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	public List<AcervoDescritor> getAcervoDescritors() {
		return acervoDescritors;
	}

	public void setAcervoDescritors(List<AcervoDescritor> acervoDescritors) {
		this.acervoDescritors = acervoDescritors;
	}


	
	
}

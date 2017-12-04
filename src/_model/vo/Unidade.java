package _model.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "Unidade")
@SequenceGenerator(name = "sq_unidade", sequenceName = "sq_unidade", initialValue = 1, allocationSize = 1)
public class Unidade extends EntityBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3057003411214384030L;
	private String unid_sg_sigla;
	private String unid_nm_unidade;
	private Integer unid_in_delecao = 0;
	private List<Acervo> acervos = new ArrayList<Acervo>();
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_unidade")
	@Column(name = "unid_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@Column(name = "unid_sg_sigla")	
	public String getUnid_sg_sigla() {
		return unid_sg_sigla;
	}

	public void setUnid_sg_sigla(String unid_sg_sigla) {
		this.unid_sg_sigla = unid_sg_sigla;
	}

	@Column(name = "unid_nm_unidade")
	public String getUnid_nm_unidade() {
		return unid_nm_unidade;
	}

	public void setUnid_nm_unidade(String unid_nm_unidade) {
		this.unid_nm_unidade = unid_nm_unidade;
	}

	@Column(name = "unid_in_delecao")
	public Integer getUnid_in_delecao() {
		return unid_in_delecao;
	}

	public void setUnid_in_delecao(Integer unid_in_delecao) {
		this.unid_in_delecao = unid_in_delecao;
	}

	@OneToMany(mappedBy="objUnidade")
	@Where(clause="acer_in_delecao=0")
	public List<Acervo> getAcervos() {
		return acervos;
	}

	public void setAcervos(List<Acervo> acervos) {
		this.acervos = acervos;
	}

	@OneToMany(mappedBy="objUnidade")
	@Where(clause="usua_in_delecao=0")
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	
}

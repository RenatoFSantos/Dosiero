package _model.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Perfil")
@SequenceGenerator(name = "sq_perfil", sequenceName = "sq_perfil", initialValue = 1, allocationSize = 1)
public class Perfil extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8029653548548054909L;
	private String perf_cd_perfil;
	private String perf_nm_perfil;
	private Integer perf_in_delecao = 0;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<PerfilPagina> perfilPaginas = new ArrayList<PerfilPagina>();

	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_perfil")
	@Column(name = "perf_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@Column(name="perf_cd_perfil")
	public String getPerf_cd_perfil() {
		return perf_cd_perfil;
	}


	public void setPerf_cd_perfil(String perf_cd_perfil) {
		this.perf_cd_perfil = perf_cd_perfil;
	}

	@Column(name="perf_nm_perfil")
	public String getPerf_nm_perfil() {
		return perf_nm_perfil;
	}


	public void setPerf_nm_perfil(String perf_nm_perfil) {
		this.perf_nm_perfil = perf_nm_perfil;
	}

	@Column(name="perf_in_delecao")
	public Integer getPerf_in_delecao() {
		return perf_in_delecao;
	}


	public void setPerf_in_delecao(Integer perf_in_delecao) {
		this.perf_in_delecao = perf_in_delecao;
	}

	@OneToMany(mappedBy="objPerfil")
	@Where(clause="usua_in_delecao=0") 
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "perf_sq_id", referencedColumnName = "perf_sq_id", updatable=false, insertable=false)	
	public List<PerfilPagina> getPerfilPaginas() {
		return perfilPaginas;
	}

	public void setPerfilPaginas(List<PerfilPagina> perfilPaginas) {
		this.perfilPaginas = perfilPaginas;
	}


	
}

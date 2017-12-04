package _model.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "perfil_pagina")
@SequenceGenerator(name = "sq_perfil_pagina", sequenceName = "sq_perfil_pagina", initialValue = 1, allocationSize = 1)
public class PerfilPagina extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3714086321266755943L;
	private Perfil objPerfil = new Perfil();
	private Pagina objPagina = new Pagina();
	private String pepa_in_acesso;
	private boolean pepa_in_acesso_total=false;
	private boolean pepa_in_acesso_leitura=false;
	private boolean pepa_in_acesso_escrita=false;
	private boolean pepa_in_acesso_exclusao=false;
		
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_perfil_pagina")
	@Column(name = "pepa_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@ManyToOne
	@JoinColumn(name="perf_sq_id", nullable=false)
	public Perfil getObjPerfil() {
		return objPerfil;
	}

	public void setObjPerfil(Perfil objPerfil) {
		this.objPerfil = objPerfil;
	}

	@ManyToOne
	@JoinColumn(name="pagi_sq_id", nullable=false)
	public Pagina getObjPagina() {
		return objPagina;
	}

	public void setObjPagina(Pagina objPagina) {
		this.objPagina = objPagina;
	}

	@Column(name="pepa_in_acesso")
	public String getPepa_in_acesso() {
		return pepa_in_acesso;
	}

	public void setPepa_in_acesso(String pepa_in_acesso) {
		this.pepa_in_acesso = pepa_in_acesso;
	}

	@Transient
	public boolean isPepa_in_acesso_total() {
		if (this.pepa_in_acesso!=null && this.pepa_in_acesso.indexOf("T")>=0) {
			pepa_in_acesso_total=true;
		}
		return pepa_in_acesso_total;
	}

	public void setPepa_in_acesso_total(boolean pepa_in_acesso_total) {
		this.pepa_in_acesso_total = pepa_in_acesso_total;
	}

	@Transient
	public boolean isPepa_in_acesso_leitura() {
		if (this.pepa_in_acesso!=null && this.pepa_in_acesso.indexOf("L")>=0) {
			pepa_in_acesso_leitura=true;
		}
		return pepa_in_acesso_leitura;
	}

	public void setPepa_in_acesso_leitura(boolean pepa_in_acesso_leitura) {
		this.pepa_in_acesso_leitura = pepa_in_acesso_leitura;
	}

	@Transient
	public boolean isPepa_in_acesso_escrita() {
		if (this.pepa_in_acesso!=null && this.pepa_in_acesso.indexOf("E")>=0) {
			pepa_in_acesso_escrita=true;
		}
		return pepa_in_acesso_escrita;
	}

	public void setPepa_in_acesso_escrita(boolean pepa_in_acesso_escrita) {
		this.pepa_in_acesso_escrita = pepa_in_acesso_escrita;
	}

	@Transient
	public boolean isPepa_in_acesso_exclusao() {
		if (this.pepa_in_acesso!=null && this.pepa_in_acesso.indexOf("X")>=0) {
			pepa_in_acesso_exclusao=true;
		}
		return pepa_in_acesso_exclusao;
	}

	public void setPepa_in_acesso_exclusao(boolean pepa_in_acesso_exclusao) {
		this.pepa_in_acesso_exclusao = pepa_in_acesso_exclusao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((objPagina == null) ? 0 : objPagina.hashCode());
		result = prime * result + ((pepa_in_acesso == null) ? 0 : pepa_in_acesso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerfilPagina other = (PerfilPagina) obj;
		if (objPagina == null) {
			if (other.objPagina != null)
				return false;
		} else if (!objPagina.getId().equals(other.objPagina.getId()))
			return false;
		if (pepa_in_acesso == null) {
			if (other.pepa_in_acesso != null)
				return false;
		} else if (!pepa_in_acesso.equals(other.pepa_in_acesso))
			return false;
		return true;
	}



	
}

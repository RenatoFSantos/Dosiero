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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "Pagina")
@SequenceGenerator(name = "sq_pagina", sequenceName = "sq_pagina", initialValue = 1, allocationSize = 1)
public class Pagina extends EntityBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2018481346424240226L;
	private String pagi_nm_pagina;
	private Modulo objModulo = new Modulo();
	private List<PerfilPagina> perfilPaginas = new ArrayList<PerfilPagina>();
	private Integer pagi_in_delecao = 0;
	private boolean pagi_in_acesso_total=false;
	private boolean pagi_in_acesso_leitura=false;
	private boolean pagi_in_acesso_escrita=false;
	private boolean pagi_in_acesso_exclusao=false;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_pagina")
	@Column(name = "pagi_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@Column(name="pagi_nm_pagina")
	public String getPagi_nm_pagina() {
		return pagi_nm_pagina;
	}

	public void setPagi_nm_pagina(String pagi_nm_pagina) {
		this.pagi_nm_pagina = pagi_nm_pagina;
	}

	@ManyToOne()
	@JoinColumn(name="modu_sq_id" , nullable=false)
	public Modulo getObjModulo() {
		return objModulo;
	}

	public void setObjModulo(Modulo objModulo) {
		this.objModulo = objModulo;
	}

	@Column(name="pagi_in_delecao")
	public Integer getPagi_in_delecao() {
		return pagi_in_delecao;
	}

	public void setPagi_in_delecao(Integer pagi_in_delecao) {
		this.pagi_in_delecao = pagi_in_delecao;
	}

	@Transient
	public boolean isPagi_in_acesso_total() {
		return pagi_in_acesso_total;
	}


	public void setPagi_in_acesso_total(boolean pagi_in_acesso_total) {
		this.pagi_in_acesso_total = pagi_in_acesso_total;
	}

	@Transient
	public boolean isPagi_in_acesso_leitura() {
		return pagi_in_acesso_leitura;
	}

	public void setPagi_in_acesso_leitura(boolean pagi_in_acesso_leitura) {
		this.pagi_in_acesso_leitura = pagi_in_acesso_leitura;
	}

	@Transient
	public boolean isPagi_in_acesso_escrita() {
		return pagi_in_acesso_escrita;
	}

	public void setPagi_in_acesso_escrita(boolean pagi_in_acesso_escrita) {
		this.pagi_in_acesso_escrita = pagi_in_acesso_escrita;
	}

	@Transient
	public boolean isPagi_in_acesso_exclusao() {
		return pagi_in_acesso_exclusao;
	}

	public void setPagi_in_acesso_exclusao(boolean pagi_in_acesso_exclusao) {
		this.pagi_in_acesso_exclusao = pagi_in_acesso_exclusao;
	}
	
	@OneToMany(mappedBy="objPagina", fetch=FetchType.EAGER)
	public List<PerfilPagina> getPerfilPaginas() {
		return perfilPaginas;
	}

	public void setPerfilPaginas(List<PerfilPagina> perfilPaginas) {
		this.perfilPaginas = perfilPaginas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((objModulo == null) ? 0 : objModulo.hashCode());
		result = prime * result + ((pagi_in_delecao == null) ? 0 : pagi_in_delecao.hashCode());
		result = prime * result + ((pagi_nm_pagina == null) ? 0 : pagi_nm_pagina.hashCode());
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
		Pagina other = (Pagina) obj;
		if (objModulo == null) {
			if (other.objModulo != null)
				return false;
		} else if (!objModulo.equals(other.objModulo))
			return false;
		if (pagi_in_delecao == null) {
			if (other.pagi_in_delecao != null)
				return false;
		} else if (!pagi_in_delecao.equals(other.pagi_in_delecao))
			return false;
		if (pagi_nm_pagina == null) {
			if (other.pagi_nm_pagina != null)
				return false;
		} else if (!pagi_nm_pagina.equals(other.pagi_nm_pagina))
			return false;
		return true;
	}


}

package _model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "documento")
@SequenceGenerator(name = "sq_documento", sequenceName = "sq_documento", initialValue = 1, allocationSize = 1)
public class Documento extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6648759706865291550L;
	private Acervo objAcervo = new Acervo();
	private Date docu_dt_inclusao;
	private String docu_nm_documento;
	private String docu_tx_conteudo;
	private String docu_nm_arquivo;
	private String docu_tx_observacao;
	private String docu_nr_codbarras;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_documento")
	@Column(name = "docu_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@ManyToOne
	@JoinColumn(name="acer_sq_id")
	public Acervo getObjAcervo() {
		return objAcervo;
	}


	public void setObjAcervo(Acervo objAcervo) {
		this.objAcervo = objAcervo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="docu_dt_inclusao")
	public Date getDocu_dt_inclusao() {
		return docu_dt_inclusao;
	}


	public void setDocu_dt_inclusao(Date docu_dt_inclusao) {
		this.docu_dt_inclusao = docu_dt_inclusao;
	}

	@Column(name="docu_tx_conteudo")
	public String getDocu_tx_conteudo() {
		return docu_tx_conteudo;
	}


	public void setDocu_tx_conteudo(String docu_tx_conteudo) {
		this.docu_tx_conteudo = docu_tx_conteudo;
	}

	@Column(name="docu_nm_documento")
	public String getDocu_nm_documento() {
		return docu_nm_documento;
	}

	public void setDocu_nm_documento(String docu_nm_documento) {
		this.docu_nm_documento = docu_nm_documento;
	}
	
	@Column(name="docu_nm_arquivo")
	public String getDocu_nm_arquivo() {
		return docu_nm_arquivo;
	}

	public void setDocu_nm_arquivo(String docu_nm_arquivo) {
		this.docu_nm_arquivo = docu_nm_arquivo;
	}

	@Column(name="docu_tx_observacao")
	public String getDocu_tx_observacao() {
		return docu_tx_observacao;
	}

	public void setDocu_tx_observacao(String docu_tx_observacao) {
		this.docu_tx_observacao = docu_tx_observacao;
	}

	@Column(name="docu_nr_codbarras")
	public String getDocu_nr_codbarras() {
		return docu_nr_codbarras;
	}

	public void setDocu_nr_codbarras(String docu_nr_codbarras) {
		this.docu_nr_codbarras = docu_nr_codbarras;
	}
	
}

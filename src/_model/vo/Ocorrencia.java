package _model.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocorrencia")
@SequenceGenerator(name = "sq_ocorrencia", sequenceName = "sq_ocorrencia", initialValue = 1, allocationSize = 1)
public class Ocorrencia extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario objUsuarioInclusao = new Usuario();
	private Usuario objUsuarioDesativacao = new Usuario();
	private String ocor_in_ativacao;
	private Date ocor_dt_inclusao;
	private String ocor_tx_ocorrencia;
	private Acervo objAcervo = new Acervo();
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_ocorrencia")
	@Column(name = "ocor_sq_id")
	public Integer getId() {
		return super.getId();
	}


	@ManyToOne
	@JoinColumn(name="usua_sq_id_inclusao", nullable=false)
	public Usuario getObjUsuarioInclusao() {
		return objUsuarioInclusao;
	}

	public void setObjUsuarioInclusao(Usuario objUsuarioInclusao) {
		this.objUsuarioInclusao = objUsuarioInclusao;
	}

	@ManyToOne
	@JoinColumn(name="usua_sq_id_desativacao")
	public Usuario getObjUsuarioDesativacao() {
		return objUsuarioDesativacao;
	}

	public void setObjUsuarioDesativacao(Usuario objUsuarioDesativacao) {
		this.objUsuarioDesativacao = objUsuarioDesativacao;
	}

	@Column(name="ocor_in_ativacao")
	public String getOcor_in_ativacao() {
		return ocor_in_ativacao;
	}

	public void setOcor_in_ativacao(String ocor_in_ativacao) {
		this.ocor_in_ativacao = ocor_in_ativacao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="ocor_dt_inclusao")
	public Date getOcor_dt_inclusao() {
		return ocor_dt_inclusao;
	}

	public void setOcor_dt_inclusao(Date ocor_dt_inclusao) {
		this.ocor_dt_inclusao = ocor_dt_inclusao;
	}

	@Column(name="ocor_tx_ocorrencia")
	public String getOcor_tx_ocorrencia() {
		return ocor_tx_ocorrencia;
	}

	public void setOcor_tx_ocorrencia(String ocor_tx_ocorrencia) {
		this.ocor_tx_ocorrencia = ocor_tx_ocorrencia;
	}

	@ManyToOne
	@JoinColumn(name="acer_sq_id", nullable=false)
	public Acervo getObjAcervo() {
		return objAcervo;
	}

	public void setObjAcervo(Acervo objAcervo) {
		this.objAcervo = objAcervo;
	}
}

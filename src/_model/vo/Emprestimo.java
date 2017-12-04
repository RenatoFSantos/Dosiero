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
@Table(name = "Emprestimo")
@SequenceGenerator(name = "sq_emprestimo", sequenceName = "sq_emprestimo", initialValue = 1, allocationSize = 1)
public class Emprestimo extends EntityBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario objUsuarioCadastro;
	private Usuario objUsuarioEmprestimo;
	private Acervo objAcervo;
	private Boleto objBoleto;
	private Date empr_dt_emprestimo;
	private Date empr_dt_prev_devolucao;
	private Date empr_dt_real_devolucao;
	private Date empr_dt_renovacao;
	private String empr_tp_emprestimo;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_emprestimo")
	@Column(name = "empr_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@ManyToOne
	@JoinColumn(name="usua_sq_id_cadastro", nullable=false)
	public Usuario getObjUsuarioCadastro() {
		return objUsuarioCadastro;
	}

	public void setObjUsuarioCadastro(Usuario objUsuarioCadastro) {
		this.objUsuarioCadastro = objUsuarioCadastro;
	}

	@ManyToOne
	@JoinColumn(name="usua_sq_id_emprestimo", nullable=false)
	public Usuario getObjUsuarioEmprestimo() {
		return objUsuarioEmprestimo;
	}

	public void setObjUsuarioEmprestimo(Usuario objUsuarioEmprestimo) {
		this.objUsuarioEmprestimo = objUsuarioEmprestimo;
	}

	@ManyToOne
	@JoinColumn(name="acer_sq_id", nullable=false)
	public Acervo getObjAcervo() {
		return objAcervo;
	}

	public void setObjAcervo(Acervo objAcervo) {
		this.objAcervo = objAcervo;
	}

	@ManyToOne
	@JoinColumn(name="bole_sq_id", nullable=false)
	public Boleto getObjBoleto() {
		return objBoleto;
	}

	public void setObjBoleto(Boleto objBoleto) {
		this.objBoleto = objBoleto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="empr_dt_emprestimo")
	public Date getEmpr_dt_emprestimo() {
		return empr_dt_emprestimo;
	}

	public void setEmpr_dt_emprestimo(Date empr_dt_emprestimo) {
		this.empr_dt_emprestimo = empr_dt_emprestimo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="empr_dt_prev_devolucao")
	public Date getEmpr_dt_prev_devolucao() {
		return empr_dt_prev_devolucao;
	}

	public void setEmpr_dt_prev_devolucao(Date empr_dt_prev_devolucao) {
		this.empr_dt_prev_devolucao = empr_dt_prev_devolucao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="empr_dt_real_devolucao")
	public Date getEmpr_dt_real_devolucao() {
		return empr_dt_real_devolucao;
	}

	public void setEmpr_dt_real_devolucao(Date empr_dt_real_devolucao) {
		this.empr_dt_real_devolucao = empr_dt_real_devolucao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="empr_dt_renovacao")
	public Date getEmpr_dt_renovacao() {
		return empr_dt_renovacao;
	}

	public void setEmpr_dt_renovacao(Date empr_dt_renovacao) {
		this.empr_dt_renovacao = empr_dt_renovacao;
	}

	@Column(name="empr_tp_emprestimo")
	public String getEmpr_tp_emprestimo() {
		return empr_tp_emprestimo;
	}

	public void setEmpr_tp_emprestimo(String empr_tp_emprestimo) {
		this.empr_tp_emprestimo = empr_tp_emprestimo;
	}
	
	
}

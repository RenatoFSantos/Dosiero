package _model.dto;

import java.io.Serializable;
import java.util.Date;

import _model.vo.Acervo;
import _model.vo.Boleto;
import _model.vo.Documento;
import _model.vo.Usuario;

public class EmprestimoDTO implements Serializable {
	
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
	
	public EmprestimoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	// --- GET's e SET's
	
	public Usuario getObjUsuarioCadastro() {
		return objUsuarioCadastro;
	}
	public void setObjUsuarioCadastro(Usuario objUsuarioCadastro) {
		this.objUsuarioCadastro = objUsuarioCadastro;
	}
	public Usuario getObjUsuarioEmprestimo() {
		return objUsuarioEmprestimo;
	}
	public void setObjUsuarioEmprestimo(Usuario objUsuarioEmprestimo) {
		this.objUsuarioEmprestimo = objUsuarioEmprestimo;
	}
	public Acervo getObjAcervo() {
		return objAcervo;
	}
	public void setObjAcervo(Acervo objAcervo) {
		this.objAcervo = objAcervo;
	}
	public Boleto getObjBoleto() {
		return objBoleto;
	}
	public void setObjBoleto(Boleto objBoleto) {
		this.objBoleto = objBoleto;
	}
	public Date getEmpr_dt_emprestimo() {
		return empr_dt_emprestimo;
	}
	public void setEmpr_dt_emprestimo(Date empr_dt_emprestimo) {
		this.empr_dt_emprestimo = empr_dt_emprestimo;
	}
	public Date getEmpr_dt_prev_devolucao() {
		return empr_dt_prev_devolucao;
	}
	public void setEmpr_dt_prev_devolucao(Date empr_dt_prev_devolucao) {
		this.empr_dt_prev_devolucao = empr_dt_prev_devolucao;
	}
	public Date getEmpr_dt_real_devolucao() {
		return empr_dt_real_devolucao;
	}
	public void setEmpr_dt_real_devolucao(Date empr_dt_real_devolucao) {
		this.empr_dt_real_devolucao = empr_dt_real_devolucao;
	}
	public Date getEmpr_dt_renovacao() {
		return empr_dt_renovacao;
	}
	public void setEmpr_dt_renovacao(Date empr_dt_renovacao) {
		this.empr_dt_renovacao = empr_dt_renovacao;
	}
	public String getEmpr_tp_emprestimo() {
		return empr_tp_emprestimo;
	}
	public void setEmpr_tp_emprestimo(String empr_tp_emprestimo) {
		this.empr_tp_emprestimo = empr_tp_emprestimo;
	}

}

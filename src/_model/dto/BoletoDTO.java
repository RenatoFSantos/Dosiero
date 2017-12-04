package _model.dto;

import java.io.Serializable;
import java.util.Date;

public class BoletoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer bole_sq_boleto;
	private String bole_nm_usuarioemprest;
	private String bole_ds_email_usuarioemprest;
	private String bole_nm_documento;
	private String bole_nr_codbarras;
	private String bole_tx_conteudo;
	private Date bole_dt_emprestimo;
	private Date bole_dt_prev_devolucao;
	private String bole_nm_usuariocadastro;
	private String bole_cd_matricula_usuariocadastro;
	private String bole_ds_telefone_usuariocadastro;
	private String bole_ds_email_usuariocadastro;
	
	public BoletoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	// --- GET's e SET's

	public Integer getBole_sq_boleto() {
		return bole_sq_boleto;
	}

	public void setBole_sq_boleto(Integer bole_sq_boleto) {
		this.bole_sq_boleto = bole_sq_boleto;
	}

	public String getBole_nm_usuarioemprest() {
		return bole_nm_usuarioemprest;
	}

	public void setBole_nm_usuarioemprest(String bole_nm_usuarioemprest) {
		this.bole_nm_usuarioemprest = bole_nm_usuarioemprest;
	}

	public String getBole_ds_email_usuarioemprest() {
		return bole_ds_email_usuarioemprest;
	}

	public void setBole_ds_email_usuarioemprest(String bole_ds_email_usuarioemprest) {
		this.bole_ds_email_usuarioemprest = bole_ds_email_usuarioemprest;
	}

	public String getBole_nm_documento() {
		return bole_nm_documento;
	}

	public void setBole_nm_documento(String bole_nm_documento) {
		this.bole_nm_documento = bole_nm_documento;
	}

	public String getBole_nr_codbarras() {
		return bole_nr_codbarras;
	}

	public void setBole_nr_codbarras(String bole_nr_codbarras) {
		this.bole_nr_codbarras = bole_nr_codbarras;
	}

	public String getBole_tx_conteudo() {
		return bole_tx_conteudo;
	}

	public void setBole_tx_conteudo(String bole_tx_conteudo) {
		this.bole_tx_conteudo = bole_tx_conteudo;
	}

	public Date getBole_dt_emprestimo() {
		return bole_dt_emprestimo;
	}

	public void setBole_dt_emprestimo(Date bole_dt_emprestimo) {
		this.bole_dt_emprestimo = bole_dt_emprestimo;
	}

	public Date getBole_dt_prev_devolucao() {
		return bole_dt_prev_devolucao;
	}

	public void setBole_dt_prev_devolucao(Date bole_dt_prev_devolucao) {
		this.bole_dt_prev_devolucao = bole_dt_prev_devolucao;
	}

	public String getBole_nm_usuariocadastro() {
		return bole_nm_usuariocadastro;
	}

	public void setBole_nm_usuariocadastro(String bole_nm_usuariocadastro) {
		this.bole_nm_usuariocadastro = bole_nm_usuariocadastro;
	}

	public String getBole_cd_matricula_usuariocadastro() {
		return bole_cd_matricula_usuariocadastro;
	}

	public void setBole_cd_matricula_usuariocadastro(String bole_cd_matricula_usuariocadastro) {
		this.bole_cd_matricula_usuariocadastro = bole_cd_matricula_usuariocadastro;
	}

	public String getBole_ds_telefone_usuariocadastro() {
		return bole_ds_telefone_usuariocadastro;
	}

	public void setBole_ds_telefone_usuariocadastro(String bole_ds_telefone_usuariocadastro) {
		this.bole_ds_telefone_usuariocadastro = bole_ds_telefone_usuariocadastro;
	}

	public String getBole_ds_email_usuariocadastro() {
		return bole_ds_email_usuariocadastro;
	}

	public void setBole_ds_email_usuariocadastro(String bole_ds_email_usuariocadastro) {
		this.bole_ds_email_usuariocadastro = bole_ds_email_usuariocadastro;
	}
	
	
	
	
	
}

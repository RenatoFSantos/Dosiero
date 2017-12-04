package _model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import _model.vo.Acervo;
import _model.vo.Classe;
import _model.vo.Cliente;
import _model.vo.Descritor;
import _model.vo.Documento;
import _model.vo.Unidade;

public class UnidadeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3189538663265306853L;
	private Integer id;
	private String unddto_sg_sigla;
	private String unddto_nm_unidade;
	private Date unddto_dt_finalvigencia;
	private String unddto_cd_classe;
	private String unddto_ds_classe;
	private String unddto_ds_assunto;
	private String unddto_in_documento;
	private String unddto_tx_fasecorrente;
	private Integer unddto_nr_fasecorrente;
	private String unddto_tx_faseintermediaria;
	private String unddto_tx_destinacaofinal;
	private String unddto_ds_localarquivo;
	private String unddto_tx_observacao;
	private String unddto_in_status;
	
	
	public UnidadeDTO() {
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public String getUnddto_sg_sigla() {
		return unddto_sg_sigla;
	}


	public void setUnddto_sg_sigla(String unddto_sg_sigla) {
		this.unddto_sg_sigla = unddto_sg_sigla;
	}


	public String getUnddto_nm_unidade() {
		return unddto_nm_unidade;
	}


	public void setUnddto_nm_unidade(String unddto_nm_unidade) {
		this.unddto_nm_unidade = unddto_nm_unidade;
	}


	public Date getUnddto_dt_finalvigencia() {
		return unddto_dt_finalvigencia;
	}


	public void setUnddto_dt_finalvigencia(Date unddto_dt_finalvigencia) {
		this.unddto_dt_finalvigencia = unddto_dt_finalvigencia;
	}


	public String getUnddto_cd_classe() {
		return unddto_cd_classe;
	}


	public void setUnddto_cd_classe(String unddto_cd_classe) {
		this.unddto_cd_classe = unddto_cd_classe;
	}


	public String getUnddto_ds_classe() {
		return unddto_ds_classe;
	}


	public void setUnddto_ds_classe(String unddto_ds_classe) {
		this.unddto_ds_classe = unddto_ds_classe;
	}


	public String getUnddto_ds_assunto() {
		return unddto_ds_assunto;
	}


	public void setUnddto_ds_assunto(String unddto_ds_assunto) {
		this.unddto_ds_assunto = unddto_ds_assunto;
	}


	public String getUnddto_in_documento() {
		return unddto_in_documento;
	}


	public void setUnddto_in_documento(String unddto_in_documento) {
		this.unddto_in_documento = unddto_in_documento;
	}


	public String getUnddto_tx_fasecorrente() {
		return unddto_tx_fasecorrente;
	}


	public void setUnddto_tx_fasecorrente(String unddto_tx_fasecorrente) {
		this.unddto_tx_fasecorrente = unddto_tx_fasecorrente;
	}


	public Integer getUnddto_nr_fasecorrente() {
		return unddto_nr_fasecorrente;
	}


	public void setUnddto_nr_fasecorrente(Integer unddto_nr_fasecorrente) {
		this.unddto_nr_fasecorrente = unddto_nr_fasecorrente;
	}


	public String getUnddto_tx_faseintermediaria() {
		return unddto_tx_faseintermediaria;
	}


	public void setUnddto_tx_faseintermediaria(String unddto_tx_faseintermediaria) {
		this.unddto_tx_faseintermediaria = unddto_tx_faseintermediaria;
	}


	public String getUnddto_tx_destinacaofinal() {
		return unddto_tx_destinacaofinal;
	}


	public void setUnddto_tx_destinacaofinal(String unddto_tx_destinacaofinal) {
		this.unddto_tx_destinacaofinal = unddto_tx_destinacaofinal;
	}


	public String getUnddto_ds_localarquivo() {
		return unddto_ds_localarquivo;
	}


	public void setUnddto_ds_localarquivo(String unddto_ds_localarquivo) {
		this.unddto_ds_localarquivo = unddto_ds_localarquivo;
	}


	public String getUnddto_tx_observacao() {
		return unddto_tx_observacao;
	}


	public void setUnddto_tx_observacao(String unddto_tx_observacao) {
		this.unddto_tx_observacao = unddto_tx_observacao;
	}


	public String getUnddto_in_status() {
		return unddto_in_status;
	}


	public void setUnddto_in_status(String unddto_in_status) {
		this.unddto_in_status = unddto_in_status;
	}
	
}

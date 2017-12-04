package _model.dto;

import javax.persistence.Entity;

public class ClasseDTO  {

	private String cdto_cd_pai;
	private String cdto_nm_pai;
	private Integer cdto_sq_id;
	private String cdto_cd_classe;
	private String cdto_ds_nome;
	private String cdto_tx_fasecorrente;
	private Integer cdto_nr_fasecorrente;
	private String cdto_tx_faseintermediaria;
	private String cdto_tx_destinacaofinal;
	private String cdto_tx_observacao;
	
	/*
	 * *******************************************************
	 * GETs e SETs
	 * *******************************************************
	 */
	
	public ClasseDTO() {
		cdto_cd_pai = "";
		cdto_nm_pai = "";
		cdto_sq_id = 0;
		cdto_cd_classe="";
		cdto_ds_nome = "";
		cdto_tx_fasecorrente="";
		cdto_nr_fasecorrente=0;
		cdto_tx_faseintermediaria="";
		cdto_tx_destinacaofinal="";
		cdto_tx_observacao="";
	}

	public String getCdto_cd_pai() {
		return cdto_cd_pai;
	}

	public void setCdto_cd_pai(String cdto_cd_pai) {
		this.cdto_cd_pai = cdto_cd_pai;
	}

	public String getCdto_nm_pai() {
		return cdto_nm_pai;
	}

	public void setCdto_nm_pai(String cdto_nm_pai) {
		this.cdto_nm_pai = cdto_nm_pai;
	}

	public Integer getCdto_sq_id() {
		return cdto_sq_id;
	}

	public void setCdto_sq_id(Integer cdto_sq_id) {
		this.cdto_sq_id = cdto_sq_id;
	}

	public String getCdto_cd_classe() {
		return cdto_cd_classe;
	}

	public void setCdto_cd_classe(String cdto_cd_classe) {
		this.cdto_cd_classe = cdto_cd_classe;
	}

	public String getCdto_ds_nome() {
		return cdto_ds_nome;
	}

	public void setCdto_ds_nome(String cdto_ds_nome) {
		this.cdto_ds_nome = cdto_ds_nome;
	}

	public String getCdto_tx_fasecorrente() {
		return cdto_tx_fasecorrente;
	}

	public void setCdto_tx_fasecorrente(String cdto_tx_fasecorrente) {
		this.cdto_tx_fasecorrente = cdto_tx_fasecorrente;
	}

	public Integer getCdto_nr_fasecorrente() {
		return cdto_nr_fasecorrente;
	}

	public void setCdto_nr_fasecorrente(Integer cdto_nr_fasecorrente) {
		this.cdto_nr_fasecorrente = cdto_nr_fasecorrente;
	}

	public String getCdto_tx_faseintermediaria() {
		return cdto_tx_faseintermediaria;
	}

	public void setCdto_tx_faseintermediaria(String cdto_tx_faseintermediaria) {
		this.cdto_tx_faseintermediaria = cdto_tx_faseintermediaria;
	}

	public String getCdto_tx_destinacaofinal() {
		return cdto_tx_destinacaofinal;
	}

	public void setCdto_tx_destinacaofinal(String cdto_tx_destinacaofinal) {
		this.cdto_tx_destinacaofinal = cdto_tx_destinacaofinal;
	}

	public String getCdto_tx_observacao() {
		return cdto_tx_observacao;
	}

	public void setCdto_tx_observacao(String cdto_tx_observacao) {
		this.cdto_tx_observacao = cdto_tx_observacao;
	}
	
}

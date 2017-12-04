package _model.dto;

import java.util.Date;

import _model.vo.Acervo;

public class DocumentoDTO {

	private Acervo objAcervo = new Acervo();
	private Integer id;
	private Long docu_cd_codbarras;
	private Date docu_dt_inclusao;
	private String docu_nm_documento;
	private String docu_tx_conteudo;
	private String docu_nm_arquivo;
	private String docu_tx_observacao;
	
	// GETs e SETs
	
	public Acervo getObjAcervo() {
		return objAcervo;
	}
	public void setObjAcervo(Acervo objAcervo) {
		this.objAcervo = objAcervo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getDocu_cd_codbarras() {
		return docu_cd_codbarras;
	}
	public void setDocu_cd_codbarras(Long docu_cd_codbarras) {
		this.docu_cd_codbarras = docu_cd_codbarras;
	}
	public Date getDocu_dt_inclusao() {
		return docu_dt_inclusao;
	}
	public void setDocu_dt_inclusao(Date docu_dt_inclusao) {
		this.docu_dt_inclusao = docu_dt_inclusao;
	}
	public String getDocu_nm_documento() {
		return docu_nm_documento;
	}
	public void setDocu_nm_documento(String docu_nm_documento) {
		this.docu_nm_documento = docu_nm_documento;
	}
	public String getDocu_tx_conteudo() {
		return docu_tx_conteudo;
	}
	public void setDocu_tx_conteudo(String docu_tx_conteudo) {
		this.docu_tx_conteudo = docu_tx_conteudo;
	}
	public String getDocu_nm_arquivo() {
		return docu_nm_arquivo;
	}
	public void setDocu_nm_arquivo(String docu_nm_arquivo) {
		this.docu_nm_arquivo = docu_nm_arquivo;
	}
	public String getDocu_tx_observacao() {
		return docu_tx_observacao;
	}
	public void setDocu_tx_observacao(String docu_tx_observacao) {
		this.docu_tx_observacao = docu_tx_observacao;
	}
}

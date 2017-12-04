package _model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import _model.vo.Classe;
import _model.vo.Cliente;
import _model.vo.Descritor;
import _model.vo.Documento;
import _model.vo.Unidade;

public class AcervoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Cliente objCliente = new Cliente();
	private Unidade objUnidade = new Unidade();
	private Classe objClasse = new Classe();
	private String acer_ds_assunto;
	private String acer_in_status;
	private Date acer_dt_referencia;
	private Date acer_dt_finalvigencia;
	private String acer_ds_localarquivo;
	private boolean acer_in_digitalizado;
	private boolean acer_in_automovimentacao;
	private Date acer_dt_prevmvt_fc_fi;
	private Date acer_dt_prevmvt_fi_df;
	private Date acer_dt_realmvt_fc_fi;
	private Date acer_dt_realmvt_fi_df;
	private String acer_tx_observacao;
	private String acer_ds_arquivodigital;
	private Date acer_dt_inclusao;
	private String acer_tx_hierarquia;
	private List<Descritor> descritors = new ArrayList<Descritor>();
	private List<Documento> documentos = new ArrayList<Documento>();
	private String acer_nr_codbarras;
	private boolean acer_in_documento;

	public AcervoDTO() {
		// TODO Auto-generated constructor stub
	}

	// --- GET's e SET's

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getObjCliente() {
		return objCliente;
	}

	public void setObjCliente(Cliente objCliente) {
		this.objCliente = objCliente;
	}

	public Unidade getObjUnidade() {
		return objUnidade;
	}

	public void setObjUnidade(Unidade objUnidade) {
		this.objUnidade = objUnidade;
	}

	public Classe getObjClasse() {
		return objClasse;
	}

	public void setObjClasse(Classe objClasse) {
		this.objClasse = objClasse;
	}

	public String getAcer_ds_assunto() {
		return acer_ds_assunto;
	}

	public void setAcer_ds_assunto(String acer_ds_assunto) {
		this.acer_ds_assunto = acer_ds_assunto;
	}

	public String getAcer_in_status() {
		return acer_in_status;
	}

	public void setAcer_in_status(String acer_in_status) {
		this.acer_in_status = acer_in_status;
	}

	public Date getAcer_dt_referencia() {
		return acer_dt_referencia;
	}

	public void setAcer_dt_referencia(Date acer_dt_referencia) {
		this.acer_dt_referencia = acer_dt_referencia;
	}

	public Date getAcer_dt_finalvigencia() {
		return acer_dt_finalvigencia;
	}

	public void setAcer_dt_finalvigencia(Date acer_dt_finalvigencia) {
		this.acer_dt_finalvigencia = acer_dt_finalvigencia;
	}

	public String getAcer_ds_localarquivo() {
		return acer_ds_localarquivo;
	}

	public void setAcer_ds_localarquivo(String acer_ds_localarquivo) {
		this.acer_ds_localarquivo = acer_ds_localarquivo;
	}

	public boolean isAcer_in_digitalizado() {
		return acer_in_digitalizado;
	}

	public void setAcer_in_digitalizado(boolean acer_in_digitalizado) {
		this.acer_in_digitalizado = acer_in_digitalizado;
	}

	public boolean isAcer_in_automovimentacao() {
		return acer_in_automovimentacao;
	}

	public void setAcer_in_automovimentacao(boolean acer_in_automovimentacao) {
		this.acer_in_automovimentacao = acer_in_automovimentacao;
	}

	public Date getAcer_dt_prevmvt_fc_fi() {
		return acer_dt_prevmvt_fc_fi;
	}

	public void setAcer_dt_prevmvt_fc_fi(Date acer_dt_prevmvt_fc_fi) {
		this.acer_dt_prevmvt_fc_fi = acer_dt_prevmvt_fc_fi;
	}

	public Date getAcer_dt_prevmvt_fi_df() {
		return acer_dt_prevmvt_fi_df;
	}

	public void setAcer_dt_prevmvt_fi_df(Date acer_dt_prevmvt_fi_df) {
		this.acer_dt_prevmvt_fi_df = acer_dt_prevmvt_fi_df;
	}

	public Date getAcer_dt_realmvt_fc_fi() {
		return acer_dt_realmvt_fc_fi;
	}

	public void setAcer_dt_realmvt_fc_fi(Date acer_dt_realmvt_fc_fi) {
		this.acer_dt_realmvt_fc_fi = acer_dt_realmvt_fc_fi;
	}

	public Date getAcer_dt_realmvt_fi_df() {
		return acer_dt_realmvt_fi_df;
	}

	public void setAcer_dt_realmvt_fi_df(Date acer_dt_realmvt_fi_df) {
		this.acer_dt_realmvt_fi_df = acer_dt_realmvt_fi_df;
	}

	public String getAcer_tx_observacao() {
		return acer_tx_observacao;
	}

	public void setAcer_tx_observacao(String acer_tx_observacao) {
		this.acer_tx_observacao = acer_tx_observacao;
	}

	public String getAcer_ds_arquivodigital() {
		return acer_ds_arquivodigital;
	}

	public void setAcer_ds_arquivodigital(String acer_ds_arquivodigital) {
		this.acer_ds_arquivodigital = acer_ds_arquivodigital;
	}

	public Date getAcer_dt_inclusao() {
		return acer_dt_inclusao;
	}

	public void setAcer_dt_inclusao(Date acer_dt_inclusao) {
		this.acer_dt_inclusao = acer_dt_inclusao;
	}

	public String getAcer_tx_hierarquia() {
		return acer_tx_hierarquia;
	}

	public void setAcer_tx_hierarquia(String acer_tx_hierarquia) {
		this.acer_tx_hierarquia = acer_tx_hierarquia;
	}

	public List<Descritor> getDescritors() {
		return descritors;
	}

	public void setDescritors(List<Descritor> descritors) {
		this.descritors = descritors;
	}
	
	public String getAcer_nr_codbarras() {
		return acer_nr_codbarras;
	}

	public void setAcer_nr_codbarras(String acer_nr_codbarras) {
		this.acer_nr_codbarras = acer_nr_codbarras;
	}

	public boolean isAcer_in_documento() {
		return acer_in_documento;
	}

	public void setAcer_in_documento(boolean acer_in_documento) {
		this.acer_in_documento = acer_in_documento;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcervoDTO other = (AcervoDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}

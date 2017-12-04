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
@Table(name = "acervo")
@SequenceGenerator(name = "sq_acervo", sequenceName = "sq_acervo", initialValue = 1, allocationSize = 1)
public class Acervo extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7503439133376189378L;
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
	private Integer acer_in_delecao = 0;
	private List<AcervoDescritor> acervoDescritors = new ArrayList<AcervoDescritor>();
	private List<Documento> documentos = new ArrayList<Documento>();
	private String acer_tx_hierarquia;
	private String acer_nr_codbarras;
	private Tipo objTipo = new Tipo();
	private List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_acervo")
	@Column(name = "acer_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@ManyToOne
	@JoinColumn(name="clie_sq_id")
	public Cliente getObjCliente() {
		return objCliente;
	}

	public void setObjCliente(Cliente objCliente) {
		this.objCliente = objCliente;
	}

	@ManyToOne
	@JoinColumn(name="unid_sq_id")
	public Unidade getObjUnidade() {
		return objUnidade;
	}

	public void setObjUnidade(Unidade objUnidade) {
		this.objUnidade = objUnidade;
	}

	@ManyToOne
	@JoinColumn(name="clas_sq_id")
	public Classe getObjClasse() {
		return objClasse;
	}

	public void setObjClasse(Classe objClasse) {
		this.objClasse = objClasse;
	}

	@Column(name="acer_ds_assunto")
	public String getAcer_ds_assunto() {
		return acer_ds_assunto;
	}

	public void setAcer_ds_assunto(String acer_ds_assunto) {
		this.acer_ds_assunto = acer_ds_assunto;
	}

	@Column(name="acer_in_status")
	public String getAcer_in_status() {
		return acer_in_status;
	}

	public void setAcer_in_status(String acer_in_status) {
		this.acer_in_status = acer_in_status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="acer_dt_referencia")
	public Date getAcer_dt_referencia() {
		return acer_dt_referencia;
	}

	public void setAcer_dt_referencia(Date acer_dt_referencia) {
		this.acer_dt_referencia = acer_dt_referencia;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="acer_dt_finalvigencia")
	public Date getAcer_dt_finalvigencia() {
		return acer_dt_finalvigencia;
	}

	public void setAcer_dt_finalvigencia(Date acer_dt_finalvigencia) {
		this.acer_dt_finalvigencia = acer_dt_finalvigencia;
	}

	@Column(name="acer_ds_localarquivo")
	public String getAcer_ds_localarquivo() {
		return acer_ds_localarquivo;
	}

	public void setAcer_ds_localarquivo(String acer_ds_localarquivo) {
		this.acer_ds_localarquivo = acer_ds_localarquivo;
	}

	@Column(name="acer_in_digitalizado")
	public boolean isAcer_in_digitalizado() {
		return acer_in_digitalizado;
	}

	public void setAcer_in_digitalizado(boolean acer_in_digitalizado) {
		this.acer_in_digitalizado = acer_in_digitalizado;
	}

	@Column(name="acer_in_automovimentacao")
	public boolean isAcer_in_automovimentacao() {
		return acer_in_automovimentacao;
	}

	public void setAcer_in_automovimentacao(boolean acer_in_automovimentacao) {
		this.acer_in_automovimentacao = acer_in_automovimentacao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="acer_dt_prevmvt_fc_fi")
	public Date getAcer_dt_prevmvt_fc_fi() {
		return acer_dt_prevmvt_fc_fi;
	}

	public void setAcer_dt_prevmvt_fc_fi(Date acer_dt_prevmvt_fc_fi) {
		this.acer_dt_prevmvt_fc_fi = acer_dt_prevmvt_fc_fi;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="acer_dt_prevmvt_fi_df")
	public Date getAcer_dt_prevmvt_fi_df() {
		return acer_dt_prevmvt_fi_df;
	}

	public void setAcer_dt_prevmvt_fi_df(Date acer_dt_prevmvt_fi_df) {
		this.acer_dt_prevmvt_fi_df = acer_dt_prevmvt_fi_df;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="acer_dt_realmvt_fc_fi")
	public Date getAcer_dt_realmvt_fc_fi() {
		return acer_dt_realmvt_fc_fi;
	}

	public void setAcer_dt_realmvt_fc_fi(Date acer_dt_realmvt_fc_fi) {
		this.acer_dt_realmvt_fc_fi = acer_dt_realmvt_fc_fi;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="acer_dt_realmvt_fi_df")
	public Date getAcer_dt_realmvt_fi_df() {
		return acer_dt_realmvt_fi_df;
	}

	public void setAcer_dt_realmvt_fi_df(Date acer_dt_realmvt_fi_df) {
		this.acer_dt_realmvt_fi_df = acer_dt_realmvt_fi_df;
	}

	@Column(name="acer_tx_observacao")
	public String getAcer_tx_observacao() {
		return acer_tx_observacao;
	}

	public void setAcer_tx_observacao(String acer_tx_observacao) {
		this.acer_tx_observacao = acer_tx_observacao;
	}

	@Column(name="acer_ds_arquivodigital")
	public String getAcer_ds_arquivodigital() {
		return acer_ds_arquivodigital;
	}

	public void setAcer_ds_arquivodigital(String acer_ds_arquivodigital) {
		this.acer_ds_arquivodigital = acer_ds_arquivodigital;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="acer_dt_inclusao")
	public Date getAcer_dt_inclusao() {
		return acer_dt_inclusao;
	}

	public void setAcer_dt_inclusao(Date acer_dt_inclusao) {
		this.acer_dt_inclusao = acer_dt_inclusao;
	}

	@Column(name="acer_in_delecao")
	public Integer getAcer_in_delecao() {
		return acer_in_delecao;
	}

	public void setAcer_in_delecao(Integer acer_in_delecao) {
		this.acer_in_delecao = acer_in_delecao;
	}

	@Column(name="acer_tx_hierarquia")
	public String getAcer_tx_hierarquia() {
		return acer_tx_hierarquia;
	}

	public void setAcer_tx_hierarquia(String acer_tx_hierarquia) {
		this.acer_tx_hierarquia = acer_tx_hierarquia;
	}
	
	@Column(name="acer_nr_codbarras")
	public String getAcer_nr_codbarras() {
		return acer_nr_codbarras;
	}

	public void setAcer_nr_codbarras(String acer_nr_codbarras) {
		this.acer_nr_codbarras = acer_nr_codbarras;
	}

	@OneToMany(mappedBy = "objAcervo", fetch=FetchType.LAZY)
	public List<AcervoDescritor> getAcervoDescritors() {
		return acervoDescritors;
	}

	public void setAcervoDescritors(List<AcervoDescritor> acervoDescritors) {
		this.acervoDescritors = acervoDescritors;
	}
	
	@OneToMany(mappedBy = "objAcervo", fetch=FetchType.LAZY)
	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}
	
	@ManyToOne
	@JoinColumn(name="tipo_sq_id")
	public Tipo getObjTipo() {
		return objTipo;
	}

	public void setObjTipo(Tipo objTipo) {
		this.objTipo = objTipo;
	}
	
	@OneToMany(mappedBy="objAcervo", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	public List<Ocorrencia> getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(List<Ocorrencia> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((acer_ds_assunto == null) ? 0 : acer_ds_assunto.hashCode());
		result = prime * result + ((acervoDescritors == null) ? 0 : acervoDescritors.hashCode());
		result = prime * result + ((documentos == null) ? 0 : documentos.hashCode());
		result = prime * result + ((objClasse == null) ? 0 : objClasse.hashCode());
		result = prime * result + ((objCliente == null) ? 0 : objCliente.hashCode());
		result = prime * result + ((objUnidade == null) ? 0 : objUnidade.hashCode());
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
		Acervo other = (Acervo) obj;
		if (acer_ds_assunto == null) {
			if (other.acer_ds_assunto != null)
				return false;
		} else if (!acer_ds_assunto.equals(other.acer_ds_assunto))
			return false;
		if (acervoDescritors == null) {
			if (other.acervoDescritors != null)
				return false;
		} else if (!acervoDescritors.equals(other.acervoDescritors))
			return false;
		if (documentos == null) {
			if (other.documentos != null)
				return false;
		} else if (!documentos.equals(other.documentos))
			return false;
		if (objClasse == null) {
			if (other.objClasse != null)
				return false;
		} else if (!objClasse.equals(other.objClasse))
			return false;
		if (objCliente == null) {
			if (other.objCliente != null)
				return false;
		} else if (!objCliente.equals(other.objCliente))
			return false;
		if (objUnidade == null) {
			if (other.objUnidade != null)
				return false;
		} else if (!objUnidade.equals(other.objUnidade))
			return false;
		return true;
	}


	
	
}

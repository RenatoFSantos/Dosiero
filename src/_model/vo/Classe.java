package _model.vo;

import java.util.ArrayList;
import java.util.Comparator;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "Classe")
@SequenceGenerator(name = "sq_classe", sequenceName = "sq_classe", initialValue = 1, allocationSize = 1)
public class Classe extends EntityBase implements Comparator<Classe> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8557420303377167401L;
	private String clas_cd_classe;
	private String clas_ds_nome;
	private String clas_tx_fasecorrente;
	private Integer clas_nr_fasecorrente;
	private String clas_tx_faseintermediaria;
	private String clas_tx_destinacaofinal;
	private String clas_tx_observacao;
	private Integer clas_in_delecao = 0;
	private Classe objClasse;
	private List<Classe> clas_filhas = new ArrayList<Classe>();
	private String clas_ds_fasecorrente;
	private List<Acervo> acervos = new ArrayList<Acervo>();

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_classe")
	@Column(name = "clas_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@Column(name="clas_cd_classe")
	public String getClas_cd_classe() {
		return clas_cd_classe;
	}

	public void setClas_cd_classe(String clas_cd_classe) {
		this.clas_cd_classe = clas_cd_classe;
	}

	@Column(name="clas_ds_nome")
	public String getClas_ds_nome() {
		return clas_ds_nome;
	}
	public void setClas_ds_nome(String clas_ds_nome) {
		this.clas_ds_nome = clas_ds_nome;
	}

	@Column(name="clas_tx_fasecorrente")
	public String getClas_tx_fasecorrente() {
		return clas_tx_fasecorrente;
	}

	public void setClas_tx_fasecorrente(String clas_tx_fasecorrente) {
		this.clas_tx_fasecorrente = clas_tx_fasecorrente;
	}

	@Column(name="clas_nr_fasecorrente")
	public Integer getClas_nr_fasecorrente() {
		return clas_nr_fasecorrente;
	}

	public void setClas_nr_fasecorrente(Integer clas_nr_fasecorrente) {
		this.clas_nr_fasecorrente = clas_nr_fasecorrente;
	}

	@Column(name="clas_tx_faseintermediaria")
	public String getClas_tx_faseintermediaria() {
		return clas_tx_faseintermediaria;
	}

	public void setClas_tx_faseintermediaria(String clas_tx_faseintermediaria) {
		this.clas_tx_faseintermediaria = clas_tx_faseintermediaria;
	}

	@Column(name="clas_tx_destinacaofinal")
	public String getClas_tx_destinacaofinal() {
		return clas_tx_destinacaofinal;
	}

	public void setClas_tx_destinacaofinal(String clas_tx_destinacaofinal) {
		this.clas_tx_destinacaofinal = clas_tx_destinacaofinal;
	}

	@Column(name="clas_tx_observacao")
	public String getClas_tx_observacao() {
		return clas_tx_observacao;
	}

	public void setClas_tx_observacao(String clas_tx_observacao) {
		this.clas_tx_observacao = clas_tx_observacao;
	}

	@Column(name="clas_in_delecao")
	public Integer getClas_in_delecao() {
		return clas_in_delecao;
	}

	public void setClas_in_delecao(Integer clas_in_delecao) {
		this.clas_in_delecao = clas_in_delecao;
	}

	@ManyToOne
	@JoinColumn(name="clas_cd_pai", nullable=true)
	public Classe getObjClasse() {
		return objClasse;
	}

	public void setObjClasse(Classe objClasse) {
		this.objClasse = objClasse;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
	@JoinColumn(name="clas_cd_pai", updatable=false)
	@Where(clause="clas_in_delecao=0") 
	public List<Classe> getClas_filhas() {
		return clas_filhas;
	}

	public void setClas_filhas(List<Classe> clas_filhas) {
		this.clas_filhas = clas_filhas;
	}

	@Transient
	public String getClas_ds_fasecorrente() {
		return clas_ds_fasecorrente;
	}

	public void setClas_ds_fasecorrente(String clas_ds_fasecorrente) {
		this.clas_ds_fasecorrente = clas_ds_fasecorrente;
	}
	
	@OneToMany(mappedBy="objClasse")
	@Where(clause="acer_in_delecao=0")
	public List<Acervo> getAcervos() {
		return acervos;
	}

	public void setAcervos(List<Acervo> acervos) {
		this.acervos = acervos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clas_cd_classe == null) ? 0 : clas_cd_classe.hashCode());
		result = prime * result + ((clas_ds_nome == null) ? 0 : clas_ds_nome.hashCode());
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
		Classe other = (Classe) obj;
		if (clas_cd_classe == null) {
			if (other.clas_cd_classe != null)
				return false;
		} else if (!clas_cd_classe.equals(other.clas_cd_classe))
			return false;
		if (clas_ds_nome == null) {
			if (other.clas_ds_nome != null)
				return false;
		} else if (!clas_ds_nome.equals(other.clas_ds_nome))
			return false;
		return true;
	}

    public int compare(Classe classe, Classe outraClasse) {
        return classe.getClas_cd_classe().
                compareTo(outraClasse.getClas_cd_classe());
    }


}

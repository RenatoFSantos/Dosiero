package _model.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "cliente")
@SequenceGenerator(name = "sq_cliente", sequenceName = "sq_cliente", initialValue = 1, allocationSize = 1)
public class Cliente extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 198773123224539333L;
	private String clie_nm_razaosocial;
	private String clie_nm_fantasia;
	private String clie_cd_cnpj;
	private String clie_cd_inscrestadual;
	private String clie_cd_inscrmunicipal;
	private String clie_ds_endereco;
	private String clie_ds_complemento;
	private String clie_ds_numero;
	private String clie_ds_bairro;
	private String clie_ds_cidade;
	private String clie_sg_uf;
	private String clie_ds_cep;
	private String clie_ds_telefone;
	private String clie_ds_email;
	private String clie_nm_contato;
	private String clie_tx_logo;
	private String clie_tx_observacao;
	private Integer clie_in_delecao = 0;
	private String clie_in_status;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<ClienteModulo> clienteModulos = new ArrayList<ClienteModulo>();
	private List<Acervo> acervos = new ArrayList<Acervo>();

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_cliente")
	@Column(name = "clie_sq_id")
	public Integer getId() {
		return super.getId();
	}


	@Column(name = "clie_nm_razaosocial")
	public String getClie_nm_razaosocial() {
		return clie_nm_razaosocial;
	}


	public void setClie_nm_razaosocial(String clie_nm_razaosocial) {
		this.clie_nm_razaosocial = clie_nm_razaosocial;
	}

	@Column(name = "clie_nm_fantasia")
	public String getClie_nm_fantasia() {
		return clie_nm_fantasia;
	}

	public void setClie_nm_fantasia(String clie_nm_fantasia) {
		this.clie_nm_fantasia = clie_nm_fantasia;
	}

	@Column(name = "clie_cd_cnpj")
	public String getClie_cd_cnpj() {
		return clie_cd_cnpj;
	}

	public void setClie_cd_cnpj(String clie_cd_cnpj) {
		this.clie_cd_cnpj = clie_cd_cnpj;
	}

	@Column(name = "clie_cd_inscrestadual")
	public String getClie_cd_inscrestadual() {
		return clie_cd_inscrestadual;
	}

	public void setClie_cd_inscrestadual(String clie_cd_inscrestadual) {
		this.clie_cd_inscrestadual = clie_cd_inscrestadual;
	}

	@Column(name = "clie_cd_inscrmunicipal")
	public String getClie_cd_inscrmunicipal() {
		return clie_cd_inscrmunicipal;
	}

	public void setClie_cd_inscrmunicipal(String clie_cd_inscrmunicipal) {
		this.clie_cd_inscrmunicipal = clie_cd_inscrmunicipal;
	}

	@Column(name = "clie_ds_endereco")
	public String getClie_ds_endereco() {
		return clie_ds_endereco;
	}

	public void setClie_ds_endereco(String clie_ds_endereco) {
		this.clie_ds_endereco = clie_ds_endereco;
	}

	@Column(name = "clie_ds_complemento")
	public String getClie_ds_complemento() {
		return clie_ds_complemento;
	}

	public void setClie_ds_complemento(String clie_ds_complemento) {
		this.clie_ds_complemento = clie_ds_complemento;
	}

	@Column(name = "clie_ds_numero")
	public String getClie_ds_numero() {
		return clie_ds_numero;
	}

	public void setClie_ds_numero(String clie_ds_numero) {
		this.clie_ds_numero = clie_ds_numero;
	}

	@Column(name = "clie_ds_bairro")
	public String getClie_ds_bairro() {
		return clie_ds_bairro;
	}

	public void setClie_ds_bairro(String clie_ds_bairro) {
		this.clie_ds_bairro = clie_ds_bairro;
	}

	@Column(name = "clie_ds_cidade")
	public String getClie_ds_cidade() {
		return clie_ds_cidade;
	}

	public void setClie_ds_cidade(String clie_ds_cidade) {
		this.clie_ds_cidade = clie_ds_cidade;
	}

	@Column(name = "clie_sg_uf")
	public String getClie_sg_uf() {
		return clie_sg_uf;
	}

	public void setClie_sg_uf(String clie_sg_uf) {
		this.clie_sg_uf = clie_sg_uf;
	}

	@Column(name = "clie_ds_cep")
	public String getClie_ds_cep() {
		return clie_ds_cep;
	}

	public void setClie_ds_cep(String clie_ds_cep) {
		this.clie_ds_cep = clie_ds_cep;
	}

	@Column(name = "clie_ds_telefone")
	public String getClie_ds_telefone() {
		return clie_ds_telefone;
	}

	public void setClie_ds_telefone(String clie_ds_telefone) {
		this.clie_ds_telefone = clie_ds_telefone;
	}

	@Column(name = "clie_ds_email")
	public String getClie_ds_email() {
		return clie_ds_email;
	}

	public void setClie_ds_email(String clie_ds_email) {
		this.clie_ds_email = clie_ds_email;
	}

	@Column(name = "clie_nm_contato")
	public String getClie_nm_contato() {
		return clie_nm_contato;
	}

	public void setClie_nm_contato(String clie_nm_contato) {
		this.clie_nm_contato = clie_nm_contato;
	}

	@Column(name = "clie_tx_logo")
	public String getClie_tx_logo() {
		return clie_tx_logo;
	}

	public void setClie_tx_logo(String clie_tx_logo) {
		this.clie_tx_logo = clie_tx_logo;
	}

	@Column(name = "clie_tx_observacao")
	public String getClie_tx_observacao() {
		return clie_tx_observacao;
	}

	public void setClie_tx_observacao(String clie_tx_observacao) {
		this.clie_tx_observacao = clie_tx_observacao;
	}
	
	@Column(name = "clie_in_delecao")
	public Integer getClie_in_delecao() {
		return clie_in_delecao;
	}

	public void setClie_in_delecao(Integer clie_in_delecao) {
		this.clie_in_delecao = clie_in_delecao;
	}

	@OneToMany(mappedBy="objCliente")
	@Where(clause="usua_in_delecao=0") 
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Transient
	public String getClie_in_status() {
		return clie_in_status;
	}

	public void setClie_in_status(String clie_in_status) {
		this.clie_in_status = clie_in_status;
	}

	@OneToMany(mappedBy="objCliente", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<ClienteModulo> getClienteModulos() {
		return clienteModulos;
	}

	public void setClienteModulos(List<ClienteModulo> clienteModulos) {
		this.clienteModulos = clienteModulos;
	}

	@OneToMany(mappedBy="objCliente")
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
		result = prime * result + ((clie_cd_cnpj == null) ? 0 : clie_cd_cnpj.hashCode());
		result = prime * result + ((clie_nm_fantasia == null) ? 0 : clie_nm_fantasia.hashCode());
		result = prime * result + ((clie_nm_razaosocial == null) ? 0 : clie_nm_razaosocial.hashCode());
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
		Cliente other = (Cliente) obj;
		if (clie_cd_cnpj == null) {
			if (other.clie_cd_cnpj != null)
				return false;
		} else if (!clie_cd_cnpj.equals(other.clie_cd_cnpj))
			return false;
		if (clie_nm_fantasia == null) {
			if (other.clie_nm_fantasia != null)
				return false;
		} else if (!clie_nm_fantasia.equals(other.clie_nm_fantasia))
			return false;
		if (clie_nm_razaosocial == null) {
			if (other.clie_nm_razaosocial != null)
				return false;
		} else if (!clie_nm_razaosocial.equals(other.clie_nm_razaosocial))
			return false;
		return true;
	}




	
}

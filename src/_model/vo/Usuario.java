package _model.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import _model.domain.SituacaoUsuario;

@Entity
@Table(name = "Usuario")
@SequenceGenerator(name = "sq_usuario", sequenceName = "sq_usuario", initialValue = 1, allocationSize = 1)
public class Usuario extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6304370530008490175L;
	private String usua_cd_matricula;	
	private SituacaoUsuario usua_in_situacao;
	private String usua_nm_usuario;
	private String usua_tx_login;
	private String usua_tx_senha;
	private String usua_ds_cargo;
	private String usua_ds_setor;
	private String usua_ds_sexo;
	private Date usua_dt_suspensao;
	private Date usua_dt_fimsuspensao;
	private Date usua_dt_inclusao;
	private Date usua_dt_exclusao;
	private String usua_ds_telefone;
	private String usua_ds_email;
	private String usua_ds_correspondencia;
	private String usua_tx_observacao;
	private String usua_tx_emprestimo;
	private Integer usua_in_delecao = 0;
	private Cliente objCliente = new Cliente();
	private Unidade objUnidade = new Unidade();
	private Perfil objPerfil = new Perfil();
	private Categoria objCategoria = new Categoria();
	private String usua_tx_foto;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_usuario")
	@Column(name = "usua_sq_id")
	public Integer getId() {
		return super.getId();
	}	


	@Column(name = "usua_cd_matricula")
	public String getUsua_cd_matricula() {
		return usua_cd_matricula;
	}

	public void setUsua_cd_matricula(String usua_cd_matricula) {
		this.usua_cd_matricula = usua_cd_matricula;
	}

	@Enumerated(EnumType.STRING)	
	@Column(name = "usua_in_situacao")
	public SituacaoUsuario getUsua_in_situacao() {
		return usua_in_situacao;
	}

	public void setUsua_in_situacao(SituacaoUsuario usua_in_situacao) {
		this.usua_in_situacao = usua_in_situacao;
	}

	@Column(name = "usua_nm_usuario")
	public String getUsua_nm_usuario() {
		return usua_nm_usuario;
	}

	public void setUsua_nm_usuario(String usua_nm_usuario) {
		this.usua_nm_usuario = usua_nm_usuario;
	}

	@Column(name = "usua_tx_login")
	public String getUsua_tx_login() {
		return usua_tx_login;
	}

	public void setUsua_tx_login(String usua_tx_login) {
		this.usua_tx_login = usua_tx_login;
	}

	@Column(name = "usua_tx_senha")
	public String getUsua_tx_senha() {
		return usua_tx_senha;
	}

	public void setUsua_tx_senha(String usua_tx_senha) {
		this.usua_tx_senha = usua_tx_senha;
	}
	
	@Column(name = "usua_ds_cargo")
	public String getUsua_ds_cargo() {
		return usua_ds_cargo;
	}

	public void setUsua_ds_cargo(String usua_ds_cargo) {
		this.usua_ds_cargo = usua_ds_cargo;
	}

	@Column(name = "usua_ds_setor")
	public String getUsua_ds_setor() {
		return usua_ds_setor;
	}

	public void setUsua_ds_setor(String usua_ds_setor) {
		this.usua_ds_setor = usua_ds_setor;
	}

	@Column(name = "usua_ds_sexo")
	public String getUsua_ds_sexo() {
		return usua_ds_sexo;
	}

	public void setUsua_ds_sexo(String usua_ds_sexo) {
		this.usua_ds_sexo = usua_ds_sexo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "usua_dt_suspensao")
	public Date getUsua_dt_suspensao() {
		return usua_dt_suspensao;
	}

	public void setUsua_dt_suspensao(Date usua_dt_suspensao) {
		this.usua_dt_suspensao = usua_dt_suspensao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "usua_dt_fimsuspensao")
	public Date getUsua_dt_fimsuspensao() {
		return usua_dt_fimsuspensao;
	}

	public void setUsua_dt_fimsuspensao(Date usua_dt_fimsuspensao) {
		this.usua_dt_fimsuspensao = usua_dt_fimsuspensao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "usua_dt_inclusao")
	public Date getUsua_dt_inclusao() {
		return usua_dt_inclusao;
	}

	public void setUsua_dt_inclusao(Date usua_dt_inclusao) {
		this.usua_dt_inclusao = usua_dt_inclusao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "usua_dt_exclusao")
	public Date getUsua_dt_exclusao() {
		return usua_dt_exclusao;
	}

	public void setUsua_dt_exclusao(Date usua_dt_exclusao) {
		this.usua_dt_exclusao = usua_dt_exclusao;
	}

	@Column(name = "usua_ds_telefone")
	public String getUsua_ds_telefone() {
		return usua_ds_telefone;
	}

	public void setUsua_ds_telefone(String usua_ds_telefone) {
		this.usua_ds_telefone = usua_ds_telefone;
	}

	@Column(name = "usua_ds_email")
	public String getUsua_ds_email() {
		return usua_ds_email;
	}

	public void setUsua_ds_email(String usua_ds_email) {
		this.usua_ds_email = usua_ds_email;
	}

	@Column(name = "usua_ds_correspondencia")
	public String getUsua_ds_correspondencia() {
		return usua_ds_correspondencia;
	}

	public void setUsua_ds_correspondencia(String usua_ds_correspondencia) {
		this.usua_ds_correspondencia = usua_ds_correspondencia;
	}

	@Column(name = "usua_tx_observacao")
	public String getUsua_tx_observacao() {
		return usua_tx_observacao;
	}

	public void setUsua_tx_observacao(String usua_tx_observacao) {
		this.usua_tx_observacao = usua_tx_observacao;
	}

	@ManyToOne
	@JoinColumn(name="clie_sq_id", nullable=false)
	public Cliente getObjCliente() {
		return objCliente;
	}

	public void setObjCliente(Cliente objCliente) {
		this.objCliente = objCliente;
	}

	@ManyToOne
	@JoinColumn(name="unid_sq_id", nullable=false)
	public Unidade getObjUnidade() {
		return objUnidade;
	}

	public void setObjUnidade(Unidade objUnidade) {
		this.objUnidade = objUnidade;
	}
	
	@Column(name = "usua_in_delecao")
	public Integer getUsua_in_delecao() {
		return usua_in_delecao;
	}

	public void setUsua_in_delecao(Integer usua_in_delecao) {
		this.usua_in_delecao = usua_in_delecao;
	}

	@ManyToOne
	@JoinColumn(name="perf_sq_id", nullable=false)
	public Perfil getObjPerfil() {
		return objPerfil;
	}

	public void setObjPerfil(Perfil objPerfil) {
		this.objPerfil = objPerfil;
	}

	@ManyToOne
	@JoinColumn(name="cate_sq_id", nullable=false)
	public Categoria getObjCategoria() {
		return objCategoria;
	}

	public void setObjCategoria(Categoria objCategoria) {
		this.objCategoria = objCategoria;
	}
	
	@Column(name="usua_tx_emprestimo")
	public String getUsua_tx_emprestimo() {
		return usua_tx_emprestimo;
	}

	public void setUsua_tx_emprestimo(String usua_tx_emprestimo) {
		this.usua_tx_emprestimo = usua_tx_emprestimo;
	}

	@Column(name="usua_tx_foto", nullable=true)
	public String getUsua_tx_foto() {
		return usua_tx_foto;
	}

	public void setUsua_tx_foto(String usua_tx_foto) {
		this.usua_tx_foto = usua_tx_foto;
	}

	
}

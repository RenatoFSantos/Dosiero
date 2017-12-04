package _model.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "categoria_tipo")
@SequenceGenerator(name = "sq_categoria_tipo", sequenceName = "sq_categoria_tipo", initialValue = 1, allocationSize = 1)
public class CategoriaTipo extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Categoria objCategoria = new Categoria();
	private Tipo objTipo = new Tipo();
	private Integer cati_nr_prazo_dev_emprestimo;
	private Integer cati_nr_prazo_dev_consulta;
	private Double cati_vl_multa_emprestimo;
	private Double cati_vl_multa_consulta;
	private Integer cati_nr_qtdmax_emprestimo;
	private Integer cati_nr_qtdmax_consulta;
	private Integer cati_nr_qtdmax_renovacao;
	private Integer cati_nr_sancao_emprestimo;
	private Integer cati_nr_sancao_consulta;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_categoria_tipo")
	@Column(name = "cati_sq_id")
	public Integer getId() {
		return super.getId();
	}


	@ManyToOne
	@JoinColumn(name="cate_sq_id", nullable=false)
	public Categoria getObjCategoria() {
		return objCategoria;
	}

	public void setObjCategoria(Categoria objCategoria) {
		this.objCategoria = objCategoria;
	}

	@ManyToOne
	@JoinColumn(name="tipo_sq_id", nullable=false)
	public Tipo getObjTipo() {
		return objTipo;
	}

	public void setObjTipo(Tipo objTipo) {
		this.objTipo = objTipo;
	}

	@Column(name="cati_nr_prazo_dev_emprestimo")
	public Integer getCati_nr_prazo_dev_emprestimo() {
		return cati_nr_prazo_dev_emprestimo;
	}

	public void setCati_nr_prazo_dev_emprestimo(Integer cati_nr_prazo_dev_emprestimo) {
		this.cati_nr_prazo_dev_emprestimo = cati_nr_prazo_dev_emprestimo;
	}

	@Column(name="cati_nr_prazo_dev_consulta")
	public Integer getCati_nr_prazo_dev_consulta() {
		return cati_nr_prazo_dev_consulta;
	}

	public void setCati_nr_prazo_dev_consulta(Integer cati_nr_prazo_dev_consulta) {
		this.cati_nr_prazo_dev_consulta = cati_nr_prazo_dev_consulta;
	}

	@Column(name="cati_vl_multa_emprestimo")
	public Double getCati_vl_multa_emprestimo() {
		return cati_vl_multa_emprestimo;
	}

	public void setCati_vl_multa_emprestimo(Double cati_vl_multa_emprestimo) {
		this.cati_vl_multa_emprestimo = cati_vl_multa_emprestimo;
	}

	@Column(name="cati_vl_multa_consulta")
	public Double getCati_vl_multa_consulta() {
		return cati_vl_multa_consulta;
	}

	public void setCati_vl_multa_consulta(Double cati_vl_multa_consulta) {
		this.cati_vl_multa_consulta = cati_vl_multa_consulta;
	}

	@Column(name="cati_nr_qtdmax_emprestimo")
	public Integer getCati_nr_qtdmax_emprestimo() {
		return cati_nr_qtdmax_emprestimo;
	}

	public void setCati_nr_qtdmax_emprestimo(Integer cati_nr_qtdmax_emprestimo) {
		this.cati_nr_qtdmax_emprestimo = cati_nr_qtdmax_emprestimo;
	}

	@Column(name="cati_nr_qtdmax_consulta")
	public Integer getCati_nr_qtdmax_consulta() {
		return cati_nr_qtdmax_consulta;
	}

	public void setCati_nr_qtdmax_consulta(Integer cati_nr_qtdmax_consulta) {
		this.cati_nr_qtdmax_consulta = cati_nr_qtdmax_consulta;
	}

	@Column(name="cati_nr_qtdmax_renovacao")
	public Integer getCati_nr_qtdmax_renovacao() {
		return cati_nr_qtdmax_renovacao;
	}

	public void setCati_nr_qtdmax_renovacao(Integer cati_nr_qtdmax_renovacao) {
		this.cati_nr_qtdmax_renovacao = cati_nr_qtdmax_renovacao;
	}

	@Column(name="cati_nr_sancao_emprestimo")
	public Integer getCati_nr_sancao_emprestimo() {
		return cati_nr_sancao_emprestimo;
	}

	public void setCati_nr_sancao_emprestimo(Integer cati_nr_sancao_emprestimo) {
		this.cati_nr_sancao_emprestimo = cati_nr_sancao_emprestimo;
	}

	@Column(name="cati_nr_sancao_consulta")
	public Integer getCati_nr_sancao_consulta() {
		return cati_nr_sancao_consulta;
	}

	public void setCati_nr_sancao_consulta(Integer cati_nr_sancao_consulta) {
		this.cati_nr_sancao_consulta = cati_nr_sancao_consulta;
	}
	
	
	
}

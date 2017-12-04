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
@Table(name = "Modulo")
@SequenceGenerator(name = "sq_modulo", sequenceName = "sq_modulo", initialValue = 1, allocationSize = 1)
public class Modulo extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1718092089971504776L;
	private String modu_nm_modulo;
	private Integer modu_in_delecao = 0;
	private List<Pagina> paginas = new ArrayList<Pagina>();
	private boolean marcado = false;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_modulo")
	@Column(name = "modu_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@Column(name="modu_nm_modulo")
	public String getModu_nm_modulo() {
		return modu_nm_modulo;
	}


	public void setModu_nm_modulo(String modu_nm_modulo) {
		this.modu_nm_modulo = modu_nm_modulo;
	}

	@Column(name="modu_in_delecao")
	public Integer getModu_in_delecao() {
		return modu_in_delecao;
	}


	public void setModu_in_delecao(Integer modu_in_delecao) {
		this.modu_in_delecao = modu_in_delecao;
	}
	
	@OneToMany(mappedBy="objModulo", fetch=FetchType.EAGER)
	@Where(clause="pagi_in_delecao=0") 
	public List<Pagina> getPaginas() {
		return paginas;
	}

	public void setPaginas(List<Pagina> paginas) {
		this.paginas = paginas;
	}

	@Transient
	public boolean isMarcado() {
		return marcado;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

}

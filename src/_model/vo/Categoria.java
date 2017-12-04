package _model.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Categoria")
@SequenceGenerator(name = "sq_categoria", sequenceName = "sq_categoria", initialValue = 1, allocationSize = 1)
public class Categoria extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cate_nm_categoria;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<CategoriaTipo> categoriaTipos = new ArrayList<CategoriaTipo>();
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_categoria")
	@Column(name = "cate_sq_id")
	public Integer getId() {
		return super.getId();
	}
		
	@Column(name="cate_nm_categoria")
	public String getCate_nm_categoria() {
		return cate_nm_categoria;
	}

	public void setCate_nm_categoria(String cate_nm_categoria) {
		this.cate_nm_categoria = cate_nm_categoria;
	}

	@OneToMany(mappedBy="objCategoria", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@OneToMany(mappedBy="objCategoria", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	public List<CategoriaTipo> getCategoriaTipos() {
		return categoriaTipos;
	}

	public void setCategoriaTipos(List<CategoriaTipo> categoriaTipos) {
		this.categoriaTipos = categoriaTipos;
	}
	
	
	
}

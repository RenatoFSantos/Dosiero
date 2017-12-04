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
@Table(name = "acervo_descritor")
@SequenceGenerator(name = "sq_acervo_descritor", sequenceName = "sq_acervo_descritor", initialValue = 1, allocationSize = 1)
public class AcervoDescritor extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7894848269702279626L;
	private Acervo objAcervo = new Acervo();
	private Descritor objDescritor = new Descritor();
		
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_acervo_descritor")
	@Column(name = "acde_sq_id")
	public Integer getId() {
		return super.getId();
	}

	@ManyToOne
	@JoinColumn(name="acer_sq_id", nullable=false)
	public Acervo getObjAcervo() {
		return objAcervo;
	}

	public void setObjAcervo(Acervo objAcervo) {
		this.objAcervo = objAcervo;
	}

	@ManyToOne
	@JoinColumn(name="desc_sq_id", nullable=false)
	public Descritor getObjDescritor() {
		return objDescritor;
	}

	public void setObjDescritor(Descritor objDescritor) {
		this.objDescritor = objDescritor;
	}

	
}

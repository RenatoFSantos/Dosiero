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
@Table(name = "cliente_modulo")
@SequenceGenerator(name = "sq_cliente_modulo", sequenceName = "sq_cliente_modulo", initialValue = 1, allocationSize = 1)
public class ClienteModulo extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4906326823151115114L;
	private Cliente objCliente = new Cliente();
	private Modulo objModulo = new Modulo();
		
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_cliente_modulo")
	@Column(name = "clmo_sq_id")
	public Integer getId() {
		return super.getId();
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
	@JoinColumn(name="modu_sq_id", nullable=false)
	public Modulo getObjModulo() {
		return objModulo;
	}

	public void setObjModulo(Modulo objModulo) {
		this.objModulo = objModulo;
	}

	
}

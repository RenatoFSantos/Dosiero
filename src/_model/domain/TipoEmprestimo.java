package _model.domain;

public enum TipoEmprestimo {
	D("Empréstimo"),
	C("Consulta"),
	P("Permanente"), 
	E("Especial");
	
	private String nome;
	
	TipoEmprestimo(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
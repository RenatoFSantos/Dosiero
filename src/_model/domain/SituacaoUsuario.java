package _model.domain;

public enum SituacaoUsuario {
	NR("Normal"), 
	SP("Suspenso"), 
	EX("Excluído");
	
	private String nome;
	
	SituacaoUsuario(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
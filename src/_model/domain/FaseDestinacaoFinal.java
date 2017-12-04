package _model.domain;

public enum FaseDestinacaoFinal {
	STP("Sem temporalidade"),
	GDP("Guarda Permanente"),
	ELM("Eliminação");
	
	
	private String nome;
	
	FaseDestinacaoFinal(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
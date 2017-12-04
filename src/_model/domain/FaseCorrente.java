package _model.domain;

public enum FaseCorrente {
	STP("Sem temporalidade"),
	ANO("Ano"),
	EVG("Enquanto Vigora"), 
	HMA("Até Homologação do Ato"), 
	HME("Até Homologação do Evento"),
	CEV("Até a Conclusão do Evento"),
	MVI("Enquanto o Aluno Mantiver o Vínculo com a IE"),
	CCA("Até a Conclusão do Caso"),
	DRN("Devolução ao Aluno após o Registro de Notas"),
	CCU("Até a Conclusão do Curso"),
	RNT("Até o Registro de Notas"),
	CCT("Até a Celebração do Contrato");
	
	
	private String nome;
	
	FaseCorrente(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
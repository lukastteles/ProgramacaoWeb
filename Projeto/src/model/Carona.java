package model;

public class Carona {
	
	private String origem;
	private String destino;
	private String hora;
	private String data;
	private int vagas;
	
	
	public Carona(String origem, String destino, String hora, String data, int vagas) {
		this.origem = origem;
		this.destino = destino;
		this.hora = hora;
		this.data = data;
		this.vagas = vagas;
	}
	
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getVagas() {
		return vagas;
	}
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}
	
	
	

}
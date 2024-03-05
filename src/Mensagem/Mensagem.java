package Mensagem;

import java.io.Serializable;

public class Mensagem implements Serializable {
	public int ttl =0;
	private String remetente;
	private String destino;
	private String conteudo;
	private static final long serialVersionUID = 1L;
	public Mensagem() {}
	public String getRemetente() {
		return this.remetente;
	}
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	public String getDestino() {
		return this.destino;
	}
	public void setDestino(String destino) throws Exception {
		if(destino.equalsIgnoreCase("p2")) {
			this.destino = "/127.0.0.2";
		}
		else if(destino.equalsIgnoreCase("p3")) {
			this.destino = "/127.0.0.3";
		}
		else if(destino.equalsIgnoreCase("p4")) {
			this.destino = "/127.0.0.4";
		}
		else if (destino.equalsIgnoreCase("broadcast")){
			this.destino = "1";
		}
		else {
			throw new Exception("destino invalido");
		}
	}
	public String getConteudo() {
		return this.conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	
}

package Servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Mensagem.Mensagem;

public class ConnServidor implements Runnable {
	public Socket socketCliente;
	public static int cont =0;
	private boolean conexao =true;
	private ObjectOutputStream saida;
	private ObjectInputStream entrada;
	private Mensagem mensagem;
	
	public ObjectOutputStream getSaida() {
		return saida;
	}

	public void setSaida(ObjectOutputStream saida) {
		this.saida = saida;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}
	
	public ConnServidor(Socket cliente){
		this.socketCliente = cliente;
	}
	
	public void run() {
		System.out.println("conexao com o cliente:" + socketCliente.getLocalAddress());
		
		try {
			setEntrada(new ObjectInputStream(socketCliente.getInputStream()));
			setSaida(new ObjectOutputStream(socketCliente.getOutputStream()));
			while(conexao) {
				setMensagem((Mensagem) getEntrada().readObject());
				Servidor.mensagensParaRepassar.add(mensagem);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}
	
	public void mandarMensagem(Mensagem mensagem) {
		try {
			getSaida().writeObject(mensagem);
			getSaida().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

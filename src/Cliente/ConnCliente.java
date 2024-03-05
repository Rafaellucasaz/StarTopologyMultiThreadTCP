package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.Socket;

import Mensagem.Mensagem;

public class ConnCliente implements Runnable{
	private ObjectInputStream entrada;
	private Socket socket;
	public boolean conexao = true;
	private Mensagem mensagem;
	
	
	public Mensagem getMensagem() {
		return mensagem;
	}
	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}
	public ObjectInputStream getEntrada() {
		return entrada;
	}
	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public ConnCliente(Socket cliente) {
		setSocket(cliente);
	}
	@Override
	public void run() {
		try {
			setEntrada(new ObjectInputStream(getSocket().getInputStream()));
			while(conexao) {
				try {
					setMensagem((Mensagem) getEntrada().readObject());
					System.out.println("mensagem Recebida : " +getMensagem().getConteudo());
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
}

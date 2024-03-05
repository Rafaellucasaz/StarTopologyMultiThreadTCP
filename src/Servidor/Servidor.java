package Servidor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Mensagem.Mensagem;



public class Servidor {
	private ServerSocket socketServidor;
	private Socket cliente;
	private int port;
	private List<ConnServidor> listaClientes = new ArrayList<ConnServidor>();
	public static List<Mensagem> mensagensParaRepassar = new ArrayList<Mensagem>();
	public Scanner scanner = null;
	
	
	public ServerSocket getSocketServidor() {
		return socketServidor;
	}

	public void setSocketServidor(ServerSocket socketServidor) {
		this.socketServidor = socketServidor;
	}

	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public Servidor(int port) {
		setPort(port);
		this.run();
	}
	
	public void run() {
		try {
			setSocketServidor(new ServerSocket(getPort()));
			System.out.println("Servidor estabelecido na porta : " + getSocketServidor().getLocalPort());
			System.out.println("HostAdress:" + InetAddress.getLocalHost().getHostAddress());
			System.out.println("HostName: " + InetAddress.getLocalHost().getHostName());
			System.out.println("aguardando clientes...");
			while(ConnServidor.cont < 3) {
				setCliente(getSocketServidor().accept());
				ConnServidor conexao = new ConnServidor(getCliente());
				listaClientes.add(conexao);
				Thread connThread = new Thread(conexao);
				ConnServidor.cont++;
				connThread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("todos os clientes conectados");
		while(true) {
			try {
				Thread.sleep(5000);
				RepassarMensagens();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void RepassarMensagens() {
		if(mensagensParaRepassar.size()>0) {
			for(int i = 0; i < mensagensParaRepassar.size();i++) {
				System.out.println("repassando mensagem");
				if(mensagensParaRepassar.get(i).getDestino().equals("1")) {
					for(int j =0; j <listaClientes.size();j++) {
						listaClientes.get(j).mandarMensagem(mensagensParaRepassar.get(i));
					}
					mensagensParaRepassar.remove(i);
				}
				else {
					for(int j = 0; j < listaClientes.size();j++) {
						
						if(mensagensParaRepassar.get(i).getDestino().equalsIgnoreCase(listaClientes.get(j).socketCliente.getLocalAddress().toString())) {
							listaClientes.get(j).mandarMensagem(mensagensParaRepassar.get(i));
							mensagensParaRepassar.remove(i);
							break;
						}
					}
				}
				
			}
		}
	}
}

package Cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import Mensagem.Mensagem;



public class Cliente {
	private Socket socket;
	private InetAddress inet;
	private String ip;
	private int porta;
	private ObjectOutputStream saida;
	public Scanner scanner = new Scanner(System.in);
	public int aux;
	
	public Socket getSocket() {
		return this.socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public InetAddress getInet() {
		return this.inet;
	}
	public void setInet(InetAddress inet) {
		this.inet = inet;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPorta() {
		return porta;
	}
	public void setPorta(int porta) {
		this.porta = porta;
	}
	public ObjectOutputStream getSaida() {
		return saida;
	}
	public void setSaida(ObjectOutputStream saida) {
		this.saida = saida;
	}
	
	
	public Cliente (String ip, int porta) {
		setIp(ip);
		setPorta(porta);
		this.run();
	}
	public void run() {
		try {
			setSocket( new Socket(ip,porta));
			
			setInet(socket.getInetAddress());
			
			System.out.println("Conexao feita com a porta" + getPorta());
			System.out.println(getSocket().getInetAddress().getHostAddress());
			setSaida(new ObjectOutputStream(socket.getOutputStream()));
			
			
			
			
			ConnCliente cliente = new ConnCliente(getSocket());
			
			Thread connCliente = new Thread(cliente);
			connCliente.start();
			
			while(aux!=2) {
				
				System.out.println("1-Enviar mensagem");
				System.out.println("2-sair");
				switch(scanner.nextInt()) {
				case 1:
					scanner.nextLine();
					try {
						mandarMensagem();
						break;
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				case 2:
					System.out.println("encerrando conexao");
					cliente.conexao=false;
				}
			}
			scanner.close();
			getSocket().close();
			getSaida().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void mandarMensagem() throws Exception{
		Mensagem mensagem = new Mensagem();
		System.out.println("para quem enviar a mensagem?(p2/p3/p4/broadcast)");
		mensagem.setDestino(scanner.nextLine());
		mensagem.setRemetente(getIp());
		System.out.println("digite a mensagem:");
		mensagem.setConteudo(scanner.nextLine());
		
		try {
			getSaida().writeObject(mensagem);
			getSaida().flush();
		} catch (IOException e) {
			System.out.println("nao foi possivel enviar a mensagem");
			e.printStackTrace();
		}
		
	}
}

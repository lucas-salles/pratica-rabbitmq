package br.edu.ifpb.pdist.pratica_rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Classe responsável por enviar itens à fila
 */
public class Produtor {
	public static void main(String[] args) throws Exception {
		// Criação de uma factory de conexão, responsável por criar as conexões
		ConnectionFactory connectionFactory = new ConnectionFactory();

		// Localização do gestor da fila (Queue Manager)
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("mqadmin");
		connectionFactory.setPassword("secret");

		String NOME_FILA = "filaOla";

		try (
				// Criação de uma conexão
				Connection connection = connectionFactory.newConnection();
				// Criando um canal na conexão
				Channel channel = connection.createChannel()) {
			// Esse corpo especifica o envio da mensagem para a fila

			// Declaração da fila. Se não existir ainda no queue manager, será criada. Se já
			// exitir, e foi criada com os mesmos parâmetros, pega a referência da fila. Se
			// foi criada com parâmetros diferentes, lança exceção
			channel.queueDeclare(NOME_FILA, false, false, false, null);
			String mensagem = "Olá mundo";
			// publica uma mensagem na fila
			channel.basicPublish("", NOME_FILA, null, mensagem.getBytes());
			System.out.println("Enviei mensagem: " + mensagem);
		}
	}
}

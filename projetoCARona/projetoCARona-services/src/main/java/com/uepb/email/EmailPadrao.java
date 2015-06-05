package com.uepb.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.br.uepb.constants.MensagensErro;

/**
 * Classe para Email Padrao
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 04/05/2015
 */

public class EmailPadrao {
	final static Logger logger = Logger.getLogger(EmailPadrao.class);
	
	/**
	 * Médoto para enviar email para um usuario sobre algum evento
	 * @param assunto Titulo do email
	 * @param textoEmail Mensagem do email
	 * @param destinatarios emails de destino
	 * @return Retorna true se o email foi enviado com sucesso
	 */
	public Boolean enviarEmail(String assunto, String textoEmail, String destinatarios){
		final String remetente = "contato.projetocarona@gmail.com";
		final String senha = "pr0j3t0c4r0n4";
		final String destinatario = "luannajnsousa@gmail.com, lukasteleslima@gmail.com";
		
		Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication()
            {
        		return new PasswordAuthentication(remetente, senha);
            }
        });
        

	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(remetente)); //Remetente
	
	        Address[] toUser = InternetAddress.parse(destinatario);  
	
	        message.setRecipients(Message.RecipientType.TO, toUser);
	        message.setSubject(assunto);//Assunto
	        message.setText(textoEmail);
	        
	        /**Método para enviar a mensagem criada*/
	        Transport.send(message);
	        
	        return true;	        
	        
	    } catch (MessagingException e) {
	    	logger.debug("enviarEmail() Exceção: "+e.getMessage()+" \n"+MensagensErro.EMAIL_NAO_ENVIADO);
	        throw new RuntimeException(MensagensErro.EMAIL_NAO_ENVIADO);
	    }
	}	
}

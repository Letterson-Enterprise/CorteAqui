package br.com.corteAqui.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnviadorEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
     private String remetente;

    public void enviarEmail(String emailDestinatario, String assunto, String textoEmail) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(remetente);
            mensagem.setTo(emailDestinatario);
            mensagem.setSubject(assunto);
            mensagem.setText(textoEmail);

            javaMailSender.send(mensagem);

            System.out.println("Email enviado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao enviar o email: " + e.getMessage());
        }
    }
}

package br.com.projeto.modelo;

import org.springframework.stereotype.Component;

@Component
public class Mensagem {
    
    // ***** Atributo
    private String texto;

    // ***** GET e SET
    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}

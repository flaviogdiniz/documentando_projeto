package br.com.projeto.controle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.modelo.Mensagem;
import br.com.projeto.modelo.Pessoa;

//Importações responsáveis pela documentação das rotas
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
public class Controle {

    // ***** Disponibilizar um objeto do tipo Mensagem
    @Autowired
    private Mensagem mensagem;
    
    // ***** Lista de pessoas (simular um banco de dados)
    private List<Pessoa> lista = new ArrayList<Pessoa>();

    // Código para efetuar o cadastro de pessoas
    int codigo = 0;

    // ***** Rotas
    @PostMapping("/cadastrar")
    @Operation(summary = "Rota responsável pelo cadastro de pessoas")
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "201", 
        description = "Pessoa cadastrada com sucesso",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Pessoa.class)
            )
        }
    ),

    @ApiResponse(
        responseCode = "400", 
        description = "Informação inválida",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Mensagem.class)
            )
        }
    )
})
    public ResponseEntity<?> cadastrar(@RequestBody Pessoa p){

        // Validação do nome (precisa ter pelo menos três caracteres)
        if(p.getNome().length() < 3){
            mensagem.setTexto("O nome deve ter pelo menos três caracteres.");
            return new ResponseEntity<Mensagem>(mensagem, HttpStatus.BAD_REQUEST);
        
        // Validação da idade (precisa ser uma idade entre 0 e 120)
        }else if(p.getIdade() < 0 || p.getIdade() > 120){
            mensagem.setTexto("Informe uma idade entre 0 e 120");
            return new ResponseEntity<Mensagem>(mensagem, HttpStatus.BAD_REQUEST);
        
        // Efetuar cadastro e retornar um objeto do tipo Pessoa
        }else{
            
            // Incrementar o código
            this.codigo += 1;

            // Especificar o código no objeto do tipo Pessoa
            p.setCodigo(codigo);

            // Adicionar o objeto do tipo Pessoa na lista
            this.lista.add(p);

            // Retorno da API
            return new ResponseEntity<Pessoa>(p, HttpStatus.CREATED);
        }

    }

    @GetMapping("/listar")
    @Operation(summary = "Rota responsável pelo listagem de pessoas")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Listagem de pessoas",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pessoa.class)
                )
            }
        )
    })
    public List<Pessoa> listarPessoas(){

        // Retornar a lista de pessoas cadastradas
        return this.lista;

    }

    @GetMapping("/obterPessoa/{codigo}")
    @Operation(summary = "Rota responsável por obter uma pessoa através do código")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Pessoa encontrada",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pessoa.class)
                )
            }
        ),

        @ApiResponse(
            responseCode = "404", 
            description = "Pessoa não encontrada",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Mensagem.class)
                )
            }
        )
    })
    public ResponseEntity<?> obterPessoa(@PathVariable long codigo){

        // Criar um objeto Pessoa
        Pessoa p = new Pessoa();

        // Realizar pesquisa na lista
        for (Pessoa pessoa : lista) {
            if(pessoa.getCodigo() == codigo){
                p = pessoa;
            }
        }

        // Verifica o tipo de retorno
        if(p.getCodigo() == 0){

            // Especifica um texto para o objeto mensagem
            mensagem.setTexto("Pessoa não encontrada");

            // Retorno
            return new ResponseEntity<Mensagem>(mensagem, HttpStatus.NOT_FOUND);
        }else{

            // Retorno
            return new ResponseEntity<Pessoa>(p, HttpStatus.OK);
        }

    }

    @PutMapping("/alterar")
    @Operation(summary = "Rota responsável pela alteração de dados das pessoas")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Pessoa alterada com sucesso",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pessoa.class)
                )
            }
        ),

        @ApiResponse(
            responseCode = "400", 
            description = "Falha ao localizar o alterar os dados da pessoa",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Mensagem.class)
                )
            }
        )
    })
    public ResponseEntity<?> alterar(@RequestBody Pessoa p){

        // Verificar a posição da pessoa informada na lista
        int posicaoAlteracao = -1;

        for (int indice = 0; indice < this.lista.size(); indice++) {
            if(this.lista.get(indice).getCodigo() == p.getCodigo()){
                posicaoAlteracao = indice;
            }
        }

        // Validar se o código informado existe
        if(posicaoAlteracao == -1){

            mensagem.setTexto("O código informado não existe, tente novamente");
            return new ResponseEntity<Mensagem>(mensagem, HttpStatus.BAD_REQUEST);

        // Validação do nome (precisa ter pelo menos três caracteres)
        }else if(p.getNome().length() < 3){
            mensagem.setTexto("O nome deve ter pelo menos três caracteres");
            return new ResponseEntity<Mensagem>(mensagem, HttpStatus.BAD_REQUEST);
        
        // Validação da idade (precisa ser uma idade entre 0 e 120)
        }else if(p.getIdade() < 0 || p.getIdade() > 120){
            mensagem.setTexto("Informe uma idade entre 0 e 120");
            return new ResponseEntity<Mensagem>(mensagem, HttpStatus.BAD_REQUEST);
        
        // Efetuar cadastro e retornar um objeto do tipo Pessoa
        }else{
            
            // Altera o objeto do tipo Pessoa na lista
            this.lista.set(posicaoAlteracao, p);

            // Retorno da API
            return new ResponseEntity<Pessoa>(p, HttpStatus.OK);
        }

    }

    @DeleteMapping("/remover/{codigo}")
    @Operation(summary = "Rota responsável pela remoção de pessoas")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Pessoa removida com sucesso",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Mensagem.class)
                )
            }
        ),

        @ApiResponse(
            responseCode = "400", 
            description = "Falha ao encontrar a pessoa ou remover",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Mensagem.class)
                )
            }
        )
    })
    public ResponseEntity<Mensagem> remover(@PathVariable int codigo){

        // Verificar a posição da pessoa informada na lista
        int posicaoRemocao = -1;

        for (int indice = 0; indice < this.lista.size(); indice++) {
            if(this.lista.get(indice).getCodigo() == codigo){
                posicaoRemocao = indice;
            }
        }

        // Verificação
        if(posicaoRemocao == -1){

            // Mensagem
            mensagem.setTexto("O código informado não existe, tente novamente");

            // Retorno
            return new ResponseEntity<Mensagem>(mensagem, HttpStatus.BAD_REQUEST);

        }else{

            // Remover pessoa da lista
            this.lista.remove(posicaoRemocao);

            // Mensagem
            mensagem.setTexto("Pessoa removida com sucesso");

            // Retorno
            return new ResponseEntity<Mensagem>(mensagem, HttpStatus.OK);
        }

    }
}
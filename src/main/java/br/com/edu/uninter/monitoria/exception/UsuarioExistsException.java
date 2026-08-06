package br.com.edu.uninter.monitoria.exception;

public class UsuarioExistsException extends RuntimeException{

    public UsuarioExistsException(){
        super("Já existe um usuário com esse nome.");
    }
}

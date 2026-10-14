package br.com.edu.uninter.monitoria.exception;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException() {
        super("Este encontro não possui imagem.");
    }
}
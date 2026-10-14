package br.com.edu.uninter.monitoria.exception;

public class ImageExistException extends RuntimeException {
    public ImageExistException() {
        super("Este encontro já possui uma imagem.");
    }
}

package org.example.shopcarcasas.exeption;

public class MsExepcion extends Exception{
    static final long serialVersionUID = -3387516993124229948L;

    private final String error;
    private final int httpCode;

    public MsExepcion(final String message, final Throwable cause, final int httpCode) {
        super(message, cause);
        this.error = "Error";
        this.httpCode = httpCode;
    }

    public MsExepcion(final String message, final int httpCode) {
        super(message);
        this.error = "Error";
        this.httpCode = httpCode;
    }

    public MsExepcion(final String message, final Throwable cause, final int httpCode, final String error) {
        super(message, cause);
        this.error = error;
        this.httpCode = httpCode;
    }

    public String getError() {
        return error;
    }

    public int getHttpCode() {
        return httpCode;
    }
}

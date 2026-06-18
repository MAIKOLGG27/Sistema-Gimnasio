package com.gym.pago_servicio.exception;


public class ErrorResponse {
    private String mensaje;
    private String detalle;
    private int status;

    public ErrorResponse(String mensaje, String detalle, int status){
        this.mensaje = mensaje;
        this.detalle =detalle;
        this.status = status;
    }
    public String getMensaje() {return mensaje;}
    public String getDetalle() {return detalle;}
    public int getStatus() {return status;}

}

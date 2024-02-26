package com.example.android;
import java.io.Serializable;

public class Datos implements Serializable{
    private String nombre;
    private int id, cant, precioCosto, precioVenta, cantVendido;

    Datos(){
        nombre = "";
        //cant = 0;
        //precioCosto = 0;
        //precioVenta = 0;
        //cantVendido = 0;
    }

    Datos(String nombre /*,int cant, int precioCosto, int precioVenta, int cantVendido*/){
        this.nombre = nombre;
        //this.cant = cant;
        //this.precioCosto = precioCosto;
        //this.precioVenta = precioVenta;
        //this.cantVendido = cantVendido;
    }

    String getNombre(){
        return nombre;
    }

    int getCant(){
        return cant;
    }

    int getCosto(){
        return precioCosto;
    }

    int getVenta(){
        return precioCosto;
    }

    int getVendido(){
        return cantVendido;
    }
}
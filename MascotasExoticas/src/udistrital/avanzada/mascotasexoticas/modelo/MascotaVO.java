/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.modelo;

/**
 *
 * @author sarit
 */
public class MascotaVO extends AnimalVO{
    
    private String apodo;

    public MascotaVO(String nombre, String clasificacion, String familia, String genero, String especie, String alimento, String apodo) {
        super(nombre, clasificacion, familia, genero, especie, alimento);
        this.apodo=apodo;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }
    
    
    
}

package co.com.flamingo.ventasTienda.controlador;

import java.awt.EventQueue;

import co.com.flamingo.ventasTienda.modelo.Modelo;
import co.com.flamingo.ventasTienda.vista.VentasTiendaVentana;

public class Principal {
	Controlador controlador;
	Modelo modelo;
	VentasTiendaVentana ventana;
	
	private void iniciar() {
		// Se instancian las clases:
		controlador = new Controlador();
		modelo = new Modelo();
		ventana = new VentasTiendaVentana();
		
		// Se establecen las relaciones entre las clases:
		modelo.setControlador(controlador);
		ventana.setControlador(controlador);
		
		// Se establecen las relaciones con la clase coordinador:
		controlador.setVentana(ventana);
		controlador.setModelo(modelo);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public static void main(String[] args) {
		Principal principal = new Principal();
		principal.iniciar();
	}

}

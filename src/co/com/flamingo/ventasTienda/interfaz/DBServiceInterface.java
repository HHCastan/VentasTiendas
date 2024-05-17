package co.com.flamingo.ventasTienda.interfaz;

import java.util.List;

import co.com.flamingo.ventasTienda.modelo.Almacen;

public interface DBServiceInterface {
	public abstract List<Almacen> getTiendas();
	public abstract void conectarInformesPOS();
	public abstract void desconectarInformesPOS();
}

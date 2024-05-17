package co.com.flamingo.ventasTienda.controlador;

import co.com.flamingo.ventasTienda.modelo.Reporte;
import co.com.flamingo.ventasTienda.modelo.ReporteTableModel;

public class ReporteTableControl {
	/** El modelo de la tabla */
	private ReporteTableModel modelo = null;
	
	/**
	 * Numero que nos servirá para construir tenders distintos para la tabla
	 */
	private static int numero = 0;


	public ReporteTableControl(ReporteTableModel modelo) {
		this.modelo = modelo;
	}

	/**
	 * Adiciona una fila al final del modelo
	 */
	public void addRow(Reporte dato) {
		this.modelo.addRpt(dato);
		numero++;
	}

	/** Elimina la fila indicada del modelo */
	public void delRow(int iRow) {
		this.modelo.delRpt(iRow);
		numero--;
	}

	public void clear() {
		if (numero > 0)
			modelo.clear();
		numero = 0;
	}

}

package co.com.flamingo.ventasTienda.modelo;

import java.util.LinkedList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ReporteTableModel implements TableModel {

	/**
	 * Lista con los datos. Cada elemento de la lista es una instancia de Reporte
	 */
	private LinkedList<Reporte> datos = new LinkedList<Reporte>();

	/**
	 * Lista de suscriptores. El JTable será un suscriptor de este modelo de datos
	 */
	private LinkedList<TableModelListener> listeners = new LinkedList<TableModelListener>();

	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "ALMACEN";
		case 1:
			return "VENTAS BRUTAS";
		case 2:
			return "ANULACIONES";
		case 3:
			return "DEVOLUCIONES";
		case 4:
			return "DESCUENTOS";
		case 5:
			return "TOTAL VENTAS";
		default:
			return null;
		}
	}

	@Override
	public int getRowCount() {
		return datos.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Reporte aux;
		
		// Se obtiene el reporte en la fila indicada:
		aux = (Reporte) (datos.get(rowIndex));

		// Se obtiene el campo apropiado según el valor de columnIndex
		switch (columnIndex) {
		case 0:
			return aux.getTienda();		
		case 1:
			return aux.getVentasBrutas();
		case 2:
			return aux.getAnulaciones();
		case 3:
			return aux.getDevoluciones();
		case 4:
			return aux.getDescuentos();
		case 5:
			return aux.getTotalVentas();
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// Adiciona un suscriptor
		listeners.add(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// Elimina un suscriptor.
		listeners.remove(l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Reporte aux;
		aux = (Reporte) (datos.get(rowIndex));
		// Cambia el campo del Item que indica columnIndex, poniendole el
		// aValue que se nos pasa.
		switch (columnIndex) {
		case 0:
			aux.setTienda((String) aValue);		
			break;
		case 1:
			aux.setVentasBrutas((String) aValue);		
			break;
		case 2:
			aux.setAnulaciones((String) aValue);		
			break;
		case 3:
			aux.setDevoluciones((String) aValue);		
			break;
		case 4:
			aux.setDescuentos((String) aValue);		
			break;
		case 5:
			aux.setTotalVentas((String) aValue);		
			break;
		default:
			break;
		}
	}
	
	/**
	 * Borra del modelo el reporte indicado:
	 */
	public void delRpt(int rowIndex) {
		// Se borra la fila
		datos.remove(rowIndex);

		// Y se avisa a los suscriptores, creando un TableModelEvent...
		TableModelEvent evento = new TableModelEvent(this, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS,
				TableModelEvent.DELETE);

		// ... y pasándoselo a los suscriptores
		avisaSuscriptores(evento);
	}

	/**
	 * Añade un reporte al final de la tabla:
	 */
	public void addRpt(Reporte newRpt) {
		// Añade el reporte al modelo
		datos.add(newRpt);

		// Avisa a los suscriptores creando un TableModelEvent...
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS,
				TableModelEvent.INSERT);

		// ... y avisando a los suscriptores
		avisaSuscriptores(evento);
	}
	
	/**
	 * Limpia la tabla de reportes:
	 */
	public void clear() {
		while (getRowCount() > 0) {
			delRpt(getRowCount() -1);
		}
	}

	/**
	 * Pasa a los suscriptores el evento.
	 */
	private void avisaSuscriptores(TableModelEvent evento) {
		int i;

		// Bucle para todos los suscriptores en la lista, se llama al metodo tableChanged() de los mismos, pasándole el
		// evento.
		for (i = 0; i < listeners.size(); i++)
			((TableModelListener) listeners.get(i)).tableChanged(evento);
	}

}

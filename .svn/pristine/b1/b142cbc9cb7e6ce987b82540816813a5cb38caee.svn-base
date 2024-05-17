package co.com.flamingo.ventasTienda.vista;

import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import org.apache.log4j.Logger;
import co.com.flamingo.ventasTienda.controlador.Controlador;
import co.com.flamingo.ventasTienda.controlador.ReporteTableControl;
import co.com.flamingo.ventasTienda.implementation.DBServiceImplementation;
import co.com.flamingo.ventasTienda.implementation.FTPServiceImplementation;
import co.com.flamingo.ventasTienda.modelo.Almacen;
import co.com.flamingo.ventasTienda.modelo.Reporte;
import co.com.flamingo.ventasTienda.modelo.ReporteTableModel;
import co.com.flamingo.ventasTienda.util.DialogWait;
import co.com.flamingo.ventasTienda.util.Util;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import java.awt.Toolkit;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JTable;

public class VentasTiendaVentana extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> dlmTiendas;
//	private Logger log = Util.getLogger();

	/** Lista de las tiendas que se consultan en la BD */
	Almacen[] stTiendasList = null;
	List<Almacen> tiendas = null;
	JList<String> lstTiendas1 = null;

	/** Servicio para conexion con bases de datos */
	DBServiceImplementation dbServiceImpl = new DBServiceImplementation();

	/** Servicio para concernientes al FTP */
	FTPServiceImplementation ftpServiceImpl;
	private JScrollPane scrollTiendas;
	private JList<String> lstTiendas;
	private JTable tableRpt;
	private JScrollPane scrollReporte;
	private ReporteTableModel modeloRPT = new ReporteTableModel();
	private ReporteTableControl controlRPT = new ReporteTableControl(modeloRPT);

	/**
	 * Create the frame.
	 */
	public VentasTiendaVentana() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/Report32.png")));
		setTitle("Consulta de ventas por tienda. By HHC");
		tiendas = dbServiceImpl.getTiendas(); // Consulta las tiendas en la BD
		if (tiendas.size() < 1) {
			Util.okDialog("Error consultando los almacenes. Cierre y vuelva a abrir la aplicación", (JFrame) this,
					JOptionPane.ERROR_MESSAGE);
		}
		stTiendasList = new Almacen[tiendas.size()];
		dlmTiendas = new DefaultListModel<String>();
		for (int i = 0; i < tiendas.size(); i++) { // Llena los arreglos de tiendas
			String linea = tiendas.get(i).toString();
			dlmTiendas.addElement(linea);
			stTiendasList[i] = (Almacen) tiendas.get(i);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 100, 1300, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollTiendas = new JScrollPane();
		scrollTiendas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollTiendas.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Seleccione la(s) Tienda(s):", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		scrollTiendas.setBounds(10, 11, 260, 459);
		contentPane.add(scrollTiendas);

		lstTiendas = new JList<String>();
		lstTiendas.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lstTiendas.setModel(dlmTiendas);
		scrollTiendas.setViewportView(lstTiendas);

		scrollReporte = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollReporte.setBorder(null);
		scrollReporte.setBounds(429, 10, 845, 460);
		contentPane.add(scrollReporte);

		tableRpt = new JTable(modeloRPT);
		tableRpt.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tableRpt.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 16));
		tableRpt.setRowHeight(30);
		scrollReporte.setColumnHeaderView(tableRpt);
		scrollReporte.setViewportView(tableRpt);

		JButton btnCurrent = new JButton("Actual");
		btnCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarDatos(3, e);
			}
		});
		btnCurrent.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnCurrent.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnCurrent.setIcon(new ImageIcon(
				VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/execute_01.png")));
		btnCurrent.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCurrent.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCurrent.setBounds(296, 11, 100, 100);
		contentPane.add(btnCurrent);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlRPT.clear();
			}
		});
		btnLimpiar.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnLimpiar.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnLimpiar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLimpiar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLimpiar.setIcon(new ImageIcon(
				VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/Clear.png")));
		btnLimpiar.setBounds(296, 259, 100, 100);
		contentPane.add(btnLimpiar);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnSalir.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnSalir.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSalir.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSalir.setIcon(new ImageIcon(
				VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/Exit.png")));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		btnSalir.setBounds(296, 370, 100, 100);
		contentPane.add(btnSalir);
		
		JButton btnPrevio = new JButton("Previo");
		btnPrevio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarDatos(4, e);
			}
		});
		btnPrevio.setIcon(new ImageIcon(VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/executePrev64.png")));
		btnPrevio.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPrevio.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPrevio.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnPrevio.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPrevio.setBounds(296, 122, 100, 100);
		contentPane.add(btnPrevio);

	}

	protected void close() {
		Util.stopWaitCursor(this.getRootPane());
		this.dispose();
	}

	public void setControlador(Controlador controlador) {
	}

	protected void actualizarDatos(int r, ActionEvent e) {
		if(tableRpt.getRowCount() > 0) {
			Util.okDialog("Primero debe limpiar los datos anteriores", (JFrame) this, JOptionPane.ERROR_MESSAGE);
			return;
		}
		int[] storesSelected = lstTiendas.getSelectedIndices();
		// Debe seleccionar al menos una tienda
		if (storesSelected.length < 1) {
			Util.okDialog("Debe seleccionar al menos una tienda", (JFrame) this, JOptionPane.ERROR_MESSAGE);
			return;
		}

		Util.startWaitCursor(this.getRootPane());

		String remoteFile = "C:/ADX_IDT4/EAMRPT01.DAT";
//		String localFile = tDir + "EAMRPT01.DAT";
		String localFile = "C:/TMP/EAMRPT01.DAT";

		// En una primera pasada manda a crear los reportes
		DialogWait wait = new DialogWait();
		SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {

		    @Override
		    protected Void doInBackground() throws Exception {
				/** Servicio para concernientes al FTP */
				FTPServiceImplementation ftpServiceImpl = null;


				for (int i = 0; i < storesSelected.length; i++) {
					int indice = storesSelected[i];
					ftpServiceImpl = new FTPServiceImplementation();
					ftpServiceImpl.setHost(stTiendasList[indice].getIP());
					ftpServiceImpl.conectar();
					ftpServiceImpl.borrarArchivo(remoteFile);
					ftpServiceImpl.ejecutarComando("ADXSTART", "ADX_IPGM:EAMRPARL.286 "+ r + " \"REPORTE SUMARIO CORTO\"");
					ftpServiceImpl.desconectar();
				}

				esperar(20000 * r);

				// En una segunda pasada va por ellos y los procesa
				for (int i = 0; i < storesSelected.length; i++) {
					deleteFile(localFile);
					int indice = storesSelected[i];
					ftpServiceImpl = new FTPServiceImplementation();
					ftpServiceImpl.setHost(stTiendasList[indice].getIP());
					ftpServiceImpl.conectar();
					ftpServiceImpl.bajarArchivo(remoteFile, localFile);
					ftpServiceImpl.desconectar();
					addLine(localFile, stTiendasList[indice].getCodigo());
				}

				wait.close();
		        return null;
		    }
		};
		mySwingWorker.execute();
		wait.makeWait("Por favor espera", e);
		Util.stopWaitCursor(this.getRootPane());
		Util.okDialog("Proceso finalizado. Revise los resultados", (JFrame) this, JOptionPane.INFORMATION_MESSAGE);
	}

	private void deleteFile(String localFile) {
		File file = new File(localFile);
		file.delete();

	}

	private void addLine(String localFile, String codigo) {
		try (Scanner file = new Scanner(new File(localFile))) {
			String totalVentasLine = "                               0";
			String anulacionesLine = "                               0";
			String ventasBrutasLine = "                               0";
			String devolucionesLine = "                               0";
			String descuentosLine = "                               0";
			while (file.hasNextLine()) {
				final String line = file.nextLine();
				if (line.contains("VENTAS BRUTAS"))
					ventasBrutasLine = line;
				if (line.contains("ANULACIONES"))
					anulacionesLine = line;
				if (line.contains("DEVOLUCIONES"))
					devolucionesLine = line;
				if (line.contains("DESCUENTOS"))
					descuentosLine = line;
				if (line.contains("TOTAL VENTAS"))
					totalVentasLine = line;
			}
			Reporte rpt = new Reporte();
			rpt.setTienda("F" + codigo);
			rpt.setVentasBrutas(ventasBrutasLine.substring(15, 32).trim());
			rpt.setAnulaciones(anulacionesLine.substring(15, 32).trim());
			rpt.setDevoluciones(devolucionesLine.substring(15, 32).trim());
			rpt.setDescuentos(descuentosLine.substring(15, 32).trim());
			rpt.setTotalVentas(totalVentasLine.substring(15, 32).trim());
			controlRPT.addRow(rpt);
		} catch (FileNotFoundException e) {
			Util.okDialog("No pude procesar archivo de F" + codigo, (JFrame) this, JOptionPane.ERROR_MESSAGE);
		}

	}

	private void esperar(int i) {
		try {

			Thread.sleep(i);
		} catch (InterruptedException ex) {
		}
	}
}

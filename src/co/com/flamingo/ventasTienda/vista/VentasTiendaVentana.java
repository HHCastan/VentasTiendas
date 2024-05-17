package co.com.flamingo.ventasTienda.vista;

import java.util.Date;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

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
	private Logger log = Util.getLogger();

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
		log.info("Ventas Tienda. Versión: 1.1.3");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/Report32.png")));
		setTitle("Consulta de ventas por tienda. By HHC");
		tiendas = dbServiceImpl.getTiendas(); // Consulta las tiendas en la BD
		if (tiendas.size() < 1) {
			Util.okDialog("Error consultando los almacenes. Cierre y vuelva a abrir la aplicación", (JFrame) this,
					JOptionPane.ERROR_MESSAGE);
			log.error("No pudo conectarse a consultar las tiendas");
		}
		stTiendasList = new Almacen[tiendas.size()];
		dlmTiendas = new DefaultListModel<String>();
		for (int i = 0; i < tiendas.size(); i++) { // Llena los arreglos de tiendas
			String linea = tiendas.get(i).toString();
			dlmTiendas.addElement(linea);
			stTiendasList[i] = (Almacen) tiendas.get(i);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		setMaximizedBounds(env.getMaximumWindowBounds());
		setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
//		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollTiendas = new JScrollPane();
		scrollTiendas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollTiendas.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Seleccione la(s) Tienda(s):", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		// Para obtener el alto de la pantalla:
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		pack();
		int alto = (int)screenSize.getHeight() - getInsets().top - getInsets().bottom - 50;
		
		scrollTiendas.setBounds(10, 11, 260, alto);
		contentPane.add(scrollTiendas);

		lstTiendas = new JList<String>();
		lstTiendas.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lstTiendas.setModel(dlmTiendas);
		scrollTiendas.setViewportView(lstTiendas);

		scrollReporte = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollReporte.setBorder(null);
		scrollReporte.setBounds(400, 10, 950, alto);
		contentPane.add(scrollReporte);

		tableRpt = new JTable(modeloRPT);
		tableRpt.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tableRpt.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 16));
		tableRpt.setRowHeight(30);
		TableColumnModel columnModel = tableRpt.getColumnModel();
		// Organiza los anchos de las columnas:
		columnModel.getColumn(0).setPreferredWidth(100);
		columnModel.getColumn(1).setPreferredWidth(160);
		columnModel.getColumn(2).setPreferredWidth(160);
		columnModel.getColumn(3).setPreferredWidth(160);
		columnModel.getColumn(4).setPreferredWidth(160);
		columnModel.getColumn(5).setPreferredWidth(160);
		columnModel.getColumn(6).setPreferredWidth(160);
		columnModel.getColumn(7).setPreferredWidth(160);
		// organiza la alineación de las columnas:
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		columnModel.getColumn(0).setCellRenderer(centerRenderer);
		columnModel.getColumn(1).setCellRenderer(rightRenderer);
		columnModel.getColumn(2).setCellRenderer(rightRenderer);
		columnModel.getColumn(3).setCellRenderer(rightRenderer);
		columnModel.getColumn(4).setCellRenderer(rightRenderer);
		columnModel.getColumn(5).setCellRenderer(rightRenderer);
		columnModel.getColumn(6).setCellRenderer(centerRenderer);
		columnModel.getColumn(7).setCellRenderer(centerRenderer);
		scrollReporte.setColumnHeaderView(tableRpt);
		scrollReporte.setViewportView(tableRpt);

		JButton btnCurrent = new JButton("Actual");
		btnCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.info("Generando reporte del periodo actual...");
				actualizarDatos(3, e);
			}
		});
		btnCurrent.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnCurrent.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnCurrent.setIcon(new ImageIcon(
				VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/execute_01.png")));
		btnCurrent.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCurrent.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCurrent.setBounds(280, 20, 100, 100);
		contentPane.add(btnCurrent);

		JButton btnPrevio = new JButton("Previo");
		btnPrevio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.info("Generando reporte del periodo previo...");
				actualizarDatos(4, e);
			}
		});
		btnPrevio.setIcon(new ImageIcon(VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/executePrev64.png")));
		btnPrevio.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPrevio.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPrevio.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnPrevio.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPrevio.setBounds(280, 140, 100, 100);
		contentPane.add(btnPrevio);

		JButton btnAmbos = new JButton("Ambos");
		btnAmbos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.info("Generando reporte de ambos periodos...");
				actualizarDatos(9, e);
			}
		});
		btnAmbos.setIcon(new ImageIcon(VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/executeAll64.png")));
		btnAmbos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAmbos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAmbos.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnAmbos.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAmbos.setBounds(280, 260, 100, 100);
		contentPane.add(btnAmbos);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlRPT.clear();
				log.info("Se limpian variables y pantalla");
			}
		});
		btnLimpiar.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnLimpiar.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnLimpiar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLimpiar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLimpiar.setIcon(new ImageIcon(
				VentasTiendaVentana.class.getResource("/co/com/flamingo/ventasTienda/imagenes/Clear.png")));
		btnLimpiar.setBounds(280, 380, 100, 100);
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
				log.info("Cerrando aplicación...");
				close();
			}
		});
		btnSalir.setBounds(280, 500, 100, 100);
		contentPane.add(btnSalir);
		
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
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmm");
		String sDate = format.format( new Date()   );
		String remoteFile = "C:/ADX_IDT4/EAMRPT01.DAT";
		String localFile = "C:/TMP/EAMRPT01_"+ r + ".DAT";
		String csvFile = "C:/TMP/RPTVTA_" + sDate +  "_"+ r + ".CSV";

		// En una primera pasada manda a crear los reportes
		DialogWait wait = new DialogWait();
		SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {

		    @Override
		    protected Void doInBackground() throws Exception {
		    	if( (r == 3) || (r == 4) ) {
					String sComando = "ADX_IPGM:EAMRPARL.286 "+ r + " \"REPORTE SUMARIO CORTO\"";
					generaRptRemoto(remoteFile, sComando);
					// Espera un minuto a que los reportes se generen
					esperar(60000);
					// En una segunda pasada va por ellos y los procesa a medida que los trae
					generaConsoildado(remoteFile, localFile, (r == 3)? "Actual" : "Previo");
		    	}
		    	else {
		    		// Primero genera el previo
					String sComando = "ADX_IPGM:EAMRPARL.286 "+ 4 + " \"REPORTE SUMARIO CORTO\"";
					generaRptRemoto(remoteFile, sComando);
					esperar(60000);
					generaConsoildado(remoteFile, localFile, "Previo");

					// Luego el current
					sComando = "ADX_IPGM:EAMRPARL.286 "+ 3 + " \"REPORTE SUMARIO CORTO\"";
					generaRptRemoto(remoteFile, sComando);
					esperar(60000);
					generaConsoildado(remoteFile, localFile, "Actual");
		    	}
				
				Util.exportToCSV(tableRpt,csvFile);

				wait.close();
		        return null;
		    }

			private void generaConsoildado(String remoteFile, String localFile, String periodo) {
				
				for (int i = 0; i < storesSelected.length; i++) {
					int indice = storesSelected[i];
					log.info("Procesando reporte en " + stTiendasList[indice].getNombre());
					deleteFile(localFile);
					ftpServiceImpl = new FTPServiceImplementation();
					ftpServiceImpl.setHost(stTiendasList[indice].getIP());
					ftpServiceImpl.conectar();
					ftpServiceImpl.bajarArchivo(remoteFile, localFile);
					ftpServiceImpl.desconectar();
					addLine(localFile, stTiendasList[indice].getCodigo(), periodo);
				}
			}

			private void generaRptRemoto(String remoteFile, String sComando) {
				for (int i = 0; i < storesSelected.length; i++) {
					int indice = storesSelected[i];
					log.info("Generando reporte en " + stTiendasList[indice].getNombre());
					ftpServiceImpl = new FTPServiceImplementation();
					ftpServiceImpl.setHost(stTiendasList[indice].getIP());
					ftpServiceImpl.conectar();
					ftpServiceImpl.borrarArchivo(remoteFile);
					ftpServiceImpl.ejecutarComando("ADXSTART", sComando);
					ftpServiceImpl.desconectar();
				}
			}
		};
		mySwingWorker.execute();
		wait.makeWait("Por favor espera", e);
		Util.stopWaitCursor(this.getRootPane());
		Util.okDialog("Proceso finalizado. Revise los resultados", (JFrame) this, JOptionPane.INFORMATION_MESSAGE);
	}

	private void deleteFile(String localFile) {
		File file = new File(localFile);
		log.info("Borrando archivo local: " + localFile);
		if(file.exists()) {
			if(!file.delete()) {
				Util.okDialog("No se puede borrar y puede generar inconsistencias: " + localFile, (JFrame) this, JOptionPane.ERROR_MESSAGE);
				log.error("Archivo no pudo ser eliminado: " + localFile);
				
			}
		}

	}

	private void addLine(String localFile, String codigo, String periodo) {
		try (Scanner file = new Scanner(new File(localFile))) {
			String totalVentasLine = "                               0";
			String anulacionesLine = "                               0";
			String ventasBrutasLine = "                               0";
			String devolucionesLine = "                               0";
			String descuentosLine = "                               0";
			String fechaRptLine = "                                ";
			while (file.hasNextLine()) {
				final String line = file.nextLine();
				// Valida que el reporte generado corresponda a la fecha
				if (line.contains("IMPRESO EN "))
				{
					if (!esFechaActual(line.substring(64,78))) {
						Util.okDialog("Fecha de generación no coincide para F" + codigo, (JFrame) this, JOptionPane.ERROR_MESSAGE);
						log.error("Fecha de generación no coincide para" + codigo);
						return;
					}
					fechaRptLine = line;
				}
				if (line.contains("VENTAS BRUTAS"))
					ventasBrutasLine = line;
				if (line.contains("ANULACIONES"))
					anulacionesLine = line;
				if (line.contains("DEVOLUCIONES"))
					devolucionesLine = line;
				if (line.contains("DESCUENTOS"))
					descuentosLine = line;
				if (line.contains("VENTAS NETAS"))
					totalVentasLine = line;
			}
			Reporte rpt = new Reporte();
			rpt.setTienda("F" + codigo);
			rpt.setVentasBrutas(ventasBrutasLine.substring(15, 32).trim());
			rpt.setAnulaciones(anulacionesLine.substring(15, 32).trim());
			rpt.setDevoluciones(devolucionesLine.substring(15, 32).trim());
			rpt.setDescuentos(descuentosLine.substring(15, 32).trim());
			rpt.setTotalVentas(totalVentasLine.substring(15, 32).trim());
			rpt.setFechaReporte(fechaRptLine.substring(64, 78).trim());
			rpt.setPeriodo(periodo);
			controlRPT.addRow(rpt);
		} catch (FileNotFoundException e) {
			Util.okDialog("No pude procesar archivo de F" + codigo, (JFrame) this, JOptionPane.ERROR_MESSAGE);
			log.error("No pude procesar archivo de F" + codigo);
		}

	}

	private boolean esFechaActual(String fechaHora) {
		String fecha = fechaHora.substring(0, 8);
		String pattern = "dd/MM/YY";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String date = sdf.format(new Date());
		if (date.compareTo(fecha) == 0)
			return true;
		return false;
	}

	private void esperar(int i) {
		try {

			Thread.sleep(i);
		} catch (InterruptedException ex) {
		}
	}
}

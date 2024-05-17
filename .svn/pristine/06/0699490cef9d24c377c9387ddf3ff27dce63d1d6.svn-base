package co.com.flamingo.ventasTienda.util;

import java.awt.Component;
import java.awt.Cursor;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Util {
	private static Properties properties = null;
	public static Logger logger = null;

	public static Logger getLogger() {
		if (logger == null)
			initLogger();
		return logger;
	}

	private static void initLogger() {
		try {
			logger = Logger.getLogger("remoteCommand");
			Properties pro = new Properties();
			pro.load(Util.class.getResourceAsStream("/config/ventasTienda4j.properties"));
			PropertyConfigurator.configure(pro);
		} catch (Throwable e) {

		}
	}

	public static Properties getProperties() {
		if (properties == null)
			loadProperties();
		return properties;
	}

	public static void loadProperties() {
		properties = new Properties();

		try {
			properties.load(Util.class.getResourceAsStream("/config/ventasTienda.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rutina para pintar un mensaje de dialogo en la pantalla
	 * 
	 * @param stMessage
	 * @param dialogo
	 * @param tipo
	 */
	public static void okDialog(final String stMessage, final JFrame dialogo, final int tipo) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog((Component) dialogo, stMessage, "Mensaje", tipo);
			}
		});
	}

	/**
	 * Rutina para pintar un dialogo de SI o NO
	 * 
	 * @param stMessage
	 * @param dialogo
	 * @param tipo
	 * @param location
	 * @param options2
	 * @return
	 */

	public static int sigaPareMessage(String stMessage, JDialog dialogo, int tipo, Object[] options) {
		return JOptionPane.showOptionDialog((Component) dialogo, stMessage, "Mensaje", JOptionPane.YES_NO_CANCEL_OPTION,
				tipo, null, options, options[0]);
	}

	/**
	 * Sets cursor for specified component to Wait cursor
	 */
	public static void startWaitCursor(JComponent component) {
		RootPaneContainer root = (RootPaneContainer) component.getTopLevelAncestor();
		root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		root.getGlassPane().setVisible(true);
	}

	/**
	 * Sets cursor for specified component to Normal cursor
	 */
	public static void stopWaitCursor(JComponent component) {
		RootPaneContainer root = (RootPaneContainer) component.getTopLevelAncestor();
		root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		root.getGlassPane().setVisible(true);
	}


}

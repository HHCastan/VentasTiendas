package co.com.flamingo.ventasTienda.implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import co.com.flamingo.ventasTienda.interfaz.DBServiceInterface;
import co.com.flamingo.ventasTienda.modelo.Almacen;
import co.com.flamingo.ventasTienda.util.Util;

public class DBServiceImplementation  implements DBServiceInterface {
	private static DBServiceImplementation dbServiceImpl;
	private Properties properties = Util.getProperties();
	private Connection connINFPOS;
	private Logger log = Util.getLogger();

	public static DBServiceImplementation getInstance() {
		if (dbServiceImpl == null)
			dbServiceImpl = new DBServiceImplementation();
		return dbServiceImpl;
	}

	@Override
	public List<Almacen> getTiendas() {
		List<Almacen> lista = new LinkedList<Almacen>();
		ResultSet rs = null;
		conectarInformesPOS();
		try {
			Statement st = connINFPOS.createStatement();
			rs = st.executeQuery("SELECT ID, Nombre, IP, tomcatServer FROM TIENDAS_POS ORDER BY ID");
			while (rs.next()) {
				Almacen almacen = new Almacen();
				String sID = rs.getString("ID");
				String sNombre = rs.getString("Nombre");
				String sIP = rs.getString("IP");
				String sTomcatServer = rs.getString("tomcatServer");
				almacen.setCodigo(sID);
				almacen.setNombre(sNombre);
				almacen.setIP(sIP);
				almacen.setTomcatServer(sTomcatServer);
				lista.add(almacen);
			}
		} catch (Exception e) {
			log.error("Error:", e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException localSQLException) {
				}
		}
		desconectarInformesPOS();

		return lista;
	}

	@Override
	public void conectarInformesPOS() {
		try {
			String url = properties.getProperty("dburl");
			String driver = properties.getProperty("driver");
			String user = properties.getProperty("dbuser");
			String pass = properties.getProperty("dbpassword");
			log.debug("Conectando. URL: " + url + ", DRIVER: " + driver + ", user: " + user + ", pass: " + pass);
			System.out.println("Conectando. URL: " + url + ", DRIVER: " + driver + ", user: " + user + ", pass: " + pass);
			connINFPOS = DriverManager.getConnection(url, user, pass);
			Class.forName(driver);
			log.debug("Conexion exitosa con la base de datos.");
		} catch (Exception e) {
			log.error("Error: ", e);
		}
	}

	@Override
	public void desconectarInformesPOS() {
		try {
			connINFPOS.close();
		} catch (Exception e) {
			log.error("Error cerrando conexion: ", e);
		}
		log.debug("Desconexión exitosa con la base de datos");
	}

}

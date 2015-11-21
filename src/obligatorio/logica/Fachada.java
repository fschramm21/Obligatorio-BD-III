package obligatorio.logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import obligatorio.exceptions.Due�oException;
import obligatorio.exceptions.LogicaException;
import obligatorio.exceptions.PersistenciaException;
import obligatorio.logica.valueObjects.VODue�o;
import obligatorio.logica.valueObjects.VOMascota;
import obligatorio.persistencia.daos.IDaoDue�os;
import obligatorio.util.IConexion;
import obligatorio.util.IPoolConexiones;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Fachada {
	private static Fachada instance = null;
	private IDaoDue�os due�os;
	private IPoolConexiones ipool;

	protected Fachada() throws LogicaException {

		Properties p = new Properties();
		String nomArch = "config/config.properties";

		try {
			p.load(new FileInputStream(nomArch));
			String pool = p.getProperty("logica.pool");
			String daoDue�o = p.getProperty("logica.daoDueno");
			this.ipool = (IPoolConexiones) (Class.forName(pool).newInstance());
			this.due�os = (IDaoDue�os) (Class.forName(daoDue�o).newInstance());
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | IOException e) {
			throw new LogicaException(e.getMessage());
		}
	}

	public static Fachada getInstance() throws LogicaException {
		if (instance == null) {
			instance = new Fachada();
		}

		return instance;
	}

	public void nuevoDue�o(VODue�o due�o) throws Due�oException,
			PersistenciaException {
		IConexion icon;
		int ced = due�o.getCedula();
		String nom = due�o.getNombre();
		String ape = due�o.getApellido();

		icon = ipool.obtenerConexion(true);

		if (!due�os.member(icon, ced)) {
			Due�o d = new Due�o(ced, nom, ape);
			due�os.insert(icon, d);
		} else {
			ipool.liberarConexion(icon, true);
			throw new Due�oException(
					"Error: ya existe el due�o con el numero de cedula ingresado.");
		}

		ipool.liberarConexion(icon, true);
	}

	public void nuevaMascota(VOMascota pMascota) throws PersistenciaException,
			Due�oException {
		String apodo = pMascota.getApodo();
		int cedulaDue�o = pMascota.getCedulaDue�o();
		String raza = pMascota.getRaza();

		IConexion icon = ipool.obtenerConexion(true);

		if (due�os.member(icon, cedulaDue�o)) {
			Due�o due�o = due�os.find(icon, cedulaDue�o);
			Mascota mascota = new Mascota(raza, apodo);

			due�o.addMascota(icon, mascota);
		} else {
			throw new Due�oException("Error: no existe due�o");
		}

		ipool.liberarConexion(icon, true);
	}

	public List<VODue�o> listarDue�os() {
		throw new NotImplementedException();
		// IConexion icon = pool.obtenerConexion(true);
		// return due�os.listarDue�os(icon);
	}

	public List<VOMascota> listarMascotas(int cedulaDue�o) {
		throw new NotImplementedException();
		// IConexion icon = pool.obtenerConexion(true);
		//
		// if (due�os.member(icon, cedulaDue�o)) {
		// return due�os.find(icon, cedulaDue�o).listarMascotas(icon);
		// } else {
		// throw new Due�oException("Error: no existe due�o");
		// }
	}

	public void borrarDue�oMascotas(int cedulaDue�o) {
		throw new NotImplementedException();
		// IConexion icon = pool.obtenerConexion(true);
		//
		// if (due�os.member(icon, cedulaDue�o)) {
		// due�os.find(icon, cedulaDue�o).borrarMascotas(icon);
		// due�os.delete(icon, cedulaDue�o);
		// } else {
		// throw new Due�oException("Error: no existe due�o");
		// }
	}
}

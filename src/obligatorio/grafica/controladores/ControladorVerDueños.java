package obligatorio.grafica.controladores;

import obligatorio.logica.valueObjects.VODue�o;

public class ControladorVerDue�os {

	
	public Object[][] listarDue�os() {
		
		Object[][] data = null;
		
		VODue�o[] due�os = {} ;
		// VODue�o[] due�os = {new VODue�o(44478897, "Alex", "Kalayjian")} ;
		// VODue�o[] due�os = (VODue�o[]) Fachada.getInstance().listarDue�os().toArray();
		
		if (due�os.length != 0) {
			
			data = new Object[due�os.length][3];
			
			for (int i=0; i < due�os.length; i++) {
				data [i][0] = new Integer(due�os[i].getCedula());
				data [i][1] = new String(due�os[i].getNombre());
				data [i][2] = new String(due�os[i].getApellido());
			}			
		} else {
			
			data = new Object[1][3];
			data [0][0] = "Vacio";
			data [0][1] = "Vacio";
			data [0][2] = "Vacio";
		}
		
		return data;
	}

}

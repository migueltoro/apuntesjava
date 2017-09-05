package us.lsi.beans;

public class TestVetoablePunto {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		VetoablePunto p1 = BeansPuntos.createVetoable(2., 200.);
		VetoablePunto p2 = BeansPuntos.createVetoable(11., 10.);
		ConsolaConVeto c1 = Consolas.createConsolaConVeto();
		ConsolaConVeto c2 = Consolas.createConsolaConVeto();
		
		p1.addVetoableChangeListener("x",c1);
		p1.addVetoableChangeListener("distanciaAlOrigen",c2);
		
		p1.setX(-3.0);
		p1.setY(2.);
		p2.setY(4.0);

		System.out.println(p1+","+p2);
	}

}

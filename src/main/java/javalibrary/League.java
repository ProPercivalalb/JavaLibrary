package javalibrary;

import java.util.ArrayList;
import java.util.List;

public class League {

	public static void main(String[] args) {
		String a = " Simon	A Grumpy Pig	D4	MID	JUN\r\n" + 
				"Olly	TDAngel	G1	TOP	SUP\r\n" + 
				"Tom	Schoflake	P2	TOP	MID\r\n" + 
				"Sammus	AstralInfusion	P5	SUP	MID\r\n" + 
				"Jakub	Kalofinx	G3	TOP	JUN\r\n" + 
				"Dan Bridges	DanBridges	G2	MID	JUN\r\n" + 
				"Tim	Tim890614	P4	JUN	SUP\r\n" + 
				"Felix	Dard Hong	P1	TOP	MID\r\n" + 
				"Yang	Acc killer	D5 MID	TOP\r\n" + 
				"Martin	subocaj	D5 JUN	MID\r\n" + 
				"Ehan	Bluebell1	D3	MID	SUP\r\n" + 
				"Tom	Send	D2	JUN	TOP\r\n" + 
				"Ben	Noodiy	P1	JUN	SUP\r\n" + 
				"Alex Barter	percivalalb	D4	SUP	MID\r\n" + 
				"Tom	Mogadon	D4	TOP	ADC\r\n" + 
				"George	gheslop	D4 MID	SUP\r\n" + 
				"Kallon	not kerberos	silver 1 (high elo)	TOP	ADC\r\n" + 
				"Jack	SuShei	P5	SUP	JUN\r\n" + 
				"David	eXoTrinity	G2	JUN	ADC\r\n" + 
				"Ellis	Erisu	D3	MID	TOP";
		String[] split = a.split("\\r");
		List<Player> players = new ArrayList<Player>();
		
		for(String s : split) {
			players.add(new Player(s));
		}
	}

}

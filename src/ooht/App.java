/**
 * 
 */
package ooht;

/**
 * @author Esa
 *
 */
public class App {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//test_Blackboard();
		UI userinterface = new UI();		
			
	}
	
	@SuppressWarnings("unused")
	private static void test_MySocket(String [] args){
		// Socket test
		try{
			//if((1 == args.length) && args[0].equals("server")) {
				MySocket s = new MySocket();
				s.setPort(args[1]);
				s.start();
			//} else {			
				MySocket s2 = new MySocket();
				s2.setPort(args[1]);
				s2.echo(args[0]);
			//}
		}catch (Exception e){
			e.printStackTrace();		
		}		
	}
	
	@SuppressWarnings("unused")
	private static void test_Blackboard(){
		Blackboard b = Blackboard.getInstance();
		b.write("hello, world!");
		b.write("it's such a nice day!");
		b.writeTo("you suck!", "self");
		b.print();
		b.clear();
		b.write("wiped board");
		b.print();
	
	}

}

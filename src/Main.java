import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
public class Main {
	public static void main(String args[]) {
/**********************************************************************************************/
		so.prefix="LB";
		so.sleep=500;
		final int PORT=9000;
		final String HOST="0.0.0.0";
		ServerSocket loadBalancer=null;
		Sys sys=new Sys();
		sys.start();
		try {
			loadBalancer=new ServerSocket();
			loadBalancer.bind(new InetSocketAddress(HOST,PORT));
			while(true) {
				so.p("listening for incoming connection...");
				Socket socket=loadBalancer.accept();
				sys.addService(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
/**********************************************************************************************/
	}
}
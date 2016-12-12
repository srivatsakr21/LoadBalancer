import java.util.ArrayList;
public class Admin extends Thread {
	private Sys sys;
	private SocketIO socketIO;
	private boolean run;
	public Admin(Sys sys, SocketIO socketIO) {
		this.setName("Admin");
		this.sys=sys;
		this.socketIO=socketIO;
		this.run=true;
	}
	public Return send(Packet packet) {
		return this.socketIO.send(packet);
	}
	public Return send(ArrayList<Packet> packets) {
		return this.socketIO.send(packets);
	}
	public Return receive() {
		return this.socketIO.receive();
	}
	public void terminate() {
		this.sys.removeAdmin();
		so.p(" Admin disconnected");
		this.run=false;
	}
	@Override
	public void run() {
		so.p("Admin connected");
		while(this.run) {
/****************************************************/

			ArrayList<Packet> packets=this.sys.getPackets();
			Return r=this.send(packets);
			if(r.type==Type.False)
				this.terminate();
			r=this.receive();
			if(r.type==Type.False)
				this.terminate();
			else if(r.packet.type==Type.Ready) {
				Sys.run=true;
			} else if(r.packet.type==Type.Wait) {
				Sys.run=false;
			}
/****************************************************/
		}
	}
}
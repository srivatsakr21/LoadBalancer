import java.util.ArrayList;
import java.util.Collections;
import java.net.Socket;
public class Sys extends Thread{
	public static boolean run;
	private Admin admin;
	static {
		run=true;
	}
	public static void terminate() { Sys.run=false; }
	private ArrayList<Service> services;
	private boolean search(String id) {
		boolean found=false;
		for(Service service:this.services)
			if(service.getName().equals(id)) {
				found=true;
				break;
			}
		return found;
	}
	public void addService(Socket socket) {
		SocketIO socketIO=new SocketIO(socket);
		Return r=socketIO.receive();
		if(r.type==Type.False) return;
		if(r.packet.type==Type.Admin&&this.admin==null) {
			this.admin=new Admin(this,socketIO);
			this.admin.start();
			return;
		}
		synchronized(this.services) {
			Service service=new Service(this,socketIO);
			if(this.search(r.packet.id)) {
				service.terminate();
				return;
			}
			service.setName(r.packet.id);
			so.p(r.packet.type+" [ "+service.getName()+" ] connected");
			service.start();
			this.services.add(service);
		}
	}
	public void removeAdmin() {
		this.admin=null;
	}
	public void removeService(Service service) {
		synchronized(this.services) {
			this.services.remove(service);
		}
	}
	public Sys() { this.services=new ArrayList<>(); }
	public void terminateAll() {
		if(this.services.size()>0) synchronized(this.services) {
			for(int i=0;i<this.services.size();++i)
				this.services.get(i).terminate();
		}
	}
	public ArrayList<Packet> getPackets() {
		ArrayList<Packet> packets=new ArrayList<>();
		synchronized(this.services) {
			for(int i=0;i<this.services.size();++i)
				packets.add(this.services.get(i).getPacket());
		}
		return packets;
	}
	@Override
	public void run() { while(true) {
		synchronized(this.services) {
			if(this.services.size()==0) continue;
			Collections.sort(this.services);
			Service service=this.services.get(0);
			service.setLoad(false);
			Packet packet=service.getPacket();
			Packet p=new Packet(so.prefix,Type.None);
			if(Sys.run)
				p.type=Type.Ready;
			else
				p.type=Type.Wait;
			if(packet.cpu<90&&packet.memory<90) {
				p.dataSet=Utility.getDataSet();
				service.setLoad(true);
				so.p("load on => "+packet);
			} else {
				so.p(packet);
			}
			if(service.send(p).type==Type.False) this.removeService(service);
			for(int i=1;i<this.services.size();++i) {
/********************************************************/
				service=this.services.get(i);
				service.setLoad(false);
				packet=service.getPacket();
				p=new Packet(so.prefix,Type.None);
				if(Sys.run)
					p.type=Type.Ready;
				else
					p.type=Type.Wait;
				so.p(packet);
				if(service.send(p).type==Type.False) this.removeService(service);
/********************************************************/
			}
		}
		try { Thread.sleep(so.sleep); } catch(InterruptedException iex) {}
	}}
}
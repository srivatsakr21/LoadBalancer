public class Service extends Thread implements Comparable<Service>{
	private Sys sys;
	private SocketIO socketIO;
	private boolean run;
	private Packet packet;
	private boolean load;
	public void setLoad(boolean load) {
		this.load=load;
	}
	public Service(Sys sys, SocketIO socketIO) {
		this.setName("");
		this.sys=sys;
		this.socketIO=socketIO;
		this.run=true;
		this.packet=new Packet(so.prefix,0,0,0,Type.None);
	}
	public Return send(Packet packet) {
		return this.socketIO.send(packet);
	}
	public Return receive() {
		return this.socketIO.receive();
	}
	public void terminate() {
		so.p("Server [ "+this.getName()+" ] disconnected");
		this.sys.removeService(this);
		this.run=false;
		this.socketIO.terminate();
	}
	public Packet getPacket() {
		Packet packet;
		synchronized(this.packet) {
			packet=Packet.copy(this.packet);
			packet.load=this.load;
		}
		return packet;
	}
	@Override
	public void run() {
		while(this.run) {
/****************************************************/
			Return r=this.receive();
			if(r.type==Type.False)
				this.terminate();
			synchronized(this.packet) {
				this.packet=r.packet;
			}
/****************************************************/
			try { Thread.sleep(so.sleep); } catch(InterruptedException iex) {}
		}
	}
	@Override
	public int compareTo(Service service) {
		if(this.getPacket().cpu>service.getPacket().cpu)
			return 1;
		else if(this.getPacket().cpu<service.getPacket().cpu)
			return -1;
		else
			return 0;
	}
}
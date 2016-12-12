import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
public class SocketIO {
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	public SocketIO(Socket socket) {
		this.socket=socket;
		try {
			this.out=new ObjectOutputStream(this.socket.getOutputStream());
			this.in=new ObjectInputStream(this.socket.getInputStream());
		} catch(IOException ioex) {}
	}
	public Return send(Packet packet) {
		Return r=new Return(Type.False);
		try {
			this.out.writeObject(packet);
			this.out.flush();
			r.packet=packet;
			r.type=Type.True;
		} catch(IOException ioex) {
		} finally {
			return r;
		}
	}
	public Return send(ArrayList<Packet> packets) {
		Return r=new Return(Type.False);
		try {
			this.out.writeObject(packets);
			this.out.flush();
			r.type=Type.True;
		} catch(IOException ioex) {
		} finally {
			return r;
		}
	}
	public Return receive() {
		Return r=new Return(Type.False);
		try {
			r.packet=(Packet)this.in.readObject();
			r.type=Type.True;
		} catch(IOException ioex) {
		} finally {
			return r;
		}
	}
	public ArrayList<Packet> receivePackets() {
		ArrayList<Packet>packets=new ArrayList<>();
		try {
			packets=(ArrayList<Packet>)this.in.readObject();
		} catch(IOException ioex) {
		} finally {
			return packets;
		}
	}
	public void terminate() {
		try { this.socket.close(); } catch(IOException ioex) {}
	}
}
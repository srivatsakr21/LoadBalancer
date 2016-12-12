public class Packet implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public String id;
	public float cpu;
	public float memory;
	public int jobs;
	public Type type;
	public String dataSet;
	public boolean load;
	private Packet(){}
	public Packet(String id, Type type) {
		this.id=id;
		this.cpu=0;
		this.memory=0;
		this.jobs=0;
		this.type=type;
		this.dataSet="";
	}
	public Packet(String id, float cpu, float memory, int jobs, Type type) {
		this.id=id;
		this.cpu=cpu;
		this.memory=memory;
		this.jobs=jobs;
		this.type=type;
		this.dataSet="";
	}
	@Override
	public String toString() {
		return this.id+", "+
					this.cpu+", "+
					this.memory+", "+
					this.jobs+", "+
					this.type.name()+","+
					this.load;
	}
	public static Packet copy(Packet packet) {
		Packet copy=new Packet();
		copy.id=packet.id;
		copy.cpu=packet.cpu;
		copy.memory=packet.memory;
		copy.jobs=packet.jobs;
		copy.type=packet.type;
		copy.dataSet=packet.dataSet;
		return copy;
	}
}
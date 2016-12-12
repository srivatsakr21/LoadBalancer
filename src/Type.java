public enum Type implements java.io.Serializable {
	None,
	True,
	False,
	Ready,
	Wait,
	LoadBalancer,
	Server,
	Admin;
	public static Type parase(String message) {
		switch(message) {
			case "None": return None;
			case "True": return True;
			case "False": return False;
			case "Ready": return Ready;
			case "Wait": return Wait;
			case "LoadBalancer": return LoadBalancer;
			case "Server": return Server;
			default: return Admin;
		}
	}
}
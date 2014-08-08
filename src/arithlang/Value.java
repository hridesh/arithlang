package arithlang;

public interface Value {
	public String toString();
	static class Int implements Value {
		private int _val;
	    public Int(int v) { _val = v; } 
	    public int v() { return _val; }
	    public String toString() { return "" + _val; }
	}
	static class Bool implements Value {
		private boolean _val;
	    public Bool(boolean v) { _val = v; } 
	    public boolean v() { return _val; }
	    public String toString() { return "" + _val; }
	}
	static class Unit implements Value {
		public static final Unit v = new Unit();
	    public String toString() { return "unit"; }
	}
	static class DynamicError implements Value { 
		private String message = "Unknown dynamic error.";
		public DynamicError() { }
		public DynamicError(String message) { this.message = message; }
	    public String toString() { return "" + message; }
	}
}

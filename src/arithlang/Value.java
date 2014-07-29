package arithlang;

public interface Value {
	public String tostring();
	static class Int implements Value {
	    int _val;
	    public Int(int v) { _val = v; } 
	    public int v() { return _val; }
	    public String tostring() { return "" + _val; }
	}
	static class Bool implements Value {
	    boolean _val;
	    public Bool(boolean v) { _val = v; } 
	    public boolean v() { return _val; }
	    public String tostring() { return "" + _val; }
	}
	static class Unit implements Value { 
	    public String tostring() { return "unit"; }
	}
	static class DynamicError implements Value { 
		String message = "Unknown dynamic error.";
		public DynamicError() { }
		public DynamicError(String message) { this.message = message; }
	    public String tostring() { return "" + message; }
	}
}

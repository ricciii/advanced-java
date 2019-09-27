public class PairValue {
	
	private String key;
	private String value;
	private String code;

	public PairValue(String key, String value) {
		this.key = key;
		this.value = value;
		this.code = key+value;
	}

	public PairValue() {
	}

	public void setKey(String key) {
		this.key = key;
		this.code=null;
		this.code=this.key+this.value;
	}

	public void setValue(String value) {
		this.value = value;
		this.code=null;
		this.code=this.key+this.value;
	}

	public String getKey() {
		return this.key;
	}

	public String getValue() {
		return this.value;
	}

	public String getCode() {
		return this.code;
	}
}
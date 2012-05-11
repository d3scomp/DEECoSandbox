package d3s.playground.vos;

import java.io.Serializable;
import java.util.List;

public class CrossingFields implements Serializable {
	private List<Field> fields;
	
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Boolean isInFields(Field field) {
		for (Field f: fields) {
			if (f.getId().equals(field.getId()))
				return true;
		}
		return false;
	}
	
	public int getFieldIndex(Field field) {
		for (int i = 0; i < fields.size(); i++) {
			if (field.getId().equals(fields.get(i).getId()))
				return i;
		}
		return fields.indexOf(field);
	}
}

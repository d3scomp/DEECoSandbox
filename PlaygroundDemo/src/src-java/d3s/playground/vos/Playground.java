package d3s.playground.vos;

import java.io.Serializable;
import java.util.List;


public class Playground implements Serializable{
	
	private List<Field> playground;
	private List<Field> crossings;
	private List<Field> disabledFields;
	private List<Field> enabledFields;
	private Integer columnCount;
	private Integer rowCount;
	private Integer fieldSize;
	
	//---------------Getters and Setters----------------------

	public Integer getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public Integer getFieldSize() {
		return fieldSize;
	}
	public void setFieldSize(Integer fieldSize) {
		this.fieldSize = fieldSize;
	}
	public List<Field> getPlayground() {
		return playground;
	}
	public void setPlayground(List<Field> playground) {
		this.playground = playground;
	}
	public List<Field> getCrossings() {
		return crossings;
	}
	public void setCrossings(List<Field> crossings) {
		this.crossings = crossings;
	}
	public List<Field> getDisabledFields() {
		return disabledFields;
	}
	public void setDisabledFields(List<Field> disabledFields) {
		this.disabledFields = disabledFields;
	}
	public List<Field> getEnabledFields() {
		return enabledFields;
	}
	public void setEnabledFields(List<Field> enabledFields) {
		this.enabledFields = enabledFields;
	}

}

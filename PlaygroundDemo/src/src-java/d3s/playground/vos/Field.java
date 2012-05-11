package d3s.playground.vos;

import java.io.Serializable;

public class Field implements Serializable {

	private Integer id;

	private Integer x;
	private Integer y;

	private Boolean traversable;
	private Integer color;

	private Field parentField;

	private Double f;
	private Double g;
	private Double h;

	public Integer getId() {
		return id;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null) {
			if (this.getClass().isInstance(o)) {
				Field fo = (Field) o;
				return (fo.getId() == null && this.id == null)
						|| (fo.getId() != null && this.id != null && this.id
								.equals(fo.getId()));
			} else
				return false;
		} else
			return false;
	}

	public Boolean getTraversable() {
		return traversable;
	}

	public void setTraversable(Boolean traversable) {
		this.traversable = traversable;
	}

	public Field getParentField() {
		return parentField;
	}

	public void setParentField(Field parentField) {
		this.parentField = parentField;
	}

	public Double getF() {
		return f;
	}

	public void setF(Double f) {
		this.f = f;
	}

	public Double getG() {
		return g;
	}

	public void setG(Double g) {
		this.g = g;
	}

	public Double getH() {
		return h;
	}

	public void setH(Double h) {
		this.h = h;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id.toString();
	}

	@Override
	public int hashCode() {
		return id;
	}
}

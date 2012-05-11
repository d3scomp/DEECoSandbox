package d3s.knowledges;

import java.io.Serializable;
import java.util.Date;

import d3s.knowledgeHandlers.KnowledgeHandler;

/**
 * @author Michael
 *
 */
public class Knowledge implements Serializable {

	protected Integer id;
	protected KnowledgeHandler knowledgeHandler;
	protected Date lastUpdate;

	public Knowledge() {
		this.id = null;
	}
	
	public Knowledge(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer value) {
		id = value;
	}
	
	public void processKnowledge() {
		if (knowledgeHandler != null)
			knowledgeHandler.handle();
	}
	
	public Knowledge getSharedKnowledge(Knowledge forWhom) {
		return null;
	}
	
	public boolean equals(Object o) {
		if (o != null) {
		if (this.getClass().isInstance(o)) {
			Knowledge ko = (Knowledge) o;
			return (ko.getId() == null && this.id == null) || (ko.getId() != null && this.id != null && this.id.equals(ko.getId()));
		} else
			return false;
		} else 
			return false;
	}

	public void setKnowledgeHandler(KnowledgeHandler knowledgeHandler) {
		this.knowledgeHandler = knowledgeHandler;
	}

	public KnowledgeHandler getKnowledgeHandler() {
		return knowledgeHandler;
	}
	
	public String toString() {
		return id.toString();
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
}

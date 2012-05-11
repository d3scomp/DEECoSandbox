package d3s.knowledgeHandlers;

import java.io.Serializable;

import d3s.components.Component;
import d3s.knowledges.Knowledge;

public abstract class KnowledgeHandler implements Serializable {
	
	private Component component;
	
	public KnowledgeHandler() {}
	
	public KnowledgeHandler(Component component) {
		this.component = component;
	}
	
	public void handle() {
		if (component != null)
			component.processKnowledge(this);
	}
	
	public abstract void run(Knowledge knowledge);
	
}

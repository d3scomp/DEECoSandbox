package d3s.playground.runtime;

import java.util.LinkedList;
import java.util.List;

import com.zink.fly.Fly;
import com.zink.fly.NotifyHandler;
import com.zink.fly.kit.FlyFactory;

import d3s.knowledges.Knowledge;
import d3s.playground.support.TemplateHelper;

public class GlobalKnowledgeBase {

	private static final Long ONE_HOUR_MILLIS = 3600000L;

	private Fly fly;
	private DEECoRuntime runtime;
	private LinkedList<Knowledge> templates;

	public GlobalKnowledgeBase(DEECoRuntime runtime) {
		this.runtime = runtime;
		
		templates = new LinkedList<Knowledge>();

		fly = FlyFactory.makeFly();
	}

	public synchronized void updateKnowledge(Knowledge knowledge) {
		Knowledge emptyTemplate = TemplateHelper.getNewInstance(knowledge);
		Knowledge template = TemplateHelper.getNewInstance(knowledge);
		template.setId(knowledge.getId());
		if (template != null && emptyTemplate != null) {
			template.setId(knowledge.getId());
			updateTemplatesAndHandlersIfNecessary(emptyTemplate);
			System.out.println("taken");
			fly.take(template, 50L);
			fly.write(knowledge, ONE_HOUR_MILLIS);
		}
	}

	public void removeKnowledge(Knowledge knowledge) {
		Knowledge template = TemplateHelper.getNewInstance(knowledge);
		if (template != null) {
			templates.remove(template);
			template.setId(knowledge.getId());
			fly.take(template, 50L);
		}
	}
	
	private void updateTemplatesAndHandlersIfNecessary(Knowledge template) {
		if (templates.contains(template)) 
			return;
		templates.add(template);
	}
	
	public synchronized List<Knowledge> getAllData() {
		return getAllDataFromTupleSpace();
	}
	
	private synchronized List<Knowledge> getAllDataFromTupleSpace() {
		List<Knowledge> result = new LinkedList<Knowledge>();
		for (Knowledge t: templates) {
			result.addAll(fly.readMany(t,
					50L));
		}
		return result;
	}

}

package d3s.playground.knowledgeHandlers;

import d3s.knowledgeHandlers.KnowledgeHandler;
import d3s.knowledges.Knowledge;
import d3s.playground.knowledges.PlaygroundKnowledge;

public class PlaygroundKnowledgeHandler extends KnowledgeHandler {

	@Override
	public void run(Knowledge knowledge) {//step function
		if (!(knowledge != null && knowledge instanceof PlaygroundKnowledge))
			return;
	}

}

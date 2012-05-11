package d3s.playground.knowledgeHandlers;

import java.util.Date;

import d3s.knowledgeHandlers.KnowledgeHandler;
import d3s.knowledges.Knowledge;
import d3s.playground.knowledges.RobotKnowledge;

/**
 * 
 * @author Michael
 * 
 *         This class delivers interpretation of the coordinator's shared
 *         knowledge for a member robot
 */
public class RobotRobotKnowledgeHandler extends KnowledgeHandler {

	private static final int KNOWLEDGE_VALIDITY_THRESHOLD = 5000;

	@Override
	public void run(Knowledge knowledge) {
		if (!(knowledge != null && knowledge instanceof RobotKnowledge))
			return;
		RobotKnowledge self = (RobotKnowledge) knowledge;
		RobotKnowledge leader = self.getConvoyRobot();
		boolean isConvoyValid = (new Date()).getTime()
				- leader.getLastUpdate().getTime() < KNOWLEDGE_VALIDITY_THRESHOLD;
		if (isConvoyValid)
			self.stop();
		else {
			self.setConvoyRobot(null);
		}
	}
}

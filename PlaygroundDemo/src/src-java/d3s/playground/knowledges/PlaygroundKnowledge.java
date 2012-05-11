package d3s.playground.knowledges;

import java.util.ArrayList;
import java.util.List;

import d3s.knowledges.Knowledge;
import d3s.playground.knowledgeHandlers.PlaygroundKnowledgeHandler;
import d3s.playground.support.PlaygroundKnowledgeHelper;
import d3s.playground.vos.Playground;

public class PlaygroundKnowledge extends Knowledge {

	private Playground playground;
	private List<CrossingKnowledge> crossings;
	private List<RobotKnowledge> robots;
	
	public PlaygroundKnowledge() {
		super();
		crossings = new ArrayList<CrossingKnowledge>();
		robots = new ArrayList<RobotKnowledge>();
	}
	
	public PlaygroundKnowledge(Integer id, Playground playground) {
		super(id);
		this.playground = playground;
	}

	@Override
	public Knowledge getSharedKnowledge(Knowledge forWhom) {
		if (forWhom == this) {
			PlaygroundKnowledge result = new PlaygroundKnowledge();
			PlaygroundKnowledgeHelper.copyPublicData(this, result);
			PlaygroundKnowledgeHelper.copyPrivateData(this, result);
			return result;
		}
		return null;
	}

	public void addRobot(RobotKnowledge robotKnowledge) {
		robots.add(robotKnowledge);
	}
	
	public void addCrossing(CrossingKnowledge crossingKnowledge) {
		crossings.add(crossingKnowledge);
	}
	
	public void removeAllRobots() {
		robots = new ArrayList<RobotKnowledge>();
	}

	public Playground getPlayground() {
		return playground;
	}

	public void setPlayground(Playground playground) {
		this.playground = playground;
	}

	public List<CrossingKnowledge> getCrossings() {
		return crossings;
	}

	public void setCrossings(List<CrossingKnowledge> crossings) {
		this.crossings = crossings;
	}

	public List<RobotKnowledge> getRobots() {
		return robots;
	}

	public void setRobots(List<RobotKnowledge> robots) {
		this.robots = robots;
	}
}

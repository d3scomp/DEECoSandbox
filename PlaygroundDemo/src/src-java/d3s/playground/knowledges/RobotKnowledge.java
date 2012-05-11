package d3s.playground.knowledges;

import d3s.knowledges.Knowledge;
import d3s.playground.knowledgeHandlers.RobotKnowledgeHandler;
import d3s.playground.knowledgeHandlers.RobotRobotKnowledgeHandler;
import d3s.playground.vos.Field;
import d3s.playground.vos.Path;

public class RobotKnowledge extends Knowledge {
	
	private Path path;
	
	private CrossingKnowledge crossing;
	private RobotKnowledge convoyRobot;
	private PlaygroundKnowledge playground;
	private Integer speed;
	private Integer maximumSpeed;
	private Integer patienceThreshold;
	
	public RobotKnowledge() {
		super();
	}
	
	public RobotKnowledge(Integer id) {
		super(id);
		knowledgeHandler = new RobotKnowledgeHandler();
	}

	@Override
	public Knowledge getSharedKnowledge(Knowledge forWhom) {
		RobotKnowledge result = new RobotKnowledge(this.id);
		result.setSpeed(this.speed);
		result.setPath(this.path);
		if (forWhom instanceof RobotKnowledge) {
			if (forWhom == this) {
				result.setMaximumSpeed(maximumSpeed);
				result.setCrossing(crossing);
				result.setConvoyRobot(convoyRobot);
				result.setPlayground(playground);
				result.setKnowledgeHandler(knowledgeHandler);
			} else {
				result.setKnowledgeHandler(new RobotRobotKnowledgeHandler());
			}
			result.setPatienceThreshold(patienceThreshold);
		} else if (forWhom instanceof CrossingKnowledge) {
			
		} else if (forWhom instanceof PlaygroundKnowledge) {
			
		}
		return result;
	}
	
	public void stop() {
		speed = 0;
	}
	
	public void move() {
		//speed = maximumSpeed;
		path.advance();
	}
	
	public Field getCurrentPosition() {
		return path.getCurrentPosition();
	}
	
	public Field getNextPosition() {
		if (path != null)
			return path.getNextPosition();
		return
			null;
	}
	
	//----------------------Getters and Setters---------------------

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public CrossingKnowledge getCrossing() {
		return crossing;
	}

	public void setCrossing(CrossingKnowledge crossing) {
		this.crossing = crossing;
	}

	public RobotKnowledge getConvoyRobot() {
		return convoyRobot;
	}

	public void setConvoyRobot(RobotKnowledge convoyRobot) {
		this.convoyRobot = convoyRobot;
	}

	public PlaygroundKnowledge getPlayground() {
		return playground;
	}

	public void setPlayground(PlaygroundKnowledge playground) {
		this.playground = playground;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public void setMaximumSpeed(Integer maximumSpeed) {
		this.maximumSpeed = maximumSpeed;
	}

	public Integer getPatienceThreshold() {
		return patienceThreshold;
	}

	public void setPatienceThreshold(Integer patienceThreshold) {
		this.patienceThreshold = patienceThreshold;
	}
}

package d3s.playground.vos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import d3s.playground.knowledges.RobotKnowledge;
import flex.messaging.io.ArrayCollection;

public class Occupancy implements Serializable{

	private Integer robotId;
	private Date since;
	private Integer patienceThreshold;
	private List<Field> path;//partial (crossing) path of the robot	
	
	public Occupancy(RobotKnowledge robot, List<Field> robotPartialPath) {
		robotId = robot.getId();
		since = new Date();
		patienceThreshold = robot.getPatienceThreshold();
		path = robotPartialPath;
	}


	public Boolean hasReachedPatienceThreshold() {
		return getUnpatientTime() > patienceThreshold;
	}
	
	public Long getUnpatientTime() {
		return new Date().getTime() - since.getTime() - patienceThreshold;
	}
	
	public boolean equals(Object o) {
		return o != null && o instanceof Occupancy && ((Occupancy) o).getRobotId().equals(robotId);
	}
	
	//----------------Getters and Setters---------------------------------
	
	public Integer getRobotId() {
		return robotId;
	}
	public void setRobotId(Integer robotId) {
		this.robotId = robotId;
	}
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}
	public Integer getPatienceThreshold() {
		return patienceThreshold;
	}
	public void setPatienceThreshold(Integer patienceThreshold) {
		this.patienceThreshold = patienceThreshold;
	}


	public List<Field> getPath() {
		return path;
	}


	public void setPath(List<Field> path) {
		this.path = path;
	}
	

}

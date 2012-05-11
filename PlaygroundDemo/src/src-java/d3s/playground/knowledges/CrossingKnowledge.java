package d3s.playground.knowledges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import d3s.knowledges.Knowledge;
import d3s.playground.knowledgeHandlers.CrossingRobotKnowledgeHandler;
import d3s.playground.support.CrossingKnowledgeHelper;
import d3s.playground.support.KnowledgeCollectionHelper;
import d3s.playground.vos.CrossingFields;
import d3s.playground.vos.CrossingFieldsOccupancy;
import d3s.playground.vos.Field;
import d3s.playground.vos.Occupancy;

public class CrossingKnowledge extends Knowledge {

	private CrossingFields blockedFields;

	private CrossingFields observedFields;
	private CrossingFields entries;
	private CrossingFields exits;

	private CrossingFieldsOccupancy observedFieldsOccupancy;
	private CrossingFieldsOccupancy entriesOccupancy;
	private CrossingFieldsOccupancy exitsOccupancy;

	private List<RobotKnowledge> robots;

	public CrossingKnowledge() {
		super();
	}

	public CrossingKnowledge(Integer id) {
		super(id);
	}

	@Override
	public Knowledge getSharedKnowledge(Knowledge forWhom) {
		CrossingKnowledge result = new CrossingKnowledge(this.id);
		CrossingKnowledgeHelper.copyPublicData(this, result);
		if (forWhom instanceof RobotKnowledge) {
			result.setKnowledgeHandler(new CrossingRobotKnowledgeHandler());
		} else if (forWhom == this) {
			CrossingKnowledgeHelper.copyPrivateData(this, result);
		}
		return result;
	}

	public void addRobot(RobotKnowledge robotKnowledge) {
		KnowledgeCollectionHelper.addKnowledge(robotKnowledge, robots);
	}

	public void removeRobot(RobotKnowledge robotKnowledge) {
		KnowledgeCollectionHelper.removeKnowledge(robotKnowledge, robots);
	}
	
	public void removeAllRobots() {
		robots = new ArrayList<RobotKnowledge>();
	}

	public Boolean isOnCrossing(Field position) {
		return isOnEntries(position) || isOnObserved(position)
				|| isOnExits(position);
	}

	public Boolean isOnEntries(Field position) {
		return entries.isInFields(position);
	}

	public Boolean isOnExits(Field position) {
		return exits.isInFields(position);
	}

	public Boolean isOnObserved(Field position) {
		return observedFields.isInFields(position);
	}

	public Occupancy getRobotOccupancy(Integer robotId,
			CrossingFieldsOccupancy where) {
		return where.getOccupancyByRobotId(robotId);
	}

	// --------------------Getters and Setters----------------------------

	public CrossingFields getObservedFields() {
		return observedFields;
	}

	public void setObservedFields(CrossingFields observedFields) {
		this.observedFields = observedFields;
	}

	public CrossingFields getEntries() {
		return entries;
	}

	public void setEntries(CrossingFields entries) {
		this.entries = entries;
	}

	public CrossingFields getExits() {
		return exits;
	}

	public void setExits(CrossingFields exits) {
		this.exits = exits;
	}

	public CrossingFieldsOccupancy getObservedFieldsOccupancy() {
		return observedFieldsOccupancy;
	}

	public void setObservedFieldsOccupancy(
			CrossingFieldsOccupancy observedFieldsOccupancy) {
		this.observedFieldsOccupancy = observedFieldsOccupancy;
	}

	public CrossingFieldsOccupancy getEntriesOccupancy() {
		return entriesOccupancy;
	}

	public void setEntriesOccupancy(CrossingFieldsOccupancy entriesOccupancy) {
		this.entriesOccupancy = entriesOccupancy;
	}

	public CrossingFieldsOccupancy getExitsOccupancy() {
		return exitsOccupancy;
	}

	public void setExitsOccupancy(CrossingFieldsOccupancy exitsOccupancy) {
		this.exitsOccupancy = exitsOccupancy;
	}

	public CrossingFields getBlockedFields() {
		return blockedFields;
	}

	public void setBlockedFields(CrossingFields blockedFields) {
		this.blockedFields = blockedFields;
	}

	public List<RobotKnowledge> getRobots() {
		return robots;
	}

	public void setRobots(List<RobotKnowledge> value) {
		this.robots = value;
	}
	
	
}

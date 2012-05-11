package d3s.playground.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import d3s.components.Component;
import d3s.knowledges.Knowledge;
import d3s.playground.ensembles.ConvoyEnsemble;
import d3s.playground.ensembles.CrossingPlaygroundEnsemble;
import d3s.playground.ensembles.RobotCrossingEnsemble;
import d3s.playground.ensembles.RobotPlaygroundEnsemble;
import d3s.playground.knowledgeHandlers.CrossingKnowledgeHandler;
import d3s.playground.knowledgeHandlers.PlaygroundKnowledgeHandler;
import d3s.playground.knowledgeHandlers.RobotKnowledgeHandler;
import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.PlaygroundKnowledge;
import d3s.playground.knowledges.RobotKnowledge;
import d3s.playground.runtime.DEECoRuntime;
import d3s.playground.support.EnumSchedulingType;
import d3s.playground.vos.CrossingFieldsOccupancy;

public class JavaPlayground {
	
	
	private static JavaPlayground instance = null;
	private DEECoRuntime runtime;
	
	public static JavaPlayground getInstance() {
		if (instance == null)
			instance = new JavaPlayground();
		return instance;
	}

	private JavaPlayground() {
		runtime = new DEECoRuntime();
		Component.runtime = runtime;
		setupApplication();
	}
	
	public List<Knowledge> getKnowledges() {
		return runtime.getKnowledges();
	}
	
	public void start() {
		runtime.startComponents();
	}
	
	public void stop() {
		runtime.stopComponents();
	}
	
	public void clear() {
		runtime.clearComponents();
	}
	
	public Component addRobotComponent(RobotKnowledge rk) {
		rk.setKnowledgeHandler(new RobotKnowledgeHandler());
		return addComponent(rk);
	}
	
	public Component addCrossingComponent(CrossingKnowledge ck) {
		ck.setEntriesOccupancy(new CrossingFieldsOccupancy());
		ck.setObservedFieldsOccupancy(new CrossingFieldsOccupancy());
		ck.setExitsOccupancy(new CrossingFieldsOccupancy());
		ck.setKnowledgeHandler(new CrossingKnowledgeHandler());
		ck.setRobots(Collections.synchronizedList(new ArrayList<RobotKnowledge>()));
		return addComponent(ck);
	}
	
	public Component addPlaygroundComponent(PlaygroundKnowledge pk) {
		pk.setKnowledgeHandler(new PlaygroundKnowledgeHandler());
		pk.setRobots(new ArrayList<RobotKnowledge>());
		return addComponent(pk);
	}
	
	public Component removeComponent(Integer id) {
		return runtime.unregisterComponent(id);
	}
	
	private Component addComponent(Knowledge knowledge) {
		Component result = new Component(knowledge, EnumSchedulingType.PERIODIC);
		return runtime.registerComponent(result);
	}
	
	private void setupApplication() {
		runtime.registerEnsemble(new RobotCrossingEnsemble());
		runtime.registerEnsemble(new RobotPlaygroundEnsemble());
		runtime.registerEnsemble(new ConvoyEnsemble());
		runtime.registerEnsemble(new CrossingPlaygroundEnsemble());
	}
}

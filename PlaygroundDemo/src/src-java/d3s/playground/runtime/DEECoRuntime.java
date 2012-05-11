package d3s.playground.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import d3s.components.Component;
import d3s.ensembles.Ensemble;
import d3s.knowledges.Knowledge;
import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.PlaygroundKnowledge;
import d3s.playground.knowledges.RobotKnowledge;
import d3s.playground.support.KnowledgeCollectionHelper;

public class DEECoRuntime {
	
	private Ensembler ensembler;
	private GlobalKnowledgeBase knowledgeManager;
	private List<Component> components;
	private Thread knowledgeUpdaterThread;
	private boolean stopped = true;
	
	public DEECoRuntime() {
		ensembler = new Ensembler();
		knowledgeManager = new GlobalKnowledgeBase(this);
		components = Collections.synchronizedList(new ArrayList<Component>());
	}

	public Component registerComponent(Component component) {
		components.add(component);
		return component;
	}
	
	public Component unregisterComponent(Integer id) {
		Component c;
		Knowledge k;
		for (int i = 0; i < components.size(); i++) {
			c = components.get(i);
			k = c.getKnowledge();
			if (k.getId() == id) {
				components.remove(i);
				knowledgeManager.removeKnowledge(k);
				return c;
			}
		}
		return null;
	}
	
	public Ensemble registerEnsemble(Ensemble ensemble) {
		ensembler.addEnsembleDefinition(ensemble);
		return ensemble;
	}
	
	public List<Knowledge> getKnowledges() {
		return knowledgeManager.getAllData();
	}
	
	public synchronized void localKnowledgeChanged(Knowledge knowledge) {
		knowledgeManager.updateKnowledge(knowledge);
	}
	
	private void updateComponentsKnowledges(List<Knowledge> knowledges) {
		Knowledge k;
		for (Component c: components) {
			k = c.getKnowledge();
			System.out.println(knowledges.toString() + k.getId());
			k = KnowledgeCollectionHelper.findKnowledgeById(k.getId(), knowledges);
			if (k != null)
				c.setKnowledge(k);
			else
				System.out.println("Error - Something is wrong there is no such knowledge in the tuple space!");
		}
	}
	
	public void stopComponents() {
		stop();
		for (Component c: components) {
			c.stop();
		}
	}
	
	public void startComponents() {
		start();
		for (Component c: components) {
			c.start();
		}
	}
	
	public void clearComponents() {
		ArrayList<Component> toRemove = new ArrayList<Component>();
		for (Component c: components) {
			c.stop();
			if (c.getKnowledge() instanceof RobotKnowledge) {
				knowledgeManager.removeKnowledge(c.getKnowledge());
				toRemove.add(c);
			} else if (c.getKnowledge() instanceof CrossingKnowledge) {
				((CrossingKnowledge) c.getKnowledge()).removeAllRobots();
				knowledgeManager.updateKnowledge(c.getKnowledge());
			} else if (c.getKnowledge() instanceof PlaygroundKnowledge) {
				((PlaygroundKnowledge) c.getKnowledge()).removeAllRobots();
				knowledgeManager.updateKnowledge(c.getKnowledge());
			}
		}
		components.removeAll(toRemove);
	}
	
	private void start() {
		stop();
		stopped = false;
		knowledgeUpdaterThread = new Thread(new KnowledgeUpdater());
		knowledgeUpdaterThread.start();
	}
	
	private void stop() {
		if (knowledgeUpdaterThread != null) {
			stopped = true;
			knowledgeUpdaterThread = null;
		}
	}
	
	private class KnowledgeUpdater implements Runnable {

		@Override
		public void run() {
			try {
				while (!stopped) {
					List<Knowledge> knowledges = knowledgeManager.getAllData();
					ensembler.knowledgesUpdated(knowledges);
					updateComponentsKnowledges(knowledges);
					Thread.sleep(1000L);
				}
			} catch (Exception e) {
				System.out.println("error");
			}
		}
		
	}
	
}

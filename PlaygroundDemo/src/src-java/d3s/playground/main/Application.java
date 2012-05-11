package d3s.playground.main;

import java.io.File;
import java.util.List;

import d3s.knowledges.Knowledge;
import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.PlaygroundKnowledge;
import d3s.playground.knowledges.RobotKnowledge;

public class Application {

	private JavaPlayground jp;
	private Process flyServerProcess;

	public Application() {
		try {
			String path = new File(".").getCanonicalPath() + File.separatorChar
					+ "libs" + File.separatorChar + "FlyServer.exe";
			flyServerProcess = Runtime.getRuntime().exec(path);
			jp = JavaPlayground.getInstance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void addPlaygroundKnowledge(PlaygroundKnowledge pk) {
		jp.addPlaygroundComponent(pk);
	}

	public void addCrossingKnowledge(CrossingKnowledge ck) {
		jp.addCrossingComponent(ck);
	}

	public void addRobotKnowledge(RobotKnowledge rk) {
		jp.addRobotComponent(rk);
	}

	public List<Knowledge> getKnowledges() {
		return jp.getKnowledges();
	}

	public void startSimulation() {
		jp.start();
	}

	public void stopSimulation() {
		jp.stop();
	}

	public void clearSimulation() {
		try {
			jp.stop();
			jp.clear();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void shutdown() {
		jp.stop();
		if (flyServerProcess != null)
			flyServerProcess.destroy();
	}

	public void test(RobotKnowledge rk) {
		System.out.println(rk);
	}
}

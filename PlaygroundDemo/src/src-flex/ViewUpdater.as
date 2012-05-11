package
{
	import d3s.knowledges.Knowledge;
	import d3s.playground.knowledges.CrossingKnowledge;
	import d3s.playground.knowledges.RobotKnowledge;
	
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.collections.ArrayCollection;

	public class ViewUpdater
	{
		public static const REFRESH_INTERVAL:int = 500;
		
		private var _robots:Vector.<RobotKnowledge>;
		private var _crossings:Array;
		private var _seqMC:SequentialNativeMethodCaller;
		private var _timer:Timer;
		
		public function ViewUpdater(robots:Vector.<RobotKnowledge>, crossings:Array, seqMC:SequentialNativeMethodCaller)
		{
			_robots = robots;
			_crossings = crossings;
			_seqMC = seqMC;
			
			_timer = new Timer(REFRESH_INTERVAL);
			_timer.addEventListener(TimerEvent.TIMER, onTimerEvent);
		}
		
		public function startSimulation():void {
			_seqMC.startSimulation();
			startTimer();
		}
		
		public function stopSimulation():void {
			_seqMC.stopSimulation();
			stopTimer();
		}
		
		private function startTimer():void {
			stopTimer();
			if (!_timer.running)
				_timer.start();
		}
		
		private function stopTimer():void {
			if (_timer.running)
				_timer.stop();
		}
		
		public function clearSimulation():void {
			stopTimer();
			_seqMC.clearSimulation();
		}
		
		private function onTimerEvent(te:TimerEvent):void {
			_seqMC.getKnowledges(onKnowledgesUpdate);
		}
		
		private function onKnowledgesUpdate(collection:ArrayCollection):void {
			for each (var k:Knowledge in collection) {
				if (k is CrossingKnowledge)
					updateCrossing(k as CrossingKnowledge);
				else if (k is RobotKnowledge)
					updateRobot(k as RobotKnowledge);
			}
		}
		
		private function updateCrossing(newKnowledge:CrossingKnowledge):void {
			for each (var ck:CrossingKnowledge in _crossings) {
				if (ck.id == newKnowledge.id) {
					ck.copyFrom(newKnowledge);
					break;
				}
			}
		}
		
		private function updateRobot(newKnowledge:RobotKnowledge):void {
			for each (var rk:RobotKnowledge in _robots) {
				if (rk.id == newKnowledge.id) {
					rk.copyFrom(newKnowledge);
					break;
				}
			}
		}
	}
}
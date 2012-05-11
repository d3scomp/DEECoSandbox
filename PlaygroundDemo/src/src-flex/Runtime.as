package
{
	import d3s.knowledges.Knowledge;
	import d3s.playground.knowledges.CrossingKnowledge;
	import d3s.playground.knowledges.PlaygroundKnowledge;
	import d3s.playground.knowledges.RobotKnowledge;
	import d3s.playground.vos.CrossingFields;
	import d3s.playground.vos.Field;
	import d3s.playground.vos.Path;
	import d3s.playground.vos.Playground;
	
	import events.PGEvent;
	
	import flash.events.EventDispatcher;
	import flash.utils.setTimeout;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
	import mx.messaging.events.MessageEvent;
	
	import net.riaspace.flerry.NativeObject;
	
	import spark.components.Application;
	import view.grid.MovePane;

	public class Runtime extends EventDispatcher
	{
		private const _map:Vector.<Vector.<int>>=Vector.<Vector.<int>>([
			new <int>[0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0], 
			new <int>[0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0], 
			new <int>[1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1], 
			new <int>[1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1], 
			new <int>[0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0], 
			new <int>[0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0], 
			new <int>[0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0], 
			new <int>[0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0], 
			new <int>[1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1], 
			new <int>[1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1], 
			new <int>[0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0], 
			new <int>[0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0]]);
		
		private var _seqMethodCaller:SequentialNativeMethodCaller;
		private var _viewUpdater:ViewUpdater;
		private var _playgroundKnowledge:PlaygroundKnowledge;
		private var _robotKnowledges:Vector.<RobotKnowledge>=new Vector.<RobotKnowledge>();
		private var _crossingsKnowledges:Array=[];

		private var _idGenerator:int=1;
		private var _currentRK:RobotKnowledge;
		private var _demoMode:Boolean;

		[Bindable]
		public var cells:ArrayCollection;

		[Bindable]
		public var checkpointSelection:Boolean=false;

		[Bindable]
		public var details:ArrayCollection=new ArrayCollection();
		
		[Bindable]
		public var playing:Boolean = false;

		public function Runtime(nativeObject:NativeObject, demoMode:Boolean = false) {
			_seqMethodCaller = new SequentialNativeMethodCaller(nativeObject);
			_viewUpdater = new ViewUpdater(_robotKnowledges, _crossingsKnowledges, _seqMethodCaller);
			_demoMode = demoMode;
			
		}
		
		public function shutdown():void {
			_seqMethodCaller.shutdown();
		}
		
		private function get id():int
		{
			return _idGenerator++;
		}
		
		public function startClick():void {
			if (checkpointSelection) {
				if (!buildRobotPath(_currentRK)) {
					return;
				}
				var op:Array = _playgroundKnowledge.playground.orientationGenerator(_currentRK.path.path.source);
				_currentRK.orientationPath.path.addAll(new ArrayCollection(op));
				_seqMethodCaller.addRobot(_currentRK);
				endCheckpointSelection();
			} else if (playing){
				stopSimulation();
			} else {
				startSimulation();
			}
		}
		
		private function startSimulation():void {
			_viewUpdater.startSimulation();
			playing = true;
		}
		
		private function stopSimulation():void {
			_viewUpdater.stopSimulation();
			playing = false;
		}
		
		public function clearClick():void {
			_viewUpdater.clearSimulation();
			playing = false;
			endCheckpointSelection();
			_robotKnowledges.splice(0,_robotKnowledges.length);
			updateDetails();
		}
		
		private function buildRobotPath(rk:RobotKnowledge):Boolean {
			var checkpoints:Array = rk.path.path.source.concat();
			if (checkpoints.length > 1) {
				var robotPath:ArrayCollection = new ArrayCollection();
				var origin:Field = checkpoints.shift() as Field;
				var destination:Field;
				var path:Array;
				var p:Playground = _playgroundKnowledge.playground;
				while (checkpoints.length) {
					destination = checkpoints.shift() as Field;
					path = p.pathFinder(origin, destination);
					path.splice(path.length-1,1);
					robotPath.addAll(new ArrayCollection(path));
					origin = destination;
				}
				robotPath.addItem(destination);
				rk.path.path.removeAll();
				rk.path.path.addAll(robotPath);
				return true;
			}
			return false;
		}

		public function doubleClicked(pge:PGEvent):void
		{
			if (checkpointSelection)
			{
				clicked(pge);
			}
			else
			{
				var occupied:Boolean=false;
				for each (var rk:RobotKnowledge in _robotKnowledges)
				{
					if (rk.path.path.length && rk.path.path.getItemAt(0) == pge.field)
					{
						checkpointSelection=true;
						break;
					}
				}
				if (!occupied)
				{
					checkpointSelection=true;
					_currentRK=createRobot(pge.field);
					dispatchEvent(new PGEvent(PGEvent.ROBOTS_ADDED));
				}
			}
		}

		public function clicked(pge:PGEvent):void
		{
			if (checkpointSelection)
			{
				var robotPath:ArrayCollection = _currentRK.path.path;
				var origin:Field = robotPath.getItemAt(robotPath.length-1) as Field;
				if (origin != pge.field)
				{
					var p:Playground = _playgroundKnowledge.playground;
					var path:Array= p.pathFinder(origin, pge.field);
					if (path && path.length)
					{
						for each (var n:Field in path)
						{
							n.color=PlaygroundCellItemRendedrer.PATH_COLOR;
						}
						(path.shift() as Field).color=PlaygroundCellItemRendedrer.CHECKPOINT_COLOR;
						(path.pop() as Field).color=PlaygroundCellItemRendedrer.CHECKPOINT_COLOR;
						robotPath.addItem(pge.field);
					}
					else
					{
						Alert.show("Path does not exists!", "Info");
					}
				}
			}
		}

		public function get robotKnowledges():Vector.<RobotKnowledge>
		{
			return _robotKnowledges;
		}

		private function createRobot(origin:Field):RobotKnowledge
		{
			var rk:RobotKnowledge = new RobotKnowledge();
			rk.id = id;
			rk.path = new Path();
			rk.path.path = new ArrayCollection([origin]);
			rk.orientationPath = new Path();
			rk.orientationPath.path = new ArrayCollection();
			_robotKnowledges.push(rk);
			return rk;
		}
		
		public function initiate():void {			
			createPlayground();
			createCrossings();
			var thisRuntime:Runtime = this;
			setTimeout(function ():void {
				if (_demoMode)
					DemoScenario.buildDemo(thisRuntime);
			}, 1000);
		}
		
		private function createPlayground():void
		{
			_playgroundKnowledge = PlaygroundHelper.producePlaygroundKnowledge(_map, id);
			_seqMethodCaller.addPlayground(_playgroundKnowledge);
			var p:Playground = _playgroundKnowledge.playground;
			
			cells = p.playground;
			MovePane.ROW_COUNT = p.rowCount;
			MovePane.COL_COUNT = p.columnCount;
			MovePane.CELL_DISTANCE = p.fieldSize;
		}

		private function createCrossings():void
		{
			var ck:CrossingKnowledge;
			var p:Playground = _playgroundKnowledge.playground;
			for each (var cf:CrossingFields in p.crossings) {
				ck = new CrossingKnowledge();
				ck.id = id;
				CrossingHelper.setUp(ck, cf, p);
				_crossingsKnowledges.push(ck);
				_playgroundKnowledge.crossings.addItem(ck);
				_seqMethodCaller.addCrossing(ck);
			}
			updateDetails();
		}


		private function updateDetails():void
		{
			details=new ArrayCollection(_crossingsKnowledges);
		}

		private function endCheckpointSelection():void
		{
			checkpointSelection=false;
			_currentRK=null;
			PlaygroundHelper.resetPlaygroundColors(_playgroundKnowledge.playground);
		}
	}
}

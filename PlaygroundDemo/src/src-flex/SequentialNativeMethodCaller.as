package
{
	import d3s.playground.knowledges.CrossingKnowledge;
	import d3s.playground.knowledges.PlaygroundKnowledge;
	import d3s.playground.knowledges.RobotKnowledge;
	
	import flash.utils.Proxy;
	
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.utils.ObjectProxy;
	
	import net.riaspace.flerry.NativeMethod;
	import net.riaspace.flerry.NativeObject;

	public class SequentialNativeMethodCaller
	{
		private var _no:NativeObject;
		private var _calls:Vector.<SequentialMethodCall> = new Vector.<SequentialMethodCall>();
		private var _currentSMC:SequentialMethodCall;
		
		public function SequentialNativeMethodCaller(nativeObject:NativeObject)
		{
			_no = nativeObject;
			_no.addEventListener(ResultEvent.RESULT, onResult);
			_no.addEventListener(FaultEvent.FAULT, onFault);
		}
		
		public function shutdown():void {
			_calls.splice(0, _calls.length);
			_no.shutdown();
		}
		
		public function getKnowledges(resultHandler:Function):void {
			var smc:SequentialMethodCall = new SequentialMethodCall();
			smc.method = function ():void {
				_no.getKnowledges();
			}
			smc.resultHandler = resultHandler;
			addSMC(smc);
		}
		
		public function addCrossing(ck:CrossingKnowledge):void {
			var smc:SequentialMethodCall = new SequentialMethodCall();
			smc.method =  function ():void {
				_no.addCrossingKnowledge(ck);
			};
			addSMC(smc);
		}
		
		public function addRobot(rk:RobotKnowledge):void {
			var smc:SequentialMethodCall = new SequentialMethodCall();
			smc.method =  function ():void {
				_no.addRobotKnowledge(rk);
			};
			addSMC(smc);
		}
		
		public function addPlayground(pk:PlaygroundKnowledge):void {
			var smc:SequentialMethodCall = new SequentialMethodCall();
			smc.method =  function ():void {
				_no.addPlaygroundKnowledge(pk);
			};
			addSMC(smc);
		}
		
		public function startSimulation():void {
			var smc:SequentialMethodCall = new SequentialMethodCall();
			smc.method = function ():void {
				_no.startSimulation();
			}
			addSMC(smc);
		}
		
		public function stopSimulation():void {
			var smc:SequentialMethodCall = new SequentialMethodCall();
			smc.method = function ():void {
				_no.stopSimulation();
			}
			addSMC(smc);
		}
		
		public function clearSimulation():void {
			var smc:SequentialMethodCall = new SequentialMethodCall();
			smc.method = function ():void {
				_no.clearSimulation();
			}
			addSMC(smc);
		}
		
		private function performNextCall():void {
			if (!_currentSMC && _calls.length) {
				_currentSMC = _calls.shift();
				_currentSMC.method();
			}

		}
		
		private function onResult(re:ResultEvent):void {
			if (_currentSMC && _currentSMC.resultHandler != null) {
				_currentSMC.resultHandler(re.result);
			}
			_currentSMC = null;
			performNextCall();
		}
		
		private function onFault(fe:FaultEvent):void {
			trace(fe.message);
		}
		
		private function addSMC(smc:SequentialMethodCall):void {
			_calls.push(smc);
			performNextCall();
		}
	}
}
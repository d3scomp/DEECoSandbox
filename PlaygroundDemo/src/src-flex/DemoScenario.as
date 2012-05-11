package
{
	import events.PGEvent;

	public class DemoScenario
	{
		public static function buildDemo(runtime:Runtime):void
		{
			var pge:PGEvent = new PGEvent(PGEvent.CLICKED);
			pge.field = PlaygroundHelper.getPlaygroundFieldById(40, runtime.cells.source);
			runtime.doubleClicked(pge);
			pge.field = PlaygroundHelper.getPlaygroundFieldById(35, runtime.cells.source);
			runtime.clicked(pge);
			runtime.startClick();
			
			pge.field = PlaygroundHelper.getPlaygroundFieldById(5, runtime.cells.source);
			runtime.doubleClicked(pge);
			pge.field = PlaygroundHelper.getPlaygroundFieldById(85, runtime.cells.source);
			runtime.clicked(pge);
			runtime.startClick();
			
			pge.field = PlaygroundHelper.getPlaygroundFieldById(51, runtime.cells.source);
			runtime.doubleClicked(pge);
			pge.field = PlaygroundHelper.getPlaygroundFieldById(56, runtime.cells.source);
			runtime.clicked(pge);
			runtime.startClick();
			
			pge.field = PlaygroundHelper.getPlaygroundFieldById(86, runtime.cells.source);
			runtime.doubleClicked(pge);
			pge.field = PlaygroundHelper.getPlaygroundFieldById(6, runtime.cells.source);
			runtime.clicked(pge);
			runtime.startClick();
			
			runtime.startClick();
		}
	}
}
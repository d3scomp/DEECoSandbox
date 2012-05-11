package
{
	import d3s.playground.vos.Field;
	
	import mx.collections.ArrayCollection;

	public class DetailsUtils
	{
		public static function toString(data:Object):String {
			var sb:String;
			if (data is Array) {
				return "[" + data.toString() + "]";
			} else if (data is Vector.<Array>) {
				sb = "[";
				for each (var a:Array in data) {
					sb += toString(a) + ", ";
				}
				if (sb.length > 2)
					sb = sb.substr(0, sb.length-2);
				sb += "]";
				return sb;
			} else if (data is Vector.<Field>) {
				sb = "[";
				for each (var n:Field in data) {
					sb += toString(n) + ", ";
				}
				if (sb.length > 2)
					sb = sb.substr(0, sb.length-2);
				sb += "]";
				return sb;
			} else if (data is Field) {
				return (data as Field).id.toString();
			} else if (data is Vector.<int>) {
				sb = "[";
				for each (var i:int in data) {
					sb += i + ", ";
				}
				if (sb.length > 2)
					sb = sb.substr(0, sb.length-2);
				sb += "]";
				return sb;
			} else if (data) {
				return data.toString();
			} else if (data is int) {
				return (data as int).toString();
			} else {
				return "[]";
			}
		}
	}
}
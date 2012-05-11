package d3s.playground.support;

import java.util.LinkedList;

import d3s.playground.vos.Field;
import d3s.playground.vos.Playground;

public class PlaygroundHelper {
	
	public static Playground createPlaygroundData(int [] [] playgroundMap, int cellSize) {
		if (playgroundMap == null || playgroundMap.length > 0)
			return null;
		Playground result = new Playground();
//		result.setFieldSize(cellSize);
//		result.setRowCount(playgroundMap.length);
//		result.setColumnCount(playgroundMap[0].length);
//		LinkedList<Field> playgroundCells = result.getPlayground();
//		LinkedList<Field> disabledCells = result.getDisabledFields();
//		LinkedList<Field> enabledCells = result.getEnabledFields();
//  		LinkedList<Field> crossingCells = new LinkedList<Field>();
//		int mapCell;
//		Field field;
//		int i;
//		for (i = 0; i < result.getRowCount(); i++) {
//			for (int j = 0; j < result.getColumnCount(); j++) {
//				field = new Field();
//				field.setX(j);
//				field.setY(i);
//				field.setId(i * result.getColumnCount() + j + 1L);
//				playgroundCells.push(field);
//				mapCell = playgroundMap[i][j];
//				if (mapCell == 0) {
//					disabledCells.push(field);
//					field.setTraversable(false);
//					continue;
//				} else if (mapCell == 2) {
//					crossingCells.push(field);
//				}
//				enabledCells.push(field);
//			}
//		}
//		LinkedList<Field> crossing;
//		while (crossingCells.size() > 0) {
//			field = crossingCells.removeFirst();
//			crossing = new LinkedList<Field>();
//			crossing.push(field);
//			crossing.push(crossingCells.removeFirst());
//			i = 0;
//			while (crossing.size() < 4) {
//				if (crossingCells.get(i).getX() - 1 == field.getX()
//					|| crossingCells.get(i).getY() - 1 == field.getY()) {
//					crossing.push(crossingCells.get(i));
//					crossingCells.remove(i);
//				} else 
//					i++;
//			}
//			result.getCrossings().push(crossing);
//		};
		return result;
	}
	
}

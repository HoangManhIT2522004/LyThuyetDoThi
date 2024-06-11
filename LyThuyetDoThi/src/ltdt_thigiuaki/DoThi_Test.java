package ltdt_thigiuaki;

import static utilz.FileMatrix.*;

import java.io.IOException;
import java.util.Arrays;

public class DoThi_Test {
	public static void main(String[] args) throws IOException {
		DoThi Graph = new DoThi_HienThucCode<>();
		if (Graph.loadGraph(DOTHI_WARSHALL1) == true) {
			System.out.println("Doc do thi thanh cong");
			Graph.printMatrix();

			/*
			 * kiem tra do thi lien thong
			 */
//			System.out.println("\nKiem tra lien thong?: "
//					+ (Graph.checkConnect() == true ? "Do thi lien thong" : "Do thi khong lien thong"));

			/*
			 * duyet do thi theo bfs , dfs
			 */
//			Graph.bfsStart(0);
//			Graph.bfs();
//			Graph.dfsStart(0);

			/*
			 * dem so thanh phan lien thong
			 */
//			Graph.soTPLienThong();

//			Graph.findPathTwoVexs(1, 3);

			/*
			 * EULER
			 */
//		Graph.checkEulerVoHuong();

//	    Graph.findCycleEulerVoHuong();

//      Graph.findCycleEulerVoHuong(0);

//	    Graph.findPathEulerVoHuong(4);

//		Graph.findPathEulerVoHuong();

//		Graph.checkEulerCoHuong();

//		Graph.findCycleEulerCoHuong();

//	    Graph.findCycleEulerCoHuong(0);

//		Graph.findPathEulerCoHuong(4);

//	    Graph.findPathEulerCoHuong();

//		Graph.checkBipartiteGraph();

//		Graph.checkHamiltonVoHuong();

//		Graph.colorGraphVoHuong();

//		Graph.colorGraphCoHuong();

//		Graph.DFSTreSpanning(0);

//		Graph.DFSTreSpanningRecursive(0);

//		Graph.BFSTreeSpanning(0);

//      Graph.SpanningTreeByKruskal();

//		Graph.SpanningTreeByPrim();

//		Graph.dijsktra(0);

//		Graph.dijsktra(0, 6);

//		Graph.floyd();

//		Graph.BellmanFord(0);

		Graph.warshall();
			
//	    Graph.findHamiltonCycle(3);

		} else {
			System.err.println("Doc do thi khong thanh cong");
		}
	}

}

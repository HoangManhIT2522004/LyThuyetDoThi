package ltdt_thigiuaki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public abstract class DoThi {
	protected int[][] matrixAdj;
	protected boolean[] dsDinhDuyet;
	protected int numOfVertex;
	protected int[] color;
	protected int[] pathHamilton;
	protected int[] path;
	protected boolean foundCycle = false;
	 
	public DoThi() {
		super();

	}

	public boolean loadGraph(String pathFile) throws IOException {
		File f = new File(pathFile);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String firstLine = br.readLine();
		this.numOfVertex = Integer.parseInt(firstLine);
		this.matrixAdj = new int[numOfVertex][numOfVertex];
		this.dsDinhDuyet = new boolean[numOfVertex];
		this.color = new int[numOfVertex];
		this.pathHamilton = new int[numOfVertex];
		this.path = new int[numOfVertex];
		String line = "";
		int indexRows = 0;
		while ((line = br.readLine()) != null) {
			String[] row = line.split(" ");
			for (int i = 0; i < numOfVertex; i++) {
				this.matrixAdj[indexRows][i] = Integer.parseInt(row[i]);
			}
			indexRows++;

		}
		br.close();
		return true;

	}

	public void printMatrix() {
		for (int i = 0; i < numOfVertex; i++) {
			for (int j = 0; j < numOfVertex; j++) {
				System.out.print(matrixAdj[i][j] + " ");
			}
			System.out.println();
		}
	}

	protected int degOfVertex(int i) {
		if (i < 0 || i > numOfVertex)
			System.out.println("Khong co dinh nay!");

		int degOfVer = 0;
		for (int j = 0; j < numOfVertex; j++)
			degOfVer += matrixAdj[i][j];

		return degOfVer;
	}

	protected int degInOfVertex(int v) {
		int degIn = 0;
		for (int i = 0; i < numOfVertex; i++) {
			degIn += matrixAdj[i][v]; // Đếm số lượng cạnh vào đỉnh v
		}
		return degIn;
	}

	protected int degOutOfVertex(int v) {
		int degOut = 0;
		for (int i = 0; i < numOfVertex; i++) {
			degOut += matrixAdj[v][i]; // Đếm số lượng cạnh ra từ đỉnh v
		}
		return degOut;
	}

	// in cay bao trum bang duyet bfs va dfs
	protected void printAdjacencyMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	// in ma da duoc to
	protected void printVertexColors(int[] colors) {
		System.out.println("Color of vertices:");
		for (int i = 0; i < colors.length; i++) {
			System.out.println("Vertex " + (i + 1) + " --->  Color " + colors[i]);
		}
	}

	// kiem tra ma tran da duyet
	public void resetVisited() {
		for (int i = 0; i < numOfVertex; i++) {
			dsDinhDuyet[i] = false;
		}
	}

	// kiem tra xem ma tran co hop le hay khong?
	public abstract boolean checkValid();

	// kiem tra ma tran vo huong khong?
	public abstract boolean CheckVoHuong();

	// them canh
	public abstract void addEdge(int v1, int v2);

	// xoa canh
	public abstract void removeEdge(int v1, int v2);

	// tinh bac cua dinh
	public abstract int deg(int v);

	// tong bac
	public abstract int sumDeg();

	// tong dinh
	public abstract int numVertexs();

	// tong canh
	public abstract int numEdges();

	// kiem tra lien thong co dinh bat dau
	public abstract boolean checkConnect(int v);

	// kiem tra lien thong
	public abstract boolean checkConnect();

	public abstract void bfsStart(int V);

	public abstract void bfs();

	public abstract void dfsStart(int v);

	public abstract void dfs();

	public abstract void soTPLienThong();

	public abstract void findPathTwoVexs(int s, int t);

	/*
	 * Euler vo huong
	 */

	public abstract void checkEulerVoHuong();

	public abstract boolean checkCycleEulerVoHuong();

	public abstract boolean checkPathEulerVoHuong();

	public abstract void findCycleEulerVoHuong(int v);

	public abstract void findCycleEulerVoHuong();

	public abstract void findPathEulerVoHuong(int v);

	public abstract void findPathEulerVoHuong();

	/*
	 * Euler vo huong
	 */

	public abstract void checkEulerCoHuong();

	public abstract boolean checkCycleEulerCoHuong();

	public abstract boolean checkPathEulerCoHuong();

	public abstract void findCycleEulerCoHuong(int v);

	public abstract void findCycleEulerCoHuong();

	public abstract void findPathEulerCoHuong(int v);

	public abstract void findPathEulerCoHuong();

//	public abstract void degreeAll();

	public abstract boolean checkBipartiteGraph();

// Hamilton

	public abstract void checkHamiltonCoHuong();

	public abstract void checkHamiltonVoHuong();
	
	public abstract void findHamiltonCycle(int start);

// do thi phang thuat toan to mau
	public abstract int[] colorGraphVoHuong();

	public abstract int[] colorGraphCoHuong();

// cay bao trum

	public abstract int[][] DFSTreSpanningRecursive(int v);

	public abstract int[][] DFSTreSpanning(int v);

	public abstract int[][] BFSTreeSpanning(int v);

	public abstract int[][] SpanningTreeByKruskal();

	public abstract int[][] SpanningTreeByPrim(int v);

	public abstract int[][] SpanningTreeByPrim();

	public abstract int[][] DFSTreSpanningRecursiveCoHuong(int v);

	public abstract int[][] DFSTreSpanningCoHuong(int v);

	public abstract int[][] BFSTreeSpanningCoHuong(int v);

	public abstract int[][] SpanningTreeByKruskalCoHuong();

	public abstract int[][] SpanningTreeByPrimCoHuong(int v);

	public abstract int[][] SpanningTreeByPrimCoHuong();

//	public abstract boolean checkCycle(int v, int[][] treeSpanning);
//
//	public abstract boolean checkCycle(int[][] treeSpanning);

	// chuong 5

	public abstract int[] dijsktra(int s);

	public abstract int[] dijsktra(int s, int t);

	public abstract int[] floyd();

//	public abstract int[] floydMoRong();

	public abstract Path[] BellmanFord(int v);
	
	public abstract void warshall();

	public class Edges {
		int u, v, w;

		public Edges(int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}

		public Edges() {

		}
	}

	class CompareWeight implements Comparator<Edges> {
		@Override
		public int compare(Edges o1, Edges o2) {
			return Integer.compare(o1.w, o2.w);
		}
	}

	// Định nghĩa lớp Vertex để lưu trữ các đỉnh và khoảng cách tương ứng
	class Vertex {
		int index; // Chỉ số đỉnh
		int distance; // Khoảng cách từ đỉnh nguồn đến đỉnh này

		public Vertex(int index, int distance) {
			this.index = index;
			this.distance = distance;
		}
	}

	class Path {
		int w, pre;

		public Path(int w, int pre) {
			super();
			this.w = w;
			this.pre = pre;
		}

		public int getW() {
			return w;
		}

		public void setW(int w) {
			this.w = w;
		}

		public int getPre() {
			return pre;
		}

		public void setPre(int pre) {
			this.pre = pre;
		}


	}

}

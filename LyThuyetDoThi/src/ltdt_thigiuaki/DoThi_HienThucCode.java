package ltdt_thigiuaki;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import ltdt_thigiuaki.DoThi.Path;

public class DoThi_HienThucCode<Edge> extends DoThi {

	// Phan kiem tra
	@Override
	public void findHamiltonCycle(int start) {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return;
		}
		resetVisited();
		Arrays.fill(pathHamilton, -1);
		pathHamilton[0] = start;
		dsDinhDuyet[start] = true;
		expand(1);
	}

	private void expand(int i) {
		if (foundCycle)
			return;

		for (int j = 0; j < numOfVertex; j++) {
			if (matrixAdj[pathHamilton[i - 1]][j] != 0 && !dsDinhDuyet[j]) {
				pathHamilton[i] = j;
				if (i < numOfVertex - 1) {
					dsDinhDuyet[j] = true;
					expand(i + 1);
					dsDinhDuyet[j] = false;
				} else // i = numOfVertex - 1
				if (matrixAdj[pathHamilton[i]][pathHamilton[0]] != 0) {
					printCycleHamilton(pathHamilton);
					foundCycle = true; // Đặt cờ thành true khi đã tìm thấy chu trình
					return;
				} else if (matrixAdj[pathHamilton[i]][pathHamilton[0]] == 0) {
					System.out.println("Do thi khong co chu trinh hamilton!");
					return;
				}
			}
		}
	}

	private void printCycleHamilton(int[] pathHamilton) {
		System.out.println("Chu trinh hamilton");
		for (int i = 0; i < pathHamilton.length; i++) {
			System.out.print((pathHamilton[i] + 1) + " ");
		}
		System.out.println((pathHamilton[0] + 1));
	}

	// ket thuc phan kiem tra

	@Override
	public boolean checkValid() {
		for (int i = 0; i < numOfVertex; i++)
			if (matrixAdj[i][i] > 1 || matrixAdj[i][i] != 0)
				return false;
		return true;
	}

	@Override
	public boolean CheckVoHuong() {
		for (int i = 0; i < numOfVertex; i++) {
			for (int j = 0; j < numOfVertex; j++) {
				if (matrixAdj[i][j] != matrixAdj[j][i]) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void addEdge(int v1, int v2) {
		if (v1 < 0 || v1 >= numOfVertex || v2 < 0 || v2 >= numOfVertex) {
			System.err.println("Dinh khong hop le");
		} else {
			matrixAdj[v1][v2] += 1;
		}

	}

	@Override
	public int numEdges() {
		int numEdge = 0;
		for (int i = 0; i < numOfVertex; i++) {
			for (int j = i + 1; j < numOfVertex; j++) {
				if (matrixAdj[i][j] > 0) {
					numEdge += matrixAdj[i][j];
				}
			}
		}
		return numEdge;
	}

	@Override
	public void removeEdge(int v1, int v2) {
		if (v1 < 0 || v1 >= numOfVertex || v2 < 0 || v2 >= numOfVertex) {
			System.err.println("Dinh khong nam trong ma tran");
			return;
		}
		matrixAdj[v1][v2] -= 1;

	}

	@Override
	public int deg(int v) {
		int degree = 0;
		if (v < 0 || v >= numOfVertex) {
			System.err.println("\nDinh khong nam trong do thi");
		} else {
			for (int i = 0; i < numOfVertex; i++) {
				degree += matrixAdj[v][i];
			}
		}
		return degree;
	}

	@Override
	public int sumDeg() {
		int sumDegree = 0;
		for (int i = 0; i < numOfVertex; i++) {
			sumDegree += deg(i);
		}
		return sumDegree;
	}

	@Override
	public int numVertexs() {
		int count = 0;
		for (int i = 0; i < numOfVertex; i++) {
			count++;
		}
		return count;
	}

	@Override
	public boolean checkConnect(int v) {
		int soDinhDaDuyet = 0;
		dsDinhDuyet[v] = true;
		soDinhDaDuyet++;
		for (int i = 0; i < numOfVertex; i++) {
			if (dsDinhDuyet[i] == true) {
				for (int j = 0; j < numOfVertex; j++) {
					if (matrixAdj[i][j] != 0 && dsDinhDuyet[j] == false) {
						dsDinhDuyet[j] = true;
						soDinhDaDuyet++;
					}
					if (soDinhDaDuyet == numOfVertex) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkConnect() {
		Queue<Integer> queue = new LinkedList<>();
		int soDinhDuyet = 0;

		// Bắt đầu từ đỉnh đầu tiên
		queue.add(0);
		dsDinhDuyet[0] = true;
		soDinhDuyet++;

		while (!queue.isEmpty()) {
			int n = queue.poll();

			for (int i = 0; i < numOfVertex; i++) {
				if (matrixAdj[n][i] != 0 && !dsDinhDuyet[i]) {
					queue.add(i);
					dsDinhDuyet[i] = true;
					soDinhDuyet++;
				}
			}
		}

		return (soDinhDuyet == numOfVertex) ? true : false;
	}

	@Override
	public void bfsStart(int v) {
		if (!checkConnect())
			System.out.println("Do thi khong lien thong!");
		else {
			Queue<Integer> open = new LinkedList<Integer>();
			List<Integer> close = new ArrayList<>();
			resetVisited();
			open.add(v);
			dsDinhDuyet[v] = true;
			while (!open.isEmpty()) {
				int n = open.remove();
				close.add(n);
				for (int i = 0; i < numOfVertex; i++) {
					if (matrixAdj[n][i] != 0 && dsDinhDuyet[i] == false) {
						open.add(i);
						dsDinhDuyet[i] = true;
					}
				}
			}
			System.out.println("\nDuyet Do Thi Theo Chieu Rong: ");
			for (int i = 0; i < close.size(); i++)
				System.out.println("Vertex=" + (close.get(i) + 1) + " " + "Visited=" + dsDinhDuyet[i]);
		}
	}

	@Override
	public void bfs() {
		if (checkConnect() == false)
			System.out.println("Do thi khong lien thong!");
		else {
			Queue<Integer> open = new LinkedList<Integer>();
			List<Integer> close = new ArrayList<>();
			resetVisited();
			open.add(0);
			dsDinhDuyet[0] = true;

			while (!open.isEmpty()) {
				int n = open.remove();
				close.add(n);
				for (int i = 0; i < numOfVertex; i++) {
					if (matrixAdj[n][i] != 0 && !dsDinhDuyet[i]) {
						open.add(i);
						dsDinhDuyet[i] = true;
					}
				}
			}
			System.out.println("\nDuyet Do Thi Theo Chieu Rong: ");
			for (int i = 0; i < close.size(); i++)
				System.out.println("Vertex=" + (close.get(i) + 1) + " " + "Visited=" + dsDinhDuyet[i]);
		}
	}

	@Override
	public void dfsStart(int v) {
		if (checkConnect() == false)
			System.out.println("Do thi khong lien thong!");
		else {
			Stack<Integer> open = new Stack<>();
			List<Integer> close = new ArrayList<>();
			resetVisited();
			open.add(v);
			dsDinhDuyet[v] = true;

			while (!open.isEmpty()) {
				int n = open.pop();
				close.add(n);
				for (int i = 0; i < numOfVertex; i++) {
					if (matrixAdj[n][i] != 0 && dsDinhDuyet[i] == false) {
						dsDinhDuyet[i] = true;
						open.add(i);
					}
				}
			}

			System.out.println("\nDuyet Do Thi Theo Chieu Sau: ");
			for (int i = 0; i < close.size(); i++)
				System.out.println("Vertex=" + (close.get(i) + 1) + " " + "Visited=" + dsDinhDuyet[i]);

		}

	}

	@Override
	public void dfs() {
		if (checkConnect() == false)
			System.out.println("Do thi khong lien thong!");
		else {
			Stack<Integer> open = new Stack<>();
			List<Integer> close = new ArrayList<>();
			resetVisited();
			open.add(0);
			dsDinhDuyet[0] = true;

			while (!open.isEmpty()) {
				int n = open.pop();
				close.add(n);
				for (int i = 0; i < numOfVertex; i++) {
					if (matrixAdj[n][i] != 0 && dsDinhDuyet[i] == false) {
						dsDinhDuyet[i] = true;
						open.add(i);
					}
				}
			}

			System.out.println("\nDuyet Do Thi Theo Chieu Sau: ");
			for (int i = 0; i < close.size(); i++)
				System.out.println("Vertex=" + (close.get(i) + 1) + " " + "Visited=" + dsDinhDuyet[i]);

		}
	}

	@Override
	public void soTPLienThong() {
		resetVisited();
		int soThanhPhanLienThong = 0;
		for (int i = 0; i < numOfVertex; i++)
			if (!dsDinhDuyet[i]) {
				bfsStart1(i);
				soThanhPhanLienThong++;
			}
		System.out.println("So thanh phan lien thong la: " + soThanhPhanLienThong);
	}

	private void bfsStart1(int j) {
		Queue<Integer> open = new LinkedList<Integer>();
		resetVisited();
		open.add(j);
		dsDinhDuyet[j] = true;
		while (!open.isEmpty()) {
			int n = open.remove();
			for (int i = 0; i < numOfVertex; i++) {
				if (matrixAdj[n][i] != 0 && dsDinhDuyet[i] == false) {
					open.add(i);
					dsDinhDuyet[i] = true;
				}
			}
		}

	}

	@Override
	public void findPathTwoVexs(int s, int t) {
		boolean[] visited = new boolean[numOfVertex];
		int[] parent = new int[numOfVertex];
		Arrays.fill(parent, -1);

		Queue<Integer> queue = new LinkedList<>();
		visited[s] = true;
		queue.add(s);

		while (!queue.isEmpty()) {
			int current = queue.poll();

			if (current == t) {
				break;
			}

			for (int i = 0; i < numOfVertex; i++) {
				if (matrixAdj[current][i] != 0 && !visited[i]) {
					visited[i] = true;
					parent[i] = current;
					queue.add(i);
				}
			}
		}

		printPath(s, t, parent);
	}

	private void printPath(int s, int t, int[] parent) {
		List<Integer> path = new ArrayList<>();
		for (int at = t; at != -1; at = parent[at]) {
			path.add(at);
		}
		Collections.reverse(path);

		if (path.get(0) == s) {
			System.out.println("Path from " + (s) + " to " + (t) + ": " + path);
		} else {
			System.out.println("No path from " + (s) + " to " + (t));
		}
	}

	/*
	 * ------------------------------- EULER -------------------------------
	 */

	@Override
	public void checkEulerVoHuong() {
		if (!checkConnect())
			System.out.println("Do thi khong lien thong");
		resetVisited();
		if (checkCycleEulerVoHuong())
			System.out.println("Do thi co chu trinh EULER");
		else if (checkPathEulerVoHuong())
			System.out.println("Do thi co duong di EULER");
		else
			System.out.println("Do thi khong co chu trinh hay duong di EUlER danh cho ma tran vo huong");
	}

	@Override
	public boolean checkCycleEulerVoHuong() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return false;
		}
		resetVisited();
		for (int i = 0; i < numOfVertex; i++) {
			if (degOfVertex(i) % 2 != 0)
				return false;
		}
		return true;
	}

	@Override
	public boolean checkPathEulerVoHuong() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return false;
		}
		resetVisited();
		int countDegLe = 0;
		int countDegChan = 0;
		for (int i = 0; i < numOfVertex; i++) {
			if (degOfVertex(i) % 2 != 0)
				countDegLe++;
			else
				countDegChan++;

		}

		return countDegLe == 2 && countDegChan == numOfVertex - 2;
	}

	@Override
	public void findCycleEulerVoHuong(int v) {
		if (!checkCycleEulerVoHuong())
			System.out.println("Do thi khong co chu trinh EULER");
		else {
			Stack<Integer> stack = new Stack<>();
			List<Integer> cycle = new ArrayList<>();

			resetVisited();
			stack.add(v);

			while (!stack.isEmpty()) {
				int n = stack.peek();
				boolean found = false;

				for (int i = 0; i < numOfVertex; i++) {
					if (matrixAdj[n][i] != 0) {
						stack.add(i);
						matrixAdj[n][i]--;
						matrixAdj[i][n]--;
						found = true;
						break; // neu tim thay dinh ke chi xet 1 dinh trong 1 vong lap
					}
				}
				if (!found) {
					stack.pop();
					cycle.add(n);
				}
			}
			// In ra chu trình Euler
			System.out.println("\nChu trinh Euler: ");
			System.out.print("Cycle: ");
			Collections.reverse(cycle);
			for (int i = 0; i < cycle.size(); i++) {
				System.out.print((cycle.get(i) + 1) + " ");
			}
		}
	}

	@Override
	public void findCycleEulerVoHuong() {
		if (!checkCycleEulerVoHuong())
			System.out.println("Do thi khong co chu trinh EULER");
		else {
			resetVisited();
			Stack<Integer> stack = new Stack<>();
			List<Integer> cycle = new ArrayList<>();
			resetVisited();
			stack.add(0);

			while (!stack.isEmpty()) {
				int n = stack.peek(); // Lấy đỉnh đầu tiên từ stack
				boolean found = false; // Biến để kiểm tra xem đã tìm thấy đỉnh kề mới không

				// Kiểm tra các đỉnh kề của đỉnh hiện tại
				for (int i = 0; i < numOfVertex; i++) {
					if (matrixAdj[n][i] != 0) { // Nếu có cạnh nối từ đỉnh hiện tại đến đỉnh kề i
						stack.add(i);
						matrixAdj[n][i]--;
						matrixAdj[i][n]--;
						found = true; // Đánh dấu là đã tìm thấy đỉnh kề mới
						break; // Thoát khỏi vòng lặp, chỉ xét một đỉnh kề tại mỗi lần duyệt
					}
				}

				if (!found) {
					stack.pop();
					cycle.add(n);
				}
			}

			// In ra chu trình Euler
			System.out.println("\nChu trinh Euler: ");
			System.out.print("Cycle: ");
			Collections.reverse(cycle);
			for (int i = 0; i < cycle.size(); i++) {
				System.out.print((cycle.get(i) + 1) + " ");
			}
		}
	}

	public void findPathEulerVoHuong(int v) {
		if (!checkPathEulerVoHuong())
			System.out.println("Do thi vo huong khong co duong di EULER");
		else {
			resetVisited();
			for (int i = 0; i < numOfVertex; i++) {
				if (degOfVertex(i) % 2 != 0) {
					v = i; // Gán đỉnh bậc lẻ cho v
				}
			}

			Stack<Integer> stack = new Stack<>();
			TimDuongDiMaTranVoHuong(v, matrixAdj, stack);

			System.out.println("\nDuong di Euler: ");
			System.out.print("Path: ");
			while (!stack.isEmpty()) {
				System.out.print((stack.pop() + 1) + " ");
			}
		}
	}

	private void TimDuongDiMaTranVoHuong(int i, int[][] matrix, Stack<Integer> stack) {
		for (int j = 0; j < numOfVertex; j++) {
			if (matrix[i][j] != 0) {
				matrix[i][j] = matrix[j][i] = 0; // Loại bỏ cạnh nối đỉnh i tới đỉnh j khỏi đồ thị
				TimDuongDiMaTranVoHuong(j, matrix, stack);
			}
		}
		stack.push(i); // Đẩy đỉnh i vào stack sau khi đã duyệt hết các đỉnh kề của nó
	}

	@Override
	public void findPathEulerVoHuong() {
		if (!checkPathEulerVoHuong())
			System.out.println("Do thi khong co duong di Euler");
		else {
			resetVisited();
			int n = 0;
			for (int i = 0; i < numOfVertex; i++) {
				if (degOfVertex(i) % 2 != 0)
					n = i;
			}

			Stack<Integer> stack = new Stack<>();
			TimDuongDiMaTranVoHuong(n, matrixAdj, stack);

			System.out.println("\nDuong di Euler: ");
			System.out.print("Path: ");
			while (!stack.isEmpty()) {
				System.out.print((stack.pop() + 1) + " ");
			}
		}

	}

	@Override
	public void checkEulerCoHuong() {
		if (!checkConnect())
			System.out.println("Do thi co huong khong lien thong");
		resetVisited();
		if (checkCycleEulerCoHuong())
			System.out.println("Do thi co huong co chu trinh EULER");
		else if (checkPathEulerCoHuong())
			System.out.println("Do thi co huong co duong di EULER");
		else
			System.out.println("Do thi co huong khong co chu trinh hay duong di EUlER danh cho ma tran co huong");
	}

	@Override
	public boolean checkCycleEulerCoHuong() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return false;
		}
		resetVisited();
		for (int i = 0; i < numOfVertex; i++) {
			if (degOutOfVertex(i) != degInOfVertex(i))
				return false;
		}
		return true;
	}

	@Override
	public boolean checkPathEulerCoHuong() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return false;
		}
		resetVisited();
		int countDegLe = 0;
		int countDegChan = 0;
		for (int i = 0; i < numOfVertex; i++) {
			if (degOutOfVertex(i) - degInOfVertex(i) == 1)
				countDegLe++;
			else if (degInOfVertex(i) - degOutOfVertex(i) == 1)
				countDegChan++;
			else if (degInOfVertex(i) != degOutOfVertex(i))
				return false;
		}

		return countDegLe == 1 && countDegChan == 1;
	}

	@Override
	public void findCycleEulerCoHuong(int v) {
		if (!checkCycleEulerCoHuong())
			System.out.println("Do thi khong co chu trinh EULER");
		else {
			Stack<Integer> stack = new Stack<>();
			List<Integer> cycle = new ArrayList<>();

			resetVisited();
			stack.add(v);

			while (!stack.isEmpty()) {
				int n = stack.peek();
				boolean found = false;

				for (int i = 0; i < numOfVertex; i++) {
					if (matrixAdj[n][i] != 0) {
						stack.add(i);
						matrixAdj[n][i]--;
						found = true;
						break; // chỉ cần thêm vào stack và giảm trọng số của cạnh
					}
				}
				if (!found) {
					stack.pop();
					cycle.add(n);
				}
			}
			// In ra chu trình Euler
			System.out.println("\nChu trinh Euler: ");
			System.out.print("Cycle: ");
			Collections.reverse(cycle);
			for (int i = 0; i < cycle.size(); i++) {
				System.out.print((cycle.get(i) + 1) + " ");
			}
		}
	}

	@Override
	public void findCycleEulerCoHuong() {
		if (!checkCycleEulerCoHuong())
			System.out.println("Do thi khong co chu trinh EULER");
		else {
			resetVisited();
			Stack<Integer> stack = new Stack<>();
			List<Integer> cycle = new ArrayList<>();
			resetVisited();
			stack.add(0);

			while (!stack.isEmpty()) {
				int n = stack.peek(); // Lấy đỉnh đầu tiên từ stack
				boolean found = false; // Biến để kiểm tra xem đã tìm thấy đỉnh kề mới không

				// Kiểm tra các đỉnh kề của đỉnh hiện tại
				for (int i = 0; i < numOfVertex; i++) {
					if (matrixAdj[n][i] != 0) { // Nếu có cạnh nối từ đỉnh hiện tại đến đỉnh kề i
						stack.add(i);
						matrixAdj[n][i]--;
						found = true; // Đánh dấu là đã tìm thấy đỉnh kề mới
						break; // Thoát khỏi vòng lặp, chỉ xét một đỉnh kề tại mỗi lần duyệt
					}
				}

				if (!found) {
					stack.pop();
					cycle.add(n);
				}
			}

			// In ra chu trình Euler
			System.out.println("\nChu trinh Euler: ");
			System.out.print("Cycle: ");
			Collections.reverse(cycle);
			for (int i = 0; i < cycle.size(); i++) {
				System.out.print((cycle.get(i) + 1) + " ");
			}
		}

	}

	@Override
	public void findPathEulerCoHuong(int v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void findPathEulerCoHuong() {
		if (!checkPathEulerCoHuong())
			System.out.println("Do thi khong co duong di Euler");
		else {
			resetVisited();
			int n = 0;
			for (int i = 0; i < numOfVertex; i++) {
				if (degOutOfVertex(i) - degInOfVertex(i) == 1) {
					n = i;
					break;
				}
			}

			Stack<Integer> stack = new Stack<>();
			TimDuongDiMaTranCoHuong(n, matrixAdj, stack);

			System.out.println("\nDuong di Euler: ");
			System.out.print("Path: ");
			while (!stack.isEmpty()) {
				System.out.print((stack.pop() + 1) + " ");
			}
		}

	}

	private void TimDuongDiMaTranCoHuong(int i, int[][] matrix, Stack<Integer> stack) {
		for (int j = 0; j < numOfVertex; j++) {
			if (matrix[i][j] != 0) {
				matrix[i][j] = 0; // Loại bỏ cạnh nối từ đỉnh i tới đỉnh j khỏi đồ thị
				TimDuongDiMaTranCoHuong(j, matrix, stack); // Sửa lại phương thức gọi đệ quy
			}
		}
		stack.push(i); // Đẩy đỉnh i vào stack sau khi đã duyệt hết các đỉnh kề của nó
	}

	/*
	 * ========================= END EULER
	 * ==========================================
	 */

	@Override
	public boolean checkBipartiteGraph() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong!");
			return false;
		}

		int[] color = new int[matrixAdj.length];
		for (int i = 0; i < color.length; i++) {
			color[i] = -1; // Tất cả các đỉnh ban đầu chưa được tô màu
		}

		// Kiểm tra từng thành phần liên thông
		for (int i = 0; i < matrixAdj.length; i++) {
			if (color[i] == -1) {
				Queue<Integer> open = new LinkedList<>();
				open.add(i);
				color[i] = 1; // Tô màu đỉnh bắt đầu là 1

				while (!open.isEmpty()) {
					int u = open.poll();

					for (int j = 0; j < matrixAdj.length; j++) {
						if (matrixAdj[u][j] != 0) { // Kiểm tra đỉnh kề
							if (color[j] == -1) {
								open.add(j);
								color[j] = 1 - color[u]; // Tô màu đỉnh kề với màu ngược lại
							} else if (color[j] == color[u]) {
								System.out.println("\nDo thi khong luong phan");
								return false;
							}
						}
					}
				}
			}
		}

		System.out.println("\nDo thi la do thi luong phan");
		return true;
	}

	/*
	 * =============================== HAMILTON ====================================
	 */

	@Override
	public void checkHamiltonVoHuong() {
		resetVisited();
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return;
		}
		resetVisited();
		Arrays.fill(pathHamilton, -1);
		pathHamilton[0] = 0;
		dsDinhDuyet[0] = true;
		expand(1);
	}

	private void printPathHamilton(int[] pathHamilton) {
		System.out.println("Duong di hamilton");
		for (int i = 0; i < pathHamilton.length; i++) {
			System.out.print((pathHamilton[i] + 1) + " ");
		}
		System.out.println();
	}

	@Override
	public void checkHamiltonCoHuong() {
		resetVisited();
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return;
		}
		resetVisited();
		Arrays.fill(pathHamilton, -1);
		pathHamilton[0] = 0; // Bắt đầu từ đỉnh 0
		dsDinhDuyet[0] = true;
		expandCoHuong(1);
	}

	private void expandCoHuong(int i) {
		for (int j = 0; j < numOfVertex; j++) {
			if (matrixAdj[pathHamilton[i - 1]][j] != 0 && !dsDinhDuyet[j]) { // Kiểm tra cạnh từ đỉnh trước đến đỉnh j
				pathHamilton[i] = j;
				if (i < numOfVertex - 1) {
					dsDinhDuyet[j] = true;
					expand(i + 1);
					dsDinhDuyet[j] = false;
				} else { // i = numOfVertex - 1
					if (matrixAdj[pathHamilton[i]][pathHamilton[0]] != 0) { // Kiểm tra cạnh từ đỉnh cuối đến đỉnh đầu
						printCycleHamiltonCoHuong(pathHamilton);
					} else {
						printPathHamiltonCoHuong(pathHamilton);
					}
				}
			}
		}
	}

	private void printPathHamiltonCoHuong(int[] pathHamilton) {
		System.out.println("Duong di Hamilton:");
		for (int i = 0; i < pathHamilton.length; i++) {
			System.out.print((pathHamilton[i] + 1) + " ");
		}
		System.out.println();
	}

	private void printCycleHamiltonCoHuong(int[] pathHamilton) {
		System.out.println("Chu trinh Hamilton:");
		for (int i = 0; i < pathHamilton.length; i++) {
			System.out.print((pathHamilton[i] + 1) + " ");
		}
		System.out.println((pathHamilton[0] + 1)); // Đóng chu trình bằng cách quay lại đỉnh đầu
	}

	/*
	 * ============================== End Hamilton =================================
	 */

	/*
	 * ================================== TO MAU
	 * =========================================
	 */

	@Override
	public int[] colorGraphVoHuong() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return null;
		}
		int[] degree = new int[numOfVertex]; // luu bac cua cac dinh
		Integer[] vertex = new Integer[numOfVertex]; // luu cac dinh
		for (int i = 0; i < numOfVertex; i++) {
			degree[i] = 0;
			vertex[i] = i;
			for (int j = 0; j < numOfVertex; j++) {
				if (matrixAdj[i][j] != 0) {
					degree[i]++;
				}
			}
		}

		Arrays.sort(vertex, (a, b) -> degree[b] - degree[a]); // sap xep cac dinh theo tu giam dan cua bac

		// mang luu mau
		int[] listColor = new int[numOfVertex];
		Arrays.fill(listColor, -1);

		// to mau dinh
		int color = 0;
		while (true) { // lap cho den khi nao cac dinh duoc to mau
			boolean colored = false;
			for (int i = 0; i < numOfVertex; i++) {
				if (listColor[vertex[i]] == -1) {
					boolean canColor = true;
					for (int j = 0; j < numOfVertex; j++) {
						if (matrixAdj[vertex[i]][j] != 0 && listColor[j] == color) {
							canColor = false;
							break;
						}
					}
					if (canColor) {
						listColor[vertex[i]] = color;
						colored = true;
					}
				}
			}

			if (!colored) {
				break;
			}

			color++;
		}

		printVertexColors(listColor);
		return listColor;
	}

	@Override
	public int[] colorGraphCoHuong() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return null;
		}

		int[] inDegree = new int[numOfVertex]; // Bậc vào của các đỉnh
		int[] outDegree = new int[numOfVertex]; // Bậc ra của các đỉnh
		Integer[] vertex = new Integer[numOfVertex]; // Lưu trữ các đỉnh
		for (int i = 0; i < numOfVertex; i++) {
			inDegree[i] = 0;
			outDegree[i] = 0;
			vertex[i] = i;
			for (int j = 0; j < numOfVertex; j++) {
				if (matrixAdj[i][j] != 0) {
					outDegree[i]++; // Tăng bậc ra của đỉnh i
					inDegree[j]++; // Tăng bậc vào của đỉnh j
				}
			}
		}

		Arrays.sort(vertex, (a, b) -> outDegree[b] - outDegree[a]); // Sắp xếp các đỉnh theo bậc ra giảm dần

		// Mảng lưu màu của các đỉnh
		int[] listColor = new int[numOfVertex];
		Arrays.fill(listColor, -1); // Khởi tạo mảng màu với giá trị -1

		// Tô màu cho các đỉnh
		int color = 0;
		while (true) {
			boolean colored = false;
			for (int i = 0; i < numOfVertex; i++) {
				if (listColor[vertex[i]] == -1) { // Nếu đỉnh chưa được tô màu
					boolean canColor = true;
					for (int j = 0; j < numOfVertex; j++) {
						// Kiểm tra các cạnh ra của đỉnh
						if (matrixAdj[vertex[i]][j] != 0 && listColor[j] == color) {
							canColor = false;
							break;
						}
						// Kiểm tra các cạnh vào của đỉnh
						if (matrixAdj[j][vertex[i]] != 0 && listColor[j] == color) {
							canColor = false;
							break;
						}
					}
					if (canColor) {
						listColor[vertex[i]] = color; // Tô màu cho đỉnh
						colored = true;
					}
				}
			}

			if (!colored) {
				break;
			}

			color++;
		}

		printVertexColors(listColor);
		return listColor;
	}

	/*
	 * ================================= END TO MAU
	 * ==================================
	 */

	/*
	 * ================================= TREE SPANING
	 * =======================================
	 */

	@Override
	public int[][] DFSTreSpanningRecursive(int v) {
		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return null;
		}
		resetVisited();
		int[][] treeSpanning = new int[numOfVertex][numOfVertex];
		List<String> edgeList = new ArrayList<>();
		dfsTreeSpanningRecursive(v, dsDinhDuyet, treeSpanning, edgeList);
		System.out.println("\nKết quả cây bao trùm duyệt theo DFS đệ quy:");
		printAdjacencyMatrix(treeSpanning);
		System.out.println(edgeList);
		return treeSpanning;
	}

	private void dfsTreeSpanningRecursive(int v, boolean[] dsDinhDuyet, int[][] treeSpanning, List<String> edgeList) {
		dsDinhDuyet[v] = true;
		for (int i = 0; i < numOfVertex; i++) {
			if (matrixAdj[v][i] != 0 && !dsDinhDuyet[i]) {
				treeSpanning[v][i] = treeSpanning[i][v] = 1;
				edgeList.add("(" + (v + 1) + "," + (i + 1) + ")");
				dfsTreeSpanningRecursive(i, dsDinhDuyet, treeSpanning, edgeList);
			}
		}
	}

	@Override
	public int[][] DFSTreSpanning(int v) {
		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return null;
		}
		resetVisited();
		int[][] treeSpanning = new int[numOfVertex][numOfVertex];
		List<String> edgeList = new ArrayList<>();
		dfsTreeSpanning(v, dsDinhDuyet, treeSpanning, edgeList);
		System.out.println("\nKết quả cây bao trùm duyệt theo DFS đệ quy:");
		printAdjacencyMatrix(treeSpanning);
		System.out.println(edgeList);
		return treeSpanning;
	}

	private void dfsTreeSpanning(int v, boolean[] dsDinhDuyet, int[][] treeSpanning, List<String> edgeList) {
		Stack<Integer> stack = new Stack<>();
		dsDinhDuyet[v] = true;
		stack.push(v);

		while (!stack.isEmpty()) {
			int n = stack.pop();
			for (int i = 0; i < numOfVertex; i++) {
				if (matrixAdj[n][i] != 0 && !dsDinhDuyet[i]) {
					dsDinhDuyet[i] = true;
					stack.push(i);
					treeSpanning[n][i] = treeSpanning[i][n] = 1;
					edgeList.add("(" + (n + 1) + "," + (i + 1) + ")");

				}
			}

		}

	}

	@Override
	public int[][] BFSTreeSpanning(int v) {
		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return null;
		}
		resetVisited();
		int[][] treeSpanning = new int[numOfVertex][numOfVertex];
		List<String> edgeList = new ArrayList<>();
		bfsTreeSpanning(v, dsDinhDuyet, treeSpanning, edgeList);
		System.out.println("\nKết quả cây bao trùm duyệt theo BFS:");
		printAdjacencyMatrix(treeSpanning);
		System.out.println(edgeList);
		return treeSpanning;
	}

	private void bfsTreeSpanning(int start, boolean[] dsDinhDuyet, int[][] treeSpanning, List<String> edgeList) {
		Queue<Integer> queue = new LinkedList<>();
		dsDinhDuyet[start] = true;
		queue.add(start);

		while (!queue.isEmpty()) {
			int v = queue.remove();
			for (int i = 0; i < numOfVertex; i++) {
				if (matrixAdj[v][i] != 0 && !dsDinhDuyet[i]) {
					dsDinhDuyet[i] = true;
					treeSpanning[v][i] = treeSpanning[i][v] = 1;
					edgeList.add("(" + (v + 1) + "," + (i + 1) + ")");
					queue.add(i);
				}
			}
		}
	}

	@Override
	public int[][] SpanningTreeByKruskal() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return null;
		}
		resetVisited();
		int[][] minTreeSpanning = new int[numOfVertex][numOfVertex];
		List<Edges> listEdges = new ArrayList<DoThi.Edges>();
		for (int i = 0; i < numOfVertex; i++) {
			for (int j = 0; j < numOfVertex; j++) {
				if (matrixAdj[i][j] != 0) {
					listEdges.add(new Edges(i, j, matrixAdj[i][j]));
				}
			}
		}
		Collections.sort(listEdges, new CompareWeight());

		// tạo cha cho đỉnh
		int[] parent = new int[numOfVertex];
		for (int i = 0; i < numOfVertex; i++) {
			parent[i] = i;
		}

		int sumWeight = 0;
		int countEdges = 0;
		int indexList = 0;
		System.out.println("\nCay bao trum nho nhat:");
		while (countEdges < numOfVertex - 1 && indexList < listEdges.size()) {
			Edges e = listEdges.get(indexList); // chọn cạnh có trọng số nhỏ nhất
			int u = parent[e.u];
			int t = parent[e.v];
			if (u != t) {
				countEdges++;
				minTreeSpanning[e.u][e.v] = minTreeSpanning[e.v][e.u] = e.w;
				System.out.printf("(%d, %d) - %d\n", e.u + 1, e.v + 1, e.w);
				sumWeight += e.w;
				// cập nhập lại cha cho cho đỉnh
				for (int i = 0; i < numOfVertex; i++) {
					if (parent[i] == t) {
						parent[i] = u;
					}
				}
			}
			indexList++; // tiếp tục với cạnh kế tiếp
		}
		System.out.println("Trong so nho nhat = " + sumWeight);
		printAdjacencyMatrix(minTreeSpanning);
		return minTreeSpanning;

	}

	@Override
	public int[][] SpanningTreeByPrim() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return null;
		}

		resetVisited();
		int[][] tree = new int[numOfVertex][numOfVertex];
		int countEdge = 0;
		int sumWeight = 0;
		dsDinhDuyet[0] = true;
		System.out.println("\nCay bao trum nho nhat:");
		while (countEdge < numOfVertex - 1) {
			Edges minEdge = new Edges();
			int minW = Integer.MAX_VALUE;
			for (int i = 0; i < numOfVertex; i++) {
				if (dsDinhDuyet[i] == true) {
					for (int j = 0; j < numOfVertex; j++) {
						if (matrixAdj[i][j] != 0 && dsDinhDuyet[j] == false && matrixAdj[i][j] < minW) {
							minEdge.u = i;
							minEdge.v = j;
							minEdge.w = matrixAdj[i][j];
							minW = matrixAdj[i][j];
						}
					}
				}
			}
			tree[minEdge.u][minEdge.v] = tree[minEdge.v][minEdge.u] = minEdge.w;
			countEdge++;
			System.out.printf("(%d,%d) - %d\n", minEdge.u + 1, minEdge.v + 1, minEdge.w);
			dsDinhDuyet[minEdge.v] = true;
			sumWeight += minEdge.w;
		}
		System.out.println("Trong so nho nhat cua cay bao trum = " + sumWeight);
		printAdjacencyMatrix(tree);
		return tree;
	}

	@Override
	public int[][] SpanningTreeByPrim(int v) {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return null;
		}

		resetVisited();
		int[][] tree = new int[numOfVertex][numOfVertex];
		int countEdge = 0;
		int sumWeight = 0;
		dsDinhDuyet[v] = true;
		System.out.println("\nCay bao trum nho nhat:");
		while (countEdge < numOfVertex - 1) {
			Edges minEdge = new Edges();
			int minW = Integer.MAX_VALUE;
			for (int i = 0; i < numOfVertex; i++) {
				if (dsDinhDuyet[i] == true) {
					for (int j = 0; j < numOfVertex; j++) {
						if (matrixAdj[i][j] != 0 && dsDinhDuyet[j] == false && matrixAdj[i][j] < minW) {
							minEdge.u = i;
							minEdge.v = j;
							minEdge.w = matrixAdj[i][j];
							minW = matrixAdj[i][j];
						}
					}
				}
			}
			tree[minEdge.u][minEdge.v] = tree[minEdge.v][minEdge.u] = minEdge.w;
			countEdge++;
			System.out.printf("(%d,%d) - %d\n", minEdge.u + 1, minEdge.v + 1, minEdge.w);
			dsDinhDuyet[minEdge.v] = true;
			sumWeight += minEdge.w;
		}
		System.out.println("Trong so nho nhat cua cay bao trum = " + sumWeight);
		printAdjacencyMatrix(tree);
		return tree;
	}

	@Override
	public int[][] DFSTreSpanningRecursiveCoHuong(int v) {
		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return null;
		}
		resetVisited();
		int[][] treeSpanning = new int[numOfVertex][numOfVertex];
		List<String> edgeList = new ArrayList<>();
		dfsTreeSpanningRecursiveCoHuong(v, dsDinhDuyet, treeSpanning, edgeList);
		System.out.println("\nKết quả cây bao trùm duyệt theo DFS đệ quy:");
		printAdjacencyMatrix(treeSpanning);
		System.out.println(edgeList);
		return treeSpanning;
	}

	private void dfsTreeSpanningRecursiveCoHuong(int v, boolean[] dsDinhDuyet, int[][] treeSpanning,
			List<String> edgeList) {
		dsDinhDuyet[v] = true;
		for (int i = 0; i < numOfVertex; i++) {
			if (matrixAdj[v][i] != 0 && !dsDinhDuyet[i]) {
				treeSpanning[v][i] = 1; // Chỉ thay đổi ma trận kề theo một hướng
				edgeList.add("(" + (v + 1) + "," + (i + 1) + ")");
				dfsTreeSpanningRecursiveCoHuong(i, dsDinhDuyet, treeSpanning, edgeList);
			}
		}
	}

	@Override
	public int[][] DFSTreSpanningCoHuong(int v) {
		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return null;
		}
		resetVisited();
		int[][] treeSpanning = new int[numOfVertex][numOfVertex];
		List<String> edgeList = new ArrayList<>();
		dfsTreeSpanningCoHuong(v, dsDinhDuyet, treeSpanning, edgeList);
		System.out.println("\nKết quả cây bao trùm duyệt theo DFS đệ quy:");
		printAdjacencyMatrix(treeSpanning);
		System.out.println(edgeList);
		return treeSpanning;
	}

	private void dfsTreeSpanningCoHuong(int v, boolean[] dsDinhDuyet, int[][] treeSpanning, List<String> edgeList) {
		Stack<Integer> stack = new Stack<>();
		dsDinhDuyet[v] = true;
		stack.push(v);

		while (!stack.isEmpty()) {
			int n = stack.pop();
			for (int i = 0; i < numOfVertex; i++) {
				if (matrixAdj[n][i] != 0 && !dsDinhDuyet[i]) {
					dsDinhDuyet[i] = true;
					stack.push(i);
					treeSpanning[n][i] = 1;
					edgeList.add("(" + (n + 1) + "," + (i + 1) + ")");

				}
			}

		}

	}

	@Override
	public int[][] BFSTreeSpanningCoHuong(int v) {
		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return null;
		}
		resetVisited();
		int[][] treeSpanning = new int[numOfVertex][numOfVertex];
		List<String> edgeList = new ArrayList<>();
		bfsTreeSpanningCoHuong(v, dsDinhDuyet, treeSpanning, edgeList);
		System.out.println("\nKết quả cây bao trùm duyệt theo BFS:");
		printAdjacencyMatrix(treeSpanning);
		System.out.println(edgeList);
		return treeSpanning;
	}

	private void bfsTreeSpanningCoHuong(int start, boolean[] dsDinhDuyet, int[][] treeSpanning, List<String> edgeList) {
		Queue<Integer> queue = new LinkedList<>();
		dsDinhDuyet[start] = true;
		queue.add(start);

		while (!queue.isEmpty()) {
			int v = queue.remove();
			for (int i = 0; i < numOfVertex; i++) {
				if (matrixAdj[v][i] != 0 && !dsDinhDuyet[i]) {
					dsDinhDuyet[i] = true;
					treeSpanning[v][i] = 1;
					edgeList.add("(" + (v + 1) + "," + (i + 1) + ")");
					queue.add(i);
				}
			}
		}
	}

	@Override
	public int[][] SpanningTreeByKruskalCoHuong() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return null;
		}
		resetVisited();
		int[][] minTreeSpanning = new int[numOfVertex][numOfVertex];
		List<Edges> listEdges = new ArrayList<DoThi.Edges>();
		for (int i = 0; i < numOfVertex; i++) {
			for (int j = 0; j < numOfVertex; j++) {
				if (matrixAdj[i][j] != 0) {
					listEdges.add(new Edges(i, j, matrixAdj[i][j]));
				}
			}
		}
		Collections.sort(listEdges, new CompareWeight());

		// tạo cha cho đỉnh
		int[] parent = new int[numOfVertex];
		for (int i = 0; i < numOfVertex; i++) {
			parent[i] = i;
		}

		int sumWeight = 0;
		int countEdges = 0;
		int indexList = 0;
		System.out.println("\nCay bao trum nho nhat:");
		while (countEdges < numOfVertex - 1 && indexList < listEdges.size()) {
			Edges e = listEdges.get(indexList); // chọn cạnh có trọng số nhỏ nhất
			int u = parent[e.u];
			int t = parent[e.v];
			// Kiểm tra xem cạnh có tạo thành chu trình không
			if (u != t) {
				countEdges++;
				minTreeSpanning[e.u][e.v] = e.w; // Thêm cạnh vào cây bao trùm theo hướng u -> v
				System.out.printf("(%d, %d) - %d\n", e.u + 1, e.v + 1, e.w);
				sumWeight += e.w;
				// cập nhập lại cha cho cho đỉnh
				for (int i = 0; i < numOfVertex; i++) {
					if (parent[i] == t) {
						parent[i] = u;
					}
				}
			}
			indexList++; // Tiếp tục với cạnh kế tiếp
		}
		System.out.println("Trọng số nhỏ nhất = " + sumWeight);
		printAdjacencyMatrix(minTreeSpanning);
		return minTreeSpanning;
	}

	@Override
	public int[][] SpanningTreeByPrimCoHuong(int v) {
		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return null;
		}

		resetVisited();
		int[][] tree = new int[numOfVertex][numOfVertex];
		int countEdge = 0;
		int sumWeight = 0;
		dsDinhDuyet[v] = true;
		System.out.println("\nCây bao trùm nhỏ nhất:");

		while (countEdge < numOfVertex - 1) {
			Edges minEdge = new Edges();
			int minW = Integer.MAX_VALUE;

			for (int i = 0; i < numOfVertex; i++) {
				if (dsDinhDuyet[i]) {
					for (int j = 0; j < numOfVertex; j++) {
						if (matrixAdj[i][j] != 0 && !dsDinhDuyet[j] && matrixAdj[i][j] < minW) {
							minEdge.u = i;
							minEdge.v = j;
							minEdge.w = matrixAdj[i][j];
							minW = matrixAdj[i][j];
						}
					}
				}
			}

			tree[minEdge.u][minEdge.v] = minEdge.w; // Chỉ thêm cạnh theo hướng u -> v
			countEdge++;
			System.out.printf("(%d, %d) - %d\n", minEdge.u + 1, minEdge.v + 1, minEdge.w);
			dsDinhDuyet[minEdge.v] = true;
			sumWeight += minEdge.w;
		}

		System.out.println("Trọng số nhỏ nhất của cây bao trùm = " + sumWeight);
		printAdjacencyMatrix(tree);
		return tree;
	}

	@Override
	public int[][] SpanningTreeByPrimCoHuong() {
		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return null;
		}

		resetVisited();
		int[][] tree = new int[numOfVertex][numOfVertex];
		int countEdge = 0;
		int sumWeight = 0;
		dsDinhDuyet[0] = true;
		System.out.println("\nCây bao trùm nhỏ nhất:");

		while (countEdge < numOfVertex - 1) {
			Edges minEdge = new Edges();
			int minW = Integer.MAX_VALUE;

			for (int i = 0; i < numOfVertex; i++) {
				if (dsDinhDuyet[i]) {
					for (int j = 0; j < numOfVertex; j++) {
						if (matrixAdj[i][j] != 0 && !dsDinhDuyet[j] && matrixAdj[i][j] < minW) {
							minEdge.u = i;
							minEdge.v = j;
							minEdge.w = matrixAdj[i][j];
							minW = matrixAdj[i][j];
						}
					}
				}
			}

			tree[minEdge.u][minEdge.v] = minEdge.w; // Chỉ thêm cạnh theo hướng u -> v
			countEdge++;
			System.out.printf("(%d, %d) - %d\n", minEdge.u + 1, minEdge.v + 1, minEdge.w);
			dsDinhDuyet[minEdge.v] = true;
			sumWeight += minEdge.w;
		}

		System.out.println("Trọng số nhỏ nhất của cây bao trùm = " + sumWeight);
		printAdjacencyMatrix(tree);
		return tree;
	}

	/*
	 * ========================== END ====================================
	 */

	/*
	 * ============================ CHUONG 5
	 * =========================================
	 */

	@Override
	public int[] dijsktra(int startPoint) {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong!");
			return null;
		} else {

			for (int i = 0; i < numOfVertex; i++) {
				for (int j = 0; j < numOfVertex; j++) {
					if (matrixAdj[i][j] < 0) {
						System.out.println("Do thi co trong so cua canh la so Am");
					}
				}
			}

			// Mảng lưu trữ khoảng cách ngắn nhất từ đỉnh nguồn đến các đỉnh khác
			int[] dist = new int[numOfVertex];
			boolean[] visited = new boolean[numOfVertex];
			// Mảng lưu trữ đỉnh trước kề của mỗi đỉnh trên đường đi ngắn nhất
			int[] prev = new int[numOfVertex];

			// Đặt khoảng cách ban đầu đến tất cả các đỉnh là vô cùng
			Arrays.fill(dist, Integer.MAX_VALUE);
			Arrays.fill(prev, -1);
			dist[startPoint] = 0;

			// Khởi tạo hàng đợi ưu tiên để luôn lấy ra đỉnh có khoảng cách ngắn nhất trước
			PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(v -> v.distance));

			// Thêm đỉnh nguồn vào hàng đợi với khoảng cách 0
			pq.offer(new Vertex(startPoint, 0));

			while (!pq.isEmpty()) {
				// Lấy ra đỉnh có khoảng cách ngắn nhất từ hàng đợi
				Vertex u = pq.poll();
				int uIndex = u.index;

				if (visited[uIndex])
					continue;
				visited[uIndex] = true;

				for (int v = 0; v < numOfVertex; v++) {
					if (matrixAdj[uIndex][v] != 0 && !visited[v]) {
						// Tính khoảng cách mới từ đỉnh nguồn đến v thông qua u
						int newDist = dist[uIndex] + matrixAdj[uIndex][v];

						// Nếu khoảng cách mới nhỏ hơn khoảng cách hiện tại, cập nhật khoảng cách
						if (newDist < dist[v]) {
							dist[v] = newDist; // Cập nhật khoảng cách ngắn nhất đến đỉnh v
							prev[v] = uIndex; // Cập nhật đỉnh trước kề của v
							pq.offer(new Vertex(v, newDist)); // Thêm đỉnh v vào hàng đợi với khoảng cách mới
						}
					}
				}
			}
			System.out.print("Đỉnh\t\t :  ");
			for (int i = 0; i < numOfVertex; i++) {
				System.out.print(i + "  ");
			}
			System.out.println("\nĐỉnh liền trước  : " + Arrays.toString(prev));
			System.out.println("Khoảng cách\t : " + Arrays.toString(dist));
			return dist; // Trả về mảng khoảng cách ngắn nhất từ đỉnh nguồn đến các đỉnh khác
		}
	}

	@Override
	public int[] dijsktra(int startPoint, int endPoint) {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong!");
			return null;
		} else {

			for (int i = 0; i < numOfVertex; i++) {
				for (int j = 0; j < numOfVertex; j++) {
					if (matrixAdj[i][j] < 0) {
						System.out.println("Do thi co trong so cua canh la so Am");
					}
				}
			}

			int[] dist = new int[numOfVertex]; // Mảng khoảng cách từ đỉnh nguồn đến các đỉnh khác
			int[] prev = new int[numOfVertex];
			boolean[] visited = new boolean[numOfVertex]; // Mảng đánh dấu các đỉnh đã được thăm

			// Đặt tất cả khoảng cách ban đầu là vô cùng, và đỉnh trước kề là -1
			Arrays.fill(dist, Integer.MAX_VALUE);
			Arrays.fill(prev, -1);
			dist[startPoint] = 0;

			// Khởi tạo hàng đợi ưu tiên để luôn lấy ra đỉnh có khoảng cách ngắn nhất trước
			PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(v -> v.distance));
			pq.offer(new Vertex(startPoint, 0));

			// Vòng lặp chính của thuật toán Dijkstra
			while (!pq.isEmpty()) {
				Vertex u = pq.poll(); // Lấy ra đỉnh có khoảng cách ngắn nhất từ hàng đợi
				int uIndex = u.index;
				if (visited[uIndex])
					continue; // Nếu đỉnh này đã được thăm, bỏ qua
				visited[uIndex] = true; // Đánh dấu đỉnh này đã được thăm

				if (uIndex == endPoint)
					break; // Nếu đã tìm thấy đường đi ngắn nhất đến endPoint, dừng lại

				// Duyệt tất cả các đỉnh kề của đỉnh u
				for (int v = 0; v < numOfVertex; v++) {
					if (matrixAdj[uIndex][v] != 0 && !visited[v]) { // Kiểm tra đỉnh kề chưa được thăm
						int newDist = dist[uIndex] + matrixAdj[uIndex][v]; // Tính khoảng cách mới đến đỉnh kề
						if (newDist < dist[v]) { // Nếu khoảng cách mới ngắn hơn khoảng cách hiện tại
							dist[v] = newDist; // Cập nhật khoảng cách mới
							prev[v] = uIndex; // Cập nhật đỉnh trước kề
							pq.offer(new Vertex(v, newDist)); // Thêm đỉnh kề vào hàng đợi với khoảng cách mới
						}
					}
				}
			}

			// In ra đường đi từ startPoint đến endPoint
			List<Integer> path = new ArrayList<>();
			int current = endPoint;
			while (current != -1) { // Duyệt ngược từ endPoint về startPoint thông qua mảng prev
				path.add(current);
				current = prev[current];
			}
			Collections.reverse(path); // Đảo ngược danh sách để có đường đi đúng thứ tự từ startPoint đến endPoint
			System.out.println("\nĐường đi từ " + startPoint + " đến " + endPoint + ": " + path);
			return dist; // Trả về mảng khoảng cách ngắn nhất từ đỉnh nguồn đến các đỉnh khác
		}
	}

	@Override
	public int[] floyd() {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong!");
			return null;
		} else {

			int infinity = 9999;

			// Khởi tạo giá trị vô cùng cho các cạnh không có trọng số
			for (int i = 0; i < matrixAdj.length; i++) {
				for (int j = 0; j < matrixAdj.length; j++) {
					if (matrixAdj[i][j] == 0) {
						matrixAdj[i][j] = infinity;
					}
				}
			}

			// Duyệt qua tất cả các đỉnh làm đỉnh trung gian
			for (int k = 0; k < matrixAdj.length; k++) {
				// Duyệt qua tất cả các cặp đỉnh (i, j)
				for (int i = 0; i < matrixAdj.length; i++) {
					for (int j = 0; j < matrixAdj.length; j++) {
						// Nếu đỉnh k làm đỉnh trung gian giúp giảm đường đi từ i đến j
						if (matrixAdj[i][k] != infinity && matrixAdj[k][j] != infinity
								&& matrixAdj[i][k] + matrixAdj[k][j] < matrixAdj[i][j]) {
							matrixAdj[i][j] = matrixAdj[i][k] + matrixAdj[k][j];
						}
					}
				}
			}

			// In ra ma trận trọng số đã cập nhật
			System.out.println("\nMa trận trọng số sau khi áp dụng thuật toán Floyd:");
			printAdjacencyMatrix(matrixAdj);

			return null;
		}
	}

	@Override
	public Path[] BellmanFord(int v) {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong!");
			return null;
		} else {
			int infinity = Integer.MAX_VALUE;
			Path[] result = new Path[numOfVertex];

			// Khởi tạo kết quả
			for (int i = 0; i < result.length; i++) {
				// reset result
				result[i] = new Path(infinity, -1);

				for (int j = 0; j < numOfVertex; j++) {
					if (matrixAdj[i][j] == 0) {
						matrixAdj[i][j] = infinity;
					} else {
						matrixAdj[i][j] = matrixAdj[i][j];
					}
				}

			}

			// Khởi tạo đỉnh xuất phát
			result[v].setW(0);

			// Duyệt qua tất cả các đỉnh
			for (int k = 0; k < numOfVertex - 1; k++)
				// Duyệt qua tất cả các cạnh
				for (int i = 0; i < numOfVertex; i++) {
					for (int j = 0; j < numOfVertex; j++)
						// Kiểm tra xem có cạnh từ i đến j không và có trọng số khác 0
						if (matrixAdj[i][j] != 0 && matrixAdj[i][j] != infinity) {
							// Kiểm tra khoảng cách từ i đến j có được cải thiện không
							if (result[i].getW() < infinity && result[i].getW() + matrixAdj[i][j] < result[j].getW()) {
								// Cập nhật đỉnh k
								result[j].setW(result[i].getW() + matrixAdj[i][j]);
								result[j].setPre(i);
							}
						}
				}

			// Kiểm tra chu trình âm
			for (int j = 0; j < numOfVertex; j++)
				for (int k = 0; k < numOfVertex; k++) {
					if (matrixAdj[j][k] != 0 && matrixAdj[j][k] != infinity && result[j].getW() != infinity
							&& result[j].getW() + matrixAdj[j][k] < result[k].getW()) {
						System.out.println("\nĐồ thị có chu trình âm.");
						return null;
					}
				}
			System.out.println("\nKết quả của thuật toán BELLMANFORD:");
			printBellmanFordResult(result);
			return result;
		}
	}

	private void printBellmanFordResult(Path[] result) {
		System.out.printf("%-10s%-10s%-10s\n", "Vertex", "Weight", "Predecessor");
		for (int i = 0; i < result.length; i++) {
			Path sb = result[i];
			System.out.printf("%-10d %-10d %-10d\n", i + 1, sb.getW(), sb.getPre() + 1);
		}
	}

	@Override
	public void warshall() {
			int[][] R = new int[numOfVertex][numOfVertex];
			for (int i = 0; i < numOfVertex; i++) {
				for (int j = 0; j < numOfVertex; j++) {
					R[i][j] = matrixAdj[i][j];

				}

			}
			// duyet warshall
			for (int k = 0; k < numOfVertex; k++) {
				for (int i = 0; i < numOfVertex; i++) {
					for (int j = 0; j < numOfVertex; j++) {
						R[i][j] = R[i][j] | (R[i][k] & R[k][j]);
					}
				}
				System.out.println("Ma tran R (" + (k + 1) + ")");
				printAdjacencyMatrix(R);
			
		}
	}

}

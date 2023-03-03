package FourInALine;
import java.util.ArrayList;


public class AlphaBeta {

	int depth;
	ArrayList<Integer> x;

	public AlphaBeta(int depth) {
		this.depth = depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}


	//pruning
	public Board Action (Board board) throws CloneNotSupportedException {


		int alpha = -999999; //-infinity
		int beta = 999999; //+infinity

		int val = max(board, depth, alpha, beta, 0, 0);

		ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>> () ;
		children = board.getChildren();

		board = board.generateSuccessor('X', x.get(0), x.get(1));

		System.out.println("-------- AI -------");
		board.printBoard();
		System.out.println("AI move: " + ((char)(x.get(0)+97)) + (x.get(1)+1));
		System.out.println();

		return board;
	}

	//max
	public int max(Board board, int depth, int alpha, int beta, int next_X, int next_Y) throws CloneNotSupportedException {
		ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();

		if(depth == 0) {
			return board.evaluation(next_X, next_Y);
		}

		children = board.getChildren();

		int value = -999999;

		for(int i=0; i<children.size(); i++) {

			int min_val = min(board.generateSuccessor('X', children.get(i).get(0),children.get(i).get(1)), depth-1, alpha, beta, children.get(i).get(0), children.get(i).get(1));

			if(value < min_val) {
				value = min_val;
				this.x = children.get(i);
			}
			if(value > alpha) {
				alpha = value;
			}
			if(alpha >= beta) {
				break;
			}
		}
		return value;
	}


	//min
	public int min(Board board, int depth, int alpha, int beta, int next_X, int next_Y) throws CloneNotSupportedException {

		ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();

		if(depth == 0) {
			return board.evaluation(next_X, next_Y);
		}

		children = board.getChildren();

		int value = 999999;

		for(int i =0; i<children.size();i++)
		{
			int max_val = max(board.generateSuccessor('O',children.get(i).get(0),children.get(i).get(1)), depth-1, alpha, beta, children.get(i).get(0), children.get(i).get(1));

			if(max_val < value)
			{
				value = max_val;
			}

			if(value < beta) {
				beta = value;
			}

			if(alpha >= beta) { 
				break;
			}
		}
		return value;
	}
	
	
	

}








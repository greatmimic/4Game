package FourInALine;
import java.util.ArrayList;

public class Board {

	int row = 8;
	int col = 8;
	char[][] board;

	//create empty board
	public Board() {
		this.board = new char[row][col];
		//blank spaces on board rep by - (dash)
		for(int i=0; i<row; i++) {
			for(int j=0; j<col; j++) {
				this.board[i][j] = '-';
			}
		}
	}

	//clone board
	public Object clone() throws CloneNotSupportedException {
		Board new_board = new Board();

		for(int i=0; i<this.row; i++) {
			new_board.board[i] = (char[]) this.board[i].clone();
		}
		return new_board;
	}

	//generate successor
	public Board generateSuccessor(char c, int row, int col) throws CloneNotSupportedException {
		Board new_board = (Board)this.clone();
		new_board.board[row][col] = c;

		return new_board;
	}

	//print board
	public void printBoard() {
		

		System.out.println("    ");
		System.out.print("  ");
		//board 1-8
		for(int col_counter=1; col_counter<this.row+1; col_counter++) {
			System.out.print(col_counter+" ");
		}
		System.out.println();
		
		//board A-H
		for(int i=0; i<this.row; i++) {
			System.out.print((char)(i+65)+" ");
			for(int j=0; j<this.col; j++) {
				System.out.print(this.board[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("-------------------");
	}

	//check win
	public boolean isWin(char c) {
		//check 4 char in a line
		String find = "" + c + "" + c + "" + c + "" + c;

		//check row
		for(int i=0; i<this.row; i++) {
			if(String.valueOf(this.board[i]).contains(find)) {
				return true;
			}
		}

		//check col
		for(int j=0; j<this.col; j++) {
			String column="";
			for(int i=0; i<this.row; i++) {
				column += this.board[i][j];
			}
			if(column.contains(find)) {
				return true;
			}
		}
		return false;

	}

	//get children
	public ArrayList<ArrayList<Integer>> getChildren() {
		ArrayList<ArrayList<Integer>> outer = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> inner = new ArrayList<Integer>();

		for(int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.col; j++) {
				if(this.board[i][j]=='-') {
					inner.add(i);
					inner.add(j);
					outer.add(inner);
					inner = new ArrayList<Integer>();
				}
			}
		}
		return outer;
	}

	//check neighbor
	public boolean hasNeighbor(int x, int y ) {
		int distance = 2;

		for(int i = x-distance; i <= x+distance; i++) {
			if(i<0||i>=8) continue;
			if(i == x) continue;
			if(this.board[i][y] != '-') return true;
		}

		for(int j = y-distance; j<= y+distance; j++) {
			if(j<0||j>=8) continue;
			if(j == y) continue;
			if(this.board[x][j] != '-') return true;
		}

		return false;
	}

	//evaluation
	public int evaluation(int X, int Y) {

		int result = 0;

		Point_score point_score = new Point_score();

		if(this.board[X][Y] == 'X') {
			result = point_score.score_point(this.board, X, Y, 'X');
		}
		if(this.board[X][Y] == 'O') {
			result = (point_score.score_point(this.board, X, Y, 'O'))*(-1);
		}

		return result;
	}
	
	//check for wrong input
	public boolean inputcheck(Board board, int row, int col) {

		if(col > 7 || row > 7) {
			System.out.println("please enter correct value");
			return false;
		}

		
		if(board.board[row][col]!='-') {
			return false;
		}
		return true;
	}
	
	//check for draw
	public boolean isDraw() {
		for(int i = 0; i<this.row; i++) {
			for(int j = 0; j<this.col; j++) {
				if(this.board[i][j]=='-') {
					return false;
				}
			}
		}
		return true;
	}
	
	//check for killer move (winning move)
	public boolean killer_check(Board board) throws CloneNotSupportedException {
		char x = 'X';
		int move = 0;
		
		//possible setups for killer ai move
		ArrayList<String> killerCases = new ArrayList<String>();
		killerCases.add("-" + x + x + x + ""); // _ xxx
		killerCases.add( "" + x + x + x + "-"); // xxx _
		killerCases.add("-" + x + x + x + "-"); // _ xxx _
		killerCases.add("" + x + "-" + x + x + ""); // x_xx
		killerCases.add("" + x + x + "-" + x + ""); // xx_x
		
		for (int check = 0; check<killerCases.size(); check++) {
			
			//check row
			for(int i=0; i<this.row; i++) {
				if(String.valueOf(this.board[i]).contains(killerCases.get(check))) {
					
					if(check == 0 || check ==1 || check==2) {
						for (int j = 0; j< this.col-2; j++) {
							if(this.board[i][j] == x && this.board[i][j+1] == x && this.board[i][j+2] == x ) {
								if(j-1>=0 && this.board[i][j-1] == '-') {
									board = board.generateSuccessor('X',i, j-1);
									move = j-1;
								}
								else if(j+3<8 && this.board[i][j+3]=='-') {
									board = board.generateSuccessor('X',i, j+3);
									move = j+3;
								}
								
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(i+97)) + move);
								System.out.println();
							}
						}
					}
					else if(check ==3 || check==4) {
						for (int j = 0; j< this.col-1; j++) {
							if(this.board[i][j] == x && this.board[i][j+1] == x) {
								if(j-2 >= 0 && this.board[i][j-1] == '-' && this.board[i][j-2] == x) {
									board = board.generateSuccessor('X',i, j-1);
									move = j-1;
								}
								else if(j+3 < 8 && this.board[i][j+2] == '-' && this.board[i][j+3] == x) {
									board = board.generateSuccessor('X',i, j+2);
									move = j+2;
								}
								
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(i+97)) + move);
								System.out.println();
							}
						}	
					}
					return true;
				}
			}

			//check column
			for(int j=0; j<this.col; j++){
				String col="";
				for(int i=0; i<this.row; i++)
					col+=this.board[i][j];
					
				if(col.contains(killerCases.get(check))) {
					
					if(check ==0 || check ==1 || check ==2){
						for (int i = 0; i< this.col-2; i++) {
							if(this.board[i][j] == x && this.board[i+1][j] == x && this.board[i+2][j] == x ) {
								if(i-1>=0 && this.board[i-1][j] == '-') {
									board = board.generateSuccessor('X',i-1, j);
									move = i-1;
								}
								else if(i+3<8 && this.board[i+3][j]=='-') {
									board = board.generateSuccessor('X',i+3, j);
									move = i+3;
								}
								
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(move+97)) + (j+1));
								System.out.println();
							}
						}
					}
					
					else if(check ==3 || check==4) {
						for (int i = 0; i< this.row-1; i++) {
							if(this.board[i][j] == x && this.board[i+1][j] == x) {
								if(i-2 >= 0 && this.board[i-1][j] == '-' && this.board[i-2][j] == x) {
									board = board.generateSuccessor('X',i-1, j);
									move = i-1;
								}
								else if(i+3 < 8 && this.board[i+2][j] == '-' && this.board[i+3][j] == x) {
									board = board.generateSuccessor('X',i+2, j);
									move = i+2;
								}
								
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(move+97)) + (j+1));
								System.out.println();
							}
						}	
					}
					
					return true;
				}
					
			}
		}
		return false;
	}
	
	//check for moves prior to possible killer move
	public Boolean pre_killer_check(Board board) {

		char o = 'O';
		char x = 'X';
		
		ArrayList<String> PrekillerCases = new ArrayList<String>();
		PrekillerCases.add("-" + x + x + "-" + "-");
		PrekillerCases.add("-" + "-" + x + x + "-");
		PrekillerCases.add("-" + x + "-" + x + "-");
		
		ArrayList<String> checkOpponent = new ArrayList<String>();
		checkOpponent.add("-" + o + o + o + "");
		checkOpponent.add( "" + o + o + o + "-");
		checkOpponent.add("-" + o + o + o + "-");
		checkOpponent.add( "" + o + "-" + o + o + "");
		checkOpponent.add( "" + o + o + "-" + o + "");
		
		// check opponent
		for (int check = 0; check<checkOpponent.size(); check++) {
			
			for(int i=0; i<this.row; i++)
				if(String.valueOf(this.board[i]).contains(checkOpponent.get(check)))
					return false;
			
			//check column
			for(int j=0; j<this.col; j++){
				String col="";
				for(int i=0; i<this.row; i++)
					col+=this.board[i][j];
					
				if(col.contains(checkOpponent.get(check)))
					return false;
			}
		}
		
		//self check
		for (int check = 0; check<PrekillerCases.size(); check++) {
			
			for(int i=0; i<this.row; i++)
				if(String.valueOf(this.board[i]).contains(PrekillerCases.get(check)))
					return true;
			
			//check column
			for(int j=0; j<this.col; j++){
				String col="";
				for(int i=0; i<this.row; i++)
					col+=this.board[i][j];
					
				if(col.contains(PrekillerCases.get(check)))
					return true;
			}
		}
		
		return false;
	}
	
	//check for moves that will lead to pre killer moves
	public Board prekiller_move(Board board, char c) throws CloneNotSupportedException {
		char x = c;
		
		ArrayList<String> PrekillerCases = new ArrayList<String>();
		PrekillerCases.add("-" + x + x + "-");
		PrekillerCases.add("-" + x + "-" + x + "-");
		
		for (int check = 0; check<PrekillerCases.size(); check ++) {
			for(int i=0; i<this.row; i++) {
				if(String.valueOf(this.board[i]).contains(PrekillerCases.get(check))) {
					
					if(check == 0) {
						for (int j = 0; j< this.col-3; j++) {
							if(this.board[i][j] == '-' && this.board[i][j+1] == x && this.board[i][j+2] == x &&  this.board[i][j+3] == '-') {
								if(j-1>=0 && this.board[i][j-1] =='-') {
									board = board.generateSuccessor('X',i, j);
									System.out.println("-------- AI -------");
									board.printBoard();
									System.out.println("AI move: " + ((char)(i+97)) + (j+1));
									System.out.println();
								}
								else if(j+4<8 && this.board[i][j+4] =='-') {
									board = board.generateSuccessor('X',i, j+3);
									System.out.println("-------- AI -------");
									board.printBoard();
									System.out.println("AI move: " + ((char)(i+97)) + (j+1+3));
									System.out.println();
								}
							}
						}
					}
					
					if(check == 1) {
						for (int j = 0; j< this.col-4; j++) {
							if(this.board[i][j] == '-' && this.board[i][j+1] == x && this.board[i][j+2] == '-' && this.board[i][j+3] == x &&  this.board[i][j+4] == '-') {
								board = board.generateSuccessor('X',i, j+2);
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(i+97)) + (j+1+2));
								System.out.println();
							}	
						}
					}
					return board;
				}	
			}
			
			//check column
			for(int j=0; j<this.col; j++){
				String col="";
				for(int i=0; i<this.row; i++)
					col+=this.board[i][j];
					
				if(col.contains(PrekillerCases.get(check))) {
					if(check == 0) {
						for (int i = 0; i< this.row-3; i++) {
							if(this.board[i][j] == '-' && this.board[i+1][j] == x && this.board[i+2][j] == x &&  this.board[i+3][j] == '-') {
								if(i-1>=0 && this.board[i-1][j] =='-') {
									board = board.generateSuccessor('X',i, j);
									System.out.println("-------- AI -------");
									board.printBoard();
									System.out.println("AI move: " + ((char)(i+97)) + (j+1));
									System.out.println();
								}
								else if(i+4<8 && this.board[i+4][j] =='-') {
									board = board.generateSuccessor('X',i+3, j);
									System.out.println("-------- AI -------");
									board.printBoard();
									System.out.println("AI move: " + ((char)(i+97+3)) + (j+1));
									System.out.println();
								}
							}
						}
					}
					
					
					if(check == 1) {
						for (int i = 0; i< this.row-4; i++) {
							if(this.board[i][j] == '-' && this.board[i+1][j] == x && this.board[i+2][j] == '-' && this.board[i+3][j] == x &&  this.board[i+4][j] == '-') {
								board = board.generateSuccessor('X',i+2, j);
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(i+97+2)) + (j+1));
								System.out.println();
							}	
						}
					}
				}
			}
		}

		return board;
	}
	
	//function for AI to decide to attack or defend
	public int AttackOrDefend(Board board) {
		int Attack = 3;
		int Defence = 2;
		char o = 'O';
		char x = 'X';
		
		ArrayList<String> DefenceCases = new ArrayList<String>();
		DefenceCases.add("-" + o + o + o + "");
		DefenceCases.add("" + o + o + o + "-");
		DefenceCases.add("-" + o + o + o + "-");
		DefenceCases.add( "" + o + "-" + o + o + "");
		DefenceCases.add( "" + o + o + "-" + o + "");
		
		ArrayList<String> AttackCases = new ArrayList<String>();
		AttackCases.add("" + x + x + "-" + "-");
		AttackCases.add("-" + "-" + x + x + "");
		AttackCases.add("-" + x + x + "-");
		AttackCases.add("" + x + "-" + x + "-");
		AttackCases.add("-" + x + "-" + x + "");
		
				
		for (int check = 0; check<DefenceCases.size(); check++) {
			for(int i=0; i<this.row; i++)
				if(String.valueOf(this.board[i]).contains(DefenceCases.get(check))) {
					return Defence;
				}
					
			
			//check column
			for(int j=0; j<this.col; j++){
				String col="";
				for(int i=0; i<this.row; i++)
					col+=this.board[i][j];
					
				if(col.contains(DefenceCases.get(check))) {
					return Defence;
				}
			}
		}
		
		for (int check = 0; check<AttackCases.size(); check++) {
			
			for(int i=0; i<this.row; i++)
				if(String.valueOf(this.board[i]).contains(AttackCases.get(check))) {
					return Attack;
				}
			
			//check column
			for(int j=0; j<this.col; j++){
				String col="";
				for(int i=0; i<this.row; i++)
					col+=this.board[i][j];
					
				if(col.contains(AttackCases.get(check))) {
					return Attack;
				}
			}
		}
		return Attack;
	}
	
	//AI to check for user pre killer move
	public Boolean checkDefend_preKiller() {
		char o = 'O';
		char x = 'X';
		
		ArrayList<String> selfCheck = new ArrayList<String>();
		selfCheck.add("-" + x + x + "-");
		selfCheck.add("" + x + x + "-" + "-");
		selfCheck.add("-" + "-" + x + x + "");
		selfCheck.add("" + x + "-" + x + "-");
		selfCheck.add("-" + x + "-" + x + "");
		
		//possible user prekiller case
		ArrayList<String> OpponentPrekillerCases = new ArrayList<String>();
		OpponentPrekillerCases.add("-" + o + o + "-" + "-");
		OpponentPrekillerCases.add("-" + "-" + o + o + "-");
		OpponentPrekillerCases.add("-" + o + "-" + o + "-");
		
		for (int check = 0; check<selfCheck.size(); check++) {
			for(int i=0; i<this.row; i++)
				if(String.valueOf(this.board[i]).contains(selfCheck.get(check))) {
					return false;
				}
			
			//check column
			for(int j=0; j<this.col; j++){
				String col="";
				for(int i=0; i<this.row; i++)
					col+=this.board[i][j];
					
				if(col.contains(selfCheck.get(check))) {
					return false;
				}
			}
		}
		
		
		for (int check = 0; check<OpponentPrekillerCases.size(); check++) {
			for(int i=0; i<this.row; i++)
				if(String.valueOf(this.board[i]).contains(OpponentPrekillerCases.get(check))) {
					System.out.println("defense 3");
					return true;
				}
			
			//check column
			for(int j=0; j<this.col; j++){
				String col="";
				for(int i=0; i<this.row; i++)
					col+=this.board[i][j];
					
				if(col.contains(OpponentPrekillerCases.get(check))) {
					System.out.println("defense 4");
					return true;
				}
			}
		}
		return false;
	}
	
}

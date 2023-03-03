package FourInALine;
import java.util.Scanner;


public class main {

	public static void main(String[] args) throws CloneNotSupportedException {

		System.out.print("Enter maximum amount of time: ");
		Scanner sc = new Scanner(System.in);
		int time = sc.nextInt();

		System.out.print("Enter the depth: ");
		Scanner dep = new Scanner(System.in);
		int depth = dep.nextInt();

		System.out.print("Who goes first?: ( 'C' for Computer AI, 'H' for Human ) ");
		Scanner AiorHuman = new Scanner(System.in);
		char order = AiorHuman.next().charAt(0);
		while(order != 'C' && order != 'H' && order != 'c' && order != 'h' ) {
			System.out.print("Please enter a correct first hand: ( 'C' for Computer AI, 'H' for Human ) ");
			Scanner correction = new Scanner(System.in);
			order = AiorHuman.next().charAt(0);
		}

		System.out.println();
		System.out.println(" Maximum time (sec): " + time );
		System.out.println(" Game Depth: " + depth + "          ");
		if(order == 'C' || order == 'c') {
			System.out.println(" First Move: " + "AI" + "         ");
		}
		else {
			System.out.println(" First Move: " + "Human" + "     ");
		}
		System.out.println();
		System.out.println("---Initial Board---");	

		AlphaBeta Agent = new AlphaBeta(depth);
		Board board = new Board();
		board.printBoard();
		System.out.println();





		//random move

		// AI first
		if (order == 'C' || order == 'c'){
			RandomMove preMove = new RandomMove();

			board = preMove.AI_first_move(board); 	// Random AI move
			board = human_move(board); 				// human move

			board = preMove.AI_second_move(board); 	// Random AI move
			board = human_move(board);				// human move
		}

		// Human first 
		if (order == 'H' || order == 'h'){
			RandomMove preMove = new RandomMove();

			board = human_move(board);				// human move
			board = preMove.AI_connect_move(board);	// Random AI move
			board = human_move(board);				// human move

		}


		//main game functions
		while(true) {

			// check killer move
			if(board.killer_check(board)) {
				System.out.println("AI win");
				break;
			}

			// check Pre-killer move
			Boolean pre_killer_check = board.pre_killer_check(board);

			if(pre_killer_check) {
				System.out.println("in1");
				board = board.prekiller_move(board, 'X');
			}

			// check Pre-killer move for opponent
			Boolean checkDefend_preKiller = board.checkDefend_preKiller();

			if(!pre_killer_check ) {
				if(checkDefend_preKiller) {
					board = board.prekiller_move(board, 'O');
				}
			}

			// decide Attack or Defend
			Agent.setDepth(board.AttackOrDefend(board));


			// Computer AI play
			if (!pre_killer_check){
				if(!checkDefend_preKiller) {
					board = Agent.Action(board); // AI move
				}				
			}

			// check if AI wins the game
			if(board.isWin('X')) {
				System.out.println("AI win");
				break;
			}

			// Human play
			board = human_move(board); // Human move

			// check if human wins the game
			if(board.isWin('O')) {
				System.out.println("Human win");
				break;
			}

			// check if draw
			if(board.isDraw()) {
				System.out.println("Draw");
				break;
			}
		}
	}
			//function for human moves
		public static Board human_move(Board board) throws CloneNotSupportedException {
			
			int row=0, col=0;
			char c='O';
			
			System.out.print("Enter your next move: ");
			Scanner in3 = new Scanner(System.in);
			String temp = in3.nextLine();
			
			char[] splitted = temp.toCharArray();
			
			row = splitted[0]-97;
			col = Integer.parseInt(String.valueOf(splitted[1]))-1; 
			
			//if input is wrong
			while(!board.inputcheck(board, row, col)) {
				System.out.println();
				System.out.println("The move is not valid");
				System.out.println();
				System.out.print("Enter your next move: ");
				in3 = new Scanner(System.in);
				temp = in3.nextLine();
				
				splitted = temp.toCharArray();
				
				row = splitted[0]-97;
				col = Integer.parseInt(String.valueOf(splitted[1])); 
			}
			
			board = board.generateSuccessor(c, row, col );
			
			System.out.println();
			System.out.println("----- HUMAN ----");
			board.printBoard(); 
			System.out.println("Human move: " + splitted[0] + splitted[1]);
			System.out.println();
			
			return board;
		}
		
	}



import java.util.*;

public class Game {

    public static final char CROSS = 'X';
    public static final char NOUGHT = 'O';
    public static final int ROWS = 3;
    public static final int COLUMNS = 3;

    //add winning bets to be done after loosing in an array.
    public List<String> punishments;
    private char[][] gameBoard;
    private Player[] players;
    private Set<Integer> playedPositions;

    public Game(Player[] players){
        gameBoard = new char[ROWS][COLUMNS];
        playedPositions = new HashSet<>();
        this.players = new Player[2];
        punishments = new ArrayList<>();
        setPlayers(players);
        initialize();
    }

    private void initialize() {
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++) {
                int rep = (ROWS * i) + j;
                gameBoard[i][j] = Integer.toString(rep).charAt(0);
            }
        }
    }

    private void useDefaultPunishments() {
        punishments.add("Dish out a dirty little secret of yours.");
        punishments.add("Bend over backwards and kiss the wall.");
        punishments.add("Inhale my Smelly sock fumes.");
        punishments.add("Undress and wiggle, wiggle, wiggle while saying shouting \"Do I look sexy now\". ");
        punishments.add("Eat the hot sauce.");
    }

    public void printPunishments() {
        System.out.println("The sucker does the deed.");
        printSeparator();
        for(String p : punishments) {
            System.out.println(p);
        }
    }
    private void setPlayers(Player[] play) {
        int i = 0;
        for(Player p: play) {
            this.players[i] = p;
            i++;
        }
    }

    private void setPieceAtPosition(Player p, int position) {
        int row = position/ROWS;
        int col = position % ROWS;

        gameBoard[row][col] = p.getPiece();
    }

    private char getPieceAtPosition(int position) {
        int row = position/ROWS;
        int col = position % ROWS;

        return gameBoard[row][col];
    }

    private Player switchTurns(Player p) {
        if (p == players[0]) return players[1];
        return players[0];
    }

    private Player findFirstPlayer() {
        if (players[0].getPiece() == CROSS) return players[0];
        return players[1];
    }

    private static void printSeparator() {

        System.out.println("----------------------------------------\n");
    }

    public void play() {
        Player presentPlayer = findFirstPlayer();
        printSeparator();
        System.out.println("\n'CROSS' starts the game.");
        printSeparator();
        Scanner sc = new Scanner(System.in);
        this.print();
        while (  !(this.isBoardFull()) && !(this.gameOver())) {
                System.out.println("Enter a position on the board");
                System.out.print(presentPlayer.getName() + ": ");
                int position = sc.nextInt();
                printSeparator();
                if ((playedPositions.add(position))) {
                    this.setPieceAtPosition(presentPlayer, position);
                    this.print();
                    presentPlayer = this.switchTurns(presentPlayer);
                } else {
                    System.out.print("Pick another position, That spot is occupied.\n");
                }
        }

        if (this.isBoardFull() && this.gameOver() == false) {
            System.out.println("You both suck!!!\n");
            System.out.println("This game was a draw");
        }
    }

    public boolean isBoardFull() {
        for (char[] c : gameBoard) {
            for (char p : c) {
                if (p != CROSS && p != NOUGHT) return false;
            }
        }
        return true;
    }

    public void print() {
        for(char[] c : gameBoard) {
            for (char p : c) {
                System.out.print(p + "\t");
            }
            System.out.println("\n");
        }

    }


    //refactor to get winner and the piece that won.
    public boolean gameOver() {
        return  this.getPieceAtPosition(0) == this.getPieceAtPosition(1) && this.getPieceAtPosition(0) == this.getPieceAtPosition(2) ||
                this.getPieceAtPosition(0) == this.getPieceAtPosition(3) && this.getPieceAtPosition(0) == this.getPieceAtPosition(6) ||
                this.getPieceAtPosition(1) == this.getPieceAtPosition(4) && this.getPieceAtPosition(1) == this.getPieceAtPosition(7) ||
                this.getPieceAtPosition(2) == this.getPieceAtPosition(5) && this.getPieceAtPosition(2) == this.getPieceAtPosition(8) ||
                this.getPieceAtPosition(3) == this.getPieceAtPosition(4) && this.getPieceAtPosition(3) == this.getPieceAtPosition(5) ||
                this.getPieceAtPosition(6) == this.getPieceAtPosition(7) && this.getPieceAtPosition(7) == this.getPieceAtPosition(8) ||
                this.getPieceAtPosition(0) == this.getPieceAtPosition(4) && this.getPieceAtPosition(0) == this.getPieceAtPosition(8) ||
                this.getPieceAtPosition(2) == this.getPieceAtPosition(4) && this.getPieceAtPosition(4) == this.getPieceAtPosition(6);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to extreme 'Noughts and Crosses'.");
        printSeparator();

        System.out.print("Enter player 1 name: ");
        String p1Name= sc.nextLine();
        System.out.print(p1Name +  ", choose (x or o): ");
        char p1Piece = sc.nextLine().toUpperCase().charAt(0);
        Player p1 = new Player(p1Name, p1Piece);
        printSeparator();

        System.out.print("Enter player 2 name: ");
        String p2Name= sc.nextLine();
        char p2Piece;
        if (p1Piece == CROSS) {
            p2Piece = NOUGHT;
        } else {
            p2Piece = CROSS;
        }
        System.out.println(p2Name + ", you are using: " + p2Piece);
        Player p2 = new Player(p2Name, p2Piece);
        Player[] players = {p1, p2};
        Game game = new Game(players);
        printSeparator();
        game.useDefaultPunishments();
        game.printPunishments();
        game.play();

    }


}

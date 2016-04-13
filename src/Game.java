import java.util.*;

public class Game {

    public static final char X = 'x';
    public static final char O = 'o';
    public static final int ROWS = 3;
    public static final int COLUMNS = 3;

    private char[][] gameBoard;
    private Player[] players;
    private Set<Integer> playedPositions;

    public Game(Player[] players){
        gameBoard = new char[ROWS][COLUMNS];
        playedPositions = new HashSet<>();
        this.players = new Player[2];
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
        if (players[0].getPiece() == X) return players[0];
        return players[1];
    }

    private static void printSeparator() {
        System.out.println("----------------------------------------\n");
    }

    public void play() {
        Player presentPlayer = findFirstPlayer();
        printSeparator();
        System.out.println("\n'X' starts the game.");
        printSeparator();
        Scanner sc = new Scanner(System.in);
        this.print();
        while (  !(this.isBoardFull()) && !(this.gameOver())) {
                System.out.println("Enter a position on the board");
                System.out.print(presentPlayer.getName() + ": ");
                int position = sc.nextInt();
                if ((playedPositions.add(position))) {
                    this.setPieceAtPosition(presentPlayer, position);
                    this.print();
                    presentPlayer = this.switchTurns(presentPlayer);
                } else {
                    System.out.print("Pick another position, That spot is occupied.\n");
                }
        }

        if (this.isBoardFull()) {
            System.out.println("You both suck!!!\n");
            System.out.println("This game was a draw");
        }
    }

    public boolean isBoardFull() {
        for (char[] c : gameBoard) {
            for (char p : c) {
                if (p != X && p != O) return false;
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

        System.out.println("Welcome to intense 'X' and 'O'.");
        printSeparator();

        System.out.print("Enter player 1 name: ");
        String p1Name= sc.next();
        System.out.print(p1Name +  ", choose (x or o): ");
        char p1Piece = sc.next().toLowerCase().charAt(0);
        Player p1 = new Player(p1Name, p1Piece);
        printSeparator();

        System.out.print("Enter player 2 name: ");
        String p2Name= sc.next();
        char p2Piece;
        if (p1Piece == 'x') {
            p2Piece = 'o';
        } else {
            p2Piece = 'x';
        }
        System.out.println(p2Name + ", you are using: " + p2Piece);
        Player p2 = new Player(p2Name, p2Piece);
        Player[] players = {p1, p2};
        Game game = new Game(players);
        game.play();

    }


}

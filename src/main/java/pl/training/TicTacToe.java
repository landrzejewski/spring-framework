package pl.training;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Silnik gry w kółko i krzyżyk (Tic-Tac-Toe) w jednej klasie.
 *
 * Plansza jest indeksowana od 0 do 8:
 *
 *   0 | 1 | 2
 *  ---+---+---
 *   3 | 4 | 5
 *  ---+---+---
 *   6 | 7 | 8
 *
 * Możliwości:
 *  - wykonywanie i cofanie ruchów z walidacją,
 *  - wykrywanie wygranej, remisu i linii zwycięskiej,
 *  - historia ruchów,
 *  - AI oparte na algorytmie minimax z cięciami alfa-beta (gra bezbłędnie),
 *  - prosta gra w konsoli (metoda main).
 */
public class TicTacToe {

    public static final char EMPTY = ' ';
    public static final char X = 'X';
    public static final char O = 'O';

    public enum State { IN_PROGRESS, X_WINS, O_WINS, DRAW }

    private static final int[][] LINES = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // wiersze
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // kolumny
            {0, 4, 8}, {2, 4, 6}             // przekątne
    };

    private final char[] board = new char[9];
    private final List<Integer> history = new ArrayList<>();
    private char currentPlayer;
    private final char startingPlayer;

    public TicTacToe() {
        this(X);
    }

    public TicTacToe(char startingPlayer) {
        if (startingPlayer != X && startingPlayer != O) {
            throw new IllegalArgumentException("Gracz musi być 'X' lub 'O'");
        }
        this.startingPlayer = startingPlayer;
        reset();
    }

    // ------------------------------------------------------------------
    // Podstawowe API
    // ------------------------------------------------------------------

    /** Czyści planszę i rozpoczyna nową grę. */
    public void reset() {
        for (int i = 0; i < 9; i++) board[i] = EMPTY;
        history.clear();
        currentPlayer = startingPlayer;
    }

    /** Wykonuje ruch bieżącego gracza na polu 0–8. */
    public void makeMove(int cell) {
        if (getState() != State.IN_PROGRESS) {
            throw new IllegalStateException("Gra jest już zakończona");
        }
        if (cell < 0 || cell > 8) {
            throw new IllegalArgumentException("Pole poza planszą: " + cell);
        }
        if (board[cell] != EMPTY) {
            throw new IllegalArgumentException("Pole " + cell + " jest zajęte");
        }
        board[cell] = currentPlayer;
        history.add(cell);
        currentPlayer = opponent(currentPlayer);
    }

    /** Wykonuje ruch po współrzędnych (wiersz, kolumna) 0–2. */
    public void makeMove(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            throw new IllegalArgumentException("Współrzędne poza planszą");
        }
        makeMove(row * 3 + col);
    }

    /** Cofa ostatni ruch. Zwraca false, jeśli nie ma czego cofać. */
    public boolean undo() {
        if (history.isEmpty()) return false;
        int last = history.remove(history.size() - 1);
        board[last] = EMPTY;
        currentPlayer = opponent(currentPlayer);
        return true;
    }

    public boolean isValidMove(int cell) {
        return cell >= 0 && cell <= 8 && board[cell] == EMPTY
                && getState() == State.IN_PROGRESS;
    }

    public List<Integer> getAvailableMoves() {
        List<Integer> moves = new ArrayList<>();
        if (getState() != State.IN_PROGRESS) return moves;
        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) moves.add(i);
        }
        return moves;
    }

    public State getState() {
        char w = findWinner();
        if (w == X) return State.X_WINS;
        if (w == O) return State.O_WINS;
        return history.size() == 9 ? State.DRAW : State.IN_PROGRESS;
    }

    public boolean isGameOver() {
        return getState() != State.IN_PROGRESS;
    }

    /** Zwraca 'X', 'O' albo EMPTY, jeśli nikt nie wygrał. */
    public char getWinner() {
        return findWinner();
    }

    /** Zwraca indeksy pól zwycięskiej linii albo null. */
    public int[] getWinningLine() {
        for (int[] line : LINES) {
            if (board[line[0]] != EMPTY
                    && board[line[0]] == board[line[1]]
                    && board[line[1]] == board[line[2]]) {
                return line.clone();
            }
        }
        return null;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char getCell(int cell) {
        return board[cell];
    }

    public char getCell(int row, int col) {
        return board[row * 3 + col];
    }

    public char[] getBoard() {
        return board.clone();
    }

    public List<Integer> getHistory() {
        return new ArrayList<>(history);
    }

    // ------------------------------------------------------------------
    // AI – minimax z cięciami alfa-beta
    // ------------------------------------------------------------------

    /** Zwraca najlepszy ruch dla bieżącego gracza albo -1, gdy gra się skończyła. */
    public int getBestMove() {
        if (isGameOver()) return -1;
        char me = currentPlayer;
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int move : getAvailableMoves()) {
            board[move] = me;
            history.add(move);
            currentPlayer = opponent(me);
            int score = minimax(me, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            undo();
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    /** Wykonuje najlepszy ruch za bieżącego gracza i go zwraca. */
    public int makeBestMove() {
        int move = getBestMove();
        if (move >= 0) makeMove(move);
        return move;
    }

    private int minimax(char me, boolean maximizing, int alpha, int beta) {
        char w = findWinner();
        int depth = history.size();
        if (w == me) return 10 - depth;               // szybsza wygrana = lepiej
        if (w == opponent(me)) return depth - 10;     // późniejsza porażka = lepiej
        if (depth == 9) return 0;

        int best = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            if (board[i] != EMPTY) continue;
            board[i] = currentPlayer;
            history.add(i);
            currentPlayer = opponent(currentPlayer);
            int score = minimax(me, !maximizing, alpha, beta);
            undo();
            if (maximizing) {
                best = Math.max(best, score);
                alpha = Math.max(alpha, score);
            } else {
                best = Math.min(best, score);
                beta = Math.min(beta, score);
            }
            if (beta <= alpha) break;
        }
        return best;
    }

    // ------------------------------------------------------------------
    // Pomocnicze
    // ------------------------------------------------------------------

    private char findWinner() {
        int[] line = getWinningLine();
        return line == null ? EMPTY : board[line[0]];
    }

    private static char opponent(char p) {
        return p == X ? O : X;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 3; r++) {
            sb.append(' ');
            for (int c = 0; c < 3; c++) {
                char ch = board[r * 3 + c];
                sb.append(ch == EMPTY ? (char) ('1' + r * 3 + c) : ch);
                if (c < 2) sb.append(" | ");
            }
            sb.append('\n');
            if (r < 2) sb.append("---+---+---\n");
        }
        return sb.toString();
    }

    // ------------------------------------------------------------------
    // Gra w konsoli: człowiek (X) kontra komputer (O)
    // ------------------------------------------------------------------

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        Scanner in = new Scanner(System.in);
        System.out.println("Kółko i krzyżyk – grasz jako X. Podaj numer pola 1–9.");

        while (!game.isGameOver()) {
            System.out.println(game);
            if (game.getCurrentPlayer() == X) {
                System.out.print("Twój ruch: ");
                if (!in.hasNextLine()) return;
                String line = in.nextLine().trim();
                try {
                    int cell = Integer.parseInt(line) - 1;
                    if (!game.isValidMove(cell)) {
                        System.out.println("Niepoprawny ruch, spróbuj ponownie.");
                        continue;
                    }
                    game.makeMove(cell);
                } catch (NumberFormatException e) {
                    System.out.println("Podaj liczbę od 1 do 9.");
                }
            } else {
                int move = game.makeBestMove();
                System.out.println("Komputer wybiera pole " + (move + 1));
            }
        }

        System.out.println(game);
        switch (game.getState()) {
            case X_WINS: System.out.println("Wygrałeś!"); break;
            case O_WINS: System.out.println("Wygrał komputer."); break;
            default:     System.out.println("Remis.");
        }
    }
}
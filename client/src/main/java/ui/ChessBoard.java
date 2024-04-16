package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 0;
    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";

    static String[][] squares = new String [8][8];
    private static Random rand = new Random();
    private static int rowsDrawn = 0;
    private static boolean footer;


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        initializePieces();
        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawTicTacToeBoard(out);
        footer = false;
        drawHeaders(out);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void initializePieces() {
        squares[0][0] = SET_TEXT_COLOR_BLUE + " R ";
        squares[0][1] = SET_TEXT_COLOR_BLUE + " N ";
        squares[0][2] = SET_TEXT_COLOR_BLUE + " B ";
        squares[0][3] = SET_TEXT_COLOR_BLUE + " Q ";
        squares[0][4] = SET_TEXT_COLOR_BLUE + " K ";
        squares[0][6] = SET_TEXT_COLOR_BLUE + " N ";
        squares[0][5] = SET_TEXT_COLOR_BLUE + " B ";
        squares[0][7] = SET_TEXT_COLOR_BLUE + " R ";
        for(int i = 0; i< BOARD_SIZE_IN_SQUARES; i++){
            squares[1][i] = SET_TEXT_COLOR_BLUE + " P ";
        }
        for(int i = 2; i < 6; i++){
            for (int j = 0 ; j < 8; j++) {
                squares[i][j] = "   ";
            }
        }
        for(int i = 0; i< BOARD_SIZE_IN_SQUARES; i++){
            squares[6][i] = SET_TEXT_COLOR_RED + " P ";
        }
        squares[7][0] = SET_TEXT_COLOR_RED + " R ";
        squares[7][2] = SET_TEXT_COLOR_RED + " B ";
        squares[7][1] = SET_TEXT_COLOR_RED + " N ";
        squares[7][3] = SET_TEXT_COLOR_RED + " K ";
        squares[7][4] = SET_TEXT_COLOR_RED + " Q ";
        squares[7][5] = SET_TEXT_COLOR_RED + " B ";
        squares[7][6] = SET_TEXT_COLOR_RED + " N ";
        squares[7][7] = SET_TEXT_COLOR_RED + " R ";
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);
        out.print("  ");
        String[] headers = { " h ", " g ", " f ", " e ", " d ", " c ", " b ", " a ", };;
        String[] footers = { " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", };
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            if(footer){
                drawHeader(out, footers[boardCol]);
            }
            else {
                drawHeader(out, headers[boardCol]);
            }
            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
            }
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {

        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    private static void drawTicTacToeBoard(PrintStream out) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            drawRowOfSquares(out);

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                drawVerticalLine(out);

                setBlack(out);
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out) {
        String header = String.valueOf(("" + (rowsDrawn + 1)));
        drawHeader(out,header);
        out.print(" ");
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
            int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

            out.print(EMPTY.repeat(prefixLength));
            if ((boardCol + rowsDrawn) % 2 == 0) {
                whiteSquare(out, squares[rowsDrawn][boardCol]);
            } else {
                blackSquare(out, squares[rowsDrawn][boardCol]);
            }
            out.print(SET_TEXT_COLOR_GREEN);
            // printPlayer(out, rand.nextBoolean() ? X : O);
            out.print(EMPTY.repeat(suffixLength));
        }
        setBlack(out);
        out.print(" ");
        String header1 = String.valueOf("" + (rowsDrawn + 1));
        drawHeader(out,header1);
        out.println();
        rowsDrawn++;
    }

    private static void drawVerticalLine(PrintStream out) {

        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_CHARS +
                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;

        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
            setRed(out);
            out.print(EMPTY.repeat(boardSizeInSpaces));

            setBlack(out);
            out.println();
        }
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }
    private static void blackSquare(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(player);
    }

    private static void whiteSquare(PrintStream out, String player) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(player);
    }
}
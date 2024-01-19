package chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private PieceType myType;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
         myType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return myType == that.myType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myType);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        var potentialMoves = new HashSet<ChessMove>();


        if (this.myType == PieceType.BISHOP) {
            int i = myPosition.getRow() - 1;
            int j = myPosition.getColumn() - 1;
            while (i < 8 && j < 8) {
                if (i - myPosition.getRow() == j - myPosition.getColumn()) {
                    if (i != (myPosition.getRow() - 1) && j != (myPosition.getColumn() - 1)) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                        i++;
                        j++;
                    } else {
                        i++;
                        j++;
                    }
                } else {
                    break;
                }
            }
            i = myPosition.getRow() - 1;
            j = myPosition.getColumn() - 1;
            while (i >= 0 && j < 8) {
                if (i - (myPosition.getRow() - 1) == (myPosition.getColumn() - 1) - j) {
                    if (i != (myPosition.getRow() - 1) && j != (myPosition.getColumn() - 1)) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                        i--;
                        j++;
                    } else {
                        i--;
                        j++;
                    }
                } else {
                    break;
                }
            }
            i = myPosition.getRow() - 1;
            j = myPosition.getColumn() - 1;
            while (i >= 0 && j >= 0) {
                if (i - myPosition.getRow() == j - myPosition.getColumn()) {
                    if (i != (myPosition.getRow() - 1) && j != (myPosition.getColumn() - 1)) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                        i--;
                        j--;
                    } else {
                        i--;
                        j--;
                    }
                } else {
                    break;
                }
            }
            i = myPosition.getRow() - 1;
            j = myPosition.getColumn() - 1;
            while (i < 8 && j >= 0) {
                if (i - (myPosition.getRow() - 1) == (myPosition.getColumn() - 1) - j) {
                    if (i != (myPosition.getRow() - 1) && j != (myPosition.getColumn() - 1)) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                        i++;
                        j--;
                    } else {
                        i++;
                        j--;
                    }
                } else {
                    break;
                }
            }


        }
        return potentialMoves;
    }

}

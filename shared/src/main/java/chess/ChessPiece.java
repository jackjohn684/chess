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
        bishopMove(board,myPosition, myPosition, potentialMoves, 1);
        return potentialMoves;
    }
    public boolean bishopMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, HashSet<ChessMove> potentialMoves, int direction)
    {
        int i = 0;
        int j = 0;
        if (direction == 1) {
            i = newPosition.getRow();
            j = newPosition.getColumn();
            if (BishopCanMove(board,myPosition, new ChessPosition(i, j))) {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i+1, j+1), null));
                bishopMove(board,myPosition, new ChessPosition(i+1,j+1), potentialMoves, 1);
            }
            else
            {
                bishopMove(board,myPosition, new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()), potentialMoves, 2);
            }
        }
        else if (direction == 2) {
            i = newPosition.getRow();
            j = newPosition.getColumn();
            if (BishopCanMove(board,myPosition, new ChessPosition(i, j))) {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i+1, j+1), null));
                bishopMove(board,myPosition, new ChessPosition(i-1,j+1), potentialMoves, 2);
            }
            else
            {
                bishopMove(board,myPosition, new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-2), potentialMoves, 3);
            }
        }
        else if (direction == 3) {
            i = newPosition.getRow();
            j = newPosition.getColumn();
            if (BishopCanMove(board, myPosition, new ChessPosition(i, j))) {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i+1, j+1), null));
                bishopMove(board,myPosition, new ChessPosition(i-1,j-1), potentialMoves, 3);
            }
            else
            {
                bishopMove(board,myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-2), potentialMoves, 4);
            }
        }
        else if (direction == 4) {
            i = newPosition.getRow();
            j = newPosition.getColumn();
            if (BishopCanMove(board, myPosition, new ChessPosition(i, j))) {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i+1, j+1), null));
                bishopMove(board,myPosition, new ChessPosition(i+1,j-1), potentialMoves, 4);
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    public boolean BishopCanMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition)
    {
        if(newPosition.getRow() >= 8 || newPosition.getColumn() >= 8 || newPosition.getRow() < 0|| newPosition.getColumn() < 0)
        {
            return false;
        }
        if(board.pieces.containsKey(new ChessPosition((newPosition.getRow()+1), (newPosition.getColumn()+1))))
        {
            return false;
        }

        if(newPosition.getRow() - (myPosition.getRow()-1) == newPosition.getColumn() - (myPosition.getColumn()-1) || newPosition.getRow() - (myPosition.getRow()-1) == (myPosition.getColumn()-1) - newPosition.getColumn())
        {
            return true;
        }
        else {
            return false;
        }
    }
}

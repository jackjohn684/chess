package chess;

import java.util.ArrayList;
import java.util.HashSet;

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

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public HashSet<ArrayList<Integer>> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ArrayList<Integer>> potentialMoves = new HashSet<ArrayList<Integer>>();
        if (this.myType == PieceType.BISHOP)
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (i-myPosition.getRow() == j-myPosition.getColumn() || i-myPosition.getRow()  == myPosition.getColumn()-j)
                    {
                        ArrayList<Integer> myList = new ArrayList<Integer>();
                        myList.add(i + 1);
                        myList.add(j + 1);
                        potentialMoves.add(myList);
                    }
                }
            }
        }
        return potentialMoves;
    }
}

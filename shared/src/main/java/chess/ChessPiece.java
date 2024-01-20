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
    private ChessGame.TeamColor myColor;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        myType = type;
        myColor = pieceColor;
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
        throw new RuntimeException();
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
            int i = myPosition.getRow();
            int j = myPosition.getColumn();
            while (i < 8 && j < 8) {
                if (i - myPosition.getRow() == j - myPosition.getColumn()) {
                    if (board.pieces.containsKey(new ChessPosition(i, j))) {
                        if (this.myColor != board.pieces.get(new ChessPosition(i, j)).myColor) {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                            break;
                        } else {
                            break;
                        }
                    }
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    i++;
                    j++;
                } else {
                    break;
                }
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn();
            while (i >= 0 && j < 8) {
                if (i - (myPosition.getRow() - 1) == (myPosition.getColumn() - 1) - j) {
                    if (board.pieces.containsKey(new ChessPosition(i, j))) {
                        if (this.myColor != board.pieces.get(new ChessPosition(i, j)).myColor) {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                            break;
                        } else {
                            break;
                        }
                    }
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    i--;
                    j++;
                } else {
                    break;
                }
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() - 2;
            while (i >= 0 && j >= 0) {
                if (i - myPosition.getRow() == j - myPosition.getColumn()) {
                    if (board.pieces.containsKey(new ChessPosition(i, j))) {
                        if (this.myColor != board.pieces.get(new ChessPosition(i, j)).myColor) {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                            break;
                        } else {
                            break;
                        }
                    }
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    i--;
                    j--;
                } else {
                    break;
                }
            }
            i = myPosition.getRow();
            j = myPosition.getColumn() - 2;
            while (i < 8 && j >= 0) {
                if (i - (myPosition.getRow() - 1) == (myPosition.getColumn() - 1) - j) {
                    if (board.pieces.containsKey(new ChessPosition(i, j))) {
                        if (this.myColor != board.pieces.get(new ChessPosition(i, j)).myColor) {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                            break;
                        } else {
                            break;
                        }
                    }
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    i++;
                    j--;
                } else {
                    break;
                }
            }
        }



        if(this.myType == PieceType.KING) {
            boolean noMove = false;
            int i = myPosition.getRow()+1;
            int j = myPosition.getColumn()+1;
            if  (i > 8 || j > 8 || i <= 0 || j <= 0) {
                noMove = true;
            }
            if (board.pieces.containsKey(new ChessPosition(i-1, j-1)))
            {
                noMove = true;
                if (this.myColor != board.pieces.get(new ChessPosition(i-1, j-1)).myColor) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
                }
            }
            if (noMove == false)
            {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            }


            noMove = false;
            i = myPosition.getRow();
            j = myPosition.getColumn()+1;
            if  (i > 8 || j > 8 || i <= 0 || j <= 0) {
                noMove = true;
            }
            if (board.pieces.containsKey(new ChessPosition(i-1, j-1)))
            {
                noMove = true;
                if (this.myColor != board.pieces.get(new ChessPosition(i-1, j-1)).myColor) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
                }
            }
            if (noMove == false)
            {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            }

            noMove = false;
            i = myPosition.getRow()-1;
            j = myPosition.getColumn()+1;
            if  (i > 8 || j > 8 || i <= 0 || j <= 0) {
                noMove = true;
            }
            if (board.pieces.containsKey(new ChessPosition(i-1, j-1)))
            {
                noMove = true;
                if (this.myColor != board.pieces.get(new ChessPosition(i-1, j-1)).myColor) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
                }
            }
            if (noMove == false)
            {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            }

            noMove = false;
            i = myPosition.getRow()-1;
            j = myPosition.getColumn();
            if  (i > 8 || j > 8 || i <= 0 || j <= 0) {
                noMove = true;
            }
            if (board.pieces.containsKey(new ChessPosition(i-1, j-1)))
            {
                noMove = true;
                if (this.myColor != board.pieces.get(new ChessPosition(i-1, j-1)).myColor) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
                }
            }
            if (noMove == false)
            {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            }

            noMove = false;
            i = myPosition.getRow()-1;
            j = myPosition.getColumn()-1;
            if  (i > 8 || j > 8 || i <= 0 || j <= 0) {
                noMove = true;
            }
            if (board.pieces.containsKey(new ChessPosition(i-1, j-1)))
            {
                noMove = true;
                if (this.myColor != board.pieces.get(new ChessPosition(i-1, j-1)).myColor) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
                }
            }
            if (noMove == false)
            {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            }
            noMove = false;
            i = myPosition.getRow();
            j = myPosition.getColumn()-1;
            if  (i > 8 || j > 8 || i <= 0 || j <= 0) {
                noMove = true;
            }
            if (board.pieces.containsKey(new ChessPosition(i-1, j-1)))
            {
                noMove = true;
                if (this.myColor != board.pieces.get(new ChessPosition(i-1, j-1)).myColor) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
                }
            }
            if (noMove == false)
            {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            }
            noMove = false;
            i = myPosition.getRow()+1;
            j = myPosition.getColumn()-1;
            if  (i > 8 || j > 8 || i <= 0 || j <= 0) {
                noMove = true;
            }
            if (board.pieces.containsKey(new ChessPosition(i-1, j-1)))
            {
                noMove = true;
                if (this.myColor != board.pieces.get(new ChessPosition(i-1, j-1)).myColor) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
                }
            }
            if (noMove == false)
            {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            }
            noMove = false;
            i = myPosition.getRow()+1;
            j = myPosition.getColumn();
            if  (i > 8 || j > 8 || i <= 0 || j <= 0) {
                noMove = true;
            }
            if (board.pieces.containsKey(new ChessPosition(i-1, j-1)))
            {
                noMove = true;
                if (this.myColor != board.pieces.get(new ChessPosition(i-1, j-1)).myColor) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
                }
            }
            if (noMove == false)
            {
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            }

        }
        return potentialMoves;
    }
}
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
    PieceType myType;
    private ChessGame.TeamColor pieceColor;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        myType = type;
        this.pieceColor = pieceColor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return myType == that.myType && pieceColor == that.pieceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myType, pieceColor);
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return myType;
    }

    @Override public String toString() {
    return "ChessPiece{" +
            "myType=" + myType +
            ", myColor=" + pieceColor +
            '}';
}

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> potentialMoves = new HashSet<ChessMove>();
        if (this.getPieceType() == PieceType.BISHOP) {
            int i = myPosition.getRow();
            int j = myPosition.getColumn();
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i++;
                j++;
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn();
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i--;
                j++;
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() - 2;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i--;
                j--;
            }
            i = myPosition.getRow();
            j = myPosition.getColumn() - 2;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i++;
                j--;
            }
        }
        if (this.getPieceType() == PieceType.KING) {
            int i = myPosition.getRow();
            int j = myPosition.getColumn();
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() - 1;
            j = myPosition.getColumn();
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn();
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() - 1;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() - 2;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() - 1;
            j = myPosition.getColumn() - 2;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow();
            j = myPosition.getColumn() - 2;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow();
            j = myPosition.getColumn() - 1;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }

        }

        if (this.getPieceType() == PieceType.KNIGHT) {
            int i = myPosition.getRow() + 1;
            int j = myPosition.getColumn();
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() + 1;
            j = myPosition.getColumn() - 2;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow();
            j = myPosition.getColumn() + 1;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() + 1;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() - 3;
            j = myPosition.getColumn();
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() - 3;
            j = myPosition.getColumn() - 2;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow();
            j = myPosition.getColumn() - 3;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() -3 ;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
                if (!blocked) {
                    potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                }
            }

        }

        if(this.getPieceType() == PieceType.PAWN && this.pieceColor == ChessGame.TeamColor.WHITE)
        {
            int i = myPosition.getRow();
            int j = myPosition.getColumn() - 1;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                }
                if (!blocked) {
                    if(i == 7)
                    {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.QUEEN));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.KNIGHT));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.ROOK));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.BISHOP));
                    }
                    else {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
            }

            i = myPosition.getRow();
            j = myPosition.getColumn();
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        if(i == 7)
                        {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.QUEEN));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.KNIGHT));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.ROOK));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.BISHOP));
                        }
                        else {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                        }
                    }
                }
            }

            i = myPosition.getRow();
            j = myPosition.getColumn() - 2;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        if(i == 7)
                        {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.QUEEN));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.KNIGHT));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.ROOK));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.BISHOP));
                        }
                        else {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                        }
                    }
                }
            }

            i = myPosition.getRow() + 1;
            j = myPosition.getColumn() - 1;
            if (myPosition.getRow() == 2) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if ((!blocked) && (board.squares[i][j] != null || board.squares[i-1][j] != null)) {
                    blocked = true;
                }
                if (!blocked) {
                    if(i == 7)
                    {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.QUEEN));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.KNIGHT));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.ROOK));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.BISHOP));
                    }
                    else {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
            }
        }
        if(this.getPieceType() == PieceType.PAWN && this.pieceColor == ChessGame.TeamColor.BLACK)
        {
            int i = myPosition.getRow() - 2;
            int j = myPosition.getColumn() - 1;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                }
                if (!blocked) {
                    if(i == 0)
                    {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.QUEEN));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.KNIGHT));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.ROOK));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.BISHOP));
                    }
                    else {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
            }

            i = myPosition.getRow() - 2;
            j = myPosition.getColumn();
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        if(i == 0)
                        {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.QUEEN));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.KNIGHT));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.ROOK));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.BISHOP));
                        }
                        else {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                        }
                    }
                }
            }

            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() - 2;
            if (true) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null) {
                    blocked = true;
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        if(i == 0)
                        {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.QUEEN));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.KNIGHT));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.ROOK));
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.BISHOP));
                        }
                        else {
                            potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                        }
                    }
                }
            }

            i = myPosition.getRow()  - 3;
            j = myPosition.getColumn() - 1;
            if (myPosition.getRow() == 7) {
                boolean blocked = !(i < 8 && j < 8 && i >= 0 && j >= 0);
                if (!blocked && board.squares[i][j] != null || board.squares[i+1][j] != null) {
                    blocked = true;
                }
                if (!blocked) {
                    if(i == 0)
                    {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.QUEEN));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.KNIGHT));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.ROOK));
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), PieceType.BISHOP));
                    }
                    else {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                }
            }
        }

        if(this.getPieceType() == PieceType.QUEEN)
        {
            int i = myPosition.getRow();
            int j = myPosition.getColumn();
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i++;
                j++;
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn();
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i--;
                j++;
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() - 2;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i--;
                j--;
            }
            i = myPosition.getRow();
            j = myPosition.getColumn() - 2;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i++;
                j--;
            }

            i = myPosition.getRow() - 1;
            j = myPosition.getColumn();
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                j++;
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() - 1;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i--;
            }
            i = myPosition.getRow() - 1;
            j = myPosition.getColumn() - 2;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                j--;
            }
            i = myPosition.getRow();
            j = myPosition.getColumn() - 1;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i++;
            }
        }
        if(this.getPieceType() == PieceType.ROOK)
        {
            int i = myPosition.getRow() - 1;
            int j = myPosition.getColumn();
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                j++;
            }
            i = myPosition.getRow() - 2;
            j = myPosition.getColumn() - 1;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i--;
            }
            i = myPosition.getRow() - 1;
            j = myPosition.getColumn() - 2;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                j--;
            }
            i = myPosition.getRow();
            j = myPosition.getColumn() - 1;
            while (i < 8 && j < 8 && i >= 0 && j >= 0) {
                if (board.squares[i][j] != null) {
                    if (board.squares[i][j].pieceColor != this.pieceColor) {
                        potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                    }
                    break;
                }
                potentialMoves.add(new ChessMove(myPosition, new ChessPosition(i + 1, j + 1), null));
                i++;
            }
        }
        return potentialMoves;
    }

}
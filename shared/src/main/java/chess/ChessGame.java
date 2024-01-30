package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board;
    TeamColor teamTurn;
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    @Override
    public String toString() {
        return "ChessGame{}";
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> potentialMoves = board.squares[startPosition.getRow() - 1][startPosition.getColumn() - 1].pieceMoves(board, startPosition);
        HashSet<ChessMove> validMoves = new HashSet<ChessMove>();
        for(ChessMove move : potentialMoves)
        {
            if(canMakeMove(move))
            {
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public boolean canMakeMove(ChessMove move)
    {
        if (board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1] != null) {
            if (board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1].pieceMoves(board, move.getStartPosition()).contains(move)) {
                ChessPiece pieceHolder = board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1];
                board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1] = board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1];
                board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1] = null;
                if (isInCheck(board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1].getTeamColor())) {
                    board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1] = board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1];
                    board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1] = pieceHolder;
                    return false;
                } else {
                    board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1] = board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1];
                    board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1] = pieceHolder;
                    return true;
                }
            }
        }
        return false;
    }
    public void makeMove(ChessMove move) throws InvalidMoveException {

        if(this.getTeamTurn() != board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1].getTeamColor())
        {
            throw new InvalidMoveException("Wrong color");
        }
        if (board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1] != null)
        {
            if (board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1].pieceMoves(board, move.getStartPosition()).contains(move))
            {
                ChessPiece pieceHolder = board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1];
                board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1] = board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1];
                board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1] = null;
                if(move.getPromotionPiece() != null)
                {
                    board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1].myType = move.getPromotionPiece();
                }
                if(isInCheck(teamTurn))
                {
                    board.squares[move.getStartPosition().getRow() - 1][move.getStartPosition().getColumn() - 1] = board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1];
                    board.squares[move.getEndPosition().getRow() - 1][move.getEndPosition().getColumn() - 1] = pieceHolder;
                    throw new InvalidMoveException("This move doesn't get you out of check");
                }
                if(getTeamTurn() == TeamColor.WHITE)
                {
                    setTeamTurn(TeamColor.BLACK);
                }
                else
                {
                    setTeamTurn(TeamColor.WHITE);
                }
            }
            else {
                throw new InvalidMoveException("You can't move there");
            }
        }
        else {
            throw new InvalidMoveException("There's no piece to move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingsPosition = new ChessPosition(0,0);
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(board.squares[i][j] != null)
                {
                    if(board.squares[i][j].getPieceType() == ChessPiece.PieceType.KING && board.squares[i][j].getTeamColor() == teamColor)
                    {
                        kingsPosition = new ChessPosition(i+1, j+1);
                    }
                }
            }
        }
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(board.squares[i][j] != null){
                    if(board.squares[i][j].pieceMoves(board, new ChessPosition(i+1,j+1)).contains(new ChessMove(new ChessPosition(i+1,j+1), kingsPosition, null)) || board.squares[i][j].pieceMoves(board, new ChessPosition(i+1,j+1)).contains(new ChessMove(new ChessPosition(i+1,j+1), kingsPosition, ChessPiece.PieceType.QUEEN)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor))
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (board.squares[i][j] != null)
                    {
                        if(board.squares[i][j].getTeamColor() == teamColor)
                        {
                            if(!validMoves(new ChessPosition(i+1,j+1)).isEmpty())
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        else {
            return false;
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.squares[i][j] != null)
                {
                    if(board.squares[i][j].getTeamColor() == teamColor)
                    {
                        if(!validMoves(new ChessPosition(i+1,j+1)).isEmpty())
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}

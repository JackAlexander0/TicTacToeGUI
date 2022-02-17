import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeTileFrame extends JFrame
{
    JPanel mainPnl, boardPnl, quitPnl;
    TicTacToeTile[][] guiTiles = new TicTacToeTile[3][3];
    JButton quitBtn;
    int moveCount;
    String player = "X";
    private static final int ROW = 3;
    private static final int COL = 3;
    private static final int minWinMoves = 5;
    private static final int minTieMoves = 7;
    private static String[][] board = new String[ROW][COL];


    public TicTacToeTileFrame()
    {
       setSize(600,600);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


       mainPnl = new JPanel();
       mainPnl.setLayout(new BorderLayout());

       boardPnl = new JPanel();
       boardPnl.setLayout(new GridLayout(3,3));

        for( int row = 0; row < ROW; row++)
            for(int col= 0; col < COL; col++)
            {
                guiTiles[row][col] = new TicTacToeTile(row, col);
                guiTiles[row][col].setText(" ");
                board[row][col] = " ";
                guiTiles[row][col].setFont(new Font("Times New Roman", Font.PLAIN, 48));
                int finalCol = col;
                int finalRow = row;
                guiTiles[row][col].addActionListener((ActionEvent ae) ->
                {
                    moveCount++;

                    // update GUI (display X or O)
                    guiTiles[finalRow][finalCol].setText(player);

                    // update board (from original)
                    board[finalRow][finalCol] = player;

                    // if we have enough moves for a win, test for it
                    if (moveCount >= minWinMoves) {
                        if (isWin(player)) {
                            gameOver();
                        }
                        else {
                            changePlayer();
                        }
                    }
                    else {
                        changePlayer();
                    }

                    // if we have enough moves for a partial board tie, test for it
                    if (moveCount>= minTieMoves) {
                        if (isTie()) {
                            gameOver();
                        }
                        else {
                            changePlayer();
                        }
                    }

                });
                boardPnl.add(guiTiles[row][col]);
            }

        mainPnl.add(boardPnl, BorderLayout.CENTER);
        add(mainPnl);

        quitPnl = new JPanel();
        quitBtn = new JButton("Quit the TTT Game!");
        quitBtn.addActionListener((ActionEvent ae) ->
        {
           System.exit(0);
        });

        quitPnl.add(quitBtn);
        mainPnl.add(quitPnl, BorderLayout.SOUTH);

        setVisible(true);
    }
    private static void clearBoard()
    {
        // sets all the board elements to a space
        for(int row=0; row < ROW; row++)
        {
            for(int col=0; col < COL; col++)
            {
                board[row][col] = " ";
            }
        }
    }
    private static boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagnalWin(player))
        {
            return true;
        }

        return false;
    }
    private static boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].equals(player) &&
                    board[1][col].equals(player) &&
                    board[2][col].equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }
    private static boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].equals(player) &&
                    board[row][1].equals(player) &&
                    board[row][2].equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }
    private static boolean isDiagnalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0].equals(player) &&
                board[1][1].equals(player) &&
                board[2][2].equals(player) )
        {
            return true;
        }
        if(board[0][2].equals(player) &&
                board[1][1].equals(player) &&
                board[2][0].equals(player) )
        {
            return true;
        }
        return false;
    }

    // checks for a tie before board is filled.
    // check for the win first to be efficient
    private static boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].equals("X") ||
                    board[row][1].equals("X") ||
                    board[row][2].equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].equals("O") ||
                    board[row][1].equals("O") ||
                    board[row][2].equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].equals("X") ||
                    board[1][col].equals("X") ||
                    board[2][col].equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].equals("O") ||
                    board[1][col].equals("O") ||
                    board[2][col].equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].equals("X") ||
                board[1][1].equals("X") ||
                board[2][2].equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].equals("O") ||
                board[1][1].equals("O") ||
                board[2][2].equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].equals("X") ||
                board[1][1].equals("X") ||
                board[2][0].equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].equals("O") ||
                board[1][1].equals("O") ||
                board[2][0].equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }
    private void gameOver() {
        int playAgain;
        if (isWin(player)) {
            playAgain = JOptionPane.showConfirmDialog(null, "Player " + player + " wins! \n Do you want to play again?","Win!",JOptionPane.YES_NO_OPTION);
        }
        else {
            playAgain = JOptionPane.showConfirmDialog(null, "It's a tie! \n Do you want to play again?","Tie!",JOptionPane.YES_NO_OPTION);
        }

        if (playAgain == 0) {
            for( int row = 0; row < ROW; row++) {
                for (int col = 0; col < COL; col++) {
                    guiTiles[row][col].setText(" ");
                    board[row][col] = " ";
                }
            }
            player = "X";
            moveCount = 0;
        }
        else {
            System.exit(0);
        }

    }
    private void changePlayer() {
        if(player.equals("X"))
        {
            player = "O";
        }
        else
        {
            player = "X";
        }
    }
}

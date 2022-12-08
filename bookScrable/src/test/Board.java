package test;

import java.util.ArrayList;

public class Board {
    private static Board b;
    private Square[][] gameBoard;
    private boolean isWord;
    private ArrayList<Word> wordList = new ArrayList<>();

    private ArrayList<Word> wordsFilter(ArrayList<Word> w) {
        w.removeIf(word -> !dictionaryLegal(word) || wordList.contains(word));
        return w;
    }

    private final boolean[][] mat = {
            {true, false, false, false, false, false, false, true, false, false, false, false, false, false, true},
            {false, true, false, false, false, false, false, false, false, false, false, false, false, true, false},
            {false, false, true, false, false, false, false, false, false, false, false, false, true, false, false},
            {false, false, false, true, false, false, false, false, false, false, false, true, false, false, false},
            {false, false, false, false, true, false, false, false, false, false, true, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {true, false, false, false, false, false, false, true, false, false, false, false, false, false, true},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, true, false, false, false, false, false, true, false, false, false, false},
            {false, false, false, true, false, false, false, false, false, false, false, true, false, false, false},
            {false, false, true, false, false, false, false, false, false, false, false, false, true, false, false},
            {false, true, false, false, false, false, false, false, false, false, false, false, false, true, false},
            {true, false, false, false, false, false, false, true, false, false, false, false, false, false, true},
    };
    final int[][] Bonusmat = {
            {3, 1, 1, 2, 1, 1, 1, 3, 1, 1, 1, 2, 1, 1, 3},
            {1, 2, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 2, 1},
            {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
            {2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2},
            {1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1},
            {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
            {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
            {3, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 3},
            {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
            {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
            {1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1},
            {2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2},
            {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
            {1, 2, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 2, 1},
            {3, 1, 1, 2, 1, 1, 1, 3, 1, 1, 1, 2, 1, 1, 3}};

    Board() {
        gameBoard = new Square[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                gameBoard[i][j] = new Square(null);
            }
        }
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                gameBoard[i][j].isWord = mat[i][j];
            }
        }
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                gameBoard[i][j].bonus = Bonusmat[i][j];
            }
        }
    }

    public void addToList(Word w){
        wordList.add(w);
    }
    public static Board getBoard() {
        if (b == null) {
            b = new Board();
        }
        return b;
    }

    public Tile[][] getTiles() {
        Tile[][] tmp = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (gameBoard[i][j] == null)
                    tmp[i][j] = null;
                else
                    tmp[i][j] = gameBoard[i][j].tile;
            }
        }
        return tmp;
    }

    public boolean boardLegal(Word w) {
        int row = w.getRow();
        int col = w.getCol();
        int size = w.getTiles().length;
        boolean flag = false;
        if (row < 0 || row > 14)
            return false;
        if (col < 0 || col > 14)
            return false;
        if (w.isVertical() && row + size > 14)
            return false;
        else if (!w.isVertical() && col + size > 14)
            return false;
        if (gameBoard[7][7].tile == null) {
            if (w.isVertical()) {
                for (int i = row; i < row + size; i++) {
                    if (i == 7 && col == 7) {
                        flag = true;
                        break;
                    }
                }
                if (!flag)
                    return false;
            } else {
                for (int i = col; i < col + size; i++) {
                    if (i == 7 && row == 7) {
                        flag = true;
                        break;
                    }
                }
                if (!flag)
                    return false;
            }
        }

        for (int i = 0; i < size; i++) {
            if (gameBoard[row][col].getTile() != null) {
                if (w.getExactTile(i) != gameBoard[row][col].tile) {
                    if (w.getExactTile(i) != null)
                        return false;
                }
            }
            if (w.isVertical())
                row++;
            else col++;
        }
        return true;
    }

    public ArrayList<Word> getWords(Word w) {
        ArrayList<Word> arr = new ArrayList<Word>();
        // adding the first word:
        arr.add(w);
        ArrayList<Tile> buffer = new ArrayList<>();
        int row=w.getRow();
        int col=w.getCol();
        int size = w.getTiles().length;
        int r=row , c=col;
        int nullCounter=2, startIndex;
            for (int i = 0; i < size; i++) {
                if (!w.isVertical()) {

                    nullCounter = 2;
                    r = row;
                    buffer.clear();
                    if (gameBoard[row - 1][col + i].tile != null && gameBoard[row + 1][col + i].tile == null) {
                        while (nullCounter != 0) {
                            if (gameBoard[r - 1][col + i].tile == null) {
                                if (nullCounter == 1) {
                                    nullCounter--;
                                    break;
                                }
                                nullCounter--;
                            }
                            if (gameBoard[r - 1][col + i].tile == null && gameBoard[r][col + i].tile != null) {
                                nullCounter--;
                                break;
                            }
                            r--;
                        }
                        startIndex = r;
                        nullCounter = 2;
                        while (nullCounter != 0) {
                            if (gameBoard[r][col + i].tile == null) {
                                if (nullCounter == 1) {
                                    nullCounter--;
                                    break;
                                }
                                nullCounter--;
                                buffer.add(w.getExactTile(i));
                            } else
                                buffer.add(gameBoard[r][col + i].tile);
                            r++;
                        }
                        arr.add(new Word(buffer, startIndex, col + i, true));

                    } else if (gameBoard[row - 1][col + i].tile != null && gameBoard[row + 1][col + i].tile != null) {
                        while (nullCounter != 0) {
                            if (gameBoard[r - 1][col + i].tile == null) {
                                nullCounter--;
                                break;
                            }
                            r--;
                        }
                        startIndex = r;
                        nullCounter = 2;
                        while (nullCounter != 0) {
                            if (gameBoard[r][col + i].tile == null) {
                                if (nullCounter == 1) {
                                    nullCounter--;
                                    break;
                                }
                                nullCounter--;
                                if (gameBoard[r + 1][col + i].tile == null) {
                                    nullCounter--;
                                    break;
                                }
                                buffer.add(w.getExactTile(i));
                            } else
                                buffer.add(gameBoard[r][col + i].tile);
                            r++;
                        }
                        arr.add(new Word(buffer, startIndex, col + i, true));
                    } else if (gameBoard[row - 1][col + i].tile == null && gameBoard[row + 1][col + i].tile != null) {
                        while (nullCounter != 0) {
                            if (gameBoard[r][col + i].tile == null) {
                                if (nullCounter == 1) {
                                    nullCounter--;
                                    break;
                                }
                                nullCounter--;
                                buffer.add(w.getExactTile(i));
                            } else
                                buffer.add(gameBoard[r][col + i].tile);
                            r++;
                        }
                        arr.add(new Word(buffer, row, col + i, true));
                    }
                }
                 else {
                     c=col;
                     nullCounter=2;
                    if (gameBoard[row + i][col - 1].tile != null && gameBoard[row + i][col + 1].tile == null) {
                        while (nullCounter != 0) {
                            if (gameBoard[row + i][c - 1].tile == null) {
                                if (nullCounter == 1) {
                                    nullCounter--;
                                    break;
                                }
                                nullCounter--;
                            }
                            c--;
                        }
                        startIndex = c;
                        nullCounter = 2;
                        while (nullCounter != 0) {
                            if (gameBoard[row + i][c - 1].tile == null) {
                                if (nullCounter == 1) {
                                    nullCounter--;
                                    break;
                                }
                                nullCounter--;
                                buffer.add(w.getExactTile(i));
                            } else
                                buffer.add(gameBoard[row + i][c - 1].tile);
                            c++;
                        }
                        arr.add(new Word(buffer, row + i, startIndex, true));

                    } else if (gameBoard[row + i][col - 1].tile != null && gameBoard[row + i][col + 1].tile != null) {
                        while (nullCounter != 0) {
                            if (gameBoard[row + i][c - 1].tile == null) {
                                if (nullCounter == 1) {
                                    nullCounter--;
                                    break;
                                }
                                nullCounter--;
                            }
                            c--;
                        }
                        startIndex = c;
                        nullCounter = 2;
                        while (nullCounter != 0) {
                            if (gameBoard[row + i][c - 1].tile == null) {
                                if (nullCounter == 1) {
                                    nullCounter--;
                                    break;
                                }
                                nullCounter--;
                                buffer.add(w.getExactTile(i));
                            } else
                                buffer.add(gameBoard[row + i][c - 1].tile);
                            c++;
                        }
                        if(buffer.size()>1)
                        arr.add(new Word(buffer, row + i, startIndex, true));

                    } else if (gameBoard[row + i][col - 1].tile == null && gameBoard[row + i][col + 1].tile != null) {
                        startIndex = col;
                        nullCounter = 2;
                        buffer.clear();
                        while (nullCounter != 0) {
                                if(gameBoard[row+i][c].tile==null) {
                                    if(gameBoard[row+i][c+2].tile==null && gameBoard[row+i][c+1].tile==null){
                                        break;
                                    }
                                    if(nullCounter==2)
                                        buffer.add(w.getExactTile(i));
                                 nullCounter--;
                                 c++;
                                }
                             else if(gameBoard[row+i][c+1].tile==null) {
                                 buffer.add(gameBoard[row + i][c].tile);
                                c++;
                                }
                             else{
                                buffer.add(gameBoard[row + i][c].tile);
                                c++;
                             }
                        }
                        if(buffer.size()>1)
                        arr.add(new Word(buffer, row + i, col, false));
                    }
                }
            }



        return arr;
    }

    public void filterWords(ArrayList<Word> arr){
        if(arr.size()==1)
            return;
        for(int i=0;i<wordList.size();i++){
            for(int j=0;j<arr.size();j++){
                if(arr.get(j).equals(wordList.get(i)) )
                    if(j>0)
                    arr.remove(j);

            }
        }
    }

    public void printBonus() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++)
                System.out.print(gameBoard[i][j].bonus + " ");
            System.out.println("");
        }
    }


    public boolean dictionaryLegal(Word word) {
        return true;
    }

    public int getScore(ArrayList<Word> words) {
       int totalSum = 0;
       int index=0,x=0,y=0;
        for (Word word : words) {
            int row=word.getRow();
            int col = word.getCol();
            int sum = 0;
            int mul = 1;
                for (int i = 0; i < word.getTiles().length; i++) {
                         x = (word.isVertical()) ? i : 0;
                         y = (word.isVertical()) ? 0 : i;
                        if (gameBoard[7][7].tile != null && word.getRow() + x == 7 && word.getCol() + y == 7) {
                            sum += word.getExactTile(i).getScore();
                        } else if (gameBoard[word.getRow() + x][word.getCol() + y].isWord) {
                            mul *= gameBoard[word.getRow() + x][word.getCol() + y].bonus;
                            sum += word.getExactTile(i).getScore();
                        } else
                            sum += word.getExactTile(i).getScore() * gameBoard[word.getRow() + x][word.getCol() + y].bonus;
                    }

            totalSum += sum * mul;
        }
        return totalSum;
    }

    private Word fillNulls(Word word) {
        Tile[] t = new Tile[word.getTiles().length];
        int row = word.getRow();
        int col = word.getCol();
        for (int i = 0; i < word.getTiles().length; i++) {
            if (word.getExactTile(i) == null)
                t[i] = gameBoard[row][col].tile;
            else
                t[i] = word.getExactTile(i);
            if (word.isVertical())
                row++;
            else
                col++;
        }
        return new Word(t, word.getRow(), word.getCol(), word.isVertical());
    }

    public int tryPlaceWord(Word w) {
        if (!boardLegal(w))
            return 0;
        if (!dictionaryLegal(w))
            return 0;
        ArrayList<Word>tmp =new ArrayList<>();
        for(int i=0;i<wordList.size();i++){
            tmp.add(wordList.get(i));
        }
        w = fillNulls(w);
        addToList(w);
        ArrayList<Word> arr =getWords(w);
        for(int i=0;i< arr.size();i++){
            isInList(arr.get(i));
        }
        //checking the other words that created!
        for (int i = 0; i < arr.size(); i++) {
            if (!dictionaryLegal(arr.get(i)))
                return 0;
        }
        for(int i=0;i<tmp.size();i++){
            for(int j=0;j< arr.size();j++){
                if(arr.get(j).equals(tmp.get(i)))
                    arr.remove(j);
            }
        }

        int score=getScore(arr);
        placeWord(w);
        return score;
    }

    public void placeWord(Word w) {
        int row = w.getRow();
        int col = w.getCol();
        for (int i = 0; i < w.getTiles().length; i++) {
            if (gameBoard[row][col].tile == null)
                gameBoard[row][col].tile = new Tile(w.getExactTile(i).getLettr());
            if (w.isVertical())
                row++;
            else col++;
        }
    }
    public void isInList(Word w){
        for(int i=0;i<wordList.size();i++){
            if(wordList.get(i).equals(w))
                return;
        }
        wordList.add(w);
    }

    public static class Square {
        private Tile tile;
        private int bonus;

        public Square(Tile tile, int bonus, boolean isWord) {
            this.tile = tile;
            this.bonus = bonus;
            this.isWord = isWord;
        }

        private boolean isWord;

        public Square(Tile tile) {
            this.tile = tile;
            this.bonus = 1;
        }

        public Tile getTile() {
            return tile;
        }

        public int getBonus() {
            return bonus;
        }

    }
}


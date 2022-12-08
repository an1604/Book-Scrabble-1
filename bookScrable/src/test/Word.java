package test;

import java.util.ArrayList;
import java.util.Arrays;

public class Word {
private int row;
private int col;
private Tile[] tiles;
private boolean vertical;

    public Word(Tile[] tiles,int row , int col, boolean vertical ) {
        this.row = row;
        this.col = col;
        this.tiles = tiles;
        this.vertical = vertical;
    }
    public Word(Tile[] tiles){
        this.tiles=tiles;
    }

    public Word(ArrayList<Tile> buffer, int row, int col, boolean vertical) {
        tiles=new Tile[buffer.size()];
        for(int i=0;i<buffer.size();i++){
            tiles[i]=new Tile(buffer.get(i).getLettr());
        }
        this.row=row;
        this.col=col;
        this.vertical=vertical;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public boolean isVertical() {
        return vertical;
    }
    public Tile getExactTile(int i){return tiles[i];}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word = (Word) o;
        return getRow() == word.getRow() && getCol() == word.getCol() && isVertical() == word.isVertical() && Arrays.equals(getTiles(), word.getTiles());
    }
    public void print(){
        for(int i=0;i<this.getTiles().length;i++){
            System.out.print(this.getTiles()[i].getLettr());
        }
    }
}

package test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Tile {
    public final char lettr;
    public final int score;
    public final int[] ScoreArr = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};

    protected Tile(char lettr) {
        this.lettr = lettr;
        this.score = ScoreArr[this.lettr-'A'];
    }

    public char getLettr() {
        return lettr;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return lettr == tile.lettr && score == tile.score && Arrays.equals(ScoreArr, tile.ScoreArr);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(lettr, score);
        result = 31 * result + Arrays.hashCode(ScoreArr);
        return result;
    }


    public static class Bag {
        private final int[] quantitiesFinal = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        private int[] quantities = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        private Tile[] TilesArr;
        private static int x = 0;
        private static Bag b;

        public Bag() {
            TilesArr = new Tile[26];
            int a = 65;
            for (int i = 0; i < 26; i++) {
                TilesArr[i] = new Tile((char) a);
                a++;
            }
            x = 98;
        }

        public static Bag getBag() {
            if (b == null)
                b = new Bag();
            return b;
        }

        public Tile getRand() {
            if (x == 0)
                return null;
            Random r = new Random();
            int i = r.nextInt(26);
            if (quantities[i] == 0) {
                for (int j = i; j < 26; j++) {
                    if (j == 25)
                        i = j % 26 - 1;
                    if (quantities[j] != 0) {
                        x--;
                        quantities[j]--;
                        return TilesArr[j];
                    }
                }
            }
            quantities[i]--;
            x--;
            return TilesArr[i];
        }
        public Tile getTile(char c){
            if(c-'A'<0 || c-'A'>25)
                return null;
            if(quantities[c-'A']==0)
                return null;
            x--;
            quantities[c-'A']--;
            return TilesArr[c-'A'];
        }
        public void put(Tile t){
            char c= t.getLettr();
            int i = c-'A';
            if(quantities[i]==quantitiesFinal[i])
                return;
            x++;
            quantities[i]++;
        }
        public int size (){return x;}
        public int[] getQuantities(){
            int[] arr= new int[26];
            for(int i=0;i<26;i++){
                arr[i]=quantities[i];
            }
            return arr;
        }
    }
}
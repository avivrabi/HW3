import java.sql.Date;
import java.sql.Timestamp;

public class Song implements Cloneable {
    String songName;
    String artist;
    Enum genre;
    int duration;

    public Song(String songName, String artist, Enum genre, int duration){
        this.songName = songName;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }

    public Song(Song song){
        this.songName = song.songName;
        this.artist = song.artist;
        this.genre = song.genre;
        this.duration = song.duration;
    }


    @Override
    public String toString() { // TODO: check
        int minute = duration / 60;
        int seconds= duration % 60;
        String secondsFormat = seconds<10 ? "0"+seconds : ""+seconds;
        String formatDuration= minute + ":" + secondsFormat;
        return "(" + songName.toString() + "," + artist.toString() + "," + genre.toString() + ","
                + formatDuration + ")";
    }
    @Override
    public boolean equals(Object other){
        if(other instanceof Song){return ((Song) other).songName.equals(this.songName)
                && ((Song) other).artist.equals(this.artist);}
        return false;
    }

    /**
     * using the utility of prime number to ensure a unique hashcode depend on the name and artist of this song only
     * @return a uniform integer to every equal songs and different if not
     */
    @Override
    public int hashCode() {
        return 2* this.songName.hashCode()+ 3*this.artist.hashCode();
    }

    // TODO: finish clone - choose way
//    @Override
//    public Song clone(){
//        return new Song(this);
//    }
    @Override
    public Song clone() {
        try {
            return (Song) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    public void setDuration(int duration){
        if(duration<=0){ //TODO: check if a song duration can be 0
            System.out.println("illegal duration! erase me its not require");//TODO: comment it
            return;
        }
        this.duration=duration;
    }

    public enum Genre {
        POP,
        ROCK,
        HIP_HOP,
        COUNTRY,
        JAZZ,
        DISCO
    }
}

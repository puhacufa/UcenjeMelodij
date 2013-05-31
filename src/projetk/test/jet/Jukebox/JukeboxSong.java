package projetk.test.jet.Jukebox;

public class JukeboxSong {
	String author;
	String title;
	String midiFile;
	String lyrics;
	String fileSize;
	String url;
	String authorLink;

	JukeboxSong () {
		
	}
	
	JukeboxSong (String _author) {
		author = _author;
	}
	
	public String getAuthorLink() {
		return authorLink;
	}

	public void setAuthorLink(String authorLink) {
		this.authorLink = authorLink;
	}

	
	public String toString () {
		return author;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMidiFile() {
		return midiFile;
	}
	public void setMidiFile(String midiFile) {
		this.midiFile = midiFile;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
}

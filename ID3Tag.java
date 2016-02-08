package mp3IdTag;
import java.io.*;

public class ID3Tag
{
	private static final String MACHINENAME = System.getProperty("user.name");
	private String title;
	private String artist;
	private String album;
	private String year;
	private String comment;
	private String genre;
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public String getAlbum()
	{
		return album;
	}

	public void setAlbum(String album)
	{
		this.album = album;
	}

	public String getYear()
	{
		return year;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}
	
	public ID3Tag(String title, String artist, String album, String year, String comment, String genre)
	{
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
		this.comment = comment;
		this.genre = genre;
	}

	private ID3Tag()
	{
		
	}
	
	public static ID3Tag parse(byte[] bytes)
	{
		try
		{
			String title = new String(readXBytes(bytes, 3, 33),"UTF-8");
			String artist = new String(readXBytes(bytes, 33, 63),"UTF-8");
			String album = new String(readXBytes(bytes, 63, 93),"UTF-8");
			String year = new String(readXBytes(bytes, 93, 97),"UTF-8");
			String comment = new String(readXBytes(bytes, 97, 127),"UTF-8");
			String genre = new String(readXBytes(bytes, 127, 128),"UTF-8");
			ID3Tag tag = new ID3Tag(title, artist, album ,year, comment, genre);
			tag.setTitle(title);
			tag.setArtist(artist);
			tag.setAlbum(album);
			tag.setYear(year);
			tag.setComment(comment);
			tag.setGenre(genre);
			return tag;
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}	
		return null;

	}
	
//	private static byte[] writeXBytes(byte[] byteArray, int fromPos, int toPos)
//	{
//		byte[] byteArrayToModify = readXBytes(byteArray, fromPos, toPos);
//		
//		return byteArrayToModify;
//	}
	
	private static byte[] readXBytes(byte[] byteArray, int fromPos, int toPos)
	{
		byte[] result = new byte[toPos-fromPos];
		for (int i = fromPos; i < toPos; i++)
		{
			result[i - fromPos] = byteArray[i];
		}
		
		return result;
	}

	public static byte[] tail(File file)
    {
        try
        {
            RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
            long fileLength = fileHandler.length() - 1;
            byte[] buffer = new byte[128];

            for (int i = 0; i < buffer.length; i++)
            {
                fileHandler.seek(fileLength - 127 + i);
                buffer[i] = fileHandler.readByte();
            }
            fileHandler.close();
            return buffer;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
		
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Title");
		sb.append(title);
		sb.append("\n");
		sb.append("Artist");
		sb.append(artist);
		sb.append("\n");
		sb.append("Album");
		sb.append(album);
		sb.append("\n");
		sb.append("Year");
		sb.append(year);
		sb.append("\n");
		sb.append("Comment");
		sb.append(comment);
		sb.append("\n");
		sb.append("Genre");
		sb.append(genre);
		sb.append("\n");
		return sb.toString();	
	}
	
	public static void main(String[] args)
	{
		byte[] id3Bytes = tail(new File("C:\\Users\\"+MACHINENAME+"\\Desktop\\Workspace\\MP3_ID3Tag\\neil.mp3"));
		ID3Tag tag = ID3Tag.parse(id3Bytes);
		System.out.println(tag.toString());
	}
}

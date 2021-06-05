public class Chunk 
{
  private int[][][] chunk;
  private int size;
  public Chunk(int s)
  {
    size = s;
    chunk = new int[size][size][size];
    for (int l = 0; l < size; l++)
    {
      for (int r = 0; r < size; r++)
      {
        for (int c = 0; c < size; c++)
        {
          if (l <= 1 || l >= size - 2 || r <= 1 || r >= size - 2 || c <= 1 || c >= size - 2)
          {
            chunk[l][r][c] = (int) (Math.random() + 0.5);
          }
        }
      }
    }
  }
  public Chunk()
  {
    this(10);
  }
  public String toString()
  {
    String output = "";
    for (int l = 0; l < chunk.length; l++)
    {
      for (int r = 0; r < chunk[l].length; r++)
      {
        for (int c = 0; c < chunk[l][r].length; c++)
        {
          output += "" + chunk[l][c][r];
        }
        output += "\n";
      }
      output +="\n\n";
    }
    return output;
  }
  public int[][][] getChunk()
  {
    return chunk;
  }
  public int get(int l, int r, int c)
  {
    return chunk[l][r][c];
  }
  public int getSize()
  {
    return size;
  }
}


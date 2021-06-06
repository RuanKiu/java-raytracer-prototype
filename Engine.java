public class Engine 
{
  private Chunk chunk;
  private float x, y, z;
  private float pitch, yaw;
  private float fovH, fovV;
  private float resolution;
  private int renderDist;
  public Engine()
  {
    chunk = new Chunk(30);
    x = y = z = chunk.getSize() / 2;
    pitch = yaw = 0f; 
    fovV = (float) Math.toRadians(48);
    fovH = 2 * (float) Math.atan(Math.tan(fovV / 2));
    resolution = 0.01f;
    renderDist = 16;
  }
  public float x()
  {
    return x;
  }
  public float y()
  {
    return y;
  }
  public float z()
  {
    return z;
  }
  public float getPitch()
  {
    return pitch;
  }
  public float getYaw()
  {
    return yaw;
  }
  public float getFOVH()
  {
    return fovH;
  }
  public float getFOVV()
  {
    return fovV;
  }
  public void setX(float x)
  {
    this.x = x;
  }
  public void setY(float y)
  {
    this.y = y;
  }
  public void setZ(float z)
  {
    this.z = z;
  }
  public void setPitch(float p)
  {
    pitch = p;
  }
  public void setYaw(float y)
  {
    yaw = y;
  }
  public void setValues(float x, float y, float z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public void updateFOV(float w, float h)
  {
    fovH = 2 * (float) Math.atan(Math.tan(fovV / 2) * w / h); 
  }
  public Chunk getChunk()
  {
    return chunk;
  }
  public void moveCamera(float value)
  {
    float stepX = (float) Math.sin(yaw);
    float stepZ = (float) Math.cos(yaw); 
    float stepY = (float) Math.sin(pitch);
    x += stepX * value;
    y += stepY * value;
    z += stepZ * value;
  }
  public void strafeCamera(float value)
  {
    float stepX = (float) Math.cos(yaw);
    float stepZ = (float) -Math.sin(yaw); 
    float stepY = (float) -Math.sin(pitch);
    x += stepX * value;
    y += stepY * value;
    z += stepZ * value;
  }
  public float castRay(float pitchAngle, float yawAngle)
  {
    float stepX = (float) Math.sin(yawAngle);
    float stepZ = (float) Math.cos(yawAngle); 
    float stepY = (float) Math.sin(pitchAngle);
    boolean hit = false;
    float dist = 0f;
    while (dist < renderDist && !hit)
    {
      //dist += resolution;
      float currentX = (x + stepX * dist);
      float currentY = (y + stepY * dist);
      float currentZ = (z + stepZ * dist);
      int nextX = (int) (currentX) + (int) (stepX / Math.abs(stepX));
      int nextY = (int) (currentY) + (int) (stepY / Math.abs(stepY));
      int nextZ = (int) (currentZ) + (int) (stepZ / Math.abs(stepZ));
      float coeffX, coeffY, coeffZ;
      if (stepX != 0 && nextX != currentX)
        coeffX = (float) (nextX - currentX) / stepX;
      else 
        coeffX = 900;
      if (stepY != 0 && nextY != currentY)
        coeffY = (float) (nextY - currentY) / stepY;
      else 
        coeffY = 900;
      if (stepZ != 0 && nextZ != currentZ)
        coeffZ = (float) (nextZ - currentZ) / stepZ;
      else coeffZ = 900;
      float minCoeff = (float) Math.min(coeffX, Math.min(coeffY, coeffZ));
      int testX = (int) ((float) currentX + stepX * (resolution * (int) (stepX / Math.abs(stepX)) + minCoeff));
      int testY = (int) ((float) currentY + stepY * (resolution * (int) (stepY / Math.abs(stepY)) + minCoeff));
      int testZ = (int) ((float) currentZ + stepZ * (resolution * (int) (stepZ / Math.abs(stepZ)) + minCoeff));
      
      if (testX < 0 || testX >= chunk.getSize() - 1 || testY < 0 || testY > chunk.getSize() - 1 || testZ < 0 || testZ > chunk.getSize() - 1)
      {
        hit = true;
      }
      else if (chunk.getChunk()[testZ][testY][testX] != 0)
      {
        hit = true; 
      }
      /**
      else if (chunk.getChunk()[testZ][testY][testX] != 0)
      {
        hit = true;
      }
      **/
      dist += minCoeff;
    }
    return dist;
  }
}

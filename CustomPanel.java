import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class CustomPanel extends JPanel implements ActionListener, ComponentListener, KeyListener
{
  private Timer timer;
  private Engine engine;
  private int resolution;
  private float shadeFactor;
  private boolean forward, backward, left, right, rr, rl, ru, rd;
  public CustomPanel()
  {
    timer = new Timer(9, this);
    engine = new Engine();
    resolution = 5;
    shadeFactor = 1f;

    addComponentListener(this);
    addKeyListener(this);
    setVisible(true);
    setSize(700, 700);
    setFocusable(true);
    requestFocusInWindow(true);

    timer.start();
  }
  // Component event handling
  public void componentResized(ComponentEvent e)
  {
    engine.updateFOV(getWidth(), getHeight());
  }
  public void componentHidden(ComponentEvent e) {}
  public void componentMoved(ComponentEvent e) {}
  public void componentShown(ComponentEvent e) {}  

  // Key event handling
  public void keyPressed(KeyEvent e)
  {
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_W: forward = true; break;
      case KeyEvent.VK_S: backward = true; break;
      case KeyEvent.VK_D: right = true; break;
      case KeyEvent.VK_A: left = true; break;
      case KeyEvent.VK_RIGHT: rr = true; break;
      case KeyEvent.VK_LEFT: rl = true; break;
      case KeyEvent.VK_UP: ru = true; break;
      case KeyEvent.VK_DOWN: rd = true; break;
    }
  }
  public void keyReleased(KeyEvent e)
  {
    switch (e.getKeyCode())
    {
      case KeyEvent.VK_W: forward = false; break;
      case KeyEvent.VK_S: backward = false; break;
      case KeyEvent.VK_D: right = false; break;
      case KeyEvent.VK_A: left = false; break;
      case KeyEvent.VK_RIGHT: rr = false; break;
      case KeyEvent.VK_LEFT: rl = false; break;
      case KeyEvent.VK_UP: ru = false; break;
      case KeyEvent.VK_DOWN: rd = false; break;
    }
  }
  public void keyTyped(KeyEvent e) {}

  // Action handling
  public void actionPerformed(ActionEvent e)
  {
    repaint();
    if (forward)
      engine.moveCamera(0.1f);
    if (backward)
      engine.moveCamera(-0.1f);
    if (right)
      engine.strafeCamera(0.1f);
    if (left)
      engine.strafeCamera(-0.1f);
    if (rr)
      engine.setYaw(engine.getYaw() + (float) Math.toRadians(3));
    if (rl)
      engine.setYaw(engine.getYaw() - (float) Math.toRadians(3));
    if (ru)
      engine.setPitch(engine.getPitch() - (float) Math.toRadians(3));
    if (rd)
      engine.setPitch(engine.getPitch() + (float) Math.toRadians(3));

  }
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    for (int i = 0; i < getWidth(); i += resolution)
    {
      float yawAngle = engine.getYaw() - engine.getFOVH() / 2 + ((float) i / getWidth()) * engine.getFOVH();  
      for (int j = 0; j < getHeight(); j += resolution)
      {
        float pitchAngle = engine.getPitch() - engine.getFOVV() / 2 + ((float) j / getHeight()) * engine.getFOVV();
        float dist = engine.castRay(pitchAngle, yawAngle);
        if (dist < 1)
          dist = 1;
        Color shade = new Color((int) (255 / (dist * shadeFactor)), (int) (255 / (dist * shadeFactor)), (int) (255 / (dist * shadeFactor)));
        g.setColor(shade);
        g.fillRect(i, j, resolution, resolution);
      }
    }
  }
}


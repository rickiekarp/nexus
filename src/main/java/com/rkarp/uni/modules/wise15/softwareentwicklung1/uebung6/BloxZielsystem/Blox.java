package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.BloxZielsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Same as Blox in com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.Blox, but with increased _player speed and collision detection.
 */
public class Blox
{
    private Box _player    = new Box(0, 20, 240, 20);
    private Box _obstacle1 = new Box(50, 50, 50, 450);
    private Box _obstacle2 = new Box(250, 50, 0, 450);
    private Box _obstacle3 = new Box(450, 50, 50, 450);

    private boolean _cursorLeftPressed;
    private boolean _cursorRightPressed;
    private boolean _cursorUpPressed;
    private boolean _cursorDownPressed;

    private final int SPEED = 5;
    
    private Graphics2D _canvas;

    private void draw()
    {
        _canvas.setColor(Color.GRAY);
        draw(_obstacle1);
        draw(_obstacle2);
        draw(_obstacle3);

        _canvas.setColor(Color.BLUE);
        draw(_player);
    }

    private void draw(Box box)
    {
        _canvas.fillRect(box.x(), box.y(), box.width(), box.height());
    }

    private void movePlayer()
    {
        if (_cursorLeftPressed)
        {
            _player.moveHorizontally(-SPEED);
        }
        else if (_cursorRightPressed)
        {
            _player.moveHorizontally(+SPEED);
        }
        if (_cursorUpPressed)
        {
            _player.moveVertically(-SPEED);
        }
        else if (_cursorDownPressed)
        {
            _player.moveVertically(+SPEED);
        }
    }

    private boolean hasPlayerReachedGoal()
    {
        return _player.x() >= 500;
    }

    private boolean hasPlayerLeftWorld()
    {
        return _player.x() < 0 || _player.y() < 0 || _player.y() > 480;
    }

    private boolean hasPlayerRunIntoObstacle()
    {
        return _player.collidesWith(_obstacle1)
            || _player.collidesWith(_obstacle2)
            || _player.collidesWith(_obstacle3);
    }










    // mostly boring implementation details

    public Blox()
    {
        configurePanel();
        configureFrame();
        listenToKeys();
        startTimer();
    }

    private JPanel _panel;
    private JFrame _frame;

    private void configurePanel()
    {
        _panel = new JPanel()
        {
            public void paint(Graphics graphics)
            {
                super.paint(graphics);
                _canvas = (Graphics2D) graphics;
                _canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                _canvas.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                draw();
                Toolkit.getDefaultToolkit().sync();
            }
        };
        _panel.setPreferredSize(new Dimension(500, 500));
    }

    private void configureFrame()
    {
        _frame = new JFrame("Blox");
        _frame.add(_panel);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.pack();
        _frame.setResizable(false);
        _frame.setVisible(true);
    }

    private void listenToKeys()
    {
        _frame.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent event)
            {
                switch (event.getKeyCode())
                {
                case 37:
                    _cursorLeftPressed = true;
                    break;
                case 39:
                    _cursorRightPressed = true;
                    break;
                case 38:
                    _cursorUpPressed = true;
                    break;
                case 40:
                    _cursorDownPressed = true;
                    break;
                }
            }

            public void keyReleased(KeyEvent event)
            {
                switch (event.getKeyCode())
                {
                case 37:
                    _cursorLeftPressed = false;
                    break;
                case 39:
                    _cursorRightPressed = false;
                    break;
                case 38:
                    _cursorUpPressed = false;
                    break;
                case 40:
                    _cursorDownPressed = false;
                    break;
                }
            }
        });
    }

    private void startTimer()
    {
        ActionListener step = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                _panel.repaint();
                movePlayer();
                checkWin();
                checkFail();
            }

            private void checkWin()
            {
                if (hasPlayerReachedGoal())
                {
                    JOptionPane.showMessageDialog(null, "You did it :-)", "WIN", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }

            private void checkFail()
            {
                if (hasPlayerLeftWorld() || hasPlayerRunIntoObstacle())
                {
                    JOptionPane.showMessageDialog(null, "You failed :-(", "FAIL", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        };
        new Timer(40, step).start();
    }
}

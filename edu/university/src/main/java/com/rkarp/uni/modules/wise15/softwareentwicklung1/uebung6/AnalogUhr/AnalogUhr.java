package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung6.AnalogUhr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Eine optisch ansprechende, automatisch animierte Analog-Uhr.
 * 
 * @author Fredrik Winkler
 * @version 12. November 2015
 */
public class AnalogUhr
{
    private Zeitmesser _zeitmesser = new Zeitmesser();

    private DrehbarerStrich _sekunden = new DrehbarerStrich(1.0, _zeitmesser, 1);
    private DrehbarerStrich _minuten = new DrehbarerStrich(0.9, _zeitmesser, 60);
    private DrehbarerStrich _stunden = new DrehbarerStrich(0.8, _zeitmesser, 3600); // Stunden Zeiger (Aufg 6.3.4)

    private DrehbarerStrich _grob = new DrehbarerStrich(0.1);
    private DrehbarerStrich _fein = new DrehbarerStrich(0.05); // Minutenstriche auf dem Ziffernblatt (Aufg 6.3.5)

    private Graphics2D _leinwand;
    private double _skalierung;

    private void zeichne()
    {
        zeichneZiffernblatt();
        zeichneMinutenZiffernblatt(); //Aufg 6.3.5
        zeichneZeiger();
    }

    private void zeichneZeiger()
    {
        zeichneZeiger(_stunden, 4, Color.RED); // Stunden Zeiger (Aufg 6.3.4)
        zeichneZeiger(_minuten, 4, Color.BLUE);
        zeichneZeiger(_sekunden, 2, Color.MAGENTA);
    }

    private void zeichneZeiger(DrehbarerStrich strich, float breite, Color farbe)
    {
        _leinwand.setStroke(new BasicStroke(breite));
        _leinwand.setColor(farbe);
        _leinwand.draw(strich.berechneLinie(_skalierung));
    }

    private void zeichneZiffernblatt()
    {
        zeichneAbschnitt(0);
        zeichneAbschnitt(30);
        zeichneAbschnitt(60);
        zeichneAbschnitt(90);
        zeichneAbschnitt(120);
        zeichneAbschnitt(150);
        zeichneAbschnitt(180);
        zeichneAbschnitt(210);
        zeichneAbschnitt(240);
        zeichneAbschnitt(270);
        zeichneAbschnitt(300);
        zeichneAbschnitt(330);
    }

    //Aufg. 6.3.5
    private void zeichneMinutenZiffernblatt()
    {
        int winkel = 6;
        for (int i = 0; i < 12; i++)
        {
            for (int e = 0; e < 4; e++)
            {
                // Minutenstriche auf dem Ziffernblatt (Aufg 6.3.5)
                _fein.setzeWinkel(winkel);
                _leinwand.draw(_fein.berechneLinie(_skalierung));
                winkel += 6;
            }
            winkel += 6;
        }
    }

    private void zeichneAbschnitt(double startWinkel)
    {
        _grob.setzeWinkel(startWinkel);
        _leinwand.draw(_grob.berechneLinie(_skalierung));
    }










    // Die folgenden Implementationsdetails muesst ihr noch nicht verstehen.
    // Grafische Oberflaechen werden erst in SE2 thematisiert.

    private JPanel _panel;
    private JFrame _frame;

    /**
     * Initialisiert die analoge Uhr auf Mitternacht und startet die Animation.
     */
    public AnalogUhr()
    {
        configurePanel();
        configureFrame();
        startTimer();
    }

    private void configurePanel()
    {
        _panel = new JPanel()
        {
            public void paint(Graphics graphics)
            {
                super.paint(graphics);
                _leinwand = (Graphics2D) graphics;
                _leinwand.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                _leinwand.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                _skalierung = Math.min(_panel.getWidth(), _panel.getHeight()) * 0.5;
                zeichne();
                Toolkit.getDefaultToolkit().sync();
            }
        };
        _panel.setPreferredSize(new Dimension(500, 500));
    }

    private void configureFrame()
    {
        _frame = new JFrame("Analog-Uhr");
        _frame.add(_panel);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.pack();
        _frame.setVisible(true);
    }

    private void startTimer()
    {
        ActionListener schritt = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                _zeitmesser.notiereAktuelleZeit();
                _panel.repaint();
            }
        };
        new Timer(40, schritt).start();
    }
}

package pruebapomodoro;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
ACLARACION!!!

   El programa funcionaría bien en teoria, pero no funciona bien con el tema de los segundos al 
*/


public class temp extends Thread implements ActionListener{
    JFrame frame;
    static JLabel tiempo;
    JButton controlTiempo;
    JButton restart;
    Cronometro crono;
    static boolean cronometroCreado;
    
    public temp()
    {
        frame = new JFrame();
        
        tiempo = new JLabel("El tiempo es ");
        
        controlTiempo = new JButton("Clickeame");
        controlTiempo.addActionListener(this);
        
        restart = new JButton("Restart");
        restart.addActionListener(this);
        
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new GridLayout());
        frame.add(tiempo);
        frame.add(controlTiempo);
        frame.add(restart);
        tiempo.setVisible(true);
    }
    
    public static void setTextTiempo(String text)
    {
           tiempo.setText(text);
    }
    
    public static boolean getCronometroCreado()
    {
        return cronometroCreado;
    }
    
    public static void setCronometroCreado(boolean num)
    {
        cronometroCreado = num;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == controlTiempo)
        {
            if(temp.getCronometroCreado() == false)
            {
                System.out.println("Se crea el cronometro!");
                temp.setCronometroCreado(true);
                crono = new Cronometro();
                crono.start();
             }
            else
            {
                if (crono.getSigaCronometro() == true)
             {
                    System.out.println("Se paro el cronometro");
                    crono.setSigaCronometro(false);
                }
                else
                {
                    System.out.println("Se despauso el cronometro");
                    crono.setSigaCronometro(true);
                }
            }
        }
        
        else if(e.getSource() == restart && temp.getCronometroCreado() == true)
        {
            
            crono.setSigaCronometro(false);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(temp.class.getName()).log(Level.SEVERE, null, ex);
            }
            crono.setTiempo(0);
        }

   }
    
    class Cronometro extends Thread
    {
        boolean sigaCronometro = true;
        boolean exit = true;
        long tiempo = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date;
        
        public boolean getSigaCronometro()
        {
            return this.sigaCronometro;
        }
        
        public void setSigaCronometro(boolean bool)
        {
            this.sigaCronometro = bool;
        }
        
        public boolean getExit()
        {
            return this.exit;
        }
        
        public void setExit(boolean bool)
        {
            this.exit = bool;
        }
        
        public long getTiempo()
        {
            return this.tiempo;
        }
        
        public void setTiempo(long num)
        {
            this.tiempo = num;
        }
        
        @Override
        public void run()
        {
            while(exit == true)
            {
                temp.setTextTiempo(String.valueOf(tiempo));
                while (sigaCronometro)
                {
                   date = Calendar.getInstance().getTime();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(temp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (sigaCronometro == true)
                    {
                    tiempo+= 1;
                    }
                   temp.setTextTiempo(String.valueOf(tiempo));
            }
            }
        }
    }
}

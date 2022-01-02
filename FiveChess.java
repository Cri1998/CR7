package Backgammon;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FiveChess extends JFrame implements MouseListener, Runnable {
   
    private static final long serialVersionUID = 1L; 
    int width = Toolkit.getDefaultToolkit().getScreenSize().width; 
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    int x, y;
    int[][] allChess = new int[15][15];
    boolean isBlack = true;
    boolean canPlay = true;
    String message = "�ڷ�����";
    // ��������
    int[] chessX = new int[255];
    int[] chessY = new int[255];
    int countX, countY;
    int maxTime = 0;
    int inputTime = 0;
    // ��Ϸʱ�����õ���Ϣ
    String blackMessage = "������";
    String whiteMessage = "������";
    int blackTime = 29;
    int whiteTime = 0;
    // ��Ϸ����ʱ
    Thread timer = new Thread(this); //�ǰѵ�ǰ���ʵ����Runnable�ӿڵ������ó�һ���߳�

    public FiveChess() {
        this.setTitle("������С��Ϸ  ");
        this.setSize(500, 500);
        this.setLocation((width - 500) / 2, (height - 500) / 2);
        this.setResizable(false);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);  
        this.repaint();
        this.addMouseListener(this);//addMouseListener������ʵ�ֶ� Mouse �ļ��������Ծ���Ҫʵ�� MouseListener ��
        timer.start();
        timer.suspend();  //�߳���ͣ
    }

    public void paint(Graphics g) {
        // ������                 
        BufferedImage bi = new BufferedImage(500, 500,BufferedImage.TYPE_INT_RGB); //ͼ�񻺴���
        Graphics g2 = bi.createGraphics();  //������

        g2.setColor(new Color(91,91,91)); 
        g2.fillRect(0, 0, 500, 500); 
        g2.setColor(new Color(128,0,255));
        g2.fill3DRect(43, 60, 375, 375, true);

        for (int i = 0; i <= 15; i++) {
            g2.setColor(Color.WHITE);
            g2.drawLine(43, i * 25 + 60, 375 + 43, 60 + i * 25);
            g2.drawLine(i * 25 + 43, 60, 43 + i * 25, 375 + 60);
        }

        g2.setFont(new Font("����", Font.BOLD, 20));
        g2.drawString("�����ɣ�" + message, 50, 50);
        g2.drawRect(30, 440, 180, 40);
        g2.drawRect(250, 440, 180, 40);
        g2.setFont(new Font("����", 0, 12));
        g2.drawString("�ڷ�ʱ�䣺" + blackMessage, 40, 465);
        g2.drawString("�׷�ʱ�䣺" + whiteMessage, 260, 465);
        // ���¿�ʼ��ť
        g2.drawRect(428, 66, 54, 20);
        g2.drawString("���¿�ʼ", 432, 80);

        // ��Ϸ���ð�ť
        g2.drawRect(428, 106, 54, 20);
        g2.drawString("��Ϸ����", 432, 120);

        // ��Ϸ˵����ť
        g2.drawRect(428, 146, 54, 20);
        g2.drawString("��Ϸ���", 432, 160);

        // �˳���Ϸ��ť
        g2.drawRect(428, 186, 54, 20);
        g2.drawString("�˳���Ϸ", 432, 200);


        // ����
        g2.drawRect(428, 246, 54, 20);
        g2.drawString("����", 442, 260);

        // ����
        g2.drawRect(428, 286, 54, 20);
        g2.drawString("����", 442, 300);

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                // ����
                if (allChess[i][j] == 1) {
                    int tempX = i * 25 + 55;
                    int tempY = j * 25 + 72;
                    g2.setColor(Color.BLACK);
                    g2.fillOval(tempX - 8, tempY - 8, 16, 16);
                }
                // ����
                if (allChess[i][j] == 2) {
                    int tempX = i * 25 + 55;
                    int tempY = j * 25 + 72;
                    g2.setColor(Color.WHITE);
                    g2.fillOval(tempX - 8, tempY - 8, 16, 16); 
                    g2.drawOval(tempX - 8, tempY - 8, 16, 16); 
                }
            }
        }
       
        g.drawImage(bi, 0, 0, this);  //��ָ��λ�ò��Ұ�ָ����״�ʹ�С����ָ����Image��

    }

    public void mouseClicked(MouseEvent arg0) { 
    }

    public void mouseEntered(MouseEvent arg0) {

    }

    public void mouseExited(MouseEvent arg0) { 

    }

    public void mousePressed(MouseEvent e) {  
        boolean checkWin = false;   
        
      
        if (canPlay) {
            x = e.getX();  //���������X
            y = e.getY();
            if (x >= 43 && x <= 418 && y >= 60 && y <= 435) {  
                    x = (x - 43) / 25;
                    y = (y - 60) / 25;
                   
                if (allChess[x][y] == 0) {

                    chessX[countX++] = x;
                    chessY[countY++] = y;

                    if (isBlack) {
                        allChess[x][y] = 1;
                        isBlack = false;
                        message = "�׷�����";
                        blackTime = inputTime;
                    } else {
                        allChess[x][y] = 2;
                        isBlack = true;
                        message = "�ڷ�����";
                        whiteTime = inputTime;
                    }
                    this.repaint();
                    checkWin = isWin();
                    if (checkWin) {
                        if (allChess[x][y] == 1) {
                            JOptionPane.showMessageDialog(this, "��Ϸ�������ڷ�ʤ��");  
                            restart();        
                        }
                        else {
                            JOptionPane.showMessageDialog(this, "��Ϸ�������׷�ʤ��");  
                            restart();
                        }
                    }
                }
            }
        }

        // ���¿�ʼ��Ϸ��ť
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 66&& e.getY() <= 86) {
            int result = JOptionPane.showConfirmDialog(this, "�Ƿ����¿�ʼ��Ϸ��","��ѡ��",JOptionPane.YES_NO_CANCEL_OPTION); 
            if (result == 0) {  
                restart();   
            }
        }
        // ��Ϸ����ʱ����
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 106&& e.getY() <= 126) {
            String input = JOptionPane.showInputDialog("��������Ϸ�����ʱ��(��λ:��),�������0,��ʾû��ʱ������:");
                                   
            maxTime = Integer.parseInt(input);   //Integer(����)
            inputTime = Integer.parseInt(input);
            if (maxTime < 0) {
            	          
                JOptionPane.showMessageDialog(this, "�������Ϸʱ���������������ã�");
            } else if (maxTime == 0) {
                int result = JOptionPane.showConfirmDialog(this, "��Ϸʱ�����óɹ����Ƿ�ʼ��Ϸ��");
                if (result == 0) {
                    restart();
                }
            } else if (maxTime > 0) {
                int result = JOptionPane.showConfirmDialog(this,"��Ϸʱ�����óɹ����Ƿ����¿�ʼ��Ϸ��");
                if (result == 0) {
                    for (int i = 0; i < 15; i++)
                        for (int j = 0; j < 15; j++)
                            allChess[i][j] = 0;
                    for (int i = 0; i < 15; i++) {
                        chessX[i] = -1;
                        chessY[i] = -1;
                    }
                    countX = 0;
                    countY = 0;
                    message = "�ڷ�����";
                    isBlack = true;
                    
                   
                    blackMessage = maxTime / 3600 + ":"+ (maxTime / 60 - maxTime / 3600 * 60) + ":"+ (maxTime - maxTime / 60 * 60);
                    whiteMessage = maxTime / 3600 + ":"+ (maxTime / 60 - maxTime / 3600 * 60) + ":"+ (maxTime - maxTime / 60 * 60);

                    blackTime = maxTime;
                    whiteTime = maxTime;
                  
                    timer.resume();  //�ָ̻߳�
                    this.canPlay = true;
                    this.repaint();
                }
            }
        }
        // ��Ϸ���
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 146
                && e.getY() <= 166) {
            JOptionPane.showMessageDialog(this, "��������ȫ�������˶��Ὰ����Ŀ֮һ����һ�����˶��ĵĴ�������������Ϸ��ͨ��˫���ֱ�ʹ�úڰ���ɫ�����ӣ���������������������ߵĽ�����ϣ����γ����������߻�ʤ��");
        }
        // �˳���Ϸ
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 186
                && e.getY() <= 206) {
            int result = JOptionPane.showConfirmDialog(this, "�Ƿ��˳���Ϸ��");
            if (result == 0) {
                System.exit(0);
            }
        }
        // ����
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 246&& e.getY() <= 266) {
            int result = JOptionPane.showConfirmDialog(this,(isBlack == true ? "�׷����壬�ڷ��Ƿ�ͬ�⣿" : "�ڷ����壬�׷��Ƿ�ͬ�⣿"));
            if (result == 0) {
            	
                allChess[chessX[--countX]][chessY[--countY]] = 0;
                isBlack = (isBlack == true) ? false : true;
                this.repaint();
            }
        }
        // ����
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 286
                && e.getY() <= 306) {
            int result = JOptionPane.showConfirmDialog(this, "�Ƿ����䣿");
            if (result == 0) {
                JOptionPane.showMessageDialog(this, "��Ϸ������"+ (isBlack == true ? "�ڷ����䣬�׷���ʤ!" : "�׷����䣬�ڷ���ʤ!"));
                for (int i = 0; i < 15; i++)
                    for (int j = 0; j < 15; j++)
                        allChess[i][j] = 0;
               
                countX = 0;
                countY = 0;
                message = "�ڷ�����";
                isBlack = true;
                if(maxTime!=0) {
                blackMessage = maxTime / 3600 + ":"+ (maxTime / 60 - maxTime / 3600 * 60) + ":"+ (maxTime - maxTime / 60 * 60);
                whiteMessage = maxTime / 3600 + ":"+ (maxTime / 60 - maxTime / 3600 * 60) + ":" + (maxTime - maxTime / 60 * 60);
                }else {
                	blackMessage = "������";
                    whiteMessage = "������";
                }
                blackTime = maxTime;
                whiteTime = maxTime;
               
                timer.resume();  //resume��ʹ�ָ̻߳�
                this.canPlay = true;
                this.repaint();
            }
        }
    }

    public void mouseReleased(MouseEvent arg0) {   //����ͷ�

    }

    private boolean isWin() {
        boolean flag = false;
        int count = 1;
        // �жϺ����Ƿ���5����������
        int color = allChess[x][y];
        // �жϺ���
        count = this.checkCount(1, 0, color);
        if (count >= 5) {
            flag = true;
        } else {
            // �ж�����
            count = this.checkCount(0, 1, color);
            if (count >= 5) {
                flag = true;
            } else {
                // �ж����ϡ�����
                count = this.checkCount(1, -1, color);
                if (count >= 5) {
                    flag = true;
                } else {
                    // �ж����¡�����
                    count = this.checkCount(1, 1, color);
                    if (count >= 5) {
                        flag = true;
                    }
                }
            }
        }

        return flag;
    }

    // �ж��������ӵ�����
    private int checkCount(int xChange, int yChange, int color) {     //һ������
        int count = 1;
        int tempX = xChange;
        int tempY = yChange;
        while (x + xChange >= 0 && x + xChange <= 14 && y + yChange >= 0&& y + yChange <= 14&& color == allChess[x + xChange][y + yChange]) {
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0) {
                if (yChange > 0)
                    yChange++;
                else {
                    yChange--;
                }
            }
        }
        xChange = tempX;
        yChange = tempY;
                                        //       �෴����
        while (x - xChange >= 0 && x - xChange <= 14 && y - yChange >= 0&& y - yChange <= 14&& color == allChess[x - xChange][y - yChange]) {
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0) {
                if (yChange > 0)
                    yChange++;
                else {
                    yChange--;
                }
            }
        }
        return count;
    }

    public void run() {   //�ж��Ƿ�ʱ
        if (maxTime > 0) {
            while (true) {
                if (isBlack) {
                    blackTime--;
                    if (blackTime == 0) {
                        JOptionPane.showMessageDialog(this, "�ڷ���ʱ,��Ϸ����!");
                        restart();
                    }
                } else {
                    whiteTime--;
                    if (whiteTime == 0) {
                        JOptionPane.showMessageDialog(this, "�׷���ʱ,��Ϸ����!");
                        restart();
                    }
                }                             //�ڷ����壬Ȼ��׷�ʱ��ظ�����ʼֵ�İ׷�ʱ��
                blackMessage = blackTime / 3600 + ":"+ (blackTime / 60 - blackTime / 3600 * 60) + ":"+ (blackTime - blackTime / 60 * 60);
                whiteMessage = whiteTime / 3600 + ":"+ (whiteTime / 60 - whiteTime / 3600 * 60) + ":"+ (whiteTime - whiteTime / 60 * 60);
                this.repaint();
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();   //�׳��쳣�����򲻻����
                }
            }
        }
    }

    public void restart() {    //��Ϸ���¿�ʼ
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                allChess[i][j] = 0;
        for (int i = 0; i < 15; i++) {
            chessX[i] = 0;
            chessY[i] = 0;
        }
        countX = 0;    //ĳһ������������X��ĵ�countX��������
        countY = 0;
        message = "�ڷ�����";
        blackMessage = "������";
        whiteMessage = "������";
        blackTime = maxTime;
        whiteTime = maxTime;
        isBlack = true;
        canPlay = true;
        this.repaint();
    }

    public static void main(String[] args) {
        new FiveChess();
    }

}
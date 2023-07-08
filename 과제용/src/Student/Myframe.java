package Student;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class Myframe {
   // 그림에 얼마 범위에 들어왔을 때 충돌로 결정할 것인지의 값
   final int ITEM_MARGIN = 60;
   final int POLICE_MARGIN = 75;
   final int BOY_MARGIN = 80;
   final int GIRL_MARGIN = 120;

   // 새로운 아이템과 캐릭터가 나타나는 주기
   final int DRINK_INTERVAL = 10;
   final int GOLD_INTERVAL = 5;
   final int POLICE_INTERVAL = 10;
   // 버튼토글숫자
   private final int GO = 1;
   private final int SUSPEND = 2;
   private final int CONT = 4;
   private final int END = 8;
   // 지폐,금의 갯수
   int count = 0;
   // 경찰의 시간당 이동속도
   int policeStep = 1;
   // 플레이어의 이동속도
   int playerStep = 20;
   // 지폐,금의 좌표
   int moneyX = 550;
   int moneyY = 550;
   // 캐릭터와 공격자가 나타나는 초기 위치 좌표
   int frameWidth = 630;
   int frameHeight = 530;
   PosImageIcon player;
   public static String id;

   // true: 생성됨. false: 생성안됨
   boolean flag = false;

   // 생성된 프레임과 패널
   JFrame startFrame = new JFrame(" 돈을 갖고 튀어라 ");
   JFrame charFrame = new JFrame(" 캐릭터를 고르시오 ");
   JFrame gameFrame = new JFrame("돈을 갖고 튀어라 ");
   JFrame setFrame = new JFrame(" 소리설정 ");
   JPanel startPanel;
   JPanel setPanel;
   JPanel charPanel;
   JPanel chartopPanel;
   JPanel charcenterPanel;
   JPanel gamemainPanel;
   JPanel scorePanel;
   GamePanel gamePanel;

   // 생성된 라벨과 버튼
   JLabel background;
   JButton start;
   JButton setting;
   JButton boy;
   JButton girl;
   JButton bstext;
   JButton estext;
   JButton go = new JButton("시작"); // 시작버튼
   JButton suspend = new JButton("일시중지"); // 일시중지 버튼
   JButton cont = new JButton("계속"); // 계속 버튼
   JButton end = new JButton("종료"); // 종료버튼
   JLabel runTime = new JLabel("도망시간 : 0분 0초");
   JLabel score = new JLabel("지폐수:");

   // Image
   final String POLICE = "Image/경찰.jpeg";
   final String MEN = "Image/남자캐릭터.png";
   final String GIRL = "Image/여자캐릭터.png";
   final String OBSTACLE = "Image/덫.png";
   final String DRINK = "Image/에너지_드링크.jpeg";
   final String MONEY = "Image/지폐.png";
   final String GOLD = "Image/금png.png";
   ImageIcon gameStartButton = new ImageIcon("Image/게임시작버튼.jpg");
   ImageIcon soundSetting = new ImageIcon("Image/소리설정.png");
   ImageIcon menImage = new ImageIcon("Image/남자캐릭터.png");
   ImageIcon girlImage = new ImageIcon("Image/여자캐릭터.png");
   ImageIcon gamebackground = new ImageIcon("Image/게임화면.png");
   ImageIcon money = new ImageIcon("Image/지폐.png");
   ImageIcon gold = new ImageIcon("Image/금.png");

   // sound
   File gameBGM = new File("sound/커닝시티.wav");
   File getItem = new File("sound/jelly1.wav");
   File crash = new File("sound/boom.wav");
   Clip gameBGMClip, getItemClip, crashClip;

   // 시간 Timer
   Timer timeTimer;
   Timer gameTimer;
   private final int TIMER_INTERVAL = 10; // 0.01초 주기
   private final int ONE_SECOND = 1000; // 1초마다 실행되는 시간 클래스
   ClockListener clockListener = new ClockListener();

   // ArrayList
   // 경찰
   ArrayList<PosImageIcon> policeList = new ArrayList<PosImageIcon>();
   // 에너지 드링크
   ArrayList<PosImageIcon> drinkList = new ArrayList<PosImageIcon>();
   // 장애물
   ArrayList<PosImageIcon> obstacleList = new ArrayList<PosImageIcon>();
   // 지폐
   ArrayList<PosImageIcon> moneyList = new ArrayList<PosImageIcon>();
   // 금
   ArrayList<PosImageIcon> goldList = new ArrayList<PosImageIcon>();

   public static void main(String[] args) {
      id = JOptionPane.showInputDialog("도둑의 이름을 입력하시오:");
      new Myframe();
   }

   public Myframe() {
      // 시작프레임 설정
      startFrame.setResizable(false);
      startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      startPanel = new JPanel();
      startFrame.add(startPanel);
      startFrame.setSize(700, 680);

      // 게임시작배경화면 만들기
      background = new JLabel(new ImageIcon("Image/경찰과 도둑 배경화면.jpg"));
      background.setBounds(0, 0, 700, 700);
      startPanel.add(background);

      // 시작버튼지정
      start = new JButton(gameStartButton);
      start.setBounds(380, 50, 350, 100);
      start.setContentAreaFilled(false);
      start.setFocusPainted(false);
      start.setBorderPainted(false);
      start.addActionListener(new StartButton());
      background.add(start);

      // 소리버튼지정
      setting = new JButton(soundSetting);
      setting.setBounds(730, 400, 120, 120);
      setting.setContentAreaFilled(false);
      setting.setFocusPainted(false);
      setting.setBorderPainted(false);
      background.add(setting);
      setting.addActionListener(new SettingButton());

      // 소리프레임
      setFrame.setResizable(false);
      setFrame.setSize(300, 300);
      setFrame.setVisible(false);
      // 소리패널
      setPanel = new JPanel();
      setPanel.setLayout(new GridLayout(1, 0));
      // 소리 음소거 버튼 0n/0ff
      bstext = new JButton("음소거 모드");
      bstext.addActionListener(new Mute());
      setPanel.add(bstext);
      estext = new JButton("음소거 모드 해제");
      estext.addActionListener(new Muteoff());
      setPanel.add(estext);
      setFrame.add(setPanel);

      // 캐릭터프레임
      charFrame.setResizable(false);
      charFrame.setSize(700, 700);
      charFrame.setVisible(false);

      // 캐릭터패널
      JLabel charex = new JLabel("캐릭터를 고르시오 ");
      charex.setBounds(300, 100, 10, 10);
      charex.setFont(new Font("HY나무B", Font.BOLD, 26));
      charPanel = new JPanel();
      chartopPanel = new JPanel();
      chartopPanel.setBounds(0, 100, 700, 100);
      charcenterPanel = new JPanel();
      charcenterPanel.setBounds(0, 300, 700, 600);
      charPanel.setLayout(null);
      charPanel.add(chartopPanel);
      charPanel.add(charcenterPanel);
      chartopPanel.add(charex);

      // 캐릭터버튼
      boy = new JButton(menImage);
      girl = new JButton(girlImage);
      charcenterPanel.add(boy);
      boy.setPreferredSize(new Dimension(300, 300));
      boy.addActionListener(new charbutton());
      charcenterPanel.add(girl);
      girl.setPreferredSize(new Dimension(300, 300));
      girl.addActionListener(new charbutton());
      Border border = BorderFactory.createTitledBorder("캐릭터");
      charcenterPanel.setBorder(border);
      charFrame.add(charPanel);

      // 게임프레임
      gameFrame.setSize(700, 700);
      gameFrame.setVisible(false);

      // gamePanel 패널
      gamePanel = new GamePanel();
      gamePanel.setBounds(0, 0, 700, 630);

      // scorePanel 패널
      scorePanel = new JPanel();
      scorePanel.setBackground(Color.white);
      scorePanel.setBounds(0, 630, 700, 70);
      scorePanel.add(go);
      scorePanel.add(suspend);
      scorePanel.add(cont);
      scorePanel.add(end);
      scorePanel.add(runTime);
      scorePanel.add(new JLabel(" Player :"));
      scorePanel.add(new JLabel(id));
      scorePanel.add(score);
      score.setFont(new Font("AR CARTER", Font.BOLD, 22));
      JLabel scoreLabel = new JLabel();
      scorePanel.add(scoreLabel);
      gamePanel.setLabel(scoreLabel);
      scoreLabel.setFont(new Font("AR CARTER", Font.BOLD, 22));
      scorePanel.setOpaque(false);
      // gamePanel.scorePanel를 담을 패널
      JPanel gamemainPanel = new JPanel();
      gamemainPanel.setLayout(null);
      gamemainPanel.add(gamePanel);
      gamemainPanel.add(scorePanel);

      // 1초마다 실행되는 시간 관련 리스너
      timeTimer = new Timer(ONE_SECOND, clockListener);
      // 0.01초마다 실행되는 움직임 관련 리스너
      gameTimer = new Timer(TIMER_INTERVAL, new MyClass());
      // 키보드 방향키가 눌릴 시 시작되는 키리스너
      gamePanel.addKeyListener(new DirectionListener());
      // 마우스가 눌릴 시 시작되는 마우스리스너
      gamePanel.addMouseListener(new Mouse());
      gamePanel.setFocusable(false);

      gameFrame.add(gamemainPanel);
      gameFrame.setResizable(false);
      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      startFrame.setVisible(true);

      // 시작.정지.재시작.종료 버튼의 리스너
      go.addActionListener(new StartListener());
      suspend.addActionListener(new SuspendListener());
      cont.addActionListener(new ContListener());
      end.addActionListener(new EndListener());
      buttonToggler(GO);

      // 음악 실행 코드
      try {
         gameBGMClip = AudioSystem.getClip();
         gameBGMClip.open(AudioSystem.getAudioInputStream(gameBGM));
         gameBGMClip.loop(Clip.LOOP_CONTINUOUSLY);
         gameBGMClip.start();

         getItemClip = AudioSystem.getClip();
         getItemClip.open(AudioSystem.getAudioInputStream(getItem));

         crashClip = AudioSystem.getClip();
         crashClip.open(AudioSystem.getAudioInputStream(crash));

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // 버튼 토글이 실행되는 조건
   private void buttonToggler(int flags) {
      if ((flags & GO) != 0)
         start.setEnabled(true);
      else
         start.setEnabled(false);
      if ((flags & SUSPEND) != 0)
         suspend.setEnabled(true);
      else
         suspend.setEnabled(false);
      if ((flags & CONT) != 0)
         cont.setEnabled(true);
      else
         cont.setEnabled(false);
      if ((flags & END) != 0)
         end.setEnabled(true);
      else
         end.setEnabled(false);
   }

   private void finishGame() {
      // 시간 디스플레이 멈춤
      timeTimer.stop();
      // 그림객체 움직임 멈춤
      gameTimer.stop();
      // 배경음악 멈춤
      gameBGMClip.stop();
      // 패널 조작 멈춤
      gamePanel.setFocusable(false);
   }

   public class MyClass implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         // 만약 장애물과 충돌하였으면 충돌의 효과음 나타내고 타이머를 중단시킴
         for (PosImageIcon s : obstacleList) {
            if (s.collide(new Point(player.x, player.y))) {
               crashClip.setFramePosition(0);
               crashClip.start();
               finishGame(); // 게임 중단
            }
         }
         // 만약 경찰과 충돌하였으면 충돌의 효과음 나타내고 타이머를 중단시킴
         for (PosImageIcon s : policeList) {
            if (s.collide(new Point(player.x, player.y))) {
               crashClip.setFramePosition(0);
               crashClip.start();
               finishGame(); // 게임 중단
            }
         }

         // 그림 객체 이동
         for (PosImageIcon s : policeList)
            s.move();
         for (PosImageIcon s : drinkList)
            s.move();

         // 만약 돈과 충돌하였으면 아이템 먹음 효과음을 나타내고 스코어 1씩 올림
         for (PosImageIcon s : moneyList) {
            if (s.collide(new Point(player.x, player.y))) {
               getItemClip.setFramePosition(0);
               getItemClip.start();

               s.x = (int) (Math.random() * 550 + 1);
               s.y = (int) (Math.random() * 550 + 1);

               count = count + 1;

             
            }

            gamePanel.repaint();

         }
         // 만약 금과 충돌하였으면 아이템 먹음 효과음을 나타내고 스코어 5씩 올림
         for (PosImageIcon s : goldList) {
            if (s.collide(new Point(player.x, player.y))) {

               getItemClip.setFramePosition(0);
               getItemClip.start();

               s.x = (int) (Math.random() * 550 + 1);

               s.y = (int) (Math.random() * 550 + 1);

               count += 5;

               // 카운터가 10주기로 장애물 1개씩 추가
              
            }
         }
     
         switch (count / 10) {
         
  
         case 1:
            if (!flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = true;
            }
            break;
         case 2:
            if (flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = false;
            }
            break;
         case 3:
            if (!flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = true;
            }
            break;
         case 4:
            if (flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = false;
            }
            break;
         case 5:
            if (!flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = true;
            }
            break;
         case 6:
            if (flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = false;
            }
            break;
         case 7:
            if (!flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = true;
            }
            break;
         case 8:
            if (flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = false;
            }
            break;
         case 9:
            if (!flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = true;
            }
            break;
         case 10:
            if (flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = false;
            }
            break;
         case 11:
            if (!flag) {
               obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
               flag = true;
            }
            break;
         case 12:
             if (flag) {
                obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
                flag = false;
             }
             break;
         case 13:
             if (!flag) {
                obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
                flag = true;
             }
             break;
         case 14:
             if (flag) {
                obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
                flag = false;
             }
             break;
         case 15:
             if (!flag) {
                obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
                flag = true;
             }
             break;
         case 16:
             if (flag) {
                obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
                flag = false;
             }
             break;
         case 17:
             if (!flag) {
                obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
                flag = true;
             }
             break;
         case 18:
             if (flag) {
                obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
                flag = false;
             }
             break;
         case 19:
             if (!flag) {
                obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
                flag = true;
             }
             break;
         case 20:
             if (flag) {
                obstacleList.add(new PosImageIcon(OBSTACLE, ITEM_MARGIN, 0, moneyX, moneyY));
                flag = false;
             }
             break;
         default:
            break;
         }

      }
   }

   // 게임이 진행되는 메인 패널
   class GamePanel extends JPanel {

      public void paintComponent(Graphics g) {
         // 잔상이 남지 않게 매번 repaint
         g.drawImage(gamebackground.getImage(), 0, 0, 700, 630, null);

         // 게임에 사용되는 그래픽 객체들 모두 그려줌
         for (PosImageIcon s : policeList)
            s.draw(g, this);

         for (PosImageIcon s : drinkList)
            s.draw(g, this);

         for (PosImageIcon s : obstacleList)
            s.draw(g, this);

         for (PosImageIcon s : moneyList)
            s.draw(g, this);

         for (PosImageIcon s : goldList)
            s.draw(g, this);

         player.draw(g, this);
         String t = count * 1 + "";
         score.setText(t);
      }

      // 스코어가 바뀌게 해주는 메소드
      public void setLabel(JLabel scoreLabel) {
         score = scoreLabel;
      }
   }

   // 게임의 시작에 사용될 초기 그래픽 객체
   private void prepareAttackers() {
      policeList.add(new DiagonallyMovingShape(POLICE, POLICE_MARGIN, policeStep, frameWidth, frameHeight));
      moneyList.add(new PosImageIcon(MONEY, ITEM_MARGIN, 0, moneyX, moneyY));
      moneyList.add(new PosImageIcon(MONEY, ITEM_MARGIN, 0, moneyX, moneyY));
      moneyList.add(new PosImageIcon(MONEY, ITEM_MARGIN, 0, moneyX, moneyY));
      moneyList.add(new PosImageIcon(MONEY, ITEM_MARGIN, 0, moneyX, moneyY));
      moneyList.add(new PosImageIcon(MONEY, ITEM_MARGIN, 0, moneyX, moneyY));
   }

   // 여러종류의 움직임을 랜덤으로 발생시키는 공격객체의 생성
   private PosImageIcon getRandomAttacker(String pic, int margin, double steps,int x,int y) {
      int rand = (int) (Math.random() * 3) + 1;
      PosImageIcon newAttacker;

      switch (rand) {
      case 1:
         newAttacker = new DiagonallyMovingShape(pic, margin, steps, frameWidth, frameHeight);
         break;
      case 2:
         newAttacker = new HorizontallyMovingShape(pic, margin, steps, frameWidth, frameHeight);
         break;
      case 3:
         newAttacker = new VerticallyMovingShape(pic, margin, steps, frameWidth, frameHeight);
         break;
      default:
         newAttacker = new ballMovingShape(pic, margin, steps, frameWidth, frameHeight);
      }

      return newAttacker;
   }

   // 게임시작 버튼을 누를 때 일어나는 이벤트
   public class StartButton implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == start) {
            startFrame.setVisible(false);
            charFrame.setVisible(true);
         }
      }
   }

   // 설정 버튼을 누를 때 일어나는 이벤트
   public class SettingButton implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == setting) {
            startFrame.setVisible(true);
            setFrame.setVisible(true);
         }
      }
   }

   // 음소거 버튼을 누를 때 일어나는 이벤트
   public class Mute implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         gameBGMClip.stop();
         getItemClip.stop();
         crashClip.stop(); // stop 아니라 애초에 재생이 불가능하도록.
      }
   }

   // 음소거 버튼 해제 을 누를 때 일어나는 이벤트
   public class Muteoff implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         gameBGMClip.start();
      }
   }

   class StartListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         // timer stop
         timeTimer.stop();
         gameTimer.stop();

         // 초기화
         startMethod();
         prepareAttackers();
         runTime.setText("도망시간  : 0분 0초");
         clockListener.times = 0;

         // 시간 디스플레이 타이머시작
         gamePanel.setFocusable(true); // 게임 프레임 키 먹게 함
         gamePanel.requestFocus();
         buttonToggler(SUSPEND + END);// 활성화된 버튼의 조정

         timeTimer.start();
         gameTimer.start();
      }
   }

   // 그래픽 객체 다 초기화
   void startMethod() {
      policeList.clear();
      moneyList.clear();
      goldList.clear();
      obstacleList.clear();
      drinkList.clear();

      player.setX(frameWidth / 2);
      player.setY(frameHeight / 2 + 250);
      count = 0;
      playerStep = 20;
      gameBGMClip.start();

   }

   // 정지 버튼을 누를 때 일어나는 이벤트
   class SuspendListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         timeTimer.stop();
         gameTimer.stop();
         gamePanel.setFocusable(false); // 게임 프레임에 키 안먹게 함
         buttonToggler(CONT + END); // 활성화 버튼의 조정
      }
   }

   // 계속 버튼을 누를 때 일어나는 이벤트
   class ContListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         timeTimer.restart();
         gameTimer.restart();
         gamePanel.setFocusable(true); // 게임 프레임 키 먹게 함
         gamePanel.requestFocus(); // 전체 프레밍에 포커싱해서 키 먹게 함
         buttonToggler(SUSPEND + END); // 활성화 버튼의 조정
      }
   }

   // 종료버튼을 위한 감청자
   class EndListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         System.exit(0);
      }
   }

   // 캐릭터버튼을 누를 때 일어나는 이벤트
   public class charbutton implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         // 남자 캐릭터를 골랐다면
         if (e.getSource() == boy) {
            player = new PosImageIcon(MEN, BOY_MARGIN, frameWidth, frameHeight);

            charFrame.setVisible(false);
            gameFrame.setVisible(true);
            gameTimer.start();
            gamePanel.setFocusable(true); // gamePanel이 포커싱될 수 있게 함
            gamePanel.requestFocus();
            timeTimer.start();

            prepareAttackers();

            buttonToggler(SUSPEND + END);
         }
         // 여자 캐릭터를 골랐다면
         if (e.getSource() == girl) {
            player = new PosImageIcon(GIRL, 100, frameWidth, frameHeight);
            charFrame.setVisible(false);
            gameFrame.setVisible(true);
            gameTimer.start();
            gamePanel.setFocusable(true); // gamePanel이 포커싱될 수 있게 함
            gamePanel.requestFocus();
            timeTimer.start();

            prepareAttackers();

            buttonToggler(SUSPEND + END);
         }
      }
   }

   public class ClockListener implements ActionListener {
      int times = 0;

      public void actionPerformed(ActionEvent event) {
         times++;
         runTime.setText("도망시간  : " + times / 60 + "분 " + times % 60 + "초");

         // 시간이 일정시간 지나면 새로운 금을 출현시킴
         if (times % GOLD_INTERVAL == 0)
            goldList.add(new PosImageIcon(GOLD, ITEM_MARGIN, 0, moneyX, moneyY));

         // 시간이 일정시간 지나면 음료를 출현/소멸 시킴
         if (times % DRINK_INTERVAL == 0) {
        	 int x =(int)Math.random() * 550 + 1;
        	 int y =(int)Math.random() * 550 + 1;
        	 if (drinkList.isEmpty())// 현재 활동 중이 아니면 하나 추가
        		 
            	drinkList.add(getRandomAttacker(DRINK, BOY_MARGIN, policeStep,x,y));
            else // 현재 활동 중이면 리스트 비우기
               drinkList.remove(0);
         }

         // 시간이 일정시간 지나면 새로운 경찰을 출현시킴
       //만약 경찰 생성 위치가 플레이어 위치와 겹치면 다시 위치 생성 
         if (times % POLICE_INTERVAL == 0) {
        	 int x =(int)Math.random() * 550 + 1;
        	 int y =(int)Math.random() * 550 + 1;
        	 
        	 while(x == player.x && y == player.y)
        	 {   
        		 x =(int)Math.random() * 550 + 1;
        	     y =(int)Math.random() * 550 + 1;
        	 }
        	 
            policeList.add(getRandomAttacker(POLICE, POLICE_MARGIN, policeStep,x,y));
         }

        
      }
   }

   class Mouse implements MouseListener {
      @Override
      public void mouseClicked(MouseEvent e) {
      }

      @Override // 음료를 클릭하면 플레이어 속도가 +20 증가
      public void mousePressed(MouseEvent e) {
         Point s = e.getPoint();

         try {
            for (PosImageIcon m : drinkList) {
               if (m.collide(new Point(s.x, s.y))) {
                  drinkList.remove(0);
                  playerStep = playerStep + 20;
               }
            }
         } catch (Exception ex) {
         }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
      }

      @Override
      public void mouseEntered(MouseEvent e) {
      }

      @Override
      public void mouseExited(MouseEvent e) {
      }

   }

   // 키보드를 누르면 플레이어의 위치를 키보드로 움직일수 있게하는 클래스
   class DirectionListener implements KeyListener {
      public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_UP)
            if (player.y >= 0)
               player.y -= playerStep;

         if (e.getKeyCode() == KeyEvent.VK_DOWN)
            if (player.y <= frameHeight)
               player.y += playerStep;

         if (e.getKeyCode() == KeyEvent.VK_LEFT)
            if (player.x >= 0)
               player.x -= playerStep;

         if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            if (player.x <= frameWidth)
               player.x += playerStep;
      }

      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }
   }
}
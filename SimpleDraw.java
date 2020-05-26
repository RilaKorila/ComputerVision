import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class SimpleDraw extends JFrame implements MouseMotionListener, MouseListener, ActionListener{
	int lastx=0, lasty=0, newx = 0, newy = 0;
	DrawPanel panel;
  JPanel MenuPanel, BasePanel;
	DrawPanel PicPanel;
  JButton button;


	int zahyoX[] = {0, 0, 0};
	int zahyoY[] = {0, 0, 0};
	boolean polygonStart;

	public void mouseMoved(MouseEvent e) {
  }

	public void mouseDragged(MouseEvent e) {
	}

  public void mousePressed(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
		lastx = newx;
		lasty = newy;
    newx = e.getX();
	  newy = e.getY() - 20; //ズレの調整
    System.out.println("x: " + newx);
    System.out.println("y: " + newy);

		//座標の配列に値を格納
		if(zahyoX[0] == 0){
			zahyoX[0] = newx;
			zahyoY[0] = newy;
		}
		else if(zahyoX[0] == lastx){
			zahyoX[1] = newx;
			zahyoY[1] = newy;
		}
		else if(zahyoX[1] == lastx){
			zahyoX[2] = newx;
			zahyoY[2] = newy;
			polygonStart = true;
		}

  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();

    if(command == "average"){//averageボタンが押されたらポリゴン化
      System.out.println("average");

			//polygonStartのフラグを元にポリゴン化を実行
			if(polygonStart){//polygon2の処理を実行
				System.out.println("execute Polygon2");
				Polygon2.execute(zahyoX[0], zahyoY[0], zahyoX[1], zahyoY[1], zahyoX[2], zahyoY[2]);
				for(int zahyo: zahyoX) { zahyo = 0; }
				for(int zahyo: zahyoY) { zahyo = 0; }
				polygonStart = false;
			}
			else{//座標が３つ選べていなかったら
				System.out.println("you have to selest three points.");
				for(int zahyo: zahyoX) { zahyo = 0; }
				for(int zahyo: zahyoY) { zahyo = 0; }
			}
    }

    else if(command == "finish"){//finishボタンがおされたら画像を保存
      System.out.println("finish");
			PicPanel.saveFile(new File("picture.jpg"));
    }
  }

  private void addJButton(JPanel targetPanel, String itemName, String actionName, ActionListener listener) {
  		JButton button = new JButton(itemName);
  		button.setActionCommand(actionName);
  		button.addActionListener(listener);
      button.setMargin(new Insets(7, 3, 7, 3));//余白：上、左、下、右
      targetPanel.add(button);
  	}

	private void init() {
    this.setTitle("Polygon");
    this.setSize(800, 1000);
    this.addMouseMotionListener(this);
    this.addMouseListener(this);

    MenuPanel = new JPanel();
    PicPanel = new DrawPanel();
    BasePanel = new JPanel();


    MenuPanel.setLayout(new BoxLayout(MenuPanel, BoxLayout.Y_AXIS));
    this.addJButton(MenuPanel, "Average", "average", this);
    this.addJButton(MenuPanel, "Finish", "finish", this);
		//PicPanel
		PicPanel.openFile(new File("picture.jpg"));
		//BasePanel
    BasePanel.setLayout(new BorderLayout());
    BasePanel.add(MenuPanel, BorderLayout.EAST);
    BasePanel.add(PicPanel, BorderLayout.WEST);
		BasePanel.add(PicPanel);
		//

    this.getContentPane().add(BasePanel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		SimpleDraw frame = new SimpleDraw();
		frame.init();
	}

}

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class DrawPanel extends JPanel {
	BufferedImage bufferImage = null;
	Graphics2D bufferGraphics = null;

	public void drawLine(int x1, int y1, int x2, int y2){
		Graphics g = this.getGraphics();
		g.drawLine(x1, y1, x2, y2);
	}

	private void createBuffer(int width, int height) {
    //バッファ用のImageとGraphicsを用意する
		bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		bufferGraphics = bufferImage.createGraphics(); //getGraphicsと似ているが、戻り値がGraphics2D。
		bufferGraphics.setBackground(Color.WHITE);
	}

	//ファイルを開く
	public void openFile(File file2open){
		BufferedImage pictureImage;
		try {
			pictureImage = ImageIO.read(file2open);
		}
		catch(Exception e){
			System.out.println("Error: reading file = " + file2open.getName());
			return;
		}
		//画像に合わせたサイズでbufferImageとbufferGraphicsを作りなおして画像を読み込む
		//ImageIO.readの戻り値をbufferImageに代入するのでは駄目みたいです。
		this.createBuffer(pictureImage.getWidth(), pictureImage.getHeight());
		System.out.println("BufferHeight: " + pictureImage.getHeight());
		System.out.println("BufferWidth: " + pictureImage.getWidth());
		bufferGraphics.drawImage(pictureImage, 0, 0, this);

		repaint(); //画像を表示するためにpaintComponentを呼ぶ
	}

	//finishボタンが押されたら呼び出す
	public void saveFile(File file2save) {
		try {
			ImageIO.write(bufferImage, "jpg", file2save);
		}
		catch (Exception e) {
			System.out.println("Error: writing file = " + file2save.getName());
			return;
		}
	}

	public void paintComponent(Graphics g){
    super.paintComponent(g);//親を呼ぶ（他に描くものがある時用）
    if(bufferImage != null){
      g.drawImage(bufferImage, 0, 0, this);//バッファを表示
    }
  }
 }

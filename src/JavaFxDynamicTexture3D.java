import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.*;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 *  This program demonstrates how animated  bouncing balls drawn into a dynamically-generated texture
 * leave a shadowy trail behind as they move that resembles motion blur.
 */

public class JavaFxDynamicTexture3D extends Application {
  private Canvas            canvas;
  private GraphicsContext   gc;
  private Group             root = new Group();
  private XGroup            cube = new XGroup();
  private double            angleX, angleY;
  private int               ballX = 120, ballY = 70, deltaX = 7, deltaY = 10;
  private static final int  ballDiameter = 100;
  private PhongMaterial     material = new PhongMaterial();


  public static void main (String[] args) {
    launch(args);
  }

  public static class XGroup extends Group {
    Rotate rx = new Rotate(0, Rotate.X_AXIS);
    Rotate ry = new Rotate(0, Rotate.Y_AXIS);

    XGroup () {
      getTransforms().addAll(ry, rx);
    }

    void setRotateX (double x) {
      rx.setAngle(x);
    }

    void setRotateY (double y) {
      ry.setAngle(y);
    }
  }

  private void updateBall () {
    gc.setFill(new Color(0.4, 0.4, 0.4, .5));
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    // If ball reaches left or right edge, reverse direction
    if (ballX + deltaX < 0 || ballX + deltaX > canvas.getWidth() - ballDiameter) {
      deltaX = -deltaX;
    }
    // If ball reaches top or bottom edge, reverse direction
    if (ballY+ deltaY < 0 || ballY + deltaY > canvas.getHeight() - ballDiameter) {
      deltaY = -deltaY;
    }
    ballX += deltaX;
    ballY += deltaY;
    gc.setFill(Color.BLUE);
    gc.fillOval(ballX, ballY, ballDiameter, ballDiameter);
    WritableImage texture = canvas.snapshot(null, null);
    while (texture.getProgress() < 1) {
      try {
        // Note: never gets here, so the image is fully generated
        Thread.sleep(1);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    material.setDiffuseMap(texture);
  }

  // Build Camera
  private PerspectiveCamera getCamera () {
    PerspectiveCamera camera = new PerspectiveCamera(true);
    Group cameraXGroup = new Group();
    cameraXGroup.getChildren().add(camera);
    camera.setTranslateZ(-1000);
    camera.setNearClip(0.1);
    camera.setFarClip(10000.0);
    camera.setFieldOfView(40);
    return camera;
  }

  private SubScene get3dScene () {
    // Build updatable texture
    canvas = new Canvas(512, 512);
    gc = canvas.getGraphicsContext2D();
    material.setDiffuseColor(new Color(0.6, 0.6, 0.6, .6));
    updateBall();
    // Build foreground object using dynamic texture
    Box box = new Box(350, 350, 350);
    box.setMaterial(material);
    // Note: must add inner box first for transparency to work
    cube.getChildren().addAll(new Box(100, 100, 100), box);
    root.getChildren().add(cube);
    SubScene subScene = new SubScene(root, 512, 512, true, SceneAntialiasing.BALANCED);
    subScene.setFill(new Color(0.8, 0.8, 0.8, 1.0));
    subScene.setCamera(getCamera());
    return subScene;
  }

  @Override
  public void start (Stage stage) {
    stage.setTitle("JavaFxDynamicTexture3D.java");
    Scene scene = new Scene(new Group(get3dScene()));
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(e -> {
      Platform.exit();
      System.exit(0);
    });
    animate();
    System.out.println("javafx.runtime.version: " + System.getProperty("java.version"));
  }

  public void animate () {
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(30), ev -> {
      updateBall();
      cube.setRotateX(angleX += 1);
      cube.setRotateY(angleY += 1.3);
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }
}

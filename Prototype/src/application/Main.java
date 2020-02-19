package application;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	public int size = 40;	
	public int plotNumber= 10; //attention mettre un nombre pair
	public int screenSize = (plotNumber-1)*size+plotNumber;
	
	public double [][] framePos= new double [(plotNumber-1)*(plotNumber-1)][2];
	public double [][] wallPos = new double [((plotNumber)*(plotNumber))][2];
	public int wallMaxNumber=10;
	public int wallNumber= wallMaxNumber;
	public boolean wallState;
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root,screenSize+50, screenSize+50);
			
			int temp =0;
			int temp2 = 0;
			
			// section world 
			
			for (int i=1 ; i<plotNumber; i++) {
				
				for (int j=1; j<plotNumber; j++) {
					
					Rectangle r = new Rectangle(((i-1)*size)+i, ((j-1)*size)+j, size, size);
					
					framePos[temp][0]= r.getX()+size/2;
					framePos[temp][1]=r.getY()+size/2;
					temp++;
					if (i!=1 || j!=1) {
						
						wallPos[temp2][0]= r.getX()-1;
						wallPos[temp2][1]= r.getY()-1;
						temp2++;
					}
					root.getChildren().add(r);
				}
			}
			
			
			
			
			
			
			
			//section wall
			
			/*Note pour demain ou suuite : j'ai pennser a réarranger toute la section wall en unifiant timeline et la partie set on mause clicked car on peut faire time line 
			 * et wall.setonmousedragged et ajouter a la suite le root mouse clicked aissi que le root setpressedkey 
			 * 
			 *  2eme questionn ... ou mettre les condition ! car probleme dans selection des mur si mur est horizontale.
			 *  
			 *  
			 *  
			*/
			Button newWall = new Button ("new wall");
			newWall.setLayoutX(40); newWall.setLayoutY(screenSize + 3);
					
			newWall.setOnMouseClicked(e->{
				
				Rectangle wall = new Rectangle(screenSize/2,screenSize + 10, 10, size*2+1);
				wall.setFill(Color.ORANGERED);
				root.getChildren().addAll(wall);
				wallState = true;
				Timeline tempoLoop = new Timeline (new KeyFrame (Duration.millis(30), new EventHandler<ActionEvent>() {
					
					public void handle(ActionEvent arg0){
						
						if (wallState = true) {
							wall.setOnMouseDragged(f->{
								
								wall.setX(f.getSceneX()-5);
								wall.setY(f.getSceneY());
								
							});
						}
						
						if(wallState = false) {
							
							wall.setOnMouseDragged(f->{
								
								wall.setX(f.getSceneX()+(size*2+1)/2-5);
								wall.setY(f.getSceneY()-(size*2+1)/2);
								
							});
							
						}
						
						
					}
				}));
				tempoLoop.play();
				
				
				
				wall.setOnMouseClicked(g->{
					
					root.setOnMouseClicked(h->{
						
						double x = h.getX();
						double y = h.getY();
						//System.out.println(x +" "+y);
						int j=0;
						for (int i=0; i<framePos.length; i++) {
							
							if (x<wallPos[i][0]+size/2 && x>wallPos[i][0]-size/2 && y<wallPos[j][1]+size/2 && y>wallPos[j][1]-size/2) {
								
								
								wall.setX(wallPos[i][0]-5); //verifer lorsque mise sur le pixel du mur 
								wall.setY(wallPos[j][1]);
								
								
							}
							
							
							
							j++;
							}
							
						});
					root.setOnKeyPressed(new EventHandler<KeyEvent>() {
					    public void handle(KeyEvent t) {
					        if (t.getCode() == KeyCode.SHIFT) {
					        	wall.setRotate(wall.getRotate() + 90); 
					            wallState=!wallState;
					        }
					    }
					    
					});
					
					});
				
			});
			
			root.getChildren().addAll(newWall);
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			//section player
			
			Circle player1 = new Circle(screenSize/2,screenSize-size/2-1,size/4);
			player1.setFill(Color.YELLOW);
			root.getChildren().addAll(player1);

			
			player1.setOnMouseClicked(e->{
							
							root.setOnMouseClicked(f->{
								
								double x = f.getX();
								double y = f.getY();
								//System.out.println(x +" "+y);
								int j=0;
								for (int i=0; i<framePos.length; i++) {
									
									if (x<framePos[i][0]+size/2 && x>framePos[i][0]-size/2 && y<framePos[j][1]+size/2 && y>framePos[j][1]-size/2) {
										
										
										player1.setCenterX(framePos[i][0]); //verifer lorsque mise sur le pixel du mur 
										player1.setCenterY(framePos[j][1]);
										
										
									}
									
									
									
									j++;
									}
									
								});
							});
			
			Timeline loop = new Timeline (new KeyFrame (Duration.millis(30), new EventHandler<ActionEvent>() {
							
							public void handle(ActionEvent arg0){
							
							player1.setOnMouseDragged(e->{
								
								player1.setCenterX(e.getSceneX());
								player1.setCenterY(e.getSceneY());
								
								
								
							});
							
								
						 }
						}));
			loop.play();			
			
			TextField setpos = new TextField();
			setpos.setLayoutX(screenSize+3); setpos.setLayoutY(10);
			setpos.resize(10, 10);
			
			
			Button validatePos = new Button("Enter -,\n       <-'");
			validatePos.setLayoutX(screenSize + 3); validatePos.setLayoutY(40);
			
			validatePos.setOnMouseClicked(k->{
				
				System.out.println(setpos.getText());
				
				setpos.clear();
			});
			
			
			
			
			
			
			
			
			
			
			root.getChildren().addAll(setpos, validatePos);
			
			scene.setFill(Color.BLUE);
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//root.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.show();
			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
		
		
	}
		



}

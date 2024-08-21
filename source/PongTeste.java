import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class PongTeste extends PApplet {

//Aqui fazemos referência as classes criadas
Ball ball, ball2;

Paddle paddleLeft;
Paddle paddleRight;

Retangle retangle;

float x  = -5*PI; 
float y  = 0; 
int cont = 0;
int temp = 0;

//Criação dos pontos;
int scoreLeft = 0;
int scoreRight = 0;

//Setamos a interface
public void setup(){
  
  //criação da bola
  ball = new Ball(width/2,height/2, 45);
  ball.sX = 5;
  ball.sY = random(-3,3);
  
  //criação do retângulo que passara no meio da tela;
  retangle = new Retangle(60, height/2,30,200);

  
  //criação da 'raquetes'
  paddleLeft = new Paddle(15,height/2, 30,200);
  paddleRight = new Paddle( width-15, height/2,30,200);
  
}

//Desenhamos as movimentações dos objetos
public void draw(){
  background(0);
  //Criação da bola
  ball.display();
  //Faz a bola mover
  ball.move();
  ball.display();
  
  //Faz o retângulo esquerdo mover
  paddleLeft.move();
  //Criação do retângulo esquerdo
  paddleLeft.display();
  //Faz o retângulo direito mover
  paddleRight.move();
  //Criação do retângulo direito
  paddleRight.display();
  
  retangle.display();
  retangle.move();
  
  //Checa se a bola colide com as bordas, invertendo sua velocidade
  if(ball.right() > width){
    scoreLeft = scoreLeft + 1;
    ball.x = width/2;
    ball.y = height/2;
  }
  
  if(ball.left() < 0){
    scoreLeft = scoreLeft + 1;
    ball.x = width/2;
    ball.y = height/2;
  }
  
  if(ball.bottom() > height){
    ball.sY = -ball.sY;
  }
  
  if(ball.top() < 0){
    ball.sY = -ball.sY;
  }
  
  if(paddleLeft.bottom()> height){
  paddleLeft.y = height-paddleLeft.h/2;
  }
  
  if (paddleLeft.top()< 0){
  paddleLeft.y = paddleLeft.h/2;
  }
  
  if(paddleRight.bottom()>height){
  paddleRight.y = height-paddleLeft.h/2;
  }
  
  if(paddleRight.top()<0){
  paddleRight.y = paddleRight.h/2;
  }
  
  //rebate a bolinha ao colidir com as raquetes
  if ( ball.left() < paddleLeft.right() && ball.y > paddleLeft.top() && ball.y < paddleLeft.bottom()){
    ball.sX = -ball.sX;
  }

  if ( ball.right() > paddleRight.left() && ball.y > paddleRight.top() && ball.y < paddleRight.bottom()) {
    ball.sX = -ball.sX;
  }
  
  //rebate a bolinha ao colidir com o retângulo no meio  
    if ( ball.left() < retangle.right() && ball.y > retangle.top() && ball.y < retangle.bottom()){
    ball.sX = -ball.sX;
  }

  if ( ball.right() > retangle.left() && ball.y > retangle.top() && ball.y < retangle.bottom()) {
    ball.sX = -ball.sX;
  }
  
  
  winner();
  
  textSize(40);
  textAlign(CENTER);
  text(scoreRight, width/2+30, 30); // Right side score
  text(scoreLeft, width/2-30, 30); // Left side score
  
}

//Aqui confere se o botão está sendo pressionado
public void keyPressed(){
  if(keyCode == UP){
    paddleRight.sYP =-6;
  }
  
  if(keyCode == DOWN){
    paddleRight.sYP = 6;
  }
  
  if(key == 'w'){
    paddleLeft.sYP = -6;
  }
  
  if(key == 's'){
    paddleLeft.sYP = 6;
  }
}

//Aqui confere se o botão foi solto
public void keyReleased(){
  if(keyCode == UP){
    paddleRight.sYP = 0;
  }
  
  if(keyCode == DOWN){
    paddleRight.sYP = 0;
  }
  
  if(key == 'w'){
    paddleLeft.sYP = 0;
  }
  
  if(key == 's'){
    paddleLeft.sYP = 0;
  }
}

//Determina o vencerdor;
public void winner(){
  if (scoreLeft >= 10){
     fill(250,232,33);
     scoreLeft = 10;
     text("O jogador da esquerda venceu!", width/2,height/2);
     if (mousePressed == true){
     scoreLeft = 0;
     scoreRight = 0;}  
} 
  if (scoreRight >= 10){
    fill(250,232,33);
    scoreRight = 10;
    text("O jogador da direita venceu!\n Clique com o mouse para\n inciar novamente", width/2,height/2);
    if (mousePressed == true){
    scoreLeft = 0;
    scoreRight = 0;}
  }
}
class Ball{
float x,y,sX,sY,d;
int c;

//Construtor
Ball(float tempX, float tempY, float tempDiameter){
  x = tempX;
  y = tempY;
  d = tempDiameter;
  sX = 0;
  sY = 0;
  c = (255);
  }

public void move(){
  y = y + sY;
  x = x + sX;
  
  }

public void display(){
  fill(c);
  ellipse(x,y,d,d);
  }

public float left(){
  return x-d/2;
  }
public float right(){
  return x+d/2;
  }
public float top(){
  return y-d/2;
  }
public float bottom(){
  return y+d/2;
  }
}
class Paddle{

  float x;
  float y;
  float w;
  float h;
  float sYP;
  float sXP;
  int c;
  
  Paddle(float tempX, float tempY, float tempW, float tempH){
    x = tempX;
    y = tempY;
    w = tempW;
    h = tempH;
    sYP = 0;
    sXP = 0;
    c=(255);
  }

  public void move(){
    y += sYP;
    x += sXP;
  }

  public void display(){
    fill(c);
    rect(x-w/2,y-h/2,w,h);
  } 
  
  //helper functions
  public float left(){
    return x-w/2;
  }
  public float right(){
    return x+w/2;
  }
  public float top(){
    return y-h/2;
  }
  public float bottom(){
    return y+h/2;
  }
}
class Retangle{
  
float X,Y,x,y,Iw,Ih, w,h;
int c;

//Construtor
Retangle(float X, float Y, float Iw, float Ih){
  x = X;
  y = Y;
  w = Iw;
  h = Ih;
  c = (255);
}

public void move(){  
  x = x + PI/180;
  y = 10*sin(x);
  noStroke();
}
  
public void display(){
  fill(c);
  rect(330,y*90, w, h);
 
  if(cont>400) {
    x=-10*PI; y=0; cont=0;
} 
  cont++;
}

  public float left(){
    return x-w/2;
  }
  public float right(){
    return x+w/2;
  }
  public float top(){
    return y-h/2;
  }
  public float bottom(){
    return y+h/2;
  }

}
  public void settings() {  size(700, 700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PongTeste" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

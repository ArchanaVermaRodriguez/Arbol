package sample;

import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ui.NodoUI;

import java.util.List;

public class AVLver<T extends Comparable<T>> /*extends Pane*/ {

    private AVL<T> avl;
    private NodoUI<T> nodoUI;
    private Pane gp;
    private int currentIndex=10;
    private AVL<T> avlTemp=avl;
    private int currentIndex1=10;

    public AVLver(AVL<T> avl, Pane gp) {
        this.avl = avl;
        this.gp = gp;
    }


    public void agregarNodoUI(NodoUI<T> nUI) {
        gp.getChildren().add(nUI);
        animar(nUI);

    }

    public void animar(NodoUI<T> nUI) {
        avlTemp=avl;
       // currentIndex=currentIndex1;

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), nUI);
        System.out.println("seraaa"+currentIndex);
        transition.setByY(20);
        nUI.setCenterY(nUI.centerY+20);

        System.out.println("Pos y inicial mov 1/2: "+nUI.getCenterY());
        transition.setCycleCount(1);
        transition.play();
        currentIndex=0;

        transition.setOnFinished(new MyEventHandler(currentIndex, nUI,avlTemp));

    }

    private void animarRecY(NodoUI nUI) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), nUI);
        transition.setByY(60);
        nUI.setCenterY(nUI.getCenterY()+60);
        System.out.println("Center y en recusividad: "+nUI.getCenterY());
        transition.setCycleCount(1);
        transition.play();
        transition.setOnFinished(new MyEventHandler(currentIndex, nUI,avlTemp));

    }
     class MyEventHandler implements EventHandler<ActionEvent> {

        int pos;
        NodoUI<T> nUI;
        AVL<T> avlTemp;

        public MyEventHandler(int pos,NodoUI<T> nUI,AVL<T> avlTemp) {
            this.pos = pos;
            this.nUI=nUI;
            this.avlTemp=avlTemp;
        }

        @Override
        public void handle(ActionEvent event) {
            TranslateTransition transition= new TranslateTransition(Duration.seconds(1),nUI);
            //se puso 0 para que llegue exacto a la posición raíz y no quede chueco
            transition.setByY(0);
            nUI.setCenterY(nUI.getCenterY()+30);
            System.out.println("Pos index "+pos);
            System.out.println("Pos y inicial 1: "+nUI.getCenterY());
            transition.setCycleCount(1);
            transition.play();
            currentIndex++;
            System.out.println("Prorundidad del nodo: "+avlTemp.profundidad(nUI.getNodo().getElemento()));

            if(currentIndex<=avlTemp.profundidad(nUI.getNodo().getElemento())){
                System.out.println("Index cuando entra pero todavia no al recurviso:"+currentIndex);

                animarRecX(nUI,avlTemp);
                animarRecY(nUI);
                animarRotaciones(nUI,avlTemp);

            }
        }
    }


    private void animarRecX(NodoUI<T> nUI, AVL<T> avlTemp){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), nUI);
        if(nUI.getNodo().getElemento().compareTo(avlTemp.getRaiz().getElemento())<0){
            transition.setByX(-30);
            nUI.setCenterX(nUI.getCenterX()-30);
            System.out.println("Center x en recusividad: "+nUI.getCenterY());
        }
        else{
            transition.setByX(+30);
            nUI.setCenterX(nUI.getCenterX()+30);
            System.out.println("Center x en recusividad: "+nUI.getCenterY());
            transition.setCycleCount(1);
        }

        transition.setCycleCount(1);
        transition.play();
        transition.setOnFinished(new MyEventHandler(currentIndex, nUI,avlTemp));
    }

    //recibe nodoUI sin rotar
    private void animarRotaciones(NodoUI<T> nUI, AVL<T> avlTemp2){
        int fE=avlTemp2.factorEquilibrio(nUI.getNodo());
        //entonces el valor es menor que la raíz
        if(fE==-2){

            TranslateTransition transition= new TranslateTransition(Duration.seconds(1),nUI);
            transition.setByX(-30);
            nUI.setCenterX(nUI.getCenterX()-30);
            transition.setByY(-60);
            nUI.setCenterY(nUI.getCenterY()-30);



        }
        avlTemp2.rotaciones(nUI.getNodo());
        animarRecY(nUI);
        animarRecX(nUI,avlTemp2);

    }



}

package sample;

public class AVL <T extends Comparable<T>>{
    private Nodo<T> raiz;

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo<T> raiz) {
        this.raiz = raiz;
    }

    private int altura(Nodo<T> nodo){
        if(nodo==null)
            return -1;
        return nodo.getAltura();
    }

    private int max(int a, int b){
        if(a>b)
            return a;
        return b;
    }

    public void altura(T elemento){
        boolean existe=buscarNodo(elemento);
        if(!existe)
            System.out.println("El elemento "+elemento+ " del cual quiere la altura no existe en el árbol");
        else{
            Nodo<T> temp=raiz;
            while(elemento.compareTo(temp.getElemento())!=0){
                if(elemento.compareTo(temp.getElemento())<0){
                    temp=temp.getIzquierdo();
                }else{
                    temp=temp.getDerecho();
                }
            }
            int altura=altura(temp);
            System.out.println("El elemento "+elemento+ " tiene una altura de: "+altura);

        }
    }


    public double profundidad(T elemento){

        boolean existe=buscarNodo(elemento);
        double res;
        if(!existe){
            System.out.println("El elemento "+elemento+ " del cual quiere la profundidad no existe en el árbol");
        res=-1;}
       else{

             res= calcularProfundidadRec(raiz,elemento,0);
            System.out.println("El elemento "+elemento+ " tiene una profundidad de "+res);

        }
        return res;
    }

    private int calcularProfundidadRec(Nodo<T> raiz,T buscando,int profundidad){
        if(raiz==null){
            return 0;
        }
        if(buscando.compareTo(raiz.getElemento())==0){
            return profundidad;
        }
        if(buscando.compareTo(raiz.getElemento())>0){
            return calcularProfundidadRec(raiz.getDerecho(),buscando,++profundidad);
        }else{
            return calcularProfundidadRec(raiz.getIzquierdo(),buscando,++profundidad);
        }
    }



    private Nodo<T> rotarSimpleALaDerecha(Nodo<T> raiz){
        System.out.println("Rotando simple a la derecha");
        Nodo<T> temp=raiz.getIzquierdo();
        raiz.setIzquierdo(temp.getDerecho());
        temp.setDerecho(raiz);
        raiz.setAltura(max(altura(raiz.getIzquierdo()),altura(raiz.getDerecho()))+1);
        temp.setAltura(max(altura(temp.getIzquierdo()), raiz.getAltura())+1);
        return temp;
    }

    private Nodo<T> rotarSimpleALaIzquierda(Nodo<T> raiz){
        System.out.println("Rotando simple a la izquierda");
        Nodo<T> temp= raiz.getDerecho();
        raiz.setDerecho(temp.getIzquierdo());
        temp.setIzquierdo(raiz);
        raiz.setAltura(max(altura(raiz.getIzquierdo()),altura(raiz.getDerecho() ))+1);
        temp.setAltura(max(raiz.getAltura(),altura(temp.getDerecho()))+1);
        return temp;
    }

    private Nodo<T> rotarDobleALaDerecha(Nodo<T> raiz){
        System.out.println("Rotando doble a la derecha");
        raiz.setIzquierdo(rotarSimpleALaIzquierda(raiz.getIzquierdo()));
        return rotarSimpleALaDerecha(raiz);

    }

    private Nodo<T> rotarDobleALaIzquierda(Nodo<T> raiz){
        System.out.println("Rotando doble a la izquierda");
        raiz.setDerecho(rotarSimpleALaDerecha(raiz.getDerecho()));
        return rotarSimpleALaIzquierda(raiz);
    }

    private Nodo<T> insertarRecursivo(Nodo<T> raiz, T elemento){
        if(raiz==null)
            raiz= new Nodo(elemento);
        else if(elemento.compareTo(raiz.getElemento())<0){
            raiz.setIzquierdo(insertarRecursivo(raiz.getIzquierdo(),elemento));

        }
        else if(elemento.compareTo(raiz.getElemento())>0){
            raiz.setDerecho(insertarRecursivo(raiz.getDerecho(),elemento));

        }
        else {
            return raiz;//no entran duplicados
        }
//
        raiz.setAltura(max(altura(raiz.getIzquierdo()),altura(raiz.getDerecho()))+1);
        int facEquilibrio=factorEquilibrio(raiz);

        if(facEquilibrio==2){
            if(elemento.compareTo(raiz.getIzquierdo().getElemento())<0) {
                raiz = rotarSimpleALaDerecha(raiz);
            }else {
                raiz = rotarDobleALaDerecha(raiz);
            }
        }

        if(facEquilibrio==-2){
            if(elemento.compareTo(raiz.getDerecho().getElemento())>0){
                raiz= rotarSimpleALaIzquierda(raiz);}
            else {
                raiz = rotarDobleALaIzquierda(raiz);
            }
        }//

        return raiz;
    }

    public Nodo<T> rotaciones(Nodo<T> nodo){
        raiz.setAltura(max(altura(raiz.getIzquierdo()),altura(raiz.getDerecho()))+1);
        int facEquilibrio=factorEquilibrio(raiz);

        if(facEquilibrio==2){
            if(nodo.getElemento().compareTo(raiz.getIzquierdo().getElemento())<0) {
                raiz = rotarSimpleALaDerecha(raiz);
            }else {
                raiz = rotarDobleALaDerecha(raiz);
            }
        }

        if(facEquilibrio==-2){
            if(nodo.getElemento().compareTo(raiz.getDerecho().getElemento())>0){
                raiz= rotarSimpleALaIzquierda(raiz);}
            else {
                raiz = rotarDobleALaIzquierda(raiz);
            }
        }

        return nodo;
    }

    public int factorEquilibrio(Nodo<T> nodo){
        if(nodo==null)
            return 0;
        return altura(nodo.getIzquierdo())-altura(nodo.getDerecho());
    }

    public void insertar( T elemento){
        raiz=insertarRecursivo(raiz,elemento);
    }

    private Nodo<T> minNodo(Nodo<T> nodo){
        Nodo<T> temp=nodo;
        while(temp.getIzquierdo()!=null){
            temp=temp.getIzquierdo();
        }
        return temp;

    }
    public void borrar(T elemento){
        boolean existe=buscarNodo(elemento);
        if(!existe)
            System.out.println("El elemento "+elemento+" que quiere borrar no existe en el árbol");
        raiz=borrarRecursivo(raiz,elemento);
    }

    private Nodo<T> borrarRecursivo(Nodo<T> raiz, T elemento){
        if(raiz==null)
            return raiz;
        if(elemento.compareTo(raiz.getElemento())<0){
            raiz.setIzquierdo(borrarRecursivo(raiz.getIzquierdo(),elemento));

        }
        else if(elemento.compareTo(raiz.getElemento())>0) {
            raiz.setDerecho(borrarRecursivo(raiz.getDerecho(), elemento));
        }
        //Ya lo encontró
        else{
            Nodo<T> temp=null;
            //caso hoja
            if(raiz.getIzquierdo()==null && raiz.getDerecho()==null){
                temp=raiz;
                raiz=null;

                //hijo en derecha
            }else if(raiz.getIzquierdo()==null){
                temp=raiz.getDerecho();
                raiz=temp;
            }

            //hijo en izquierdo
            else if(raiz.getDerecho()==null){
                temp=raiz.getIzquierdo();
                raiz=temp;
            }
            else{
                //tiene hijos de los dos lados

                //busca el hijo más chico del lado derecho
                temp = minNodo(raiz.getDerecho());

                raiz.setElemento(temp.getElemento());

                raiz.setDerecho(borrarRecursivo(raiz.getDerecho(),temp.getElemento()));
            }

            if(raiz==null)
                return raiz;

            raiz.setAltura(max(altura(raiz.getIzquierdo()),altura(raiz.getDerecho()))+1);
            int facEquilibrio=factorEquilibrio(raiz);

            if(facEquilibrio==2){
                if(elemento.compareTo(raiz.getIzquierdo().getElemento())<0) {
                    raiz = rotarSimpleALaDerecha(raiz);
                }else {
                    raiz = rotarDobleALaDerecha(raiz);
                }
            }
            if(facEquilibrio==-2) {
                if (elemento.compareTo(raiz.getDerecho().getElemento()) > 0) {
                    raiz = rotarSimpleALaIzquierda(raiz);
                } else {
                    raiz = rotarDobleALaIzquierda(raiz);
                }
            }
        }
        return raiz;
    }

    private boolean buscarNodo(T elemento){
        Nodo<T> temp=raiz;
        while(elemento.compareTo(temp.getElemento())!=0){
            if(elemento.compareTo(temp.getElemento())<0){
                temp=temp.getIzquierdo();
            }else{
                temp=temp.getDerecho();
            }
            if(temp==null)
                return false;
        }
        return true;
    }


}
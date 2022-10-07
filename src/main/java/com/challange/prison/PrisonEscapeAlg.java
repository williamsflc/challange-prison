package com.challange.prison;

import com.challange.prison.object.Position;
import com.challange.prison.object.PrisonAlgorithmException;
import com.challange.prison.object.TreeRouteNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Esta clase contiene el algoritmo necesario para analizar si un prisionero puede
 * escapar o no de una carcel y la ruta de secape mas corta
 * 
 * 2022-10-01
 * @author Williams Lopez
 * 
 * 
 */
public class PrisonEscapeAlg {
    
    public final static char PRISIONER = 'P';
    public final static char WALL      = '|';
    public final static char EXIT      = 'S';
    public final static char ROUTE     = '_';
    public final static char OUTSIDE   = 'O';
    
    public final static char GUARD_UP   = '^';
    public final static char GUARD_DOWN = 'V';
    public final static char GUARD_LEFT = '<';
    public final static char GUARD_RIGHT= '>';
    
    public final static char UP    = 'U';
    public final static char DOWN  = 'D';
    public final static char LEFT  = 'L';
    public final static char RIGHT = 'R';
    
    private char[][] prision;
    private int prisionerResist;
    private TreeRouteNode treeRoute;

    public PrisonEscapeAlg(String definition, int prisonerResist) {
        try {
            this.prision = readPrisionDefinition(definition);
            this.prisionerResist = prisonerResist;
            this.treeRoute = createRoutesTree(prision, prisionerResist);
        } catch (PrisonAlgorithmException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    
    
    /**
     * El metodo indica si el prisioneero puede escapar de acuerdo a la definición
     * de la prision provista

     * @return
     * @throws PrisonAlgorithmException 
     */
    public boolean canEscape() throws PrisonAlgorithmException{
        List<TreeRouteNode> escapeRoutes = treeRoute.findEscapeRoutes();
        return escapeRoutes.stream().anyMatch(t -> (t.getRouteDistance() <= prisionerResist));
    }
    
    /**
     * Obtiene la ruta mas corta de escape de la prision definida
     * de acuerdo a la posición del prisionero
     * no se considera la resistencia del prisionero
     * @return 
     */
    public String[] shorterEscapeRoute(){
        
        TreeRouteNode shorter = treeRoute.findShorterEscapeRoute();
        
        if(shorter != null){
            String[] route = new String[shorter.getRouteDistance()+1];
            TreeRouteNode p = shorter;
            for(int i= shorter.getRouteDistance(); i>=0 ; i--){
                if(p==null) break;
                route[i] = p.getPosition().toString();
                p = p.getParent();
            }
            return route;
        }
        
        return null;
    }
    
    /**
     * Obtiene la posicion del prisionero, valida que la posición sea correcta
     * @param p
     * @param prision
     * @return 
     */
    private Position findPrisonerPosition(char[][] prision) throws PrisonAlgorithmException{
        Position p = find('P', prision);
        if(p==null){
            throw new PrisonAlgorithmException("No se encontro prisionero en la definicion de esta carcel");
        }
        
        if(getNextPositionValue(UP, p, prision)==EXIT||
                getNextPositionValue(DOWN, p, prision)==EXIT ||
                getNextPositionValue(LEFT, p, prision)==EXIT ||
                getNextPositionValue(RIGHT, p, prision)==EXIT){
            throw new PrisonAlgorithmException("El prisionero no puede estar ubicado al lado de una salida, posicion actual "+p);
        }
        
        return p;
    }
    
    /**
     * Obtiene el siguiente valor dependiendo de la dirección
     * @param direction UP,DOWN,LEFT, RIGHT
     * @param p
     * @param prision 
     * @return Posición siguiente o null si ya se llegó al limite
     */
    private Position getNextPosition(char direction, Position p,char[][] prision){
        
        int x=p.getX(),y=p.getY();
        int h = prision.length;
        int w = prision[0].length;
        
        switch(direction){
            case UP:   y=(p.getY() - 1 <  0) ? -1 : p.getY()-1; break;
            case DOWN: y=(p.getY() + 1 >= h) ? -1 : p.getY()+1; break;        
            case LEFT: x=(p.getX() - 1 <  0) ? -1 : p.getX()-1; break;
            case RIGHT:x=(p.getX() + 1 >= w) ? -1 : p.getX()+1; break;        
        }
      
        if(x == -1 || y == -1){
            return null;
        }
        
        return new Position(x, y);
      
    }
    
    /**
     * Obtiene el valor de una posicion en la matriz de la prision
     * @param p
     * @param prision
     * @return 
     */
    private char getPositionValue(Position p, char[][] prision){
        
        if(p.getY() >= prision.length || p.getX() >= prision[0].length||
                p.getX() < 0 || p.getY() < 0){
            return OUTSIDE;
        }
        
        return prision[p.getY()][p.getX()];
    }
    
    /**
     * Obtiene el valor de la siguiente posición dependiendo de la direccion
     * @param direction
     * @param p
     * @param prision
     * @return 
     */
    private char getNextPositionValue(char direction, Position p,char[][] prision){
        Position np = getNextPosition(direction, p, prision);
        if(np==null){
            return OUTSIDE;
        }
        return prision[np.getY()][np.getX()];
    }
    
    /**
     * Encuentra la posición de un caracter en la matriz
     * @param c
     * @return 
     */
    private Position find(char c,char[][] prision){
        
        for(int y=0;y<prision.length;y++){
            for(int x = 0;x<prision.length;x++){
                if(prision[y][x] == c){
                    return new Position(x, y);
                }
            }
        }
        return null;
    }
    
    
    
    
    
    /**
     * Crea un arbol de rutas de salida
     * @param p
     * @param prision
     * @param presistance
     * @return 
     */
    private TreeRouteNode createRoutesTree(char[][] prision, int presistance) throws PrisonAlgorithmException{
        
        Position p = findPrisonerPosition(prision);
        
        if(p == null){
            throw new PrisonAlgorithmException("No se encontro prisionero en esta definicion");
        }
        
        TreeRouteNode n = new TreeRouteNode(null,p,0);
        n.setType(PRISIONER);
        n.setExit(false);
        n.setLeaf(false);
        createRoutesTree(prision, n, presistance);
        return n;
        
    }
        

    /**
     * Metodo recursivo para crear el arbol de rutas
     * Se crean todas las rutas posibles incluso las que no tienen salida
     * para utilizarlo como ayuda en UI
     * @param prision
     * @param n
     * @param presistance
     * @throws PrisonAlgorithmException 
     */
    private void createRoutesTree(char[][] prision, TreeRouteNode n, int presistance) throws PrisonAlgorithmException{
        
        if(n == null){
            return;
        }
        
        switch(n.getType()){
            case WALL: 
                n.setLeaf(true);
                n.setExit(false);
                n.setComment("No pasa, encontró una pared");
                return;
            case GUARD_DOWN:
            case GUARD_UP:
            case GUARD_LEFT:
            case GUARD_RIGHT:
                n.setLeaf(true);
                n.setExit(false);
                n.setComment("No pasa, encontró un guardia");
                return;
            case ROUTE:
                n.setLeaf(false);
                n.setExit(false);
                break;
            case EXIT:
                n.setLeaf(true);
                n.setExit(true);
                break;
            case PRISIONER:
                break;
        }
        
        if(isGuardPointing(n.getPosition(), prision)){
            n.setLeaf(true);
            n.setComment("No pasa debido a que hay un guardia en su dirección");
            return;
        }
        
        if(n.isExit()){
            n.setLeaf(true);
            if(n.getRouteDistance()<=presistance){
                n.setComment("Salida exitosa");
            }else{
                n.setComment("La distancia "+n.getRouteDistance()+" es mayor a la resistencia "+presistance+" del prisionero");
            }
            return;
        }

        //Busca en las 4 posiciones
        HashMap<Character,Position> alternatives = new HashMap<>();
        alternatives.put(UP, getNextPosition(UP, n.getPosition(), prision));
        alternatives.put(DOWN, getNextPosition(DOWN, n.getPosition(), prision));
        alternatives.put(LEFT, getNextPosition(LEFT, n.getPosition(), prision));
        alternatives.put(RIGHT, getNextPosition(RIGHT, n.getPosition(), prision));
        
        alternatives.forEach((dir,pos)->{
            if(pos != null){ 
                try{
                    
                    //excluye el nodo de regreso
                    if(n.getParent()!=null && n.getParent().getPosition().equals(pos)){
                        return;
                    }
                    
                    char type = getPositionValue(pos, prision);
                    TreeRouteNode nch = new TreeRouteNode(n, pos, n.getRouteDistance() + 1);
                    nch.setType(type);
                    n.addChild(nch);
                    createRoutesTree(prision, nch, presistance);
                } catch (PrisonAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        
    }
    


    /**
     * Verifica si hay algun guardia apuntando a la posición actual del prisionero
     * el cual se ncuentra en la posicion p
     * @param p  posicion del prisionero
     * @param prision definicion de la prision
     * @return 
     */
    private boolean isGuardPointing(Position p, char[][] prision){

        Position n;
        char c;
        
        //busca a la izquierda
        n=p;
        while ((n=getNextPosition(LEFT, n, prision)) != null) {
            c = getPositionValue(n, prision);
            if(c == GUARD_RIGHT){return true;}
            if(c != ROUTE){break;}
        }
        
        //busca a la derecha
        n=p;
        while ((n=getNextPosition(RIGHT, n, prision)) != null) {
            c = getPositionValue(n, prision);
            if(c == GUARD_LEFT){return true;}
            if(c != ROUTE){break;}
        }
        
        //busca arriba
        n=p;
        while ((n=getNextPosition(UP, n, prision)) != null) {
            c = getPositionValue(n, prision);
            if(c == GUARD_DOWN){return true;}
            if(c != ROUTE){break;}
        }
        
        //busca abajo
        n=p;
        while ((n=getNextPosition(DOWN, n, prision)) != null) {
            c = getPositionValue(n, prision);
            if(c == GUARD_UP){return true;}
            if(c != ROUTE){break;}
        }
        
        return false;
    }    
    
    
    /**
     * Lee una definición de presión desde un string
     * @param str
     * @return 
     * @throws com.challange.prison.object.PrisonAlgorithmException 
     */
    public static char[][] readPrisionDefinition(String str) throws PrisonAlgorithmException{
        
        str = str.toUpperCase();
        String validCharacters = " \r\n\t"+PRISIONER+WALL+EXIT+GUARD_DOWN+GUARD_LEFT+GUARD_RIGHT+GUARD_UP+ROUTE;
        List<List<Character>> mtrx = new ArrayList<>();
        
        List<Character> row = new ArrayList<>();
        
        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            if(validCharacters.indexOf(c)<0){
                throw new PrisonAlgorithmException("Se encontro un caracter inválido en la definicion: "+c);
            }
            
            if(c == ' ' || c=='\t' || c=='\r'){
                continue;
            }
            
            if(c == '\n'){
                if(!row.isEmpty()){
                    mtrx.add(row);
                }
                row = new ArrayList<>();
                continue;
            }
            row.add(c);
        }
        
        if(!row.isEmpty()){
            mtrx.add(row);
        }
        
        if(mtrx.isEmpty() || mtrx.get(0).isEmpty()){
            throw new PrisonAlgorithmException("Definicion de matriz incorrecta");
        }
        
        char[][] arrdef = new char[mtrx.size()][mtrx.get(0).size()];
        
        for(int y=0;y<arrdef.length;y++){
            for(int x=0;x<arrdef[0].length;x++){
                arrdef[y][x] = mtrx.get(y).get(x);
            }
        }
        
        return arrdef;
    }
    
    /**
     * Get prison matrix
     * @return 
     */
    public char[][] getPrisonMatrix(){
        return prision;
    }
    
    
    /**
     * Obtiene el arbol de rutas
     * @return 
     */
    public TreeRouteNode getTreeRouteNode(){
        return this.treeRoute;
    }
    
    
    
}



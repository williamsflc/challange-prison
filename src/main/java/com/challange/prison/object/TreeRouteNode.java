/*
 * Copyright (C) 2022 Williams Lopez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.challange.prison.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa un arbol de rutas de salida de prision
 * Estructura de datos de arbol.
 * 
 * @author Williams Lopez
 */
public class TreeRouteNode {
    
    private TreeRouteNode parent;
    private List<TreeRouteNode> childs;
    private Position position;
    private int routeDistance;
    private boolean exit;
    private boolean leaf;
    private String comment;
    private char type;

    /**
     * Cra un uevo nodo
     * @param parent  padre del nodo
     * @param position posicion del nodo
     * @param routeDistance distancia hasta este nod
     */
    public TreeRouteNode(TreeRouteNode parent, Position position, int routeDistance) {
        this.parent = parent;
        this.position = position;
        this.routeDistance = routeDistance;
    }
    
    

    /**
     * Obtiene el padre del nodo
     * Null si es el nodo raíz (root)
     * @return 
     */
    public TreeRouteNode getParent() {
        return parent;
    }

    public void setParent(TreeRouteNode parent) {
        this.parent = parent;
    }

    /**
     * Obtiene los hijos del nodo
     * Null si es el útlimo nodo (hoja)
     * @return 
     */
    public List<TreeRouteNode> getChilds() {
        return childs;
    }

    public void setChilds(List<TreeRouteNode> childs) {
        this.childs = childs;
    }
    
    /**
     * Posicion del nodo en la matriz de la carcel
     * @return 
     */
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    

    /**
     * Distancia hasta este nodo del prisionero
     * @return 
     */
    public int getRouteDistance() {
        return routeDistance;
    }

    public void setRouteDistance(int routeSize) {
        this.routeDistance = routeSize;
    }

    /**
     * Determina si es salida
     * @return 
     */
    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    /**
     * Determina si es el último nodo de un camino
     * @return 
     */
    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * Cualquier comentario para el nodo
     * @return 
     */
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
    public void addChild(TreeRouteNode child){
        if(this.childs==null){
            this.childs = new ArrayList<>();
        }
        this.childs.add(child);
    }

    /**
     * Type EscapeAnalisysAlgorithm: WALL, PRISIONER, GUARD, etc.
     * @return 
     */
    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.position);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TreeRouteNode other = (TreeRouteNode) obj;
        return Objects.equals(this.position, other.position);
    }

    @Override
    public String toString() {
        return position.toString()+":"+type;
    }
    
    /**
     * Genera un string con la ruta
     * @return 
     */
    public String toStringRoute(){
        StringBuilder sb = new StringBuilder();
        TreeRouteNode n = this;
        do{
            sb.insert(0, ";");
            sb.insert(0, n.toString());
            
        }while((n=n.getParent())!=null);
        return sb.toString();
    }
    
    /**
     * Encuentra las rutas de escape contenidas en este arbol de rutas 
     * @return 
     */
    
    public List<TreeRouteNode> findEscapeRoutes(){
        List<TreeRouteNode> rutas = new ArrayList<>();
        findEscapeRoutes(this,rutas);
        return rutas;
    }
    
    /**
     * Busca las rutas de escape de forma recursiva en el nodo indicado n
     * @param n
     * @param salidas 
     */
    private void findEscapeRoutes(TreeRouteNode n, List<TreeRouteNode> salidas){
        
        if(n.isExit()){
            salidas.add(n);
        }
        
        if(n.getChilds()!=null && !n.getChilds().isEmpty()){
            n.getChilds().forEach(nch -> findEscapeRoutes(nch,salidas));
        }
    }
    
    
    /**
     * Busca la ruta mas corata
     * @return 
     */
    public TreeRouteNode findShorterEscapeRoute(){
        List<TreeRouteNode> escapeRoutes = findEscapeRoutes();
        
        if(escapeRoutes == null || escapeRoutes.isEmpty()){
            return null;
        }
        
        TreeRouteNode shorter = null;
        
        for(TreeRouteNode t : escapeRoutes){
            if(shorter == null || t.getRouteDistance() < shorter.getRouteDistance()){
                shorter = t;
            }
        }
        return shorter;
    }
    
    
}

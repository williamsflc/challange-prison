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
package com.challange.prison.ui;

import com.challange.prison.PrisonEscapeAlg;
import com.challange.prison.object.TreeRouteNode;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

/**
 *
 * @author Williams Lopez
 */
public class PrisonPane extends JComponent{

    PrisonElement[][] elements;
    
    public PrisonPane() {
        
    }
    
    public void setPrison(char[][] prison){
        
        this.removeAll();
        this.setLayout(new GridLayout(prison.length, prison[0].length));
        elements = new PrisonElement[prison.length][prison[0].length];
        
        for(int i=0;i<prison.length;i++){
            for(int j=0;j<prison[0].length;j++){
                elements[i][j] = new PrisonElement();
                elements[i][j].setType(prison[i][j]);
                this.add(elements[i][j]);
            }
        }
        
    }
    
    
    public void setTreeRoutes(TreeRouteNode tree,int prisonerResistance){
        
        List<TreeRouteNode> route = tree.findEscapeRoutes();

        //pintamos primero las rutas invalidas por resistencia
        route.stream().filter(r -> (r.isExit() && prisonerResistance < r.getRouteDistance())).forEachOrdered(r -> {
            TreeRouteNode n = r.getParent();
            do{
                if(n.getType()==PrisonEscapeAlg.ROUTE){
                    elements[n.getPosition().getY()][n.getPosition().getX()].setRouteState(PrisonElement.ROUTE_INVALID);
                }
            }while((n = n.getParent()) != null);
            
        });
        //pintamos luego la ruta valida (si hay)
        route.stream().filter(r -> (r.isExit() && prisonerResistance >= r.getRouteDistance())).forEachOrdered(r -> {
            TreeRouteNode n = r.getParent();
            do{
                if(n.getType()==PrisonEscapeAlg.ROUTE){
                  elements[n.getPosition().getY()][n.getPosition().getX()].setRouteState(PrisonElement.ROUTE_OPTIONAL);
                }
            }while((n = n.getParent()) != null);
        });
        
        //pintamos la mas corta
        TreeRouteNode n = tree.findShorterEscapeRoute();
        if(n != null){
            if(n.getRouteDistance()<= prisonerResistance){
                do{
                    if(n.getType()==PrisonEscapeAlg.ROUTE){
                        elements[n.getPosition().getY()][n.getPosition().getX()].setRouteState(PrisonElement.ROUTE_VALID);
                    }
                }while((n = n.getParent()) != null);
            }
        }
    }
    
    
}

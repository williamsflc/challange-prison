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

import com.challange.prison.PrisonApp;
import com.challange.prison.PrisonEscapeAlg;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Williams Lopez
 */
public class PrisonElement extends JLabel{

    public static final int ROUTE_VALID = 1;
    public static final int ROUTE_INVALID = 2;
    public static final int ROUTE_NORMAL = 3;
    public static final int ROUTE_OPTIONAL = 4;
    
    private int type;
    
    public PrisonElement() {
        
        setMinimumSize(new Dimension(25, 25));
        
    }
    
    
    
    public void setType(char type){
        this.type = type;
        String icon = "wall.png";
        switch(type){
            case PrisonEscapeAlg.WALL:        icon = "wall.png"; break;
            case PrisonEscapeAlg.PRISIONER:   icon = "prisoner.png"; break;
            case PrisonEscapeAlg.EXIT:        icon = "exit.png"; break;
            case PrisonEscapeAlg.GUARD_DOWN:  icon = "guard_down.png"; break;
            case PrisonEscapeAlg.GUARD_UP:    icon = "guard_up.png"; break;
            case PrisonEscapeAlg.GUARD_RIGHT: icon = "guard_right.png"; break;
            case PrisonEscapeAlg.GUARD_LEFT:  icon = "guard_left.png"; break;
            case PrisonEscapeAlg.ROUTE:       icon = "route.png"; break;
        }

        try {
            URL url = getClass().getResource("/img/"+icon);
            this.setIcon(new ImageIcon(url));
        } catch (Exception e) {
            PrisonApp.log(e.getMessage(), e);
        }
        
    }
    
    public void setRouteState(int state){
        String icon = "route_normal.png";
        switch(state){
            case ROUTE_VALID:     icon = "route_valid.png";   break;
            case ROUTE_INVALID:   icon = "route_invalid.png"; break;
            case ROUTE_NORMAL:    icon = "route.png";  break;
            case ROUTE_OPTIONAL:  icon = "route_optional.png";  break;
        }
        
        try {
            URL url = getClass().getResource("/img/"+icon);
            this.setIcon(null);
            this.setIcon(new ImageIcon(url));
        } catch (Exception e) {
            PrisonApp.log(e.getMessage(), e);
        }
        
    }
    
    
}

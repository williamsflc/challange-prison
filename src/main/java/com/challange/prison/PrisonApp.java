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
package com.challange.prison;

import com.challange.prison.object.PrisonAlgorithmException;
import com.challange.prison.ui.PrisonFrame;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 *
 * @author Williams Lopez
 */
public class PrisonApp {
    
    public static void main(String[] args) throws Exception{
        
        
        if(args.length < 2){
            executeUI();
        }else{
            executeCMD(args);
        }
                
        
        
        
        
    }

    private static void executeUI() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
                PrisonApp.log(ex.getMessage(), ex);
            }
            
            PrisonFrame frame = new PrisonFrame();
            frame.setTitle("Prison Challange UI - Frame");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setBackground(Color.WHITE);
            frame.setSize(800, 800);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

    private static void executeCMD(String[] args) throws IOException, PrisonAlgorithmException{
        Integer res = null;
        String def  = null;
        
        try{
            res  = Integer.parseInt(args[0]);
            def = new String(Files.readAllBytes(Paths.get(args[1])));
        }catch(IOException | NumberFormatException err){
            PrisonApp.log("Forma de ejecución: challange-prison.jar <resistencia prisionero> <archivo con def de carcel>, ej: challange-prison.jar 5 prision.txt", err);
            System.exit(0);
        }
        
        PrintWriter salida = new PrintWriter(System.out);
        
        PrisonEscapeAlg alg = new PrisonEscapeAlg(def, res);
        salida.println("Ejecute la opción deseada");
        salida.println("   1 = canEscape ");
        salida.println("   2 = shortestEscapeRoute ");
        salida.println(">> ");
        
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String opcion = buff.readLine();
        
        if("1".equals(opcion)){
            salida.println("Resultado canEscape: "+alg.canEscape());
        } else if("2".equals(opcion)){
            salida.println("Resultado shorterEscapeRoute: "+Arrays.toString(alg.shorterEscapeRoute()));
        }else{
            salida.println("Opcion incorrecta");
        }
    }

  
    
    public static void log(String message, Throwable err){
        if(err !=null){
            Logger.getLogger(PrisonApp.class.getName()).log(Level.SEVERE,message,err);
        }else{
            Logger.getLogger(PrisonApp.class.getName()).log(Level.INFO, message);
        }
    }
    
}

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
import com.challange.prison.object.PrisonAlgorithmException;
import java.awt.HeadlessException;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Williams Lopez
 */
public class PrisonFrame extends JFrame{
    
    private HelpFrame frame ;
    private ConfigFrame configFrame;
    private PrisonPane prisonPane;
    private PrisonEscapeAlg prisonAlg;
    private int prisonerResistance;

    public PrisonFrame() throws HeadlessException {
        init();
        prisonerResistance = 20;
    }
    
    
    private void init(){
        this.prisonPane = new PrisonPane();
        this.setContentPane(prisonPane);
        this.createMenu();
    }
    
    
    private void createMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        JMenuItem item;
        
        item = new JMenuItem("Cargar prision");
        item.addActionListener((e)->{ abrirPrision(); });
        menu.add(item);
        
        item = new JMenuItem("Ejecutar funcion: canEscape");
        item.addActionListener((e)->{ canEscapeUI(); });
        menu.add(item);
        
        item = new JMenuItem("Ejecutar funcion: shorterEscapeRoute");
        item.addActionListener((e)->{ shorterEscapeRouteUI(); });
        menu.add(item);
        
        configFrame = new ConfigFrame(this);
        item = new JMenuItem("Configurar resistencia de prisionero");
        item.addActionListener((e)->{ configFrame.showFrame(); });
        menu.add(item);
        
        frame = new HelpFrame();
        item = new JMenuItem("Ayuda");
        item.addActionListener((e)->{ frame.showFrame(); });
        menu.add(item);
        
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }
    
    
    
    
    private void abrirPrision() {
        
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
            fileChooser.setDialogTitle("Seleccionar archivo con definicion de prision");
            fileChooser.setApproveButtonText("Seleccionar prision");
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setCurrentDirectory(new File("test").getAbsoluteFile().getParentFile());

            int res = fileChooser.showOpenDialog(this);

            if(res != JFileChooser.APPROVE_OPTION){
                return;
            }

            File file = fileChooser.getSelectedFile();

            try {
                String data = new String(Files.readAllBytes(file.toPath()));
                prisonAlg = new PrisonEscapeAlg(data, prisonerResistance);
                prisonPane.setPrison(prisonAlg.getPrisonMatrix());
                prisonPane.setTreeRoutes(prisonAlg.getTreeRouteNode(), prisonerResistance);
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(this, "Validación del archivo: \n"+e.getMessage());
                System.out.println("Error al validar el archivo:"+e.getMessage());
            }
            
            

        
            this.setExtendedState(JFrame.NORMAL);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        
    }
    
    
    
    private void canEscapeUI() {
        try {
            JOptionPane.showMessageDialog(this, "canEscape: "+prisonAlg.canEscape());
        } catch (PrisonAlgorithmException | HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Error al ejecutar la funcion: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void shorterEscapeRouteUI() {
        try {
            JOptionPane.showMessageDialog(this, "shorterEscapeRoute: \n"+Arrays.toString(prisonAlg.shorterEscapeRoute()));
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Error al ejecutar la funcion: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getPrisonerResistance() {
        return prisonerResistance;
    }

    public void setPrisonerResistance(int prisonerResistance) {
        this.prisonerResistance = prisonerResistance;
        if(prisonAlg != null){
            prisonPane.setPrison(prisonAlg.getPrisonMatrix());
            prisonPane.setTreeRoutes(prisonAlg.getTreeRouteNode(), prisonerResistance);
            this.setExtendedState(JFrame.NORMAL);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }
    
    
    

    
    
}
